/**
 * 
 */
package jyffe.viccipher;

import static org.junit.Assert.*;

import java.util.Random;

import junitparams.JUnitParamsRunner;
import static junitparams.JUnitParamsRunner.$;
import junitparams.Parameters;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jyffe
 * 
 * Tests for the Codec -class
 * Uses JUnitParams for test parameterization
 * https://github.com/Pragmatists/junitparams/wiki/Quickstart
 *
 */
@RunWith(JUnitParamsRunner.class)
public class CodecTest {

	private Codec sut = null;

	private String[] UB = null;
	char[][]CB = null;
	
	@Before
	public void setUp() throws Exception {

		sut = new Codec();
		
		UB = null;
		CB = null;
	}

	@After
	public void tearDown() throws Exception {

		sut = null;
		
		UB = null;
		CB = null;
	}
	
	private Object[] testGenerateUBlockOkData(){
	    return $(
    		$("0221215831", ((Object) new String[]{"2433363143", "6766994579", "3325839262", "6573121888", "1204339669"})),	// Ref http://www.quadibloc.com/crypto/pp1324.htm
    		$("6573848300", ((Object) new String[]{"1201221301", "3213434314", "5347777459", "8714441947", "5858850312"})),	// Ref GC42A0X
            $("6573848300", ((Object) new String[]{"1201221301", "3213434314", "5347777459", "8714441947", "5858850312"}))
   		);
	}
	
	@Test
	@Parameters(method = "testGenerateUBlockOkData")
	public void testGenerateUBlockOk(String T, String[] expected){
		UB = sut.generateUBlock(T);
		
		for(int i = 0; i < UB.length; i++){
			if(UB[i].compareTo(expected[i]) != 0){
				fail("expected:<" + expected[i] + "> but was:<" + UB[i] + ">");
			}
		}
	}
	
