/**
 * 
 */
package jyffe.viccipher;

/**
 * @author Jyffe
 * 
 * Used to generate and store various keys necessary for VIC-cipher encoding and decoding
 */
public class KeyChain {
	private String S1 = null;			// Key S1
	private String S2 = null;			// Key S2
	private String G = null;			// Key G
	private String T = null;			// Key T
	private int[] K1IntArray = null;	// Key K1 as array of integers	// Should have an object instead? 
	private int[] K2IntArray = null;	// Key K2 as array of integers
	private String K1 = null;			// Key K1
	private String K2 = null;			// Key K2
	
	private Sequencer Sequencer = new Sequencer();
	private ChainCalculator CC = new ChainCalculator();
	private Mod10Calculator Mod10 = new Mod10Calculator(); 
	
	public String getKeyS1(){
		return this.S1;
	}
	
	public String getKeyS2(){
		return this.S2;
	}

	public String getKeyG(){
		return this.G;
	}
	
	public String getKeyT(){
		return this.T;
	}
	
	public String getKeyK1(){
		return this.K1;
	}
	
	public String getKeyK2(){
		return this.K2;
	}
	
	public int[] getKeyK1IntArray(){
		return this.K1IntArray;
	}
	
	public int[] getKeyK2IntArray(){
		return this.K2IntArray;
	}
	
	/**
	 * Generates key pair S1 and S2 by sequencing String s (10 first characters for S1 and 10 subsequential characters for S2). 
	 * Values of the key pair S can be retrieved by using getKeyS1() and getKeyS2() -methods.
	 * 
	 * @param s				String seed for generating the key S. Note that s.length() must be exactly 10.
	 * @throws Exception	Thrown if s.length() != 10. Exception message contains reason for the exception.
	 */
	public void generateKeyPairS(String s) throws Exception {
		s = s.replaceAll("[^A-Öa-ö0-9]", "");

		if(s.length() < 20){
			
			throw new Exception("KeyGenerator.generateKeyS() : s.length() < 20");
		}
		
		this.S1 = Sequencer.sequenceToString(s.substring(0, 10));
		
		this.S2 = Sequencer.sequenceToString(s.substring(10, 20));
	}

	/**
	 * Generates key G from date, RI and S1 by using mod10 subtraction, chain addition and mod10 addition. Value of the key G can be
	 * retrieved by using the getKeyG() -method.
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
	 * @throws Exception	Thrown if any of the given seed parameters are not valid. Exception message contains reason for the exception.
	 */
	public void generateKeyG(String date, String RI, String s1) throws Exception {
		
		if(RI.length() < 5){
			
			throw new Exception("Sequencer.generateKeyG() : RI.length() < 5");
			
		}else if (RI.length() > 5){
			
			throw new Exception("Sequencer.generateKeyG() : RI.length() > 5");
		}

		// Remove separators from the date String, assumes that '.' is always used...
		date = date.replaceAll("\\.+", "");

		// Use and RI five first numbers of the date String for mod10 subtraction to get first iteration of the key G
		this.G = Mod10.subtract(RI, date.substring(0, 5));
		
		// Chain add 5 iterations to get second iteration of the key G
		this.G = CC.add(this.G, 5);
		
		// Use second iteration of the key G and key S1 to get third and final iteration of the key G
		this.G = Mod10.add(this.G, s1);
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
	 * Value of the key T can be retrieved by using the getKeyT() -method.
	 * TODO: Error handling via exceptions
	 * 
	 * @param G		String representation of key G
	 * @param S2	String representation of key S2
	 */
	public void generateKeyT(String G, String S2){
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
				T[i] = '.';				// TODO: Should throw an exception
				break;
			}
		}
		
		this.T = String.valueOf(T);
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
	 * Generates length for the first transposition by adding the agent ID to the last number of the U-block.
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
	
	public void generateKeyPairK(String[] UB, String T, int ID){
		int t1length = generateFirstTranspositionLength(UB, ID);; // Length of the first transposition
		int t2length = generateSecondTranspositionLength(UB, ID); // Length of the second transposition

		/* Referenced example at http://www.quadibloc.com/crypto/pp1324.htm does not sequence the T. However, the results are the same
		 * only the algorithm related to generating K1 would be a slightly difference. Now it is easier to implement as T contains any
		 * certain number 1..n exactly once.
		 * 
		 */
		
		T = Sequencer.sequenceToString(T);
		
		String ub = "";
		
		this.K1 = "";
		this.K2 = "";
		
		this.K1IntArray = new int[t1length];
		this.K2IntArray = new int[t2length];

		int col = 0;
		
		for(int i = 1; i <= T.length(); i++){
			if(i == 10){
				col = T.indexOf(String.valueOf(0));
			}else{
				col = T.indexOf(String.valueOf(i));
			}
			
			for(int row = 0; row < UB.length; row++){
				ub += UB[row].charAt(col);
			}
		}

		this.K1 = ub.substring(0, t1length);
		
		this.K1IntArray = Sequencer.sequenceToIntArray(this.K1);
		this.K1 = Sequencer.sequenceToString(this.K1);
		
		col = 0;

		this.K2 = ub.substring(t1length, t1length + t2length);
		
		this.K2IntArray = Sequencer.sequenceToIntArray(this.K2);
		this.K2 = Sequencer.sequenceToString(this.K2);
	}
	
	public String generateKeyC(String[] UB){
		
		return Sequencer.sequenceToString(UB[UB.length-1]);
	}
}