/**
 * 
 */
package jyffe.viccipher;

import java.util.HashMap;

/**
 * @author Jyffe
 * 
 * Codec -class contains functionality needed for encoding and decoding VIC ciphered messages
 *
 */

public class Codec {
	private ChainCalculator CC = new ChainCalculator();
	
	/**
	 * Generate U-block from key T by chain adding 50 iterations to key T and regrouping the resulting String of 50 characters 
	 * (the key T itself from beginning of the String is omitted) into 5 rows of 10 characters each.
	 * 
	 * @param T		String representation of key T
	 * @return		U-block as array of String[5]
	 */
	public String[] generateUBlock(String T){
		
		String ub = CC.add(T, 50);
		String[] UB = new String[5];
		
		// First 10 characters (the key T) are not included into the U-block
		int bIndex = 10, eIndex = 20;
		
		for(int i = 0; i < UB.length; i++){
			UB[i] = ub.substring(bIndex, eIndex);
			bIndex += 10;
			eIndex += 10;
		}
		
		return UB;
	}

	/**
	 * Generates a straddling checkerboard which is used to encode the actual message. Creation of the checkerboard includes the following 
	 * steps:
	 * <pre>
	 * 1) Place the key C in the top row
	 * 2) Place the keyphrase in the second row, if the keyphrase contains same letters multiple times, only the first letter is included
	 * 3) Number the rows below
	 * 4) Place the remaining alphabets (that are not included in the keyphrase) in alphabetical order from up to down, left to right
	 * 5) Fill remain cells with necessary special characters
	 * 
	 * E.g.             
	 *                   v v v
	 *     2 9 6 0 5 8 1 7 3 4     <- Key C, last three numbers (that have no corresponding letter in keyphrase) are used to number the rows
	 *     A S T E L I N           <- keyphrase
	 * > 7 B F J O R W Z Ö ' &     <- place B, C, D, F, G, H, J, K, M, O, P, Q, R, U, V, W, X, Y, Z, Å, Ä and Ö in the rows below from top to
	 * > 3 C G K P U X Å ° # !        down, left to right (A, E, I, L, N and S are in keyphrase and not used)
	 * > 4 D H M Q V Y Ä . ^ ?     <- Fill remaining cells with necessary special characters, here °, ., ', #, ^,&, ! and ? are used
	 * </pre> 
	 * 
	 * TODO: What to do if keyphrase contains characters not in the charset?
	 * 
	 * @param C				String representation of key C
	 * @param keyphrase		Keyphrase as a String, 1 - 7 characters long
	 * @return				Straddling checkerboard as char[][] array
	 */
	public char[][] generateCheckerBoard(String C, String keyphrase){

		char[] array = keyphrase.toCharArray();
		char ch;
		int cnt = 0;
		
		HashMap<Character, Integer> hMap = new HashMap<Character, Integer>();

		// Remove duplicate characters, if any
		for(int i = 0; i < keyphrase.length(); i++){
	        ch = array[i];
	        if(hMap.get(ch) == null){
	            array[cnt++] = ch;
	            hMap.put(ch, 1);
	        }
	    }
		
	    keyphrase = new String(array, 0, cnt);
		
		// Number of rows depend on number of spaces in the keyphrase
	    int rows = C.length() - keyphrase.replace(" ", "").length() + 2; // No need to remove spaces, just count them
		int cols = C.length() + 1;

		// To all uppercase
		keyphrase = keyphrase.toUpperCase();
		
		// [rows][columns]
		char[][] CB = new char[rows][cols];
		
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ°.'#^&!?";
		
		// Initialize the CB with ' '
		for (int row = 0; row < rows; row++){
			for (int col = 0; col < cols; col++){
				CB[row][col] = ' ';
			}
		}
		
		/*
		 * Initialize the first row:
		 * Write key C in the first row starting from [0][1]
		 */
		for (int col = 1; col <= C.length(); col++){
			CB[0][col] = C.charAt(col - 1);
		}
		
		/*
		 * Initialize the second row:
		 * Write keyphrase in the second row starting from [1][1]
		 */
		for (int col = 1; col <= keyphrase.length(); col++){
			CB[1][col] = keyphrase.charAt(col - 1);
		}
		
		/*
		 * Label rows with the remaining numbers in row 1 (key C) that have no letter in row 2 (keyphrase) in the same position (col) 
		 */
		int pos = 1;
		
		for(int row = 2; row < rows; row++ ){
			for(int col = pos; col < cols; col++){
				if(CB[1][col] == ' '){
					CB[row][0] = CB[0][col];
					pos = col+1;
			
					break;
				}
			}
		}

		/*
		 * Find out which characters are used in the keyphrase and remove them from character set for the checker board
		 */
		for(int i = 0; i < keyphrase.length(); i++){
			if(characters.contains(String.valueOf(keyphrase.charAt(i)))){ 
				characters = characters.replace(keyphrase.charAt(i), ' ');
				characters = characters.replaceAll("\\s+", "");
			}else{
				
			}
		}

		/*
		 * Populate the checker board
		 * 
		 * Note! There seems to be two variants of this around there... another one populates the table row by row from left to right.
		 * This one goes from up to down, left to right.
		 */
		int n = 0;
		
		// Start from column 1 i.e. from the first 'content' column
		int col = 1;

		while(col < cols){
			// Start from row 2 i.e. after the 'header' rows
			int row = 2;
		
			// The table can have more slots than characters to use for population in which case there will remain number of empty cells.
			while(row < rows && n < characters.length()){
				CB[row][col] = characters.charAt(n);
				
				n++;
				row++;
			}

			col++;
		}
		
		return CB;
	}
	
