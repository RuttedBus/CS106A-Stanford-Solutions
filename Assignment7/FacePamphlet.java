/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public static void main(String[] args) {
		new FacePamphlet().start(args);
	}
	
	public void init() {
		initWest();
		initNorth();
		//Create the canvas
		faceCanvas = new FacePamphletCanvas();
		add(faceCanvas);
		faceDB = new FacePamphletDatabase();
		currentProfile = null;
		addActionListeners();
    }
    
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
		if(e.getSource() == add ) {
			String name = nameField.getText();
			//If valid string, create a new profile and display it
			if(!isValidString(name)) return;
			FacePamphletProfile profile = new FacePamphletProfile(name);
			faceDB.addProfile(profile);
			faceCanvas.displayProfile(profile);
			faceCanvas.showMessage("New profile created");
			currentProfile = profile;
		}
		else if(e.getSource() == delete) {
			String name = nameField.getText();
			if(!isValidString(name)) return;
			//If profile exists, delete profile
			if(faceDB.containsProfile(name)) {
				//Clear the canvas
				faceCanvas.removeAll();
				faceCanvas.showMessage("Profile of " + name + " deleted");
				faceDB.deleteProfile(name);
				currentProfile = null;
			}
			else {
				faceCanvas.showMessage("A profile with the name " + name + " does not exist");
			}
		}
		else if(e.getSource() == lookup) {
			String name = nameField.getText();
			if(!isValidString(name)) return;
			//if profile exists, display it
			if(faceDB.containsProfile(name)) {
				FacePamphletProfile profile = faceDB.getProfile(name);
				faceCanvas.displayProfile(profile);
				faceCanvas.showMessage("Displaying " + name);
				currentProfile = profile;
			}
			//Let user know profile does not exist
			else {
				faceCanvas.showMessage("A profile with the name " + name + " does not  exist");
			}
		}
		else if(e.getSource() == statusField || e.getSource() == status) {
			String status = statusField.getText();
			if(!isValidString(status)) return;
			//Update status if status is not empty
			if(currentProfile != null) {
				currentProfile.setStatus(status);
				faceCanvas.displayProfile(currentProfile);
				faceCanvas.showMessage("Status updated to " + status);
			}
			else {
				faceCanvas.showMessage("Please select a profile to update status");
			}
		}
		//Check if the picture field is not blank
		else if(e.getSource() == picture || e.getSource() == pictureField) {
			String pictureName = pictureField.getText();
			if(!isValidString(pictureName)) return;
			if(currentProfile != null) {
				//Make image null, so it stays as no image
				GImage image = null;
				try {
					//If image exists, update the profile with the picture and display it
					image = new GImage(pictureName);
					currentProfile.setImage(image);
					faceCanvas.displayProfile(currentProfile);
					faceCanvas.showMessage("Picture updated");
				}
				catch(ErrorException ex) {
					faceCanvas.showMessage("Picture does not exist");
				}
			}
			else {
				faceCanvas.showMessage("Please select a profile to update picture");
			}
		}
		else if(e.getSource() == friend || e.getSource() == friendField) {
			String friendName = friendField.getText();
			if(!isValidString(friendName)) return;
			//If profile is chosen and friend exists, execute
			if(currentProfile != null) {
				if(faceDB.containsProfile(friendName)) {
					//Get the friends profile
					FacePamphletProfile friendProfile = faceDB.getProfile(friendName);
					//Add friend to current profile, and add current profile as a friend to the friendProfile
					currentProfile.addFriend(friendName);
					friendProfile.addFriend(currentProfile.getName());
					faceCanvas.displayProfile(currentProfile);
					faceCanvas.showMessage(friendName + " added as a friend");
				}
				else {
					faceCanvas.showMessage("A profile with the name " + friendName + " does not exist");
				}
			}
			else {
				faceCanvas.showMessage("Please select a profile to add a friend");
			}
		}
	}
    
    /** This method initializes the west side of the GUI */
    private void initWest() {
    	//Create the status fields
    	statusField = new JTextField(TEXT_FIELD_SIZE);
    	statusField.addActionListener(this);
    	add(statusField, WEST);
    	status = new JButton("Change Status");
    	add(status, WEST);
    	add(new JLabel(EMPTY_LABEL_TEXT), WEST);
    	
    	//Create the picture fields
    	pictureField = new JTextField(TEXT_FIELD_SIZE);
    	pictureField.addActionListener(this);
    	add(pictureField, WEST);
    	picture = new JButton("Change Picture");
    	add(picture, WEST);
    	add(new JLabel(EMPTY_LABEL_TEXT), WEST);
    	
    	//Create the friend fields
    	friendField = new JTextField(TEXT_FIELD_SIZE);
    	friendField.addActionListener(this);
    	add(friendField, WEST);
    	friend = new JButton("Add Friend");
    	add(friend, WEST);
    }
    
    /** This method initializes the NORTH side of the GUI */
    private void initNorth() {
    	JLabel nameLabel = new JLabel("Name");
    	add(nameLabel, NORTH);
    	//Create the textfield for the name
    	nameField = new JTextField(TEXT_FIELD_SIZE);
    	nameField.addActionListener(this);
    	add(nameField, NORTH);
    	//Create the buttons
    	add = new JButton("Add");
    	add(add, NORTH);
    	delete = new JButton("Delete");
    	add(delete, NORTH);
    	lookup = new JButton("Lookup");
    	add(lookup, NORTH);
    }
    
    /** Method that checks if a string is not whitespace only */
    private boolean isValidString(String str) {
    	str = str.trim();
    	if(str.equals("")) {
    		return false;
    	}
    	return true;
    }
    /** Instance Variables */
    private JTextField nameField;
    private JTextField statusField;
    private JTextField pictureField;
    private JTextField friendField;
    private JButton add;
    private JButton delete;
    private JButton lookup;
    private JButton status;
    private JButton picture;
    private JButton friend;
    
    private FacePamphletDatabase faceDB;
    private FacePamphletCanvas faceCanvas;
    private FacePamphletProfile currentProfile;
}

	
	