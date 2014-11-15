/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

/** Resets the display so that only the scaffold appears */
	public void reset() {
		drawScaffold();
	}
/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		/** Set the beginning of the label one head radius away from the left side of the screen */
		int x = HEAD_RADIUS;
		/** Set the top of the label 1 and a half head diameters away from the footY */
		int y = getHeight()/2 + LEG_LENGTH + HEAD_RADIUS*3;
		GLabel stateOfWord = new GLabel(word, x, y);
		stateOfWord.setFont("Calibri-36");
		if(getElementAt(x, y) != null) {
			remove(getElementAt(x,y));
		}
		add(stateOfWord);
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter) {
		wrongGuessedLetters += letter;
		/** Set the x of the label the head radius away from the left of the screen */
		int x = HEAD_RADIUS;
		/** Set the top of the label 1 and a half head diameters away from the foot */
		int y = getHeight()/2 + LEG_LENGTH + HEAD_RADIUS*4;
		GLabel incorrectLetters = new GLabel(wrongGuessedLetters, x, y);
		incorrectLetters.setFont("Calibri-24");
		/** If the label is already on the screen, remove it and add the new one */
		if(getElementAt(x,y) != null) {
			remove(getElementAt(x,y));
		}
		add(incorrectLetters);
		/** Switch statement that draws a body part according to how many incorrect guesses there are */
		switch(wrongGuessedLetters.length()) {
		case 1: drawHead(); break;
		case 2: drawBody(); break;
		case 3: drawLeftArm(); break;
		case 4: drawRightArm(); break;
		case 5: drawLeftLeg(); break;
		case 6: drawRightLeg(); break;
		case 7: drawLeftFoot(); break;
		case 8: drawRightFoot(); break;
		}
	}
	
	private void drawScaffold() {
		drawHangingStick();
		drawBeam();
		drawRope();
	}
	/** This method draws the stick from which the man hangs */ 
	private void drawHangingStick() {
		/** Setting x so that the beam is in the middle */
		int x = getWidth()/2 - BEAM_LENGTH;
		/** Set the top of the scaffold to where the rope would begin */
		int topY = getHeight()/2 - ROPE_LENGTH - HEAD_RADIUS*2 - BODY_LENGTH;
		/** From where the scaffold starts, add the scaffold height */
		int bottomY = topY + SCAFFOLD_HEIGHT;
		GLine hangingStick = new GLine(x, topY, x, bottomY );
		add(hangingStick);
	}
	/** This method draws the beam connected to the hanging stick */
	private void drawBeam() {
		/** Set the left side of the stick to where the x of the hanging stick is */
		int leftX = getWidth()/2 - BEAM_LENGTH;
		/** Set the right side of the beam to where the beam ends */
		int rightX = leftX + BEAM_LENGTH;
		/** Set the height of the beam to where the hanging stick height begins */
		int y = getHeight()/2 - ROPE_LENGTH - HEAD_RADIUS*2 - BODY_LENGTH;
		GLine beam = new GLine(leftX, y, rightX, y);
		add(beam);
	}
	/** This method draws the rope */
	private void drawRope() {
		/** Set the rope in the middle */
		int x = getWidth()/2;
		/** Set the top of the rope to where the beam height is */
		int topY = getHeight()/2 - ROPE_LENGTH - HEAD_RADIUS*2 - BODY_LENGTH;
		/** Set the bottom of the rope the rope length away from the top */
		int bottomY = topY + ROPE_LENGTH;
		GLine rope = new GLine(x, topY, x, bottomY);
		add(rope);
	}
	/** This method draws the head */
	private void drawHead() {
		/** Set the middle of the head to the middle of the screen */
		int x = getWidth()/2 - HEAD_RADIUS;
		/** Set the top of the head to where the rope ends */
		int y = getHeight()/2 - BODY_LENGTH - HEAD_RADIUS*2;
	    GOval head = new GOval(x, y, HEAD_RADIUS*2, HEAD_RADIUS*2);
	    add(head);
	}
	/** This method draws the body */
	private void drawBody() {
		/** Set the body in the middle of the screen */
		int x = getWidth()/2;
		/** Set the top of the body to where the bottom of the head is */
		int topY = getHeight()/2 - BODY_LENGTH;
		/** Set the bottom of the body body length pixels away from the top of the body */
		int bottomY = topY + BODY_LENGTH;
		GLine body = new GLine(x, topY, x, bottomY);
		add(body);
	}
	/** This method draws the left arm */
	private void drawLeftArm() {
		/** Set the arm to be the arm length away from the body */
		int armXLeft = getWidth()/2 - UPPER_ARM_LENGTH;
		/** Set the other side of the arm to be on the body */
		int armXRight = armXLeft + UPPER_ARM_LENGTH;
		/** Set where the armY is head radius from the middle of the body*/
		int armY = getHeight()/2 - BODY_LENGTH/2 - ARM_OFFSET_FROM_HEAD;
		GLine arm = new GLine(armXLeft, armY, armXRight, armY);
		add(arm);
		/** Set the x of the hand where the left side of the arm is */
		int handX = armXLeft;
		/** Set the top of the hand to where the arm is */
		int handTopY = armY;
		/** Set the bottom for the y of the hand to lower arm length pixels away from the top */
		int handBottomY = armY + LOWER_ARM_LENGTH;
		GLine hand = new GLine(handX, handTopY, handX, handBottomY);
		add(hand);
	}
	/** This method draws the right arm */
	private void drawRightArm() {
		/** Set the arm to be the arm length away from the body */
		int armXRight = getWidth()/2 + UPPER_ARM_LENGTH;
		/** Set the other side of the arm to be on the body */
		int armXLeft = armXRight - UPPER_ARM_LENGTH;
		/** Set where the armY is head radius from the middle of the body*/
		int armY = getHeight()/2 - BODY_LENGTH/2 - ARM_OFFSET_FROM_HEAD;
		GLine arm = new GLine(armXRight, armY, armXLeft, armY);
		add(arm);
		/** Set the x of the hand where the right side of the arm is */
		int handX = armXRight;
		/** Set the top of the hand to where the arm is */
		int handTopY = armY;
		/** Set the bottom for the y of the hand to lower arm length pixels away from the top */
		int handBottomY = armY + LOWER_ARM_LENGTH;
		GLine hand = new GLine(handX, handTopY, handX, handBottomY);
		add(hand);
	}
	
	/** This method draws the left leg */
	private void drawLeftLeg() {
		/** Setting the x of the left leg to the left side of the hip */
		int legX = getWidth()/2 - HIP_WIDTH;
		/** Setting the top of the leg to where the the middle of height is, also where the hipY is */
		int topY = getHeight()/2;
		/** Setting the bottom of the leg leg length pixels away from the top */
		int bottomY = topY + LEG_LENGTH;
		GLine leftLeg = new GLine(legX, topY, legX, bottomY);
		add(leftLeg);
		/** Setting the hip where the bottom of the leg is */
		int hipY = topY;
		/** Setting the hip to where the leg begins */
		int hipXLeft = legX;
		/** Setting the right side of the hip hip width away from the left side */
		int hipXRight = hipXLeft + HIP_WIDTH;
		GLine hip = new GLine(hipXLeft, hipY, hipXRight, hipY);
		add(hip);
	}
	/** This method draws the right leg */
	private void drawRightLeg() {
		/** Setting the x of the right leg to the right side of the hip */
		int legX = getWidth()/2 + HIP_WIDTH;
		/** Setting the top of the leg to where the the middle of height is, also where the hipY is */
		int topY = getHeight()/2;
		/** Setting the bottom of the leg leg length pixels away from the top */
		int bottomY = topY + LEG_LENGTH;
		GLine rightLeg = new GLine(legX, topY, legX, bottomY);
		add(rightLeg);
		/** Setting the hip where the bottom of the leg is */
		int hipY = topY;
		/** Setting the hip to where the leg begins */
		int hipXRight = legX;
		/** Setting the left side of the hip hip width away from the right side */
		int hipXLeft = hipXRight - HIP_WIDTH;
		GLine hip = new GLine(hipXLeft, hipY, hipXRight, hipY);
		add(hip);
	}
	/** This method draws the left foot */
	private void drawLeftFoot() {
		/** First set the left side of foot to where the legX is, then subtract the length of the foot */
		int footXLeft = getWidth()/2 - HIP_WIDTH - FOOT_LENGTH;
		/** Set the right side of left foot leg_length pixels away from the left side of the foot */
		int footXRight = footXLeft + FOOT_LENGTH;
		/** Set the y of the foot where the leg is */
		int footY = getHeight()/2 + LEG_LENGTH;
		GLine leftFoot = new GLine(footXLeft, footY, footXRight, footY);
		add(leftFoot);
	}
	/** This method draws the right foot */
	private void drawRightFoot() {
		/** First set the left side of foot to where the legX is */
		int footXLeft = getWidth()/2 + HIP_WIDTH;
		/** Set the right side of right foot leg_length pixels away from the left side of the foot */
		int footXRight = footXLeft + FOOT_LENGTH;
		/** Set the y of the foot where the leg is */
		int footY = getHeight()/2 + LEG_LENGTH;
		GLine rightFoot = new GLine(footXLeft, footY, footXRight, footY);
		add(rightFoot);
	}
	/* String for letters wrongly guessed */
	private String wrongGuessedLetters = "";
/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

}
