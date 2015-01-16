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

	private boolean debug = false;
	
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
		
		char[][] cb = new char[][]{
				{' ', '2', '9', '6', '0', '5', '8', '1', '7', '3', '4'},
				{' ', 'A', 'S', 'T', 'E', 'L', 'I', 'N', ' ', ' ', ' '},
				{'7', 'B', 'F', 'J', 'O', 'R', 'W', 'Z', 'Ö', '\'', '&'},
				{'3', 'C', 'G', 'K', 'P', 'U', 'X', 'Å', '°', '#', '!'},
				{'4', 'D', 'H', 'M', 'Q', 'V', 'Y', 'Ä', '.', '^', '?'}
		};
		
		char[][] CB = tester.generateCheckerBoard("2960581734", "ASTELIN");
		
		for(int row = 0; row < cb.length; row++){
			for(int col = 0; col < cb[row].length; col++){
				if(debug){System.out.print(CB[row][col]);}
				
				assertEquals(String.valueOf(cb[row][col]), String.valueOf(CB[row][col]));
			}
			if(debug){System.out.println();}
		}
		if(debug){System.out.println();}
		
		cb = new char[][]{
				{' ', '2', '9', '6', '0', '5', '8', '1', '7', '3', '4'},
				{' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', ' ', ' ', ' '},
				{'7', 'H', 'K', 'N', 'Q', 'T', 'W', 'Z', 'Ö', '\'', '&'},
				{'3', 'I', 'L', 'O', 'R', 'U', 'X', 'Å', '°', '#', '!'},
				{'4', 'J', 'M', 'P', 'S', 'V', 'Y', 'Ä', '.', '^', '?'}
		};
		
		CB = tester.generateCheckerBoard("2960581734", "ABCDEFG");
		
		for(int row = 0; row < cb.length; row++){
			for(int col = 0; col < cb[row].length; col++){
				if(debug){System.out.print(CB[row][col]);}
				
				assertEquals(String.valueOf(cb[row][col]), String.valueOf(CB[row][col]));
			}
			if(debug){System.out.println();}
		}
		if(debug){System.out.println();}
		
		cb = new char[][]{
				{' ', '2', '9', '6', '0', '5', '8', '1', '7', '3', '4'},
				{' ', 'H', 'I', 'J', 'K', 'L', 'M', 'N', ' ', ' ', ' '},
				{'7', 'A', 'D', 'G', 'Q', 'T', 'W', 'Z', 'Ö', '\'', '&'},
				{'3', 'B', 'E', 'O', 'R', 'U', 'X', 'Å', '°', '#', '!'},
				{'4', 'C', 'F', 'P', 'S', 'V', 'Y', 'Ä', '.', '^', '?'}
		};
		
		CB = tester.generateCheckerBoard("2960581734", "HIJKLMN");
		
		for(int row = 0; row < cb.length; row++){
			for(int col = 0; col < cb[row].length; col++){
				if(debug){System.out.print(CB[row][col]);}
				
				assertEquals(String.valueOf(cb[row][col]), String.valueOf(CB[row][col]));
			}
			if(debug){System.out.println();}
		}
		if(debug){System.out.println();}
		
		cb = new char[][]{
				{' ', '2', '9', '6', '0', '5', '8', '1', '7', '3', '4'},
				{' ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', ' ', ' ', ' '},
				{'7', 'A', 'D', 'G', 'J', 'M', 'W', 'Z', 'Ö', '\'', '&'},
				{'3', 'B', 'E', 'H', 'K', 'N', 'X', 'Å', '°', '#', '!'},
				{'4', 'C', 'F', 'I', 'L', 'V', 'Y', 'Ä', '.', '^', '?'}
		};
		
		CB = tester.generateCheckerBoard("2960581734", "OPQRSTU");
		
		for(int row = 0; row < cb.length; row++){
			for(int col = 0; col < cb[row].length; col++){
				if(debug){System.out.print(CB[row][col]);}
				
				assertEquals(String.valueOf(cb[row][col]), String.valueOf(CB[row][col]));
			}
			if(debug){System.out.println();}
		}
		if(debug){System.out.println();}

		cb = new char[][]{
				{' ', '2', '9', '6', '0', '5', '8', '1', '7', '3', '4'},
				{' ', 'V', 'W', 'X', 'Y', 'Z', 'Å', 'Ä', ' ', ' ', ' '},
				{'7', 'A', 'D', 'G', 'J', 'M', 'P', 'S', 'Ö', '\'', '&'},
				{'3', 'B', 'E', 'H', 'K', 'N', 'Q', 'T', '°', '#', '!'},
				{'4', 'C', 'F', 'I', 'L', 'O', 'R', 'U', '.', '^', '?'}
		};

		CB = tester.generateCheckerBoard("2960581734", "VWXYZÅÄ");
		
		for(int row = 0; row < cb.length; row++){
			for(int col = 0; col < cb[row].length; col++){
				if(debug){System.out.print(CB[row][col]);}
				
				assertEquals(String.valueOf(cb[row][col]), String.valueOf(CB[row][col]));
			}
			if(debug){System.out.println();}
		}
		if(debug){System.out.println();}
		
		// Should test sepcial characters and different keyphrase lengths as well... threre are something fishy about these...
		
	}
	
	@Test
	public void testEncodeOk(){
		BlockChipher tester = new BlockChipher();
		
		char[][] cb = new char[][]{
				{' ', '2', '4', '9', '0', '7', '3', '1', '8', '5', '6'},
				{' ', 'A', 'S', 'T', 'E', 'L', 'I', 'N', ' ', ' ', ' '},
				{'8', 'B', 'F', 'J', 'O', 'R', 'W', 'Z', 'Ö', '\'', '&'},
				{'5', 'C', 'G', 'K', 'P', 'U', 'X', 'Å', '°', '#', '!'},
				{'6', 'D', 'H', 'M', 'Q', 'V', 'Y', 'Ä', '.', '^', '?'}
		};
		
		assertEquals("359878084376938015961959099636659087878012293968656931106900", tester.encode("IKROFILMI ON KÄTKETTY? KERRO NAATIT. ^ MINNE M", cb, "7118931213141021465"));
		assertEquals("5666656800", tester.encode("!?^.", cb, "7118931213141021465"));
	}

	@Test
	public void testReplaceMessageOk(){
		BlockChipher tester = new BlockChipher();

		char[][] cb = new char[][]{
				{' ', '2', '4', '9', '0', '7', '3', '1', '8', '5', '6'},
				{' ', 'A', 'S', 'T', 'E', 'L', 'I', 'N', ' ', ' ', ' '},
				{'8', 'B', 'F', 'J', 'O', 'R', 'W', 'Z', 'Ö', '\'', '&'},
				{'5', 'C', 'G', 'K', 'P', 'U', 'X', 'Å', '°', '#', '!'},
				{'6', 'D', 'H', 'M', 'Q', 'V', 'Y', 'Ä', '.', '^', '?'}
		};
		
		assertEquals("3598780843769380159619590996366590878780122939686569311069", tester.replaceMessage("IKROFILMI ON KÄTKETTY? KERRO NAATIT. ^ MINNE M", cb));
		
	}
	
	@Test
	public void testPatchMessageOk(){
		BlockChipher tester = new BlockChipher();
		
		assertEquals("359878084376938015961959099636659087878012293968656931106900", tester.patchMessage("3598780843769380159619590996366590878780122939686569311069"));
		
		if(tester.patchMessage("3598780843769380159619590996366590878780122939686569311069").length() % 5 != 0){
			fail();
		}
	}
	
	@Test
	public void testFirstTranspositionOk(){
		BlockChipher tester = new BlockChipher();
		
		/*
		 * K1    7118931213141021465
		 * msg   3598780843769380159
		 *       6195909963665908787
		 *       8012293968656931106
		 *       900
		 *       
		 * ->      368951009910852792809093899466338766665956399803081171580976
		 */

		//assertEquals("368951009910852792809093899466338766665956399803081171580976", tester.firstTransposition("359878084376938015961959099636659087878012293968656931106900", "7118931213141021465"));
	}
}
