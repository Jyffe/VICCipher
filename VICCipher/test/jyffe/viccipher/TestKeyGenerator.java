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
 * Very basic and basically testOk type of a test suite for refactoring purposes.
 * 
 * TODO: A lot of error cases probably is escaping in between fingers atm...
 *
 */
public class TestKeyGenerator {

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 *  Tests for generateKeyS()...
	 * 
	 */

	@Test
	public void testGenerateKeySOk() throws Exception {
		KeyGenerator tester = new KeyGenerator();
		
		assertEquals("6203189574", tester.generateKeyS("IDREAMOFJE"));
		assertEquals("6203189574", tester.generateKeyS("I D R E A M O F J E"));
		assertEquals("6203189574", tester.generateKeyS("I dream of Je"));
	}
	
	@Test
	public void testGenerateKeySExceptionStringTooLong() throws Exception {
		KeyGenerator tester = new KeyGenerator();
		
		try{
			tester.generateKeyS("I dream of Jeanne in T-shirt");
		} catch (Exception e){
			if(e.getMessage().compareTo("KeyGenerator.generateKeyS() : s.length() > 10") != 0){
				fail("expected <KeyGenerator.generateKeyS() : s.length() > 10>"+" but was <:"+e.getMessage()+">");
			}
		}
	}
	
	@Test
	public void testGenerateKeySExceptionStringTooShort() throws Exception {
		KeyGenerator tester = new KeyGenerator();
		
		try{
			tester.generateKeyS("I dream");
		} catch (Exception e){
			if(e.getMessage().compareTo("KeyGenerator.generateKeyS() : s.length() < 10") != 0){
				fail("expected <KeyGenerator.generateKeyS() : s.length() < 10>"+" but was <:"+e.getMessage()+">");
			}
		}
	}
	
	/** 
	 * Tests for generateKeyG()...
	 * 
	 */

	@Test
	public void testGenerateGOk() throws Exception {
		KeyGenerator tester = new KeyGenerator();
		
		assertEquals("6551517891", tester.generateKeyG("7.4.1776", "77651", "6203189574"));
		
	}
	
	@Test
	public void testGenerateKeyGExceptionRITooLong() throws Exception {
		KeyGenerator tester = new KeyGenerator();
		
		try{
			tester.generateKeyG("7.4.1776", "776519", "6203189574");
		} catch (Exception e){
			if(e.getMessage().compareTo("Sequencer.generateKeyG() : RI.length() > 5") != 0){
				fail("expected <Sequencer.generateKeyG() : RI.length() > 5>"+" but was <:"+e.getMessage()+">");
			}
		}
	}
	
	@Test
	public void testGenerateKeyGExceptionRITooShort() throws Exception {
		KeyGenerator tester = new KeyGenerator();
		
		try{
			tester.generateKeyG("7.4.1776", "7765", "6203189574");
		} catch (Exception e){
			if(e.getMessage().compareTo("Sequencer.generateKeyG() : RI.length() < 5") != 0){
				fail("expected <Sequencer.generateKeyG() : RI.length() < 5>"+" but was <:"+e.getMessage()+">");
			}
		}
	}
	
	@Test
	public void testGenerateKeyGExceptionS1TooLong() throws Exception {
		KeyGenerator tester = new KeyGenerator();
		
		try{
			tester.generateKeyG("7.4.1776", "77651", "62031895741");
		} catch (Exception e){
			if(e.getMessage().compareTo("Sequencer.generateKeyG() : S1.length() > 10") != 0){
				fail("expected <Sequencer.generateKeyG() : S1.length() > 10>"+" but was <:"+e.getMessage()+">");
			}
		}
	}
	
	@Test
	public void testGenerateKeyGExceptionS1TooShort() throws Exception {
		KeyGenerator tester = new KeyGenerator();
		
		try{
			tester.generateKeyG("7.4.1776", "77651", "620318957");
		} catch (Exception e){
			if(e.getMessage().compareTo("Sequencer.generateKeyG() : S1.length() < 10") != 0){
				fail("expected <Sequencer.generateKeyG() : S1.length() < 10>"+" but was <:"+e.getMessage()+">");
			}
		}
	}
	
	/**
	 *  Tests for generateKeyT()...
	 * 
	 */

	@Test
	public void testGenerateKeyTOk() throws Exception {
		KeyGenerator tester = new KeyGenerator();
		
		assertEquals("0221215831", tester.generateKeyT("6551517891", "1674205839"));
		assertEquals("1674205839", tester.generateKeyT("1234567890", "1674205839"));
	}
	
	@Test
	public void testGenerateKeyTNok() throws Exception {
		KeyGenerator tester = new KeyGenerator();
		
		assertEquals("-1", tester.generateKeyT("abcdefghij", "1674205839"));
	}
	
	/**
	 *  Tests for generateFirstTransposition()...
	 * 
	 */
	
	@Test
	public void testGenerateFirstTranspositionOk(){
		KeyGenerator tester = new KeyGenerator();
		
		String[] UB = new String[] {
				"2433363143",
				"6766994579",
				"3325839262",
				"6573121888",
				"1204339669"
		};
		
		assertEquals(14, tester.generateFirstTranspositionLength(UB, 8));
	}
	
	@Test
	public void testGenerateFirstTranspositionNok(){
		KeyGenerator tester = new KeyGenerator();
		
		String[] UB = new String[] {
				"0000000000",
				"0000000000",
				"0000000000",
				"0000000000",
				"0000000000"
		};
		
		assertEquals(-1, tester.generateFirstTranspositionLength(UB, 8));
	}

	/**
	 *  Tests for generateSecondTransposition()...
	 * 
	 */
	@Test
	public void testGenerateSecondTranspositionOk(){
		KeyGenerator tester = new KeyGenerator();
		
		String[] UB = new String[] {
				"2433363143",
				"6766994579",
				"3325839262",
				"6573121888",
				"1204339669"
		};
		
		assertEquals(17, tester.generateSecondTranspositionLength(UB, 8));
	}
	
	/**
	 *  Tests for generateKeyK()...
	 * 
	 */
	@Test
	public void testGenerateKeyKOk(){
		KeyGenerator tester = new KeyGenerator();
		
		String[] UB = new String[] {
				"5200197365",
				"7201060912",
				"9211669031",
				"1327259342",
				"4599742766"
		};
		
		// This all is restructuring sake for now - need to revise once restructuring is over AND the functionality is corrected 
		assertEquals("7099200129", tester.generateKeyK(UB, "5028290760", 7, 0));
		assertEquals("1062757914", tester.generateKeyK(UB, "5028290760", 7, 1));
	}
}