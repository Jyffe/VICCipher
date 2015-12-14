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
 * Tests for the ChainCalculator -class
 * Test data reference http://www.quadibloc.com/crypto/pp1324.htm
 * 
 * TODO: A lot of error cases probably is escaping in between fingers atm...
 *
 */

public class TestKeyChain {

	boolean debug = false;
	
	@Test
	public void testGenerateKeyPairSOk() throws Exception {
		KeyChain tester = new KeyChain();
		
		/*
		 * Song:	I dream of Jeanne in T-shirt
		 * 			IDREAMOFJEANNEINTSHIRT
		 * S1		IDREAMOFJE
		 * 			6203189574  
		 * S2		ANNEINTSHI
		 *          1672480935    
		 */
		
		tester.generateKeyPairS("I dream of Jeanne in T-shirt");
		
		assertEquals("6203189574", tester.getKeyS1());
		assertEquals("1672480935", tester.getKeyS2());
		
		tester.generateKeyPairS("Mä näitä polkuja tallaan kai viimeiseen asti Jos sä rakkaani seisot mun vierelläin");
		
		// Mä näitä pol
		// Mänäitäpol
		// MÄNÄITÄPOL
		// M  Ä  N  Ä  I  T  Ä  P  O  L
		// 3  8  4  9  1  7  0  6  5  2
		assertEquals("3849170652", tester.getKeyS1());
		
		// kuja tallaa
		// kujatallaa
		// KUJATALLAA
		// K U J A T A L L A A
		// 6 0 5 1 9 2 7 8 3 4
		assertEquals("6051927834", tester.getKeyS2());
	}

	@Test
	public void testGenerateKeyPairSExceptionStringTooShort() throws Exception {
		KeyChain tester = new KeyChain();
		
		try{
			tester.generateKeyPairS("I dream");
		} catch (Exception e){
			if(e.getMessage().compareTo("KeyGenerator.generateKeyS() : s.length() < 20") != 0){
				fail("expected <KeyGenerator.generateKeyS() : s.length() < 20>"+" but was <:"+e.getMessage()+">");
			}
		}
	}
	
	/** 
	 * Tests for generateKeyG()...
	 * 
	 */

	@Test
	public void testGenerateGOk() throws Exception {
		KeyChain tester = new KeyChain();

		tester.generateKeyG("7.4.1776", "77651", "6203189574");
		
		assertEquals("6551517891", tester.getKeyG());
	}
	
	@Test
	public void testGenerateKeyGExceptionRITooLong() throws Exception {
		KeyChain tester = new KeyChain();
		
		try{
			tester.generateKeyG("7.4.1776", "776519", "6203189574");
		} catch (Exception e){
			if(e.getMessage().compareTo("Sequencer.generateKeyG() : RI.length() > 5") != 0){
				fail("expected <Sequencer.generateKeyG() : RI.length() > 5>"+" but was <:"+e.getMessage()+">");
			}
		}
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void testGenerateKeyGExceptionRITooShort() throws Exception {
		KeyChain tester = new KeyChain();
		
		try{
			tester.generateKeyG("7.4.1776", "7765", "6203189574");
		} catch (Exception e){
			if(e.getMessage().compareTo("Sequencer.generateKeyG() : RI.length() < 5") != 0){
				fail("expected <Sequencer.generateKeyG() : RI.length() < 5>"+" but was <:"+e.getMessage()+">");
			}
		}
	}
	
	/**
	 *  Tests for generateKeyT()...
	 * 
	 */

	@Test
	public void testGenerateKeyTOk() throws Exception {
		KeyChain tester = new KeyChain();
		
		// http://www.quadibloc.com/crypto/pp1324.htm
		tester.generateKeyT("6551517891", "1674205839");
		assertEquals("0221215831", tester.getKeyT());

		// Additional tests
		tester.generateKeyT("1234567890", "1674205839");
		assertEquals("1674205839", tester.getKeyT());
		
		tester.generateKeyT("xxxxxxxxxx", "1234567890");
		assertEquals("..........", tester.getKeyT());
	}
	
	/**
	 *  Tests for generateFirstTransposition()...
	 * 
	 */
	
	@Test
	public void testGenerateFirstTranspositionLengthOk(){
		KeyChain tester = new KeyChain();
		
		// Ref http://www.quadibloc.com/crypto/pp1324.htm
		String[] UB = new String[] {
				"2433363143",
				"6766994579",
				"3325839262",
				"6573121888",
				"1204339669"
		};
		
		assertEquals(14, tester.generateFirstTranspositionLength(UB, 8));
		
		// Other tests
		UB = new String[] {
				"2433363143",
				"6766994579",
				"3325839262",
				"6573121888",
				"1204339211"
		};
		
		assertEquals(10, tester.generateFirstTranspositionLength(UB, 8));
	}
	
	@Test
	public void testGenerateFirstTranspositionLengthNok(){
		KeyChain tester = new KeyChain();
		
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
	public void testGenerateSecondTranspositionLengthOk(){
		KeyChain tester = new KeyChain();
		
		// Ref http://www.quadibloc.com/crypto/pp1324.htm
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
	 *  Tests for generateKeyPairK()...
	 * 
	 */
	@Test
	public void testGenerateKeyPairKOk(){
		KeyChain tester = new KeyChain();
		
		String[] UB = new String[] {
				"5200197365",
				"7201060912",
				"9211669031",
				"1327259342",
				"4599742766"
		};
		
		int[] K1 = new int[] {12,13,1,4,10,2,14,7,5,8,6,9,11,3};
		int[] K2 = new int[] {6,8,1,3,7,9,4,11,12,5,10,13,2};
		
		tester.generateKeyPairK(UB, "5028290760", 7);
		
		assertEquals("1213141021475869113", tester.getKeyK1());
		assertEquals("68137941112510132", tester.getKeyK2());
		
		if(debug){System.out.println("K1 int array...");}
		
		for(int i = 0; i < K1.length; i++){
			
			if(debug){System.out.println("Expected " + K1[i] + ", was " + tester.getKeyK1IntArray()[i]);}
	
			if(K1[i] != tester.getKeyK1IntArray()[i]){
				fail();
			}
		}
		
		if(debug){System.out.println("K2 int array...");}
		
		for(int i = 0; i < K2.length; i++){
			
			if(debug){System.out.println("Expected " + K2[i] + ", was " + tester.getKeyK2IntArray()[i]);}
	
			if(K2[i] != tester.getKeyK2IntArray()[i]){
				fail();
			}
		}
	}
	
	/**
	 *  Tests for generateKeyC()...
	 * 
	 */
	@Test
	public void testGenerateKeyCOk(){
		KeyChain tester = new KeyChain();
		
		String[] UB = new String[] {
				"5200197365",
				"7201060912",
				"9211669031",
				"1327259342",
				"4599742766"
		};
		
		assertEquals("2490731856", tester.generateKeyC(UB));
	}
}