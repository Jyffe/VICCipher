/**
 * 
 */
package jyffe.viccipher;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

/**
 * @author Jyffe
 *
 */
public class TestKeyGenerator {

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testOk() throws Exception {
		KeyGenerator tester = new KeyGenerator();
		
		assertEquals("6203189574", tester.generateKeyS("IDREAMOFJE"));
		assertEquals("6203189574", tester.generateKeyS("I D R E A M O F J E"));
		assertEquals("6203189574", tester.generateKeyS("I dream of Je"));
	}
	
	@Test
	public void testExceptionStringTooLong() throws Exception {
		KeyGenerator tester = new KeyGenerator();
		
		try{
			tester.generateKeyS("I dream of Jeanne in T-shirt");
		} catch (Exception e){
			if(e.getMessage().compareTo("Sequencer.generateKeyS() : s.length() > 10") != 0){
				fail("expected <Sequencer.generateKeyS() : s.length() > 10>"+" but was <:"+e.getMessage()+">");
			}
		}
	}
	
	@Test
	public void testExceptionStringTooShort() throws Exception {
		KeyGenerator tester = new KeyGenerator();
		
		try{
			tester.generateKeyS("I dream");
		} catch (Exception e){
			if(e.getMessage().compareTo("Sequencer.generateKeyS() : s.length() < 10") != 0){
				fail("expected <Sequencer.generateKeyS() : s.length() < 10>"+" but was <:"+e.getMessage()+">");
			}
		}
	}

}