	/**
	 * Replaces characters of the messages with numerical encoding by using the straddling checkerboard. The checkerboard contains two
	 * characters with a specific meaning: '^' is used to indicate where from the message starts (message is broken in two and parts 
	 * are sifted around to complicate any attempts to crack the code) and it is encoded normally. '#' is used to indicate numbers and 
	 * it is also encoded normally but the plain text message is not allowed to contain any '#' characters. Encoding contains the following 
	 * steps:
	 * <pre>
	 * 1. Remove all spaces
	 * 2. Convert all the characters to uppercase
	 * 3. Encode numbers
	 * 4. Encode character that are included in the keyphrase (row 2 of the checkerboard)
	 * 5. Encode rest of the characters
	 * 
	 * Numbers are encoded by adding character '#' -character in front of them and encoding '#' normally. Numbers themselves are kept intact.
	 * If character is found from keyphrase (second row of the checkerboard) they are encoded with single digit taken from same column in row
	 * above. Else the character gets a two digit value by selecting first a row number from the leftmost column and then a column number
	 * from topmost row.
	 * 
	 * E.g.
	 * 
	 * Straddling checkerboard:
	 * 
	 *     2 4 9 0 7 3 1 8 5 6
	 *     A S T E L I N 
	 *   8 B F J O R W Z Ö ' &
	 *   5 C G K P U X Å ° # !
	 *   6 D H M Q V Y Ä . ^ ?
	 *   
	 * Message: "code is 1234 ^ The secret"
	 *  -> codeis1234^Thesecret                 Remove spaces
	 *  -> CODEIS1234^THESECRET                 Convert to uppercase
	 *  -> CODE#1#2#3#4^THESECRET               Mark numbers with #
	 *  -> CODE551552553554^THESECRET           Encode numbers
	 *  -> COD0551552553554^9H040CR09           Encode characters found in keyphrase
	 *  -> 528062055155255355465964040528709    Encode rest of the characters
	 * </pre>
	 * 
	 * @param msg 			The plain text message to be encoded
	 * @param checkerboard 	The straddling checkerboard used for the encoding
	 * @return				Message encoded in numeric codes 				
	 */
	public String encodeMessage(String msg, char[][] checkerboard){
		
		msg = msg.replaceAll(" ", "");
		msg = msg.toUpperCase();
		
		String hashcode = null;
		// Check what is the code for the #-character and store it
		for(int row = 2; row < checkerboard.length; row++){
			for(int col = 1; col < checkerboard[0].length; col++){
				if(checkerboard[row][col] == '#'){
					hashcode = String.valueOf(checkerboard[row][0]) + String.valueOf(checkerboard[0][col]); 
				}
			}
		}

		// Encode numbers by using the '#' -character
		int n = 0;
		String tmp = "";
		
		while(n < msg.length()){
			if(msg.charAt(n) >= '0' && msg.charAt(n) <= '9'){
				tmp += hashcode;
				tmp += msg.charAt(n);
			}else{
				tmp += msg.charAt(n);
			}
			
			n++;
		}
		msg = tmp;

		// Replace all characters with a single digit code if found from the keyphrase
		for(int col = 1; col < checkerboard[0].length; col++){
			if(msg.contains(String.valueOf(checkerboard[1][col]))){
				msg = msg.replace(checkerboard[1][col], checkerboard[0][col]);
			}
		}
		
		// Replace all the remaining characters with double digit code
		String regexp = null;
		
		for(int row = 2; row < checkerboard.length; row++){
			for(int col = 1; col < checkerboard[0].length; col++){
				if(msg.contains(String.valueOf(checkerboard[row][col]))){
					if(checkerboard[row][col] == '^'){
						regexp = "\\^";
					} else if(checkerboard[row][col] == '.'){
						regexp = "\\.";
					} else if(checkerboard[row][col] == '?'){
						regexp = "\\?";
					} else if(checkerboard[row][col] == '!'){
						regexp = "\\!";
					}
					else {
						regexp = String.valueOf(checkerboard[row][col]);
					}
					msg = msg.replaceAll(regexp, String.valueOf(checkerboard[row][0] + String.valueOf(checkerboard[0][col])));
				}
			}
		}

		return msg;
	}
	
