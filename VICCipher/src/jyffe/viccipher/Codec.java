/**
 * 
 */
package jyffe.viccipher;

/**
 * @author Jyffe
 *
 */

public class Codec {
	private ChainCalculator CC = new ChainCalculator();
	
	/**
	 * Generate U-block from key T by chain adding 50 iterations to key T and regrouping the resulting String of 50 characters 
	 * (the key T itself from beginning of the String is omitted) into 5 rows of 10 characters each.
	 * 
	 * TODO: Random placing of the start point indicator '^'
	 * TODO: Error handling
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
	 * Generates checkerboard which is used to encode the actual message. Creation of the checkerboard includes the following steps:
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
	 * > 7 B F J O R W Z ÷ ' &     <- place B, C, D, F, G, H, J, K, M, O, P, Q, R, U, V, W, X, Y, Z, ≈, ƒ and ÷ in the rows below from top to
	 * > 3 C G K P U X ≈ ∞ # !        down, left to right (A, E, I, L, N and S are in keyphrase and not used)
	 * > 4 D H M Q V Y ƒ . ^ ?     <- Fill remaining cells with necessary special characters, here ∞, ., ', #, ^,&, ! and ? are used
	 * </pre> 
	 * 
	 * TODO: What should be done if there are NO white spaces in the keyphrase and its length is the same as C?
	 * TODO: How numbers should be implemented in the checkerboard?
	 * TODO: Support for numbers?
	 * TODO: If the keyphrase contains same letter multiple times, only first one is included
	 * TODO: Implement version of method that works better with english language (or any other with less characters as in scandic languages)
	 *       i.e. one that fills the checkerboard from left to right and top to bottom
	 * 
	 * @param C				String representation of key C
	 * @param keyphrase		Keyphrase as a String
	 * @return				Checkerboard as Character[][] array
	 */
	public char[][] generateCheckerBoard(String C, String keyphrase){

		// Bugi 10.2.2015
		// Keyphrase ei saa olla liian pitk‰, muutoin kaikki merkit eiv‰t mahdu mukaan. Maksimi pituus n‰ill‰ merkeill‰ on 7.
		// Edelleen jos kyphrase sis‰lt‰‰ sman kirjaimen monta kertaa, niist‰ otetaan mukaan vain ensimm‰inen
		// Esim. KALOSSIT -> KALOSIT
		// Samoin allaoleva rivien m‰‰r‰n laskeminen pit‰‰ mietti‰ - jos on vakio merkit, niin rivej‰ pit‰‰ olla tietty m‰‰r‰ eli keyphrase
		// pit‰‰ olla vakiomittainen
		
		
		// Number of rows depend on number of white spaces in keyphrase
		int rows = C.length() - keyphrase.replaceAll("[^A-÷a-ˆ0-9]", "").length() + 2;
		int cols = C.length() + 1;

		keyphrase = keyphrase.toUpperCase();
		
		// [rows][columns]
		char[][] CB = new char[rows][cols];
		
		// What about the numbers?
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ≈ƒ÷∞.'#^&!?";
		
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
					col = cols; // Jump out
				}
			}
		}

		/*
		 * Find out which characters are used in the keyphrase and remove them from character set for the checker board
		 */
		for(int i = 0; i < keyphrase.length(); i++){
			if(characters.contains(String.valueOf(keyphrase.charAt(i)))){	// Tests lack here both in coverage (special characters) and length != 7 
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
		
		for(int col = 1; col < cols; col++){
			for(int row = 2; row < rows; row++){
				CB[row][col] = characters.charAt(n);
				n++;
			}
		}
		
		return CB;
	}
	
	/**
	 * 
	 * TODO: Support for numbers?
	 * 
	 * @param msg
	 * @param checkerboard
	 * @return
	 */
	public String replaceMessage(String msg, char[][] checkerboard){
		
		// Am I doing this right now?
		msg = msg.replaceAll("\\s", "");
		msg = msg.toUpperCase();
		
		for(int col = 1; col < checkerboard[0].length; col++){
			if(msg.contains(String.valueOf(checkerboard[1][col]))){
				msg = msg.replace(checkerboard[1][col], checkerboard[0][col]);
			}
		}
		
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
	
	public String unreplaceMessage(String msg, char[][] checkerboard){
	
		String tmp = "";
		
		/*
		 *  3598780843769380159619590996366590878780122939686569311069
		 * 	IKROFILMI ON KƒTKETTY? KERRO NAATIT. ^ MINNE M
		 * 
		 *  3 59 87 80 84 3 7 69 3 80 1 59 61 9590996366590878780122939686569311069
		 * 	I K  R  O  F  I L M  I O  N K  ƒ  TKETTY? KERRO NAATIT. ^ MINNE M
		 * 
		 * 	{' ', '2', '4', '9', '0', '7', '3', '1', '8', '5', '6'},
			{' ', 'A', 'S', 'T', 'E', 'L', 'I', 'N', ' ', ' ', ' '},
			{'8', 'B', 'F', 'J', 'O', 'R', 'W', 'Z', '÷', '\'', '&'},
			{'5', 'C', 'G', 'K', 'P', 'U', 'X', '≈', '∞', '#', '!'},
			{'6', 'D', 'H', 'M', 'Q', 'V', 'Y', 'ƒ', '.', '^', '?'}
		 */
		
		int cnt = 0;
		int col = 0, row = 0;
		boolean found = false;

		while(cnt < msg.length()){
			found = false;

			row = 0;
			col = 0;
			
			while(row < checkerboard.length && found == false){
				if(msg.charAt(cnt) == checkerboard[row][0]){
					while(col < checkerboard[row].length && found == false){
						if(msg.charAt(cnt+1) == checkerboard[0][col]){
							
							tmp += checkerboard[row][col];

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
	 * 
	 * TODO: Fill end of the message with random numbers instead of zero
	 * 
	 * @param msg
	 * @return
	 */
	public String patchMessage(String msg){
		for(int i = 0; i < msg.length()%5; i++){
			// Should be random numbers...
			msg += '0';
		}

		return msg;
	}

	public String unpatchMessage(String msg){
		
		// TODO: WHAT? HOW?
		
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

		// Find column identified by digit 1 of key K1, find column identified by digit 2 of key K1...
		for(int i = 1; i <= K1.length; i++){
			while(i != K1[col]){
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
	 * 
	 * 
	 * @param msg
	 * @param K1
	 * @return
	 */
	public String reverseFirstTransposition(String msg, int[] K1){
		
		int rows = 0;

		if(msg.length() % K1.length == 0){
			rows = msg.length() / K1.length;
		} else{
			rows = msg.length() / K1.length + 1;
		}
		
		int fullCols = msg.length() - (rows - 1) * K1.length;
		
		int col = 0;
		int lastRow = 0;
		int cnt = 0;
		
		char[][] table = new char[rows][K1.length];
		
		for(int i = 1; i <= K1.length; i++){
			while(i != K1[col]){
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

		
		
		msg = "";
		for(int row = 0; row < rows; row++){
			for(int i = 0; i < table[row].length; i++){

				//msg += table[row][i];
				
				if(row==lastRow){
					if(i < fullCols){
						msg += table[row][i];
					}
				}else{
					msg += table[row][i];
				}

			}
			
			// TODO
		
		}
				
		return msg;
	}
	
	/**
	 * Further reorders the message based on key K2. This time order is broken up further.
	 * 
	 * Write 'x' on each column under the K2 until column that has a '1' in K2 in it. Column with the '1' does not have a 'x' written.
	 * Do same for the following row but place on more 'x' and so forth. If cols/rows run out the same is repeated but by using digit '2'
	 * this time. Number of rows depends on the length of the message.
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
	 * THIS HAS A BUG SOMEWHERE - ' ' -chars above get included into the message (6 x ' ')           
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
		
		// Calculate number of full columns
		int fullCols = msg.length() - (rows - 1) * K2.length;

		int col = 0;
		int lastRow = 0;
		int cnt = 0;

		// Create a table rows x K2.length
		char[][] table = new char[rows][K2.length];
		
		int n = 0;
		for(int i = 1; i <= K2.length; i++){
			if(K2.length == 10 && i == 10){
				n = 0;
			}else{
				n = i;
			}
			
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
}
