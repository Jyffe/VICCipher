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
public class TestBlockCipher {

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGenerateUBlockOk() {
		BlockChipher tester = new BlockChipher();
		
		String[] UB = tester.generateUBlock("0221215831");
		
		if(UB[0].compareTo("2433363143") != 0){
			fail();
		}else if(UB[1].compareTo("6766994579") != 0){
			fail();
		}else if(UB[2].compareTo("3325839262") != 0){
			fail();
		}else if(UB[3].compareTo("6573121888") != 0){
			fail();
		}else if(UB[4].compareTo("1204339669") != 0){
			fail();
		}
		
	}
	
	@Test
	public void testGenerateCheckerBoardOk(){
		BlockChipher tester = new BlockChipher();
		
		char[][] CB = tester.generateCheckerBoard("2960581734", "ASTELIN");
		
		/*
		for(int rows = 0; rows < CB.length; rows++){
			for(int cols = 0; cols < CB[rows].length; cols++){
				System.out.print(CB[rows][cols]);
			}
			System.out.println();
		}*/
		
	}

}
