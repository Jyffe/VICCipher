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
public class TestMod10Calculator {

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testSubtractOk() {
		Mod10Calculator tester = new Mod10Calculator();
		
		assertEquals("03584", tester.subtract("77651", "74177"));
	}

	@Test
	public void testAddOk() {
		Mod10Calculator tester = new Mod10Calculator();
		
		assertEquals("6551517891", tester.add("6203189574", "0358438327"));
	}
}
