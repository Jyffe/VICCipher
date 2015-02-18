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
 * Tests for the Codec -class
 * Test data reference http://www.quadibloc.com/crypto/pp1324.htm
 *
 */
public class TestCodec {

	private boolean debug = false;
	
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGenerateUBlockOk() {
		Codec tester = new Codec();
		
		// Ref http://www.quadibloc.com/crypto/pp1324.htm
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
		Codec tester = new Codec();
		
		// Ref http://www.quadibloc.com/crypto/pp1324.htm
		/*
		 * Reference uses different way of constructing the checkerboard, which is not implemented atm. and thus this test is omitted for now
		 * 
		char[][] cb = new char[][]{
				{' ', '1', '2', '0', '5', '3', '4', '8', '6', '7', '9'},
				{' ', 'A', 'T', ' ', 'O', 'N', 'E', ' ', 'S', 'I', 'R'},
				{'0', 'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M'},
				{'8', 'P', 'Q', 'U', 'V', 'W', 'X', 'Y', 'Z', '.', '/'}
		};
		
		char[][] CB = tester.generateCheckerBoard("1205348679", "AT ONE SIR");
		
		for(int row = 0; row < cb.length; row++){
			for(int col = 0; col < cb[row].length; col++){
				if(debug){System.out.print(CB[row][col]);}
				
				assertEquals(String.valueOf(cb[row][col]), String.valueOf(CB[row][col]));
			}
			if(debug){System.out.println();}
		}
		if(debug){System.out.println();}
		*/
		
		// Other tests
		char[][]cb = new char[][]{
				{' ', '2', '9', '6', '0', '5', '8', '1', '7', '3', '4'},
				{' ', 'A', 'S', 'T', 'E', 'L', 'I', 'N', ' ', ' ', ' '},
				{'7', 'B', 'F', 'J', 'O', 'R', 'W', 'Z', 'Ö', '\'', '&'},
				{'3', 'C', 'G', 'K', 'P', 'U', 'X', 'Å', '°', '#', '!'},
				{'4', 'D', 'H', 'M', 'Q', 'V', 'Y', 'Ä', '.', '^', '?'}
		};
		
		char[][]CB = tester.generateCheckerBoard("2960581734", "ASTELIN");
		
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
		
		// TODO: Should test sepecial characters and different keyphrase lengths as well... threre are something fishy about these...
		
	}
	
	/* Dead code...
	 * 

	@Test
	public void testEncodeOk(){
		Codec tester = new Codec();
		
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
	 */
	
	@Test
	public void testReplaceMessageOk(){
		Codec tester = new Codec();

		// Ref http://www.quadibloc.com/crypto/pp1324.htm
		char[][] cb = new char[][]{
				{' ', '1', '2', '0', '5', '3', '4', '8', '6', '7', '9'},
				{' ', 'A', 'T', ' ', 'O', 'N', 'E', ' ', 'S', 'I', 'R'},
				{'0', 'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M'},
				{'8', 'P', 'Q', 'U', 'V', 'W', 'X', 'Y', 'Z', '.', '/'}
		};
	
		// Reference actually has an error in the example as dots have been dropped out of the message
		//assertEquals("83419481074164002504419505885809680020246673462101077604730388580905107647004327288885808370707014643265094095348825025854948481436468372047310953204", tester.replaceMessage("We are pleased to hear of your success in establishing your false identity. You will be sent some money to cover expenses within a month.", cb));
		assertEquals("83419481074164002504419505885809680020246673462101077604730388580905107647004327288885808370707014643265094095348825025854948481436468372047310953204", tester.replaceMessage("WE ARE PLEASED TO HEAR OF YOUR SUCCESS IN ESTABLISHING YOUR FALSE IDENTITY YOU WILL BE SENT SOME MONEY TO COVER EXPENSES WITHIN A MONTH", cb));
		
		// Other tests
		cb = new char[][]{
				{' ', '2', '4', '9', '0', '7', '3', '1', '8', '5', '6'},
				{' ', 'A', 'S', 'T', 'E', 'L', 'I', 'N', ' ', ' ', ' '},
				{'8', 'B', 'F', 'J', 'O', 'R', 'W', 'Z', 'Ö', '\'', '&'},
				{'5', 'C', 'G', 'K', 'P', 'U', 'X', 'Å', '°', '#', '!'},
				{'6', 'D', 'H', 'M', 'Q', 'V', 'Y', 'Ä', '.', '^', '?'}
		};
		
		assertEquals("3598780843769380159619590996366590878780122939686569311069", tester.replaceMessage("IKROFILMI ON KÄTKETTY? KERRO NAATIT. ^ MINNE M", cb));
		
	}
	
