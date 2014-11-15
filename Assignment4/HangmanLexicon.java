/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import acm.util.*;
import java.util.*;
import java.io.*;
public class HangmanLexicon {
	
	/** An array list of all the words */
	ArrayList<String> lexicon = new ArrayList<String>();
	public HangmanLexicon() {
	try {
		BufferedReader rd = new BufferedReader(new FileReader("HangmanLexicon.txt"));
		while(true) {
			String word = rd.readLine();
			/** If the line being read has nothing, break out of the loop */
			if(word == null) {
				break;
			}
			/** Add the line if the line contains something */
			lexicon.add(word);
		}
		rd.close();
	}
	catch(IOException ex) {
		throw(new ErrorException(ex));
		}
	}
/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return lexicon.size();
	}

/** Returns the word at the specified index. */
	public String getWord(int index) {
		return lexicon.get(index);
	};
}
