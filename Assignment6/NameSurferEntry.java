/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

/* Constructor: NameSurferEntry(line) */
/**
 * Creates a new NameSurferEntry from a data line as it appears
 * in the data file.  Each line begins with the name, which is
 * followed by integers giving the rank of that name for each
 * decade.
 */
	public NameSurferEntry(String line) {
		name = readName(line);
		ranks = new int[NDECADES];
		readRankings(line);
	}

/* Method: getName() */
/**
 * Returns the name associated with this entry.
 */
	public String getName() {
		return name;
	}

/* Method: getRank(decade) */
/**
 * Returns the rank associated with an entry for a particular
 * decade.  The decade value is an integer indicating how many
 * decades have passed since the first year in the database,
 * which is given by the constant START_DECADE.  If a name does
 * not appear in a decade, the rank value is 0.
 */
	public int getRank(int decade) {
		return ranks[decade];
	}

/* Method: toString() */
/**
 * Returns a string that makes it easy to see the value of a
 * NameSurferEntry.
 */
	public String toString() {
		String nameValue = "";
		// Build the String of the name and values
		nameValue +=  "\"" + name +" [ "; 
		for(int i = 0; i < ranks.length; i++) {
			nameValue += ranks[i] + " ";
		}
		nameValue += "]\"";
		return nameValue;
	}
	
	/** Method that reads name from a line 
	 * @param line The line that contains the name
	 * @return The name
	 */
	private String readName(String line) {
		String name = "";
		boolean inName = false;
		//Go through string
		for(int i = 0; i < line.length(); i++) {
			char ch = line.charAt(i);
			//If the character is a letter, add it to name string
			if(Character.isLetter(ch)) {
				inName = true;
				name += ch;
			}
			else {
				//If name was already begun, break
				if(inName) {
					break;
				}
			}
		}
		return name;
	}
	
	/** Method that takes in a string and parses all ints
	 * @param rankings The String that contains all ints, or ranking
	 * @return an array holding all the rankings
	 */
	private void readRankings(String rankings) {
		String number = "";
		boolean hasDigit = false;
		int rankingIndex = 0;
		for(int i = 0; i < rankings.length(); i++) {
			//if a digit is found, begin to build number, taking that digit and adding it to number
			if(Character.isDigit(rankings.charAt(i))) {
				number += rankings.substring(i, i+ 1);
				hasDigit = true;
			}
			//If digit is not character, parse the string that contains the digits, and print it. Make number empty, and make hasDigit false
			if(!Character.isDigit(rankings.charAt(i))) {
				if(hasDigit) {
					//Get the ranking
					int ranking = Integer.parseInt(number);
					//Reset the number
					number = "";
					//Add number to ranks array
					ranks[rankingIndex] = ranking;
					//Increment index
					rankingIndex++;
					hasDigit = false;
				}
			}
		}
		if(hasDigit) {
			int rank = Integer.parseInt(number);
			ranks[rankingIndex] = rank;
		}
	}
	private String name;
	private int[] ranks;
	
}