	private Object[] testGenerateCheckerBoardOkData(){
		return $(
				$("4758960312", "KALOSIT", ((Object) new char[][]{				
					{' ', '4', '7', '5', '8', '9', '6', '0', '3', '1', '2'},
					{' ', 'K', 'A', 'L', 'O', 'S', 'I', 'T', ' ', ' ', ' '},
					{'3', 'B', 'E', 'H', 'N', 'R', 'W', 'Z', 'Ö', '\'', '&'},
					{'1', 'C', 'F', 'J', 'P', 'U', 'X', 'Å', '°', '#', '!'},
					{'2', 'D', 'G', 'M', 'Q', 'V', 'Y', 'Ä', '.', '^', '?'}
				})),
				$("4758960312", "KALOSSIT", ((Object) new char[][]{				
					{' ', '4', '7', '5', '8', '9', '6', '0', '3', '1', '2'},
					{' ', 'K', 'A', 'L', 'O', 'S', 'I', 'T', ' ', ' ', ' '},
					{'3', 'B', 'E', 'H', 'N', 'R', 'W', 'Z', 'Ö', '\'', '&'},
					{'1', 'C', 'F', 'J', 'P', 'U', 'X', 'Å', '°', '#', '!'},
					{'2', 'D', 'G', 'M', 'Q', 'V', 'Y', 'Ä', '.', '^', '?'}
				})),
				$("4758960312", "PAARMA", ((Object) new char[][]{
					{' ', '4', '7', '5', '8', '9', '6', '0', '3', '1', '2'},
					{' ', 'P', 'A', 'R', 'M', ' ', ' ', ' ', ' ', ' ', ' '},
					{'9', 'B', 'H', 'O', 'W', 'Ö', '&', ' ', ' ', ' ', ' '},
					{'6', 'C', 'I', 'Q', 'X', '°', '!', ' ', ' ', ' ', ' '},
					{'0', 'D', 'J', 'S', 'Y', '.', '?', ' ', ' ', ' ', ' '},
					{'3', 'E', 'K', 'T', 'Z', '\'', ' ', ' ', ' ', ' ', ' '},
					{'1', 'F', 'L', 'U', 'Å', '#', ' ', ' ', ' ', ' ', ' '},
					{'2', 'G', 'N', 'V', 'Ä', '^', ' ', ' ', ' ', ' ', ' '}
				})),
				$("2960581734", "ASTELIN", ((Object) new char[][]{
					{' ', '2', '9', '6', '0', '5', '8', '1', '7', '3', '4'},
					{' ', 'A', 'S', 'T', 'E', 'L', 'I', 'N', ' ', ' ', ' '},
					{'7', 'B', 'F', 'J', 'O', 'R', 'W', 'Z', 'Ö', '\'', '&'},
					{'3', 'C', 'G', 'K', 'P', 'U', 'X', 'Å', '°', '#', '!'},
					{'4', 'D', 'H', 'M', 'Q', 'V', 'Y', 'Ä', '.', '^', '?'}
				})),
				$("2960581734", "THE MAN", ((Object) new char[][]{
					{' ', '2', '9', '6', '0', '5', '8', '1', '7', '3', '4'},
					{' ', 'T', 'H', 'E', ' ', 'M', 'A', 'N', ' ', ' ', ' '},
					{'0', 'B', 'G', 'L', 'R', 'W', 'Å', '.', '&', ' ', ' '},
					{'7', 'C', 'I', 'O', 'S', 'X', 'Ä', '\'', '!', ' ', ' '},
					{'3', 'D', 'J', 'P', 'U', 'Y', 'Ö', '#', '?', ' ', ' '},
					{'4', 'F', 'K', 'Q', 'V', 'Z', '°', '^', ' ', ' ', ' '}
				})),
				$("2960581734", "ABCDEFG", ((Object) new char[][]{
					{' ', '2', '9', '6', '0', '5', '8', '1', '7', '3', '4'},
					{' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', ' ', ' ', ' '},
					{'7', 'H', 'K', 'N', 'Q', 'T', 'W', 'Z', 'Ö', '\'', '&'},
					{'3', 'I', 'L', 'O', 'R', 'U', 'X', 'Å', '°', '#', '!'},
					{'4', 'J', 'M', 'P', 'S', 'V', 'Y', 'Ä', '.', '^', '?'}	
				})),
				$("2960581734", "HIJKLMN", ((Object) new char[][]{
					{' ', '2', '9', '6', '0', '5', '8', '1', '7', '3', '4'},
					{' ', 'H', 'I', 'J', 'K', 'L', 'M', 'N', ' ', ' ', ' '},
					{'7', 'A', 'D', 'G', 'Q', 'T', 'W', 'Z', 'Ö', '\'', '&'},
					{'3', 'B', 'E', 'O', 'R', 'U', 'X', 'Å', '°', '#', '!'},
					{'4', 'C', 'F', 'P', 'S', 'V', 'Y', 'Ä', '.', '^', '?'}
				})),
				$("2960581734", "OPQRSTU", ((Object) new char[][]{
					{' ', '2', '9', '6', '0', '5', '8', '1', '7', '3', '4'},
					{' ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', ' ', ' ', ' '},
					{'7', 'A', 'D', 'G', 'J', 'M', 'W', 'Z', 'Ö', '\'', '&'},
					{'3', 'B', 'E', 'H', 'K', 'N', 'X', 'Å', '°', '#', '!'},
					{'4', 'C', 'F', 'I', 'L', 'V', 'Y', 'Ä', '.', '^', '?'}	
				})),
				$("2960581734", "VWXYZÅÄ", ((Object) new char[][]{
					{' ', '2', '9', '6', '0', '5', '8', '1', '7', '3', '4'},
					{' ', 'V', 'W', 'X', 'Y', 'Z', 'Å', 'Ä', ' ', ' ', ' '},
					{'7', 'A', 'D', 'G', 'J', 'M', 'P', 'S', 'Ö', '\'', '&'},
					{'3', 'B', 'E', 'H', 'K', 'N', 'Q', 'T', '°', '#', '!'},
					{'4', 'C', 'F', 'I', 'L', 'O', 'R', 'U', '.', '^', '?'}
				})),
				$("7041682359", "N#C", ((Object) new char[][]{					// Ref: bug found 23.2.2015 with random data
					{' ', '7', '0', '4', '1', '6', '8', '2', '3', '5', '9'},	// Special characters were counted as spaces,
					{' ', 'N', '#', 'C', ' ', ' ', ' ', ' ', ' ', ' ', ' '},	// creating too many rows
					{'1', 'A', 'I', 'Q', 'X', '.', ' ', ' ', ' ', ' ', ' '},
					{'6', 'B', 'J', 'R', 'Y', '\'', ' ', ' ', ' ', ' ', ' '},
					{'8', 'D', 'K', 'S', 'Z', '^', ' ', ' ', ' ', ' ', ' '},
					{'2', 'E', 'L', 'T', 'Å', '&', ' ', ' ', ' ', ' ', ' '},
					{'3', 'F', 'M', 'U', 'Ä', '!', ' ', ' ', ' ', ' ', ' '},
					{'5', 'G', 'O', 'V', 'Ö', '?', ' ', ' ', ' ', ' ', ' '},
					{'9', 'H', 'P', 'W', '°', ' ', ' ', ' ', ' ', ' ', ' '}
				}))
				);
	}
	
