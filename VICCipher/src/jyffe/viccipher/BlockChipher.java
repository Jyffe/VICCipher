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
	private Sequencer Sequencer = new Sequencer();
	
	/**
	 * Generate U-block from key T by chain adding 50 iterations to key T and regrouping the resulting String of 50 characters 
	 * (the key T itself from beginning of the String is omitted) into 5 rows of 10 characters each.
	 * 
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
	 * ...
	 * 
	 * TODO: What should be done if there are NO white spaces in the keyphrase and its length is the same as C?
	 * TODO: How numbers should be implemented in the checkerboard?
	 * 
	 * @param C				String representation of key C
	 * @param keyphrase		Keyphrase as a String
	 * @return				Checkerboard as Character[][] array
	 */
	public char[][] generateCheckerBoard(String C, String keyphrase){
		// Number of rows depend on number of white spaces in keyphrase
		int rows = C.length() - keyphrase.replaceAll("[^A-Öa-ö0-9]", "").length() + 2;
		int cols = C.length() + 1;

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
		 * Wtite keyphrase in the second row starting from [1][1]
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
		 * Find out which charactes are used in the keyphrase and remove them from character set for the checker board
		 */
		for(int i = 0; i < keyphrase.length(); i++){
			if(characters.contains(String.valueOf(keyphrase.charAt(i)))){
				characters = characters.replace(keyphrase.charAt(i), ' ');
				characters = characters.replaceAll("\\s+", "");
			}
		}
		
		/*
		 * Populate the checker board
		 * 
		 * Note! There seems to be two variants of this around there... another one populates the table row by row from left to right
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
}
