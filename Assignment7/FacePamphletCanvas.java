/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		lastY = 0;
		lastX = 0;
		nameH = 0;
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		GLabel message = new GLabel(msg);
		double x = getWidth()/2 - (message.getWidth()/2);
		double y = getHeight() - BOTTOM_MESSAGE_MARGIN - message.getAscent();
		message.setFont(MESSAGE_FONT);
		if(getElementAt(lastX, lastY) != null) {
			remove(getElementAt(lastX, lastY));
		}
		add(message, x, y);
		lastX = x;
		lastY = y;
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		displayName(profile.getName());
		displayImage(profile.getImage());
		displayStatus(profile.getStatus());
		displayFriends(profile.getFriends());
	}
	
	/**Method that displays the name for a profile
	 * @param name The name to display
	 */
	private void displayName(String name) {
		GLabel nameLabel = new GLabel(name);
		nameH = nameLabel.getHeight();
		double x = LEFT_MARGIN;
		double y = TOP_MARGIN + nameH;
		nameLabel.setColor(Color.BLUE);
		nameLabel.setFont(PROFILE_NAME_FONT);
		add(nameLabel, x, y);
	}
	
	/**Method that displays an image to the canvas
	 * @param image The image to be displayed
	 */
	private void displayImage(GImage image) {
		//If image does not exist, draw a rectangle with no image available in it
		if(image == null) {
			GRect rect = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
			double y = TOP_MARGIN + IMAGE_MARGIN + nameH;
			double x = LEFT_MARGIN;
			add(rect, x ,y);
			
			GLabel label = new GLabel("No Image");
			label.setFont(PROFILE_IMAGE_FONT);
			//Center the no image label in rectangle
			x = x + (rect.getWidth()/2) - label.getWidth()/2;
			y = y + (rect.getHeight()/2) + label.getAscent()/2;
			label.setFont(PROFILE_IMAGE_FONT);
			add(label, x, y);
		}
		//If image is available, add it to the canvas
		else {
			double x = LEFT_MARGIN;
			double y = TOP_MARGIN + IMAGE_MARGIN + nameH;
			add(image, x, y);
		}
	}
	
	/**Method that displays the current status of the profile
	 * @param status The status to be displayed 
	 */
	private void displayStatus(String status) {
		//Decide the status label
		GLabel statusLabel;
		if(status.equals("")) {
			statusLabel = new GLabel("No current status");
			statusLabel.setFont(PROFILE_STATUS_FONT);
		}
		else {
			statusLabel = new GLabel(status);
			statusLabel.setFont(PROFILE_STATUS_FONT);
		}
		double x = LEFT_MARGIN;
		//Y coordinate is set 20 + status height under the image
		double y = TOP_MARGIN + IMAGE_MARGIN + nameH + STATUS_MARGIN + IMAGE_HEIGHT + statusLabel.getHeight();
		add(statusLabel, x, y);
	}
	
	/**Method that displays a friends list for a profile */
	private void displayFriends(Iterator<String> it) {
		//Create label for friends
		GLabel friends = new GLabel("Friends:");
		double x = getWidth()/2;
		//Get the y coordinate for the friend label
		double y = TOP_MARGIN + nameH + IMAGE_MARGIN - friends.getAscent(); 
		friends.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friends, x, y);
		int counter = 0;
		//While friends is a list, add friends name, and their height times the counter. X is the same
		while(it.hasNext()) {
			String friendName = it.next();
			GLabel label = new GLabel(friendName);
			label.setFont(PROFILE_FRIEND_FONT);
			counter++;
			double labelY = y + label.getHeight() * counter;
			add(label, x, labelY);
		}
	}
	
	
	
	/**Instance Variables */
	private double lastX, lastY;
	private double nameH;
}