	private Object[] testGenerateCheckerBoardRandomData(){
		Randomizer Randomizer = new Randomizer();
		Random random = new Random();
		
		String C = Randomizer.randomUniqueNumber(10);
		String passphrase = Randomizer.randomUniqueString(random.nextInt(7) + 1); // 1 - 7
		
		// checkerboard content does not matter really as the test method checks only length of the table
		return $(
			$(C, passphrase, ((Object) new char[][]{
				{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
				{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
				{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
				{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
				{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}
			}))
		);
	}

	@Test
	@Parameters(method = "testGenerateCheckerBoardOkData")
	public void testGenerateCheckerBoardOk(String C, String keyphrase, char[][] expected){

		CB = sut.generateCheckerBoard(C, keyphrase);
		
		for(int row = 0; row < CB.length; row++){
			for(int col = 0; col < CB[row].length; col++){
				if(String.valueOf(CB[row][col]).compareTo(String.valueOf(expected[row][col])) != 0 ){
					for(int r = 0; r < CB.length; r++){
						for(int c = 0; c < CB[r].length; c++){
							System.out.print(String.valueOf(CB[r][c]));
						}
						System.out.println();
					}
					fail("expected:<["+ String.valueOf(expected[row][col]) + "]> but was:<[" + String.valueOf(CB[row][col]) +"]>");
				}
			}
		}
	}
	
	@Test
	@Parameters(method = "testGenerateCheckerBoardRandomData")
	public void testGenerateCheckerBoardRandom(String C, String keyphrase, char[][] expected){

		CB = sut.generateCheckerBoard(C, keyphrase);
		
		int rows = C.length() - keyphrase.replace(" ", "").length() + 2; // No need to remove spaces, just count them
		
		if(CB.length != rows){
			for(int row = 0; row < CB.length; row++){
				for(int col = 0; col < CB[row].length; col++){
					System.out.print(String.valueOf(CB[row][col]));
				}
				System.out.println();
			}
			fail("expected:<CB.length:<" + rows + "> but was:<" + CB.length + ">");
		}
		
	}
	
	private Object[] testEncodeMessageOkData(){

		return $(
			$("IKROFILMI ON KÄTKETTY? KERRO NAATIT. ^ MINNE M",
				((Object) new char[][]{
					{' ', '2', '4', '9', '0', '7', '3', '1', '8', '5', '6'},
					{' ', 'A', 'S', 'T', 'E', 'L', 'I', 'N', ' ', ' ', ' '},
					{'8', 'B', 'F', 'J', 'O', 'R', 'W', 'Z', 'Ö', '\'', '&'},
					{'5', 'C', 'G', 'K', 'P', 'U', 'X', 'Å', '°', '#', '!'},
					{'6', 'D', 'H', 'M', 'Q', 'V', 'Y', 'Ä', '.', '^', '?'}
				}),
			"3598780843769380159619590996366590878780122939686569311069"
			),
			$("66. Merkitse muistiin!^Salainen koodini on 6",
				((Object) new char[][]{
					{' ', '2', '4', '9', '0', '7', '3', '1', '8', '5', '6'},
					{' ', 'A', 'S', 'T', 'E', 'L', 'I', 'N', ' ', ' ', ' '},
					{'8', 'B', 'F', 'J', 'O', 'R', 'W', 'Z', 'Ö', '\'', '&'},
					{'5', 'C', 'G', 'K', 'P', 'U', 'X', 'Å', '°', '#', '!'},
					{'6', 'D', 'H', 'M', 'Q', 'V', 'Y', 'Ä', '.', '^', '?'}
				}),
			"5565566869087593940695734933156654272310159808062313801556"
			)
		);
	}

	@Test
	@Parameters(method = "testEncodeMessageOkData")
	public void testEncodeMessageOk(String msg, char[][] checkerboard, String expected){
		
		assertEquals(expected, sut.encodeMessage(msg, checkerboard));	
	}

	private Object[] testDecodeMessageOkData(){

		return $(
			$("3598780843769380159619590996366590878780122939686569311069",
				((Object) new char[][]{
						{' ', '2', '4', '9', '0', '7', '3', '1', '8', '5', '6'},
						{' ', 'A', 'S', 'T', 'E', 'L', 'I', 'N', ' ', ' ', ' '},
						{'8', 'B', 'F', 'J', 'O', 'R', 'W', 'Z', 'Ö', '\'', '&'},
						{'5', 'C', 'G', 'K', 'P', 'U', 'X', 'Å', '°', '#', '!'},
						{'6', 'D', 'H', 'M', 'Q', 'V', 'Y', 'Ä', '.', '^', '?'}
				}),
				"IKROFILMIONKÄTKETTY?KERRONAATIT.^MINNEM"
			),
			$("5565566869087593940695734933156654272310159808062313801556",
				((Object) new char[][]{
					{' ', '2', '4', '9', '0', '7', '3', '1', '8', '5', '6'},
					{' ', 'A', 'S', 'T', 'E', 'L', 'I', 'N', ' ', ' ', ' '},
					{'8', 'B', 'F', 'J', 'O', 'R', 'W', 'Z', 'Ö', '\'', '&'},
					{'5', 'C', 'G', 'K', 'P', 'U', 'X', 'Å', '°', '#', '!'},
					{'6', 'D', 'H', 'M', 'Q', 'V', 'Y', 'Ä', '.', '^', '?'}
				}),
				"66.MERKITSEMUISTIIN!^SALAINENKOODINION6"
			)
		);
	}
	
	@Test
	@Parameters(method = "testDecodeMessageOkData")
	public void testDecodeMessageOk(String msg, char[][] checkerboard, String expected){

		assertEquals(expected, sut.decodeMessage(msg, checkerboard));
	}

	private Object[] testEncodeAndDecodeMessageRandomData(){
		
		Randomizer Randomizer = new Randomizer();
		Random random = new Random();
		
		String msg = Randomizer.randomString(random.nextInt(1000));

		return $(
			$(msg,
				((Object) new char[][]{
					{' ', '2', '4', '9', '0', '7', '3', '1', '8', '5', '6'},
					{' ', 'A', 'S', 'T', 'E', 'L', 'I', 'N', ' ', ' ', ' '},
					{'8', 'B', 'F', 'J', 'O', 'R', 'W', 'Z', 'Ö', '\'', '&'},
					{'5', 'C', 'G', 'K', 'P', 'U', 'X', 'Å', '°', '#', '!'},
					{'6', 'D', 'H', 'M', 'Q', 'V', 'Y', 'Ä', '.', '^', '?'}
				})
			)
		);
	}
	
	@Test
	@Parameters(method = "testEncodeAndDecodeMessageRandomData")
	public void testEncodeAndDecodeMessageRandom(String msg, char[][] checkerboard){
		String encoded = sut.encodeMessage(msg, checkerboard);
		String decoded = sut.decodeMessage(encoded, checkerboard);
		
		if(msg.compareTo(decoded) != 0){
			fail("expected:<["+ msg + "]> but was:<[" + decoded +"]>");
		}
	}
	
	private Object[] testPatchMessagePatchingOkData(){
		return $(
				$("123456", "1234560000"),
				$("1234567", "1234567000"),
				$("12345678", "1234567800"),
				$("123456789", "1234567890"),
				$("1234567890", "1234567890")
				);
	}
	
	@Test
	@Parameters(method = "testPatchMessagePatchingOkData")
	public void testPatchMessagePatchingOk(String msg, String expected){
		
		assertEquals(expected, sut.patchMessage(msg));
		
	}
	
	private Object[] testFirstTranspositionOkData(){
		return $(
			$(	"359878084376938015961959099636659087878012293968656931106900",
				((Object) new int[]{12,13,1,4,10,2,14,7,5,8,6,9,11,3}),
				"916308608362085590458670838975397969017996991138326506990186"
			),
			$(	"359878084376938015961959099636659087878012293968656931106900",	// Ref bug related to K1.length being exactly 10
				((Object) new int[]{1,4,0,2,7,5,8,6,9,3}),
				"371616839091366050569529809790856789780831019866493860995923"
			)
		);
	}
	
	@Test
	@Parameters(method = "testFirstTranspositionOkData")
	public void testFirstTranspositionOk(String msg, int[] K1, String expected){

		assertEquals(expected, sut.firstTransposition(msg, K1));
	}

	private Object[] testReverseFirstTranspositionOkData(){
		return $(
			$(	"916308608362085590458670838975397969017996991138326506990186",
				((Object) new int[]{12,13,1,4,10,2,14,7,5,8,6,9,11,3}),
				"359878084376938015961959099636659087878012293968656931106900"
			),
			$(	"75531313346882731356929453381641179724177934649032983643837229197733127281434208833784442749362",
				((Object) new int[]{1,2,5,4,6,3}),
				"71908153738755723936393719387232138419264134744735234743882963137388474481329626423474296931012"
			),
			$(	"371616839091366050569529809790856789780831019866493860995923",	// Ref bug related to K1.length being exactly 10
				((Object) new int[]{1,4,0,2,7,5,8,6,9,3}),
				"359878084376938015961959099636659087878012293968656931106900"
			)
		);
	}

	@Test
	@Parameters(method = "testReverseFirstTranspositionOkData")
	public void testReverseFirstTransposition(String msg, int[] K1, String expected){
		
		assertEquals(expected, sut.reverseFirstTransposition(msg, K1));
	}

	private Object[] testFirstTranspositionAndReverseFirstTranspositionRandomData(){
		Randomizer Randomizer = new Randomizer();
		Random random = new Random();
		
		String msg = Randomizer.randomString(random.nextInt(1000));
		
		// TODO: Random K1
		
		return $(	msg,
					((Object) new int[]{1,2,5,4,6,3})
				);
	}
	
	@Test
	@Parameters(method = "testFirstTranspositionAndReverseFirstTranspositionRandomData")
	public void testFirstTranspositionAndReverseTransposition(String msg, int[] K1){
		
		String encoded = sut.firstTransposition(msg, K1);
		String decoded = sut.reverseFirstTransposition(encoded, K1);
		
		if(msg.compareTo(decoded) != 0){
			fail("expected:<" + msg + "> but was:<" + decoded + ">");
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
		
		Randomizer randomString = new Randomizer();
		
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
		
		Randomizer randomString = new Randomizer();
		
		String originalMsg = randomString.randomNumber(60);
		String RI1 = randomString.randomNumber(5);
		String encryptedMsg = tester.embedRandomIndicator(originalMsg, RI1, "22.2.1978");
		String decryptedMsg = tester.removeRandomIndicator(encryptedMsg, "22.2.1978");
		
		if(originalMsg.compareTo(decryptedMsg) != 0){
			fail("expected:<" + originalMsg + "> but was:<" + decryptedMsg + ">");
		}
	}
}
