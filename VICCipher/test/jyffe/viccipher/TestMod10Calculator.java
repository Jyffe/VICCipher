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
 * Tests for the Mod10Calculator -class
 * Test data reference http://www.quadibloc.com/crypto/pp1324.htm
 *
 */
public class TestMod10Calculator {
	@Test
	public void testSubtractOk() {
		Mod10Calculator tester = new Mod10Calculator();
		
		// Ref http://www.quadibloc.com/crypto/pp1324.htm
		assertEquals("03584", tester.subtract("77651", "74177"));
		assertEquals("03584", tester.subtract("77651", "74177"));
	}

	@Test
	public void testAddOk() {
		Mod10Calculator tester = new Mod10Calculator();
		
		// Ref http://www.quadibloc.com/crypto/pp1324.htm
		assertEquals("6551517891", tester.add("6203189574", "0358438327"));
	}
}
