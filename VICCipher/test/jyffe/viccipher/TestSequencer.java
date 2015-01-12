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
public class TestSequencer {

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		Sequencer tester = new Sequencer();
		
		assertEquals("6203189574", tester.sequence("IDREAMOFJE"));
		assertEquals("1674205839", tester.sequence("anniewitht"));
		assertEquals("6203189574", tester.sequence("iDREamoFjE"));
		assertEquals("6203189574", tester.sequence("I DREAM OF JE"));
		assertEquals("1674205839", tester.sequence("A.N!N I=E*W<I>T-H#T"));
		assertEquals("135246", tester.sequence("еджедж"));
		assertEquals("1234567890", tester.sequence("abcdefghij"));
		assertEquals("123456789101112", tester.sequence("abcdefghijkl"));
		assertEquals("162534", tester.sequence("Avatar"));
				
		assertEquals("1234567890", tester.sequence("1234567890"));
		assertEquals("1205348679", tester.sequence("1204339669"));
		assertEquals("1205348679", tester.sequence("1 2 043 39  669"));
		assertEquals("1205348679", tester.sequence("1 &2..043%39(/669"));
		
		assertEquals("1234567890", tester.sequence("abcde12345"));

	}

}