	@Test
	public void testUnreplaceMessageOk(){
		Codec tester = new Codec();
		
		char[][] cb = new char[][]{
				{' ', '2', '4', '9', '0', '7', '3', '1', '8', '5', '6'},
				{' ', 'A', 'S', 'T', 'E', 'L', 'I', 'N', ' ', ' ', ' '},
				{'8', 'B', 'F', 'J', 'O', 'R', 'W', 'Z', 'Ö', '\'', '&'},
				{'5', 'C', 'G', 'K', 'P', 'U', 'X', 'Å', '°', '#', '!'},
				{'6', 'D', 'H', 'M', 'Q', 'V', 'Y', 'Ä', '.', '^', '?'}
		};
		
		assertEquals("IKROFILMIONKÄTKETTY?KERRONAATIT.^MINNEM", tester.unreplaceMessage("3598780843769380159619590996366590878780122939686569311069", cb));
	}
	
	@Test
	public void testPatchMessageOk(){
		Codec tester = new Codec();
		
		assertEquals("359878084376938015961959099636659087878012293968656931106900", tester.patchMessage("3598780843769380159619590996366590878780122939686569311069"));
		
		//	Ref http://www.quadibloc.com/crypto/pp1324.htm
		if(tester.patchMessage("3598780843769380159619590996366590878780122939686569311069").length() % 5 != 0){
			fail();
		}
		
		assertEquals("359878084376938015961959099636659087878012293968656931106900", tester.patchMessage("3598780843769380159619590996366590878780122939686569311069"));
		
		if(tester.patchMessage("3598780843769380159619590996366590878780122939686569311069").length() % 5 != 0){
			fail();
		}
	}
	
	@Test
	public void testUnpatchMessageOk(){
		Codec tester = new Codec();
		
		// No need to implement and test? Patched zeros will be handled in the unreplaceMessage()
		//assertEquals("3598780843769380159619590996366590878780122939686569311069", tester.unpatchMessage("359878084376938015961959099636659087878012293968656931106900"));
	}
	
	@Test
	public void testFirstTranspositionOk(){
		Codec tester = new Codec();

		// Ref http://www.quadibloc.com/crypto/pp1324.htm
		
		int[] K1 = new int[] {3, 6, 5, 3, 4, 6, 9, 3, 2, 3, 3, 9, 2, 8};
		
		/*
		 * Other tests
		 *                         
		 *            1      2                 ...
		 *            v      v                 v
		 * K1   12 13 1 4 10 2 14 7 5 8 6 9 11 3
		 *      --------------------------------
		 * msg  3  5  9 8 7  8 0  8 4 3 7 6 9  3
		 *      8  0  1 5 9  6 1  9 5 9 0 9 9  6
		 *      3  6  6 5 9  0 8  7 8 7 8 0 1  2
		 *      2  9  3 9 6  8 6  5 6 9 3 1 1  0
		 *      6  9  0 0
		 *      
		 *      1     2    3    4     5    6    7    8    9    10   11   12    13    14
		 * ->   91630 8608 3620 85590 4586 7083 8975 3979 6901 7996 9911 38326 50699 0186
		 * 
		 * ->   916308608362085590458670838975397969017996991138326506990186
		 */

		//K1 = new int[] {7, 11, 8, 9, 3, 12, 13, 1, 4, 10, 2, 14, 6, 5};
		K1 = new int[] {12,13,1,4,10,2,14,7,5,8,6,9,11,3};
		
		//assertEquals("897570837996458636209911383269163085590397950699860801866901", tester.firstTransposition("359878084376938015961959099636659087878012293968656931106900", K1));
		assertEquals("916308608362085590458670838975397969017996991138326506990186", tester.firstTransposition("359878084376938015961959099636659087878012293968656931106900", K1));
		
		
	}
	
	@Test
	public void testReverseFirstTransposition(){
		Codec tester = new Codec();
		
		int[] K1 = new int[] {12,13,1,4,10,2,14,7,5,8,6,9,11,3};
		
		assertEquals("359878084376938015961959099636659087878012293968656931106900", tester.reverseFirstTransposition("916308608362085590458670838975397969017996991138326506990186", K1));
	}

	@Test
	public void testFirstTranspositionAndReverseTransposition(){
		Codec tester = new Codec();
		
		RandomString randomString = new RandomString();
		
		int[] K1 = new int[] {12,13,1,4,10,2,14,7,5,8,6,9,11,3};
		
		String originalMsg = randomString.randomNumber(60);
		String encryptedMsg = tester.firstTransposition(originalMsg, K1);
		String decryptedMsg = tester.reverseFirstTransposition(encryptedMsg, K1);
		
		if(originalMsg.compareTo(decryptedMsg) != 0){
			fail("expected:<" + originalMsg + "> but was:<" + decryptedMsg + ">");
		}
	}

