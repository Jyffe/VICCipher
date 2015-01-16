/**
 * 
 */
package jyffe.viccipher;

/**
 * @author Jyffe
 *
 */
public class BlockChipher {

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
	 * > 7 B F J O R W Z Ö ' &     <- place B, C, D, F, G, H, J, K, M, O, P, Q, R, U, V, W, X, Y, Z, Å, Ä and Ö in the rows below from top to
	 * > 3 C G K P U X Å ° # !        down, left to right (A, E, I, L, N and S are in keyphrase and not used)
	 * > 4 D H M Q V Y Ä . ^ ?     <- Fill remaining cells with necessary special characters, here °, ., ', #, ^,&, ! and ? are used
	 * </pre> 
	 * 
	 * TODO: What should be done if there are NO white spaces in the keyphrase and its length is the same as C?
	 * TODO: How numbers should be implemented in the checkerboard?
	 * TODO: Support for numbers?
	 * TODO: If the keyphrase contains same letter multiple times, only first one is included
	 * 
	 * @param C				String representation of key C
	 * @param keyphrase		Keyphrase as a String
	 * @return				Checkerboard as Character[][] array
	 */
	public char[][] generateCheckerBoard(String C, String keyphrase){
		// Number of rows depend on number of white spaces in keyphrase
		int rows = C.length() - keyphrase.replaceAll("[^A-Öa-ö0-9]", "").length() + 2;
		int cols = C.length() + 1;

		keyphrase = keyphrase.toUpperCase();
		
		// [rows][columns]
		char[][] CB = new char[rows][cols];
		
		// What about the numbers?
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
					col = cols; // Jump out
				}
			}
		}

		/*
		 * Find out which characters are used in the keyphrase and remove them from character set for the checker board
		 */
		for(int i = 0; i < keyphrase.length(); i++){
			if(characters.contains(String.valueOf(keyphrase.charAt(i)))){	// Tests lack in this both in coverage (special characters) and length != 7 
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
	
	/**
	 * @param msg
	 * @param K1
	 * @return
	 */
	public String firstTransposition(String msg, String K1){
		
		int rows = 0;
		
		if(msg.length() % K1.length() == 0){
			rows = msg.length() / K1.length();
		} else{
			rows = msg.length() / K1.length() + 1;
		}
		
		String[] table = new String[rows];
		
		for(int row = 0; row < rows; row++){
			table[row] = msg.substring(row * 10, row * 10 + K1.length());
		}
		
		
		
		return msg;
	}
	
	/**
	 * TODO: remove this from this class - this is wrapper i.e. VICCipher stuff
	 * TODO: 
	 * 
	 * @param msg
	 * @param checkerboard
	 * @return
	 */
	public String encode(String msg, char[][] checkerboard, String K1){
		
		// Am I doing this right now?
		/*
		msg = msg.replaceAll("\\s", "");
		
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
		}*/
		
		msg = replaceMessage(msg, checkerboard);

		/*
		for(int i = 0; i < msg.length()%5; i++){
			// Should be random numbers...
			msg += '0';
		}
		*/
		
		msg = patchMessage(msg);
		
		// First transposition...
		
		
		return msg;
	}
}
