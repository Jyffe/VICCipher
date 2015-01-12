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
	 * 
	 * @param s				String seed for generating the key S. Note that s.length() must be exactly 10.
	 * @return				String representation of key S
	 * @throws Exception	Thrown if s.length() != 10. Exception message contains reason for the exception.
	 */
	public String generateKeyS(String s) throws Exception {

		s = Sequencer.sequence(s);
		
		if(s.length() < 10){
			
			throw new Exception("Sequencer.generateKeyS() : s.length() < 10");
			
		} else if(s.length() > 10){
			
			throw new Exception("Sequencer.generateKeyS() : s.length() > 10");
		}
		return s;
	}
	
	/**
	 * Generates key G from date, RI and S1 by using mod10 subtraction, chain addition and mod10 addition.
	 * 
	 * <pre>
	 * TODO:	Check whether date string is valid
	 * </pre>
	 * 
	 * @param date			String representation of a date as a seed for generating key G. Date must be in format dd.mm.yyyy.
	 * @param RI			Random Indicator String. Ri.length() must be 5.
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
		
		// Remove separators from the date String
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
	 * Generate key T from key G and key S2
	 * 
	 * TODO: Error handling
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
			}
		}
		return String.valueOf(T);
	}
}