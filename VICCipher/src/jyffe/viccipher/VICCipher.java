/**
 * 
 */
package jyffe.viccipher;

/**
 * @author Jyffe
 *
 */
public class VICCipher {

	/**
	 * @return
	 * 
	 * TODO: throws
	 */
	public String encode(int ID, String song, String RI, String date, String keyphrase, String plainmsg){
		KeyChain KC = new KeyChain();
		
		String S1 = null, S2 = null;
		
		try{
			KC.generateKeyPairS(song);
			S1 = KC.getKeyS1();
			S2 = KC.getKeyS2();
		} catch(Exception e){
			
			System.out.println("Virhe: "+e.getMessage());
		}

		String G = null;
		
		try{
			KC.generateKeyG(date, RI, S1);
			
			G = KC.getKeyG();
			
		} catch(Exception e){
			
			System.out.println("Virhe: "+e.getMessage());
		}
		
		String T = null;
		
		try{
			KC.generateKeyT(G, S2);
			
			T = KC.getKeyT();
		
		} catch(Exception e){
			
			System.out.println("Virhe: "+e.getMessage());
		}

		String[] UB = null;
		
		Codec BC = new Codec();
		
		UB = BC.generateUBlock(T);

		
		//String K1 = null, K2 = null;

		try{
			KC.generateKeyPairK(UB, T, ID);

			//K1 = KC.getKeyK1();
			//K2 = KC.getKeyK2();
			
		} catch(Exception e){
			System.out.println("Virhe: "+e.getMessage());
		}

		String C = null;
		
		try{
			C = KC.generateKeyC(UB);
			
			
		} catch(Exception e){
			System.out.println("Virhe: "+e.getMessage());
		}
		
		char[][] checkerboard = null;
		
		checkerboard = BC.generateCheckerBoard(C, keyphrase);
		
		String encryptedmsg = null;
		
		System.out.println("Plain message: "+plainmsg);
		encryptedmsg = BC.encodeMessage(plainmsg, checkerboard);
		System.out.println("Replaced message: "+encryptedmsg);
		encryptedmsg = BC.patchMessage(encryptedmsg);
		System.out.println("Patched message: "+encryptedmsg);
		encryptedmsg = BC.firstTransposition(encryptedmsg, KC.getKeyK1IntArray());
		System.out.println("1st transpositioned message: "+encryptedmsg);
		encryptedmsg = BC.secondTransposition(encryptedmsg, KC.getKeyK2IntArray());
		System.out.println("2nd transpositioned message: "+encryptedmsg);
		encryptedmsg = BC.embedRandomIndicator(encryptedmsg, RI, date);
		System.out.println("Message with embedded RI: "+encryptedmsg);
		
		return encryptedmsg;
	}
	
	/**
	 * @return
	 */
	public String decode(int ID, String song, String date, String keyphrase, String encodedmsg){
		KeyChain KC = new KeyChain();
		Codec BC = new Codec();
		
		String decodedmsg = "";
		
		String S1 = null, S2 = null;
		
		try{
			KC.generateKeyPairS(song);
			S1 = KC.getKeyS1();
			S2 = KC.getKeyS2();
		} catch(Exception e){
			
			System.out.println("Virhe: "+e.getMessage());
		}

		String G = null;
		
		String RI = BC.getRandomIndicator(encodedmsg, date);
		
		try{
			KC.generateKeyG(date, RI, S1);
			
			G = KC.getKeyG();
			
		} catch(Exception e){
			
			System.out.println("Virhe: "+e.getMessage());
		}
		
		String T = null;
		
		try{
			KC.generateKeyT(G, S2);
			
			T = KC.getKeyT();
		
		} catch(Exception e){
			
			System.out.println("Virhe: "+e.getMessage());
		}

		String[] UB = null;
		
		UB = BC.generateUBlock(T);

		
		//String K1 = null, K2 = null;

		try{
			KC.generateKeyPairK(UB, T, ID);

			//K1 = KC.getKeyK1();
			//K2 = KC.getKeyK2();
			
		} catch(Exception e){
			System.out.println("Virhe: "+e.getMessage());
		}

		String C = null;
		
		try{
			C = KC.generateKeyC(UB);
			
			
		} catch(Exception e){
			System.out.println("Virhe: "+e.getMessage());
		}
		
		char[][] checkerboard = null;
		
		checkerboard = BC.generateCheckerBoard(C, keyphrase);

		System.out.println("Encoded message:"+encodedmsg);
		decodedmsg = BC.removeRandomIndicator(encodedmsg, date);
		System.out.println("Message with RI removed: "+decodedmsg);
		decodedmsg = BC.reverseSecondTransposition(decodedmsg, KC.getKeyK2IntArray());
		System.out.println("2nd transposition reversed: "+decodedmsg);

		decodedmsg = BC.reverseFirstTransposition(decodedmsg, KC.getKeyK1IntArray());
		//decodedmsg = BC.reverseFirstTransposition("73269970602891876482016024890511601786165669614597", KC.getKeyK1IntArray());
		System.out.println("1st transposition reversed: "+decodedmsg);
		//322789999820251644333817714524773446214813497874
		decodedmsg = BC.decodeMessage(decodedmsg, checkerboard);
		System.out.println("Decoded message: "+decodedmsg);
		
		return decodedmsg;
	}

}
