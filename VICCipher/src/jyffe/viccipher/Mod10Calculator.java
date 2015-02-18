/**
 * 
 */
package jyffe.viccipher;

/**
 * @author Jyffe
 *
 */
public class Mod10Calculator {
	
	/**
	 * Perform mod10 subtraction. Both subtracted and subtractor must be of equal length of digits.
	 * 
	 * TODO: Add checks for lengths and for other than numeric values
	 * 
	 * @param subtracted	Number to be mod10 subtracted from
	 * @param subtractor	Number to be mod10 subtracted
	 * @return				Result of mod10 subtraction
	 */
	public String subtract(String subtracted, String subtractor){
		int i = 0;
		int a = 0, b = 0, c = 0;
		
		String answer = new String();
		
		while(i < subtracted.length()){
			a = Integer.valueOf(String.valueOf(subtracted.charAt(i)));
			b = Integer.valueOf(String.valueOf(subtractor.charAt(i)));
			c = a - b;
			
			if(c >= 0){
				answer += String.valueOf(c);
			} else {
				answer += String.valueOf(10 + c);
			}

			i++;
		}
		return answer;
	}
	
	/**
	 * Perform mod10 addition. Both addends must be of equal length of digits.
	 * 
	 * TODO: Add checks for lengths and for other than numeric values
	 * 
	 * @param addend1	Number to be mod10 added
	 * @param addend2	Number to be mod10 added
	 * @return			Result of mod10 addition
	 */
	public String add(String addend1, String addend2){
		int i = 0;
		int a = 0, b = 0, c = 0;
		
		String answer = new String();
		
		while(i < addend1.length()){
			a = Integer.valueOf(String.valueOf(addend1.charAt(i)));
			b = Integer.valueOf(String.valueOf(addend2.charAt(i)));
			c = a + b;
			
			if(c <= 9){
				answer += String.valueOf(c);
			} else {
				answer += String.valueOf(c - 10);
			}
			
			i++;
		}
		
		return answer;
		
	}
}