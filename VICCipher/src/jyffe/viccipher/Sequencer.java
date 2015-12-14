/**
 * 
 */
package jyffe.viccipher;

/**
 * @author Jyffe
 *
 * Sequencer is used to sequence strings of characters and numbers i.e. convert the String to sequence of numbers.
 */
public class Sequencer {
		
	/**
	 * Performs sequencing operation for the given String i.e. converts String of characters and numbers to String representation of 
	 * sequence of numbers. In the sequencing each character gets an individual value. String is converted in alphabetical order (abc...123...).
	 * If the length of sequenced String is 10, the 10th character gets the value 0. If the length of sequenced String is over 10, the 10th
	 * character gets the value 10, 11th gets the value 11 and so on. For example:
	 * 
	 * <pre>
	 * "abcdefghij"   -> "1234567890"
	 * "abcdefghijkl" -> "123456789101112"
	 * "1234567890"   -> "1234567890"
	 * "Avatar"       -> "162534" 
	 * </pre>
	 * 
	 * @param s		String to be sequenced
	 * @return		Sequenced String s
	 */
	public String sequenceToString(String s){
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖabcdefghij";
		String numbers = "1234567890";
		String letters = "abcdefghij";
	
		/* n = 1 as sequencing the String starts with numeric value 1 i.e. 10th character gets the value 0 or 10 depending 
		 * of length of the String
		 */
		Integer i = 0, n = 1; 
		
		/* TODO
		 * BUG 12.1.2014 
		 * Known issue: '^' breaks the code... for some reason the following regexp does not qualify for this character...
		 */
		s = s.replaceAll("[^A-Öa-ö0-9]", ""); // Remove any special characters and white spaces out of the String
		s = s.toUpperCase(); // Conver the String to all upper case letters

		/* In case the String contains numerals they are at first converted to low case letters a-j to avoid the following replacement
		 * routine to break up.
		 */
		for(int j = 0; j < numbers.length(); j++){
			s = s.replaceAll(String.valueOf(numbers.charAt(j)), String.valueOf(letters.charAt(j)));
		}

		// The actual sequencing routine.
		while(i < characters.length()){
			while(s.contains(String.valueOf(characters.charAt(i)))){
				// If length of sequenced String is 10, the 10th character gets value 0
				if(s.length() <= 10){
					if(n == 10){
						n = 0;
						
						s = s.replaceFirst(String.valueOf(characters.charAt(i)), n.toString());
					} 
					else {
							s = s.replaceFirst(String.valueOf(characters.charAt(i)), n.toString());
					}
				} 
				// Else the 10th character gets value 10, 11th character gets value 11 and so on
				else {
					s = s.replaceFirst(String.valueOf(characters.charAt(i)), n.toString());
				}
				
				n++;
			}
			
			i++;
		}

		return s;
	}
	
	public int[] sequenceToIntArray(String s){
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖabcdefghij";
		String numbers = "1234567890";
		String letters = "abcdefghij";
	
		/* n = 1 as sequencing the String starts with numeric value 1 i.e. 10th character gets the value 0 or 10 depending 
		 * of length of the String
		 */
		Integer i = 0, n = 1; 
		
		/* TODO
		 * BUG 12.1.2014 
		 * Known issue: '^' breaks the code... for some reason the following regexp does not qualify for this character...
		 */
		s = s.replaceAll("[^A-Öa-ö0-9]", ""); // Remove any special characters and white spaces out of the String
		s = s.toUpperCase(); // Convert the String to all upper case letters

		/* In case the String contains numerals they are at first converted to low case letters a-j to avoid the following replacement
		 * routine to break up.
		 */
		for(int j = 0; j < numbers.length(); j++){
			s = s.replaceAll(String.valueOf(numbers.charAt(j)), String.valueOf(letters.charAt(j)));
		}

		int[] result = new int[s.length()];
		
		// The actual sequencing routine.
		while(i < characters.length()){
			while(s.contains(String.valueOf(characters.charAt(i)))){
				// If length of sequenced String is 10, the 10th character gets value 0
				if(s.length() <= 10){
					if(n == 10){
						n = 0;
						
						result[s.indexOf(String.valueOf(characters.charAt(i)))] = n;
						s = s.replaceFirst(String.valueOf(characters.charAt(i)), " ");
					} 
					else {
							result[s.indexOf(String.valueOf(characters.charAt(i)))] = n;
							s = s.replaceFirst(String.valueOf(characters.charAt(i)), " ");
					}
				} 
				// Else the 10th character gets value 10, 11th character gets value 11 and so on
				else {
					result[s.indexOf(String.valueOf(characters.charAt(i)))] = n;
					s = s.replaceFirst(String.valueOf(characters.charAt(i)), " ");
				}
				n++;
			}
			i++;
		}

		return result;
	}
}