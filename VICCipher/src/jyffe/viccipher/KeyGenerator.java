/**
 * 
 */
package jyffe.viccipher;

/**
 * @author Jyffe
 *
 */
public class KeyGenerator {

	private Sequencer Sequencer = new Sequencer();
	
	/**
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public String generateKeyS(String s) throws Exception{

		s = Sequencer.sequence(s);
		
		if(s.length() < 10){
			
			throw new Exception("Sequencer.generateKeyS() : s.length() < 10");
			
		} else if(s.length() > 10){
			
			throw new Exception("Sequencer.generateKeyS() : s.length() > 10");
		}
		return s;
	}
}