	@Test
	public void testSecondTranspositionOk(){
		Codec tester = new Codec();
		
		/*
		 * Rows = msg.length() / K2.length() + msg.length() % K2.length()
		 * Rows = 60 / 13 + 1 = 5
		 *
		 * K2   6 8 1 3 7 9 4 11 12 5 10 13 2
		 * 1    x x
		 * 2    x x x
		 * 3    x x x x
		 * 4    x x x x x
		 * 5    x x x x x x
		 * 
		 * 916308608362085590458670838975397969017996991138326506990186 ->
		 * 
		 * K2   6 8 1 3 7 9 4 11 12 5 10 13 2
		 * 1    9 1
		 * 2    6 3 0
		 * 3    8 6 0 8
		 * 4    3 6 2 0 8
		 * 5    5 5 9 0 4 5
		 * 
		 * 8670838975397969017996991138326506990186 ->
		 * 
		 * K2   6 8 1 3 7 9 4 11 12 5 10 13 2
		 * 1    9 1 8 6 7 0 8 3  8  9 7  5  3
		 * 2    6 3 0 9 7 9 6 9  0  1 7  9  9
		 * 3    8 6 0 8 6 9 9 1  1  3 8  3  2
		 * 4    3 6 2 0 8 6 5 0  6  9 9  0  1
		 * 5    5 5 9 0 4 5 8 6
		 * 
		 * -> 800293921698008695891399683577684136650996577893910680165930
		 */
		
		int[] K2 = new int[] {6,8,1,3,7,9,4,11,12,5,10,13,2};
		assertEquals("800293921698008695891399683577684136650996577893910680165930", tester.secondTransposition("916308608362085590458670838975397969017996991138326506990186", K2));
	}
	
	// Puuttuu tasan 10 merkkiä pitkän K2 testaus -> 10 = 0
	@Test
	public void testReverseSecondTransposition(){
		Codec tester = new Codec();
		
		int[] K2 = new int[] {6,8,1,3,7,9,4,11,12,5,10,13,2};
		
		assertEquals("916308608362085590458670838975397969017996991138326506990186", tester.reverseSecondTransposition("800293921698008695891399683577684136650996577893910680165930", K2));
	}
	
	@Test
	public void testSecondTranspositionAndReverseTransposition(){
		Codec tester = new Codec();
		
		RandomString randomString = new RandomString();
		
		int[] K2 = new int[] {6,8,1,3,7,9,4,11,12,5,10,13,2};
		
		String originalMsg = randomString.randomNumber(60);
		String encryptedMsg = tester.secondTransposition(originalMsg, K2);
		String decryptedMsg = tester.reverseSecondTransposition(encryptedMsg, K2);
		
		if(originalMsg.compareTo(decryptedMsg) != 0){
			fail("expected:<" + originalMsg + "> but was:<" + decryptedMsg + ">");
		}
	}
	
	@Test
	public void testEmbedRandomIndicator(){
		Codec tester = new Codec();
		
		assertEquals("80029392169800869589139962964483577684136650996577893910680165930", tester.embedRandomIndicator("800293921698008695891399683577684136650996577893910680165930", "29644", "22.2.1978"));
	}
	
	@Test
	public void testGetRandomIndicator(){
		Codec tester = new Codec();
		
		assertEquals("29644", tester.getRandomIndicator("80029392169800869589139962964483577684136650996577893910680165930", "22.2.1978"));
	}
	
	@Test
	public void testRemoveRandomIndicator(){
		Codec tester = new Codec();
		
		assertEquals("800293921698008695891399683577684136650996577893910680165930", tester.removeRandomIndicator("80029392169800869589139962964483577684136650996577893910680165930", "22.2.1978"));
	}
	
	/**
	 * TODO: Implement randomDate()
	 */
	@Test
	public void testEmbedAndRemoveRandomIndicator(){
		Codec tester = new Codec();
		
		RandomString randomString = new RandomString();
		
		String originalMsg = randomString.randomNumber(60);
		String RI1 = randomString.randomNumber(5);
		String encryptedMsg = tester.embedRandomIndicator(originalMsg, RI1, "22.2.1978");
		String decryptedMsg = tester.removeRandomIndicator(encryptedMsg, "22.2.1978");
		
		if(originalMsg.compareTo(decryptedMsg) != 0){
			fail("expected:<" + originalMsg + "> but was:<" + decryptedMsg + ">");
		}
	}
}
