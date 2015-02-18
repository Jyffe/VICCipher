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
 * Tests for the Sequencer -class
 * Test data reference http://www.quadibloc.com/crypto/pp1324.htm
 * 
 */
public class TestSequencer {

	boolean debug = false;
	
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testSequenceToStringOk() {
		Sequencer tester = new Sequencer();
		
		// Ref http://www.quadibloc.com/crypto/pp1324.htm
		assertEquals("6203189574", tester.sequenceToString("IDREAMOFJE"));
		assertEquals("1674205839", tester.sequenceToString("ANNIEWITHT"));
		assertEquals("1205348679", tester.sequenceToString("1204339669"));
		
		// Additional test cases to cover scandic letters, numbers, hunt down error cases and ensure test coverage
		assertEquals("1674205839", tester.sequenceToString("anniewitht"));
		assertEquals("6203189574", tester.sequenceToString("iDREamoFjE"));
		assertEquals("6203189574", tester.sequenceToString("I DREAM OF JE"));
		assertEquals("1674205839", tester.sequenceToString("A.N!N I=E*W<I>T-H#T"));
		assertEquals("135246", tester.sequenceToString("еджедж"));
		assertEquals("1234567890", tester.sequenceToString("abcdefghij"));
		assertEquals("123456789101112", tester.sequenceToString("abcdefghijkl"));
		assertEquals("162534", tester.sequenceToString("Avatar"));
		assertEquals("1234567890", tester.sequenceToString("1234567890"));
		assertEquals("1205348679", tester.sequenceToString("1 2 043 39  669"));
		assertEquals("1205348679", tester.sequenceToString("1 &2..043%39(/669"));
		assertEquals("1234567890", tester.sequenceToString("abcde12345"));
		assertEquals("1234567890", tester.sequenceToString("abcde12345"));
		assertEquals("3109481113516714212", tester.sequenceToString("36534693233928"));
		assertEquals("1581241025113131761614179", tester.sequenceToString("94735236270398134"));
	}
	
	@Test
	public void testSequenceToIntArray(){
		Sequencer tester = new Sequencer();
		
		int[] right = new int[] {6,2,0,3,1,8,9,5,7,4};

		int[] result = tester.sequenceToIntArray("IDREAMOFJE");
		
		for(int i = 0; i < result.length; i++){
			if(debug){System.out.print(result[i] + " ");}
			
			if(result[i] != right[i]){
				fail();
			}
		}
		
		if(debug){System.out.println();}
		
		right = new int[] {3,10,9,4,8,11,13,5,1,6,7,14,2,12};

		result = tester.sequenceToIntArray("36534693233928");
		
		for(int i = 0; i < result.length; i++){
			if(debug){System.out.print(result[i] + " ");}
			
			if(result[i] != right[i]){
				fail();
			}
		}
		
		if(debug){System.out.println();}
		
		right = new int[] {15,8,12,4,10,2,5,11,3,13,17,6,16,14,1,7,9};

		result = tester.sequenceToIntArray("94735236270398134");
		
		for(int i = 0; i < result.length; i++){
			if(debug){System.out.print(result[i] + " ");}
			
			if(result[i] != right[i]){
				fail();
			}
		}
	}

}
