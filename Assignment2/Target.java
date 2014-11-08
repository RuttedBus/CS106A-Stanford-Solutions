/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;

import java.awt.*;

public class Target extends GraphicsProgram {	
	private static final double bigRadius = 72;
	private static final double mediumRadius = 46.8;
	private static final double smallRadius = 21.6;
	public void run() {
		addOutside();
		addMiddle();
		addCenter();
		
	}
	private void addOutside() {
		double  x = getWidth()/2 - bigRadius;
		double y = getHeight()/2 - bigRadius;
		double diameter = bigRadius * 2;
		
		GOval outSideCircle = new GOval(x, y, diameter, diameter);
		outSideCircle.setFillColor(Color.red);
		outSideCircle.setFilled(true);
		add(outSideCircle);
	}
	private void addMiddle() {
		double x = getWidth()/2 - mediumRadius;
		double y = getHeight()/2 - mediumRadius;
		double diameter = mediumRadius * 2;
		GOval middleCircle = new GOval(x, y, diameter, diameter);
		middleCircle.setFilled(true);
		middleCircle.setFillColor(Color.white);
		add(middleCircle);
	}
	private void addCenter() {
		double x = getWidth()/2 - smallRadius;
		double y = getHeight()/2 - smallRadius;
		double diameter = smallRadius * 2;
		GOval smallCircle = new GOval(x, y, diameter, diameter);
		smallCircle.setFilled(true);
		smallCircle.setFillColor(Color.RED);
		add(smallCircle);
	}
	
}