	/**
	 * Decodes the message by replacing single or double digit encoding with characters from the checkerboard. Decoding contains the
	 * following steps:
	 * <pre>
	 * 1. Check if the number can be found from the first column of the checkerboard
	 * 2. If the character was in the first column it means that also the second number is part of the same coding
	 * 3. If double digit encoding, search the second number from the first row of the checkerboard
	 * 4. Read the character from [row][col]
	 * 5. If the read character was '#' it indicates a number, read the next number from the encoded message and add it to decoded message
	 *    String
	 * 6. Else add the character from [row][col] to decoded message String
	 * 7. If the character was not found from the first column of the checkerboard it has a single digit encoding and correct character is read 
	 *    from the second row of the checkerboard under corresponding number in the first row and added to decoded message String
	 *    
	 * E.g.
	 * 
	 * Encoded message: 528062055155255355465964040528709
	 * 
	 * Straddling checkerboard:
	 * 
	 *     2 4 9 0 7 3 1 8 5 6
	 *     A S T E L I N 
	 *   8 B F J O R W Z Ö ' &
	 *   5 C G K P U X Å ° # !
	 *   6 D H M Q V Y Ä . ^ ?
	 * 
	 * -> 52 80 62 0 55 1 55 2 55 3 55 4 65 9 64 0 4 0 52 87 0 9
	 * -> C  O  D  E #  1 #  2 #  3 #  4 ^  T H  E S E C  R  E T
	 * 
	 * </pre>
	 * 
	 * @param msg				Encoded message
	 * @param checkerboard		The straddling checkerboard used for the encoding
	 * @return					Decoded plain text message
	 */
	public String decodeMessage(String msg, char[][] checkerboard){
	
		String tmp = "";
		
		int cnt = 0;
		int col = 0, row = 0;
		boolean found = false;

		while(cnt < msg.length()){
			found = false;

			row = 0;
			col = 0;
			
			// Check if the digit can be found from the first column of the checkerboard
			while(row < checkerboard.length && found == false){
				// If it is found, it means that also the next digit is part of the coding for a single character
				if(msg.charAt(cnt) == checkerboard[row][0]){
					// Find the column with the second digit
					while(col < checkerboard[row].length && found == false){
						// And one found place the character in [row][col] in the temp String
						if(msg.charAt(cnt+1) == checkerboard[0][col]){
							// If the character is # it means that it needs to be discarded and the next character (a number) will be added
							// to temp String
							if(checkerboard[row][col] == '#'){
								tmp += msg.charAt(cnt+2);
								cnt++;
							}else{
								tmp += checkerboard[row][col];
							}

							found = true;
							cnt++;
						}
						col++;
					}

				}
				row++;
			}
			
			row = 0;
			col = 0;

			// Decode rest of the characters by checking the single digit valuye from row 0 of the checkerboard
			while(col < checkerboard[0].length && found == false){
				if(msg.charAt(cnt) == checkerboard[0][col]){
					
					tmp += checkerboard[1][col];
					
					found = true;
				}
				
				col++;
			}
			cnt++;
		}
	
		return tmp;
	}
	
