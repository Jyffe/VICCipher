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
		
		assertEquals("6203189574", tester.sequenceCharacters("IDREAMOFJE"));
		assertEquals("1674205839", tester.sequenceCharacters("ANNIEWITHT"));
		
		assertEquals("1205348679", tester.sequenceNumbers("1204339669"));
	}

}
