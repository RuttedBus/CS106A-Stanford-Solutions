/*
 * File: FacePamphletDatabase.java
 * -------------------------------
 * This class keeps track of the profiles of all users in the
 * FacePamphlet application.  Note that profile names are case
 * sensitive, so that "ALICE" and "alice" are NOT the same name.
 */

import java.util.*;

public class FacePamphletDatabase implements FacePamphletConstants {

	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the database.
	 */
	public FacePamphletDatabase() {
		profilesMap = new HashMap<String, FacePamphletProfile>();
	}
	
	
	/** 
	 * This method adds the given profile to the database.  If the 
	 * name associated with the profile is the same as an existing 
	 * name in the database, the existing profile is replaced by 
	 * the new profile passed in.
	 */
	public void addProfile(FacePamphletProfile profile) {
		String name = profile.getName();
		//If profile with name already exists, remove the old profile, and add a new one.
		if(profilesMap.containsKey(name)) {
			profilesMap.remove(name);
			profilesMap.put(name, profile);
		}
		//If no profile with name already exists, just add it to the database
		else {
			profilesMap.put(name, profile);
		}
	}

	
	/** 
	 * This method returns the profile associated with the given name 
	 * in the database.  If there is no profile in the database with 
	 * the given name, the method returns null.
	 */
	public FacePamphletProfile getProfile(String name) {
		//Return profile id profile exists
		if(profilesMap.containsKey(name)) {
			return profilesMap.get(name);
		}
		return null;
	}
	
	
	/** 
	 * This method removes the profile associated with the given name
	 * from the database.  It also updates the list of friends of all
	 * other profiles in the database to make sure that this name is
	 * removed from the list of friends of any other profile.
	 * 
	 * If there is no profile in the database with the given name, then
	 * the database is unchanged after calling this method.
	 */
	public void deleteProfile(String name) {
		//If profile exists, remove the name
		if(profilesMap.containsKey(name)) {
			profilesMap.remove(name);
		}
		Iterator<String> it = profilesMap.keySet().iterator();
		//Go through each profile, and remove friend, if they are one
		while(it.hasNext()) {
			String currentName = it.next();
			FacePamphletProfile currentProfile = profilesMap.get(currentName);
			currentProfile.removeFriend(name);
		}
	}

	
	/** 
	 * This method returns true if there is a profile in the database 
	 * that has the given name.  It returns false otherwise.
	 */
	public boolean containsProfile(String name) {
		// Return true if profile exists
		if(profilesMap.containsKey(name)) {
			return true;
		}
		return false;
	}
	
	/**Instance Variables */
	private Map <String, FacePamphletProfile> profilesMap;
}
