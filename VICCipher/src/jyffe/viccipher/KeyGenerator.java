/**
 * 
 */
package jyffe.viccipher;

/**
 * @author Jyffe
 * 
 * Used to generate various keys necessary for VIC-cipher encoding and decoding
 */
/**
 * @author Jyffe
 *
 */
public class KeyGenerator {

	private Sequencer Sequencer = new Sequencer();
	private ChainCalculator CC = new ChainCalculator();
	private Mod10Calculator Mod10 = new Mod10Calculator();
	
	/**
	 * Generates key S by sequencing String s
	 * <br>
	 * <br>
	 * <b>Known issues</b>
	 * <br>
	 * 14.1.2015: Length check is not working properly as it checks length of the String including white spaces and special characters whereas 
	 * number of alphanumeric characters is significant as the String is passed to Sequencer.sequence() method. As a workaround, Remove any 
	 * white spaces and special characters from the String s before passing it for the method. 
	 * 	 * 
	 * @param s				String seed for generating the key S. Note that s.length() must be exactly 10.
	 * @return				String representation of key S
	 * @throws Exception	Thrown if s.length() != 10. Exception message contains reason for the exception.
	 */
	public String generateKeyS(String s) throws Exception {

		s = Sequencer.sequence(s);
		
		// Bug 14.1.2015: Only number of alphanumeric characters is meaningful, not length of the entire String
		if(s.length() < 10){
			
			throw new Exception("KeyGenerator.generateKeyS() : s.length() < 10");
			
		} else if(s.length() > 10){
			
			throw new Exception("KeyGenerator.generateKeyS() : s.length() > 10");
		}
		
		return s;
	}
	
	/**
	 * Generates key G from date, RI and S1 by using mod10 subtraction, chain addition and mod10 addition.
	 * 
	 * <pre>
	 * TODO:	Check whether date string is valid
	 * TODO:	Works only with time format of dd.mm.yyy, implement something nicer
	 * TODO:	Removal of date separators assumes that separator is always a '.'
	 * </pre>
	 * 
	 * @param date			String representation of a date as a seed for generating key G. Date must be in format dd.mm.yyyy.
	 * @param RI			Random Indicator String. RI.length() must be 5.
	 * @param S1			String representation of the key S1. S1.length() must be 10.
	 * @return				String representation of key G.
	 * @throws Exception	Thrown if any of the given seed parameters are not valid. Exception message contains reason for the exception.
	 */
	public String generateKeyG(String date, String RI, String S1) throws Exception {
		
		String G = null;
		
		if(RI.length() < 5){
			
			throw new Exception("Sequencer.generateKeyG() : RI.length() < 5");
			
		}else if (RI.length() > 5){
			
			throw new Exception("Sequencer.generateKeyG() : RI.length() > 5");
		}
		
		if(S1.length() < 10){
			
			throw new Exception("Sequencer.generateKeyG() : S1.length() < 10");
			
		}else if (S1.length() > 10){
			
			throw new Exception("Sequencer.generateKeyG() : S1.length() > 10");
		}
		
		// Remove separators from the date String, assumes that '.' is always used...
		date = date.replaceAll("\\.+", "");

		// Use and RI five first numbers of the date String for mod10 subtraction to get first iteration of the key G
		G = Mod10.subtract(RI, date.substring(0, 5));
		
		// Chain add 5 iterations to get second iteration of the key G
		G = CC.add(G, 5);
		
		// Use second iteration of the key G and key S1 to get third and final iteration of the key G
		G = Mod10.add(G, S1);
		
		return G;
	}
	
	/**
	 * Generate key T from key G and key S2 by replacing numbers of the key G based on the following scheme:
	 * <pre>
	 * S2                1674205839   Search '6' of G from row above, replace that with number of S2 in the same column i.e. '0' and place it 
	 * Numbers 1 - 0     1234567890   as first number of the T. Search '5' of G from tow above, replace that with number of S2 in the same column
	 * G                 6551517891   i.e. '2' and place it as second number of the T. And so forth...
	 * ----------------------------
	 * T                 0221215831           
	 * </pre>
	 * TODO: Error handling via exceptions
	 * 
	 * @param G		String representation of key G
	 * @param S2	String representation of key S2
	 * @return		String representation of T
	 */
	public String generateKeyT(String G, String S2){
		char[] T = G.toCharArray();
		
		for(int i = 0; i < T.length; i++){
			switch(T[i]){
			case '1':
				T[i] = S2.charAt(0);
				break;
			case '2':
				T[i] = S2.charAt(1);
				break;
			case '3':
				T[i] = S2.charAt(2);
				break;
			case '4':
				T[i] = S2.charAt(3);
				break;
			case '5':
				T[i] = S2.charAt(4);
				break;
			case '6':
				T[i] = S2.charAt(5);
				break;
			case '7':
				T[i] = S2.charAt(6);
				break;
			case '8':
				T[i] = S2.charAt(7);
				break;
			case '9':
				T[i] = S2.charAt(8);
				break;
			case '0':
				T[i] = S2.charAt(9);
				break;
			default:
				return "-1";
			}
		}
		
		return String.valueOf(T);
	}
	
