/**
 * 
 */
package jyffe.viccipher;

/**
 * @author Jyffe
 *
 */
public class Sequencer {
		
	/**
	 * @param s
	 * @return
	 * 
	 */
	public String sequenceCharacters(String s){
		
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ���";
		
		Integer i = 0, n = 1;
		
		//s.replaceAll("[^A-Za-z0-9 ]", ""); 
		// laitetaanko t�nne vai ei...
		
		while(i < letters.length()){
			while(s.contains(String.valueOf(letters.charAt(i)))){
				if(s.length() <= 10){
					if(n == 10){
						n = 0;
						
						s = s.replaceFirst(String.valueOf(letters.charAt(i)), n.toString());
					} 
					else {
							s = s.replaceFirst(String.valueOf(letters.charAt(i)), n.toString());
					}
				} 
				else {
					s = s.replaceFirst(String.valueOf(letters.charAt(i)), n.toString());
				}
				
				n++;
			}
			
			i++;
		}
	
		return s;
	}
	
	public String sequenceNumbers(String s){
	
		String numbers = "1234567890";
		String letters = "abcdefghij";
		
		Integer i = 0, n = 1;
		
		for(i = 0; i < numbers.length(); i++){
			s = s.replaceAll(String.valueOf(numbers.charAt(i)), String.valueOf(letters.charAt(i)));
		}
		
		i = 0;
		
		while(i < letters.length()){
			while(s.contains(String.valueOf(letters.charAt(i)))){
				if(s.length() <= 10){
					if(n == 10){
						n = 0;
						
						s = s.replaceFirst(String.valueOf(letters.charAt(i)), n.toString());
					} 
					else {
							s = s.replaceFirst(String.valueOf(letters.charAt(i)), n.toString());
					}
				} 
				else {
					s = s.replaceFirst(String.valueOf(letters.charAt(i)), n.toString());
				}
				
				n++;
			}
			
			i++;
		}
		
		return s;
	}
}
