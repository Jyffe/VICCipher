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
public class TestChainCalculator {

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		ChainCalculator tester = new ChainCalculator();
		
		assertEquals("0358438327", tester.add("03584", 5));
		assertEquals("0000000000", tester.add("00000", 5));
		assertEquals("00000", tester.add("00000", -1));
		assertEquals("00000", tester.add("00000", 0));
	}

}
