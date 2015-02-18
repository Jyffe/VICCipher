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
 */
public class TestChainCalculator {

	@Test
	public void testAddOk() {
		ChainCalculator tester = new ChainCalculator();
		
		// Ref http://www.quadibloc.com/crypto/pp1324.htm
		assertEquals("0358438327", tester.add("03584", 5));
		
		// Additional test cases
		assertEquals("1234535798", tester.add("12345", 5));
		assertEquals("0000000000", tester.add("00000", 5));
		assertEquals("00000", tester.add("00000", -1));
		assertEquals("00000", tester.add("00000", 0));
		assertEquals("999998888766653", tester.add("99999", 10));
	}

}
