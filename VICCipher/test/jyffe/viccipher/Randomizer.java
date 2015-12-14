package jyffe.viccipher;

import java.util.HashMap;
import java.util.Random;

public class Randomizer {

	private static final char[] symbols;

	  static {
	    StringBuilder tmp = new StringBuilder();
	    for (char ch = '0'; ch <= '9'; ++ch)
	      tmp.append(ch);
	    for (char ch = 'A'; ch <= 'Z'; ++ch)
	      tmp.append(ch);
	    tmp.append('Å');
	    tmp.append('Ä');
	    tmp.append('Ö');
	    tmp.append('°');
	    tmp.append('.');
	    tmp.append('\'');
	    //tmp.append('#'); Ei ole sallittu merkki, käytetään koodaamaan numeroita
	    tmp.append('^');
	    tmp.append('&');
	    tmp.append('!');
	    tmp.append('?');
	    
	    symbols = tmp.toString().toCharArray();
	  }
	  
	  private static final char[] numbers;

	  static {
	    StringBuilder tmp = new StringBuilder();
	    for (char ch = '0'; ch <= '9'; ++ch)
	      tmp.append(ch);
	    
	    numbers = tmp.toString().toCharArray();
	  }

	  private final Random random = new Random();

	  public String randomString(int len) {
		  char[] buf = new char[len];
		  
		  for (int idx = 0; idx < buf.length; ++idx) 
		      buf[idx] = symbols[random.nextInt(symbols.length)];
		    return new String(buf);
	  }
	  
	  public String randomNumber(int len) {
		  char[] buf = new char[len];
		  
		  for (int idx = 0; idx < buf.length; ++idx) 
		      buf[idx] = numbers[random.nextInt(numbers.length)];
		    return new String(buf);
	  }

	  public String randomUniqueNumber(int len) {
		  Sequencer Sequencer = new Sequencer();
		  
		  return Sequencer.sequenceToString(this.randomNumber(len));
	  }
	  
	  public String randomUniqueString(int len){
		  char[] buffer = new char[len];
		  char ch;
		  
		  HashMap<Character, Integer> hMap = new HashMap<Character, Integer>();
		  
		  for (int idx = 0; idx < buffer.length; ++idx){
			  ch = symbols[random.nextInt(symbols.length)];
			  if(hMap.get(ch) == null){
				  buffer[idx] = ch;
				  hMap.put(ch,  1);
			  }
		  }
		      
		  return new String(buffer);		  
	  }
	  
	  /*
	  public int[] randomUniqueIntegers(int len){
		  int[] buffer = new int[len];
		  
		  Random random = new Random();
		  
		  for(int i = 0; i < buffer.length; i++){
			  buffer[i] = random.nextInt(10);  
		  }
		  
		  Sequencer sequencer = new Sequencer();
		  
		  return sequencer.sequenceToIntArray(buffer);
	  }
	  */
}