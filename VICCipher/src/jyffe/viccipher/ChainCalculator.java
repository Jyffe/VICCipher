/**
 * 
 */
package jyffe.viccipher;

/**
 * @author Jyffe
 * ChainCalculator is used to perform chain calculation operations. 
 */
public class ChainCalculator {
	
	/**
	 * Performs a chain add operation for a String representation of a numeric value by summing up consecutive numbers and placing them in the 
	 * end of the String representation of the original number thus lengthening the original String. For example chain adding five iterations for 
	 * number "12345"
	 * 
	 * <pre>
	 * 1 + 2 = 3  -> 123453
	 * 2 + 3 = 5  -> 1234565
	 * 3 + 4 = 7  -> 12345657
	 * 4 + 5 = 9  -> 123456579
	 * 5 + 6 = 11 -> 1234565791
	 * </pre>
	 * 
	 * Note! Only ones of the result are added.
	 * 
	 * @param s 	String value representing the numeric value that will be appended with chain addition 
	 * @param n		Count of numbers to be added at the end of String s i.e. length of return value is s.length() + n
	 * @return		String value s representing the numeric value of chain addition result
	 */
	public String add ( String s, int n ) {
		
		Integer i = 0, l1 = 0, l2 = 0, v = 0;
		
		/* Implementation does not check if the String is really representation of a number or something totally else... Feeding any String in does
		 * not break the code though.
		 */
		while(i < n) {
			l1 = Character.getNumericValue(s.charAt(i));
			l2 = Character.getNumericValue(s.charAt(i+1));
			
			// Add only ones
			if(l1 + l2 > 9){
				v = l1 + l2 - 10;
			} else{
				v = l1 + l2;
			}
			
			s += Integer.toString(v);
			
			i++;
		}
		
		return s;
	}
}