	/**
	 *  Patches the given message with '0' so that the length of the message is divisible by 5
	 *  
	 * @param msg	Encoded message
	 * @return		Patched message
	 */
	public String patchMessage(String msg){
		for(int i = 0; i < msg.length()%5; i++){

			msg += '0';
		}

		return msg;
	}

	/**
	 * TODO: This is not possible without modifying the entire algorithm scheme somehow?
	 * 
	 * @param msg
	 * @return
	 */
	public String unpatchMessage(String msg){
		
		return msg;
	}
	
	/**
	 * Reordrers the message based on the key K1.
	 * 
	 * Place the message in rows with length equal to number of digits of the key K1, last row can be incomplete: 
	 * 
	 * Msg	69678926408001327895271562009694671561611784816960
	 * 
	 * K1  8 7 9 1 5 2 3 10 11 6 4
	 * ---------------------------
	 * 1   6 9 6 7 8 9 2 6  4  0 8
	 * 2   0 0 1 3 2 7 8 9  5  2 7
	 * 3   1 5 6 2 0 0 9 6  9  4 6
	 * 4   7 1 5 6 1 6 1 1  7  8 4
	 * 5   8 1 6 9 6 0
	 * 
	 * Reorder the message by reading first column indicated by digit 1 in the key K1, then by digit 2 and so on:
	 * -> 73269 97060 2891 8764 82016 0248 90511 60178 61656 6961 4597
	 * -> 73269970602891876482016024890511601786165669614597
	 * 
	 * @param msg 	Message for first transposition encoding
	 * @param K1 	Key K1 as an int array
	 * @return 		Encoded message
	 */
	public String firstTransposition(String msg, int[] K1){
		int rows = 0;

		// Calculate number of rows needed for the table
		if(msg.length() % K1.length == 0){
			rows = msg.length() / K1.length; // If message length is dividable by length of K1
		} else{
			rows = msg.length() / K1.length + 1; // If message length is not dividable by length of K1 one additional row is needed
		}

		String[] table = new String[rows];

		// Divide up the message in rows
		for(int row = 0; row < rows; row++){
			if(row * K1.length + K1.length < msg.length()){
				table[row] = msg.substring(row * K1.length, row * K1.length + K1.length); // Substrings equal to length of K1
			}else {
				table[row] = msg.substring(row * K1.length, msg.length()); // Last row may be shorter than K1
			}
		}
		
		msg = ""; // Empty the message before appending anything into it

		int col = 0;
		int nbr = 0;

		// Find column identified by digit 1 of key K1, find column identified by digit 2 of key K1...
		for(int i = 1; i <= K1.length; i++){
			
			// Bug fix 24.2.2014
			// If K1.length is exactly 10, the number 10 is presented as a 0 instead
			if(K1.length == 10 && i == 10){
				nbr = 0;
			}else{
				nbr = i;
			}
			
			while(nbr != K1[col]){
				col++;
			}
			
			// Add the matching column to the msg
			for(int row = 0; row < rows; row++){
				if(col < table[row].length()){
					msg += table[row].charAt(col);	
				}
			}

			// Start again with the next digit
			col = 0;
		}

		return msg;
	}

	/**
	 * Reverses the first transposition of the message
	 * 
	 * TODO: Javadoc documentation
	 * 
	 * @param msg
	 * @param K1
	 * @return
	 */
	public String reverseFirstTransposition(String msg, int[] K1){
		
		int rows = 0;

		// Calculate number of rows needed for the table
		if(msg.length() % K1.length == 0){
			rows = msg.length() / K1.length; // If message length is dividable by length of K1
		} else{
			rows = msg.length() / K1.length + 1; // If message length is not dividable by length of K1 one additional row is needed
		}
		
		// Calculate number of columns that will become full
		int fullCols = msg.length() - (rows - 1) * K1.length;
		
		int lastRow = 0;
		int cnt = 0;
		int nbr = 0;
		
		char[][] table = new char[rows][K1.length];
		
		// Populate the table column by column starting from 1
		for(int i = 1; i <= K1.length; i++){
			int col = 0;
			
			// Bug fix 24.2.2014
			// If K1.length is exactly 10, the number 10 is presented as a 0 instead
			if(K1.length == 10 && i == 10){
				nbr = 0;
			}else{
				nbr = i;
			}
						
			// Find the column with the number in question
			while(nbr != K1[col]){
				col++;
			}
			
			// Check how many rows will be used for the column in question (not all columns become full)
			if(col < fullCols){
				lastRow = rows;
			}else{
				lastRow = rows - 1;
			}
			
			// Write characters from the message to the column in question
			for(int row = 0; row < lastRow; row++){
				table[row][col] = msg.charAt(cnt);
				cnt++;
			}
		}

		// Empty the message before using it again
		msg = "";
		
		// Reorder the message by reading characters from the table row by row
		for(int row = 0; row < rows; row++){
			for(int i = 0; i < table[row].length; i++){

				// Omit empty cells from the last row
				if(row == table.length - 1){
					if(i < fullCols){
						msg += table[row][i];
					}
				}else{
					msg += table[row][i];
				}
			}
		}
				
		return msg;
	}
	