	/**
	 * Generates length for the first transposition. Length equals to first number backwards unequal to the last number plus the agent ID.
	 * 
	 * <pre>
	 * TODO: Is it really not possible that all the U-block has the same value?
	 * </pre>
	 * 
	 * @param UB	U-block as array of String[5]
	 * @param ID	Agent identification as a String
	 * @return		Length of the first transposition as an integer
	 */
	public int generateFirstTranspositionLength(String [] UB, int ID){
		int lastrow = UB.length - 1;
		int lastcol = UB[0].length() - 1;
		
		// If all the array had the same value something is (?) terribly wrong...
		Integer result = -1;
		
		for(int i = 1; i < lastcol; i++){
			if( Integer.parseInt(String.valueOf(UB[lastrow].charAt(lastcol - i))) != Integer.parseInt(String.valueOf(UB[lastrow].charAt(lastcol)))){
				
				result = Integer.parseInt(String.valueOf(UB[lastrow].charAt(lastcol - i))) + ID;
				
				break;
			} else{
				
				result = -1;
			}
		}

		return result;
	}
	
	/**
	 * Generates length for the first transposition by adding the agend ID to the last number of the U-block.
	 * 
	 * @param UB 	U-block as array of String[5]
	 * @param ID	Agent identification as a String
	 * @return		Length of the first transposition as an integer
	 */
	public int generateSecondTranspositionLength(String [] UB, int ID){
		int lastrow = UB.length - 1;
		int lastcol = UB[0].length() - 1;
						
		return Integer.parseInt(String.valueOf(UB[lastrow].charAt(lastcol))) + ID;
	}
	
	/**
	 * Calculates length of the first transposition based on the U-block, key T and agent ID
	 * 
	 * @param UB	U-block as a String[]
	 * @param T		String representation of the key T
	 * @param ID	Agent ID as an Integer
	 * @return		Length of the first transposition
	 */
	public String generateKeyK1(String[] UB, String T, int ID){

		int t1length = 0; // Length of the first transposition
		t1length = generateFirstTranspositionLength(UB, ID);
		
		T = Sequencer.sequence(T);
		
		String seed = "";
		
		int col = 0;

		// 0 = 10
		for(int i = 0; i < T.length(); i++){
			if(i == 0){
				col = T.indexOf(String.valueOf(9));
			}else{
				col = T.indexOf(String.valueOf(i));
			}
						
			for(int row = 0; row < UB.length; row++){
				seed += UB[row].charAt(col);
			}
		}
		
		seed = seed.substring(0, t1length);
		
		seed = Sequencer.sequence(seed);
		
		return seed;
	}

	/**
	 * Calculates length of the second transposition based on the U-block, key T and agent ID
	 * 
	 * @param UB	U-block as a String[]
	 * @param T		String representation of the key T
	 * @param ID	Agent ID as an Integer
	 * @return		Length of the second transposition
	 */
	public String generateKeyK2(String[] UB, String T, int ID){

		int t1length = 0, t2length = 0; // Length of the first and second transposition
		
		t2length = generateSecondTranspositionLength(UB, ID);
		
		t1length = generateFirstTranspositionLength(UB, ID);
		
		T = Sequencer.sequence(T);
		
		String seed = "";
		
		int col = 0;

		// 0 = 10
		for(int i = 0; i < T.length(); i++){
			if(i == 0){
				col = T.indexOf(String.valueOf(9));
			}else{
				col = T.indexOf(String.valueOf(i));
			}
						
			for(int row = 0; row < UB.length; row++){
				seed += UB[row].charAt(col);
			}
		}

		seed = seed.substring(t1length, t1length + t2length);

		seed = Sequencer.sequence(seed);
		
		return seed;
	}

	public String generateKeyC(String[] UB){
		
		return Sequencer.sequence(UB[UB.length-1]);
	}
}