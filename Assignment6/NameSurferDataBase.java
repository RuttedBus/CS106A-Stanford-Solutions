/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */
import java.io.*;
import acm.util.*;
import java.util.*;
public class NameSurferDataBase implements NameSurferConstants {
	
/* Constructor: NameSurferDataBase(filename) */
/**
 * Creates a new NameSurferDataBase and initializes it using the
 * data in the specified file.  The constructor throws an error
 * exception if the requested file does not exist or if an error
 * occurs as the file is being read.
 */
	public NameSurferDataBase(String filename) {
		// Read each line, add a name and namesurfer entry to hashmap
		try {
			BufferedReader rd = new BufferedReader(new FileReader(filename));
			while(true) {
				String line = rd.readLine();
				if(line == null) {
					break;
				}
				NameSurferEntry entry = new NameSurferEntry(line);
				rankMap.put(entry.getName(), entry);
			}
			rd.close();
		}
		catch(IOException e) {
			throw(new ErrorException(e));
		}
	}
	
/* Method: findEntry(name) */
/**
 * Returns the NameSurferEntry associated with this name, if one
 * exists.  If the name does not appear in the database, this
 * method returns null.
 */
	public NameSurferEntry findEntry(String name) {
		char firstLetter = name.charAt(0);
		//If letter is not uppercase, change the name string to have first letter uppercase. Then change other letters to lowercase
		if(Character.isLowerCase(firstLetter)) {
			firstLetter = Character.toUpperCase(firstLetter);
		}
		String otherLetters = name.substring(1);
		otherLetters = otherLetters.toLowerCase();
		name = firstLetter + otherLetters;
		// Return null if name is not in database
		if(rankMap.containsKey(name)) {
			return rankMap.get(name);
		}
		return null;
	}
	private Map<String, NameSurferEntry> rankMap = new HashMap<String, NameSurferEntry>();
}