	/**
	 * Further reorders the message based on key K2. This time order is broken up further.
	 * 
	 * Write 'x' on each column under the K2 until column that has a '1' in K2 in it. Column with the '1' does not have a 'x' written.
	 * Do same for the following row but place on more 'x' and so forth. If cols/rows run out the same is repeated but by using digit '2'
	 * this time. Number of rows depends on the length of the message. An example:
	 *  
	 * msg 73269970602891876482016024890511601786165669614597  
	 * 
	 * K2   6 11 9 1 14 2 12 7 3 13 10 5 4 8
	 * -------------------------------------
	 * 1    x x  x
	 * 2    x x  x x
	 * 3    x x  x x x
	 * 4    x x  x x x  x
	 * 
	 * Replace characters 'x' with numbers from the message from left to right and from up to down.
	 * 
	 * K2   6 11 9 1 14 2 12 7 3 13 10 5 4 8
	 * -------------------------------------
	 * 1    7 3  2
	 * 2    6 9  9 7
	 * 3    0 6  0 2 8
	 * 4    9 1  8 7 6  4
	 * 
	 * Place the remaining numbers of the message to empty cells of the table starting from top row and left to right.
	 * 
	 * 82016024890511601786165669614597
	 * 
	 * K2   6 11 9 1 14 2 12 7 3 13 10 5 4 8
	 * -------------------------------------
	 * 1    7 3  2 8 2  0 1  6 0 2  4  8 9 0
	 * 2    6 9  9 7 5  1 1  6 0 1  7  8 6 1
	 * 3    0 6  0 2 8  6 5  6 6 9  6  1 4 5
	 * 4    9 1  8 7 6  4 9  7
	 * 
	 * Reorder the message by reading first column indicated by digit 1 in the key K2, then by digit 2 and so on:
	 * -> 8727 0164 006 964 881 7609 6667 015 2908 476 3961 1159 219 2586
	 * -> 87270164006964881760966670152908476396111592192586
	 * 
	 * @param msg	Message for second transposition encoding
	 * @param K2	Key K2 as an int array
	 * @return		Encoded message
	 * 
	 * TODO: Would use some refactoring... not very pretty code
	 */
	public String secondTransposition(String msg, int[] K2){
		int rows = 0;

		// Calculate number of rows needed for the table
		if(msg.length() % K2.length == 0){
			rows = msg.length() / K2.length; // If message length is dividable by length of K2
		} else{
			rows = msg.length() / K2.length + 1; // If message length is not dividable by length of K2 one additional row is needed
		}

		// Create and initialize the table
		String[] table = new String[rows];

		for(int i = 0; i < rows; i++){
			table[i] = "";
		}
		
		// TODO: This also lacks situation where cols run out and next row starts from 2 and so on...
		int stop = 0;
		int row = 0;
		
		while(K2[stop] != 1){
			stop++;
		}
		
		while(row < rows){
			for(int col = 0; col < stop; col++){
				table[row] += "x";
			}
			row++;
			stop++;
		}
		
		int count = 0;
		for(int i = 0; i < table.length; i++){
			while(table[i].contains("x")){
				table[i] = table[i].replaceFirst("x", String.valueOf(msg.charAt(count)));
				count++;
			}
		}
		
		for(int i = 0; i < table.length; i++){
			for(int j = table[i].length(); j < K2.length; j++){
				table[i] += " ";
			}
		}
		
		for(int i = 0; i < table.length; i++){
			while(table[i].contains(" ") && count < msg.length()){
				table[i] = table[i].replaceFirst(" ", String.valueOf(msg.charAt(count)));
				count++;
			}
		}
		
		int col = 0;
		msg = "";
		
		for(int i = 1; i <= K2.length; i++){
			while(i != K2[col]){
				col++;
			}
			
			for(int roww = 0; roww < rows; roww++){
				if(col < table[roww].length()){
					msg += table[roww].charAt(col);	
				}
				
			}
			
			col = 0;
		}

		msg = msg.replaceAll("\\s", "");
		
		return msg;
	}
	
