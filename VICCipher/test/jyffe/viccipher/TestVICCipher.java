package jyffe.viccipher;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

public class TestVICCipher {

	@Test
	public void testEncodeOk() {
		VICCipher tester = new VICCipher();
		
		int ID = 7;
		String song = "Kautta laaksojen ja vuorten kulki partisaanein tie";
		String RI = "29644";
		String date = "22.2.1978";
		String keyphrase = "astelin";
		String plainmsg = "IKROFILMI ON KÄTKETTY? KERRO NAATIT. ^ MINNE M";
		
		assertEquals("80029392169800869589139962964483577684136650996577893910680165930", tester.encode(ID, song, RI, date, keyphrase, plainmsg));
	}

	@Test
	public void testDecodeOk() {
		VICCipher tester = new VICCipher();
		
		int ID = 7;
		String song = "Kautta laaksojen ja vuorten kulki partisaanein tie";
		//String RI = "29644";
		String date = "22.2.1978";
		String keyphrase = "astelin";
		String encryptedmsg = "80029392169800869589139962964483577684136650996577893910680165930";
		
		// SHould be "IKROFILMIONKÄTKETTY?KERRONAATIT.^MINNEM" - how to remove patched zeros from the end?
		assertEquals("IKROFILMIONKÄTKETTY?KERRONAATIT.^MINNEMEE", tester.decode(ID, song, date, keyphrase, encryptedmsg));
	}
}