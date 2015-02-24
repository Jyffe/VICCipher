/**
 * 
 */
package jyffe.viccipher;

import static org.junit.Assert.*;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jyffe
 * 
 * Tests for the ChainCalculator -class
 * Uses JUnitParams for test parameterization
 * https://github.com/Pragmatists/junitparams/wiki/Quickstart
 *
 */
@RunWith(JUnitParamsRunner.class)
public class ChainCalculatorTest {

	private ChainCalculator sut = null;
	
	@Before
	public void setUp() throws Exception {

		sut = new ChainCalculator();	
	}

	@After
	public void tearDown() throws Exception {

		sut = null;
	}
	
	@Test
	@Parameters({
		//s, n, expected
		"03584, 5, 0358438327",	// Ref http://www.quadibloc.com/crypto/pp1324.htm
		"85307, 5, 8530738370",	// Ref GC42A0X
		"12345, 5, 1234535798",
		"00000, 5, 0000000000",
		"00000, -1, 00000",
		"00000, 0, 00000",
		"99999, 10, 999998888766653"
	})
	public void testAddOk(String s, int n, String expected) {
		assertEquals(expected, sut.add(s, n));
	}
}