	/**
	 * Msg 87270164006964881760966670152908476396111592192586
	 * 
	 * 1.   2.   3.  4.  5.  6.   7.   8.  9.   10. 11.  12.  13. 14.
	 * 8727 0164 006 964 881 7609 6667 015 2908 476 3961 1159 219 2586	// Note that columns that will become full needs to be calculated
	 * 
	 *      0 1  2 3 4  5 6  7 8 9  10 111213 
	 * K2   6 11 9 1 14 2 12 7 3 13 10 5 4 8
	 * -------------------------------------
	 * 1    7 3  2 8 2  0 1  6 0 2  4  8 9 0
	 * 2    6 9  9 7 5  1 1  6 0 1  7  8 6 1
	 * 3    0 6  0 2 8  6 5  6 6 9  6  1 4 5
	 * 4    9 1  8 7 6  4 9  7
	 * 
	 *
	 *            
	 *                        
	 * TODO: THIS HAS A BUG SOMEWHERE - ' ' -chars above get included into the message (6 x ' ')           
	 * 
	 * @param msg	Message for second transposition decoding
	 * @param K2	Key K2 as an int array
	 * @return		Encoded message
	 * 
	 */
	public String reverseSecondTransposition(String msg, int[] K2){
		int rows = 0;
		
		// Calculate number of rows needed for the table
		if(msg.length() % K2.length == 0){
			rows = msg.length() / K2.length;
		} else{
			rows = msg.length() / K2.length + 1;
		}

		// Calculate number of full columns i.e. number of columns that have a number in all of the rows
		int fullCols = msg.length() - (rows - 1) * K2.length;
		
		int col = 0;
		int lastRow = 0;
		int cnt = 0;

		// Create a table rows x K2.length
		char[][] table = new char[rows][K2.length];
		
		int n = 0;
		for(int i = 1; i <= K2.length; i++){
			// If K2.length() is exactly 10, the number 10 is coded as 0. If length is > 10, the count goes normally.
			if(K2.length == 10 && i == 10){
				n = 0;
			}else{
				n = i;
			}
			
			// Search for the matching number/column, columns are read in numerical order starting from 1
			while(n != K2[col]){
				col++;
			}
			
			if(col < fullCols){
				lastRow = rows;
			}else{
				lastRow = rows - 1;
			}
			
			for(int row = 0; row < lastRow; row++){
				table[row][col] = msg.charAt(cnt);
				cnt++;
			}
			
			col = 0;
		}

		cnt = 0;
		
		while(1 != K2[cnt]){
			cnt++;
		}
		
		msg = "";
		String tmp = "";
		
		for(int row = 0; row < rows; row++){
			for(int i = 0; i < cnt; i++){
				msg += table[row][i];
			}
			
			for(int i = cnt; i < table[row].length; i++){
				
				if(i < fullCols){
					lastRow = rows;
				}else{
					lastRow = rows - 1;
				}
				
				if(row==lastRow){
					if(i < fullCols){
						tmp += table[row][i];
					}
				}else{
					tmp += table[row][i];
				}
					
			}
		
			cnt++;
		}
		
		msg += tmp;
		
		return msg;
	}
	
	/**
	 * @param msg
	 * @param RI
	 * @param date
	 * @return
	 * 
	 * TODO: How about using java.Date ?
	 * TODO: Handling of last number of the date if it is 1 or 0 (both indicate the last block)
	 * TODO: What if the number is bigger than number of blocks?
	 */
	public String embedRandomIndicator(String msg, String RI, String date){
		int n = Integer.parseInt(String.valueOf(date.charAt(date.length()-1)));

		// bug fix 10.2.2015
		// zero not taken into account, 0 = 1 = the first block
		if(n == 0){
			n = 1;
		}

		String temp = msg.substring(0, msg.length() - ((n - 1) * 5));
		temp += RI;
		temp += msg.substring(msg.length() - ((n - 1) * 5), msg.length());
		
		return temp;
	}
	
	/**
	 * @param msg
	 * @param date
	 * @return
	 */
	public String getRandomIndicator(String msg, String date){
		int n = Integer.parseInt(String.valueOf(date.charAt(date.length()-1)));
		
		// bug fix 10.2.2015
		// zero not taken into account, 0 = 1 = the first block
		if(n == 0){
			n = 1;
		}
		
		String RI = msg.substring(msg.length() - (n * 5), msg.length() - (n * 5) + 5);
		
		return RI;
	}
	
	/**
	 * @param msg
	 * @param date
	 * @return
	 */
	public String removeRandomIndicator(String msg, String date){
		int n = Integer.parseInt(String.valueOf(date.charAt(date.length()-1)));
		
		// bug fix 10.2.2015
		// zero not taken into account, 0 = 1 = the first block
		if(n == 0){
			n = 1;
		}
				
		String temp = msg.substring(0, msg.length() - (n * 5));
		temp += msg.substring(msg.length() - (n * 5) + 5, msg.length());

		return temp;
	}
	
	/**
	 * TODO: Random placing of the start point indicator '^'
	 * 
	 * @param msg
	 * @return
	 */
	public String breakMessage(String msg){
		
		return msg;
	}
	
	/**
	 * @param msg
	 * @return
	 */
	public String combineMessage(String msg){
		
		return msg;
	}
}
