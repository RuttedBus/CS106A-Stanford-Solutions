/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class ProgramHierarchy extends GraphicsProgram {	
	private static final int BOX_HEIGHT = 60;
	private static final int BOX_WIDTH = 150;
	public void run() {
		addProgram();
		addConsole();
		addGraphics();
		addDialog();
		consoleLine();
		graphicsLine();
		dialogLine();
	
	}
	private void addProgram() {
		//make GRect for Program
		int x = getWidth()/2 - BOX_WIDTH/2;
		int y = getHeight()/2 - BOX_HEIGHT;
		GRect rectProgram = new GRect(x, y, BOX_WIDTH, BOX_HEIGHT);
		add(rectProgram);
		
		//make program text, center it in box rectProgram
		GLabel program = new GLabel("Program", x, y);
		add(program);
		//get box height and width, divide height by two, and width by 2
		double getHalfWidth = BOX_WIDTH/2;
		double getHalfHeight = BOX_HEIGHT/2;
		// get half the width of program
		double centerX = program.getWidth()/2;
		//get half the height of program
		double centerY = program.getAscent()/2;
		// to get a, get center width of box, and subtract half of width of program, to move half to one side, half to other
		double a = (getHalfWidth - centerX);
		//to get b, get center height of box, and subtract half the height of program, to move it up to middle
		double b = (getHalfHeight + centerY);
		//move the word program by what a and b is
		program.move(a, b);
	}
	private void addConsole() {
		//create rectConsole, same x as rectProgram, y is BOX_HEIGHT more than rectProgram
		int x = getWidth()/2 - BOX_WIDTH/2;
		int y = getHeight()/2 + BOX_HEIGHT;
		GRect rectConsole = new GRect(x,y, BOX_WIDTH, BOX_HEIGHT);
		add(rectConsole);
		//create label that is ConsoleProgram
		GLabel console = new GLabel("ConsoleProgram", x, y);
		add(console);
		//get half width of box 
		double getHalfWidth = BOX_WIDTH/2;
		//get half height of box
		double getHalfHeight = BOX_HEIGHT/2;
		//get half width of ConsoleProgram
		double centerX = console.getWidth()/2;
		//get half the height of consoleProgram
		double centerY = console.getAscent()/2;
		// to get a, get center width of box, and subtract half of width of ConsoleProgram, to move half to one side, half to other
		double a = (getHalfWidth - centerX);
		//to get b, get center height of box, and subtract half the height of ConsoleProgram, to move it up to middle
		double b = (getHalfHeight + centerY);
		//move the word program by what a and b is
		console.move(a, b);
	}
	private void addGraphics() {
		int x = getWidth()/2 - (BOX_WIDTH/2) - (BOX_WIDTH + 20);
		int y = getHeight()/2 + BOX_HEIGHT;
		GRect rectGraphics = new GRect(x, y, BOX_WIDTH, BOX_HEIGHT);
		add(rectGraphics);
		
		//make GraphicsProgram text, center it in box rectGraphics
		GLabel GraphicsProgram = new GLabel("GraphicsProgram", x, y);
		add(GraphicsProgram);
		//get box height and width, divide height by two, and width by 2
		double getHalfWidth = BOX_WIDTH/2;
		double getHalfHeight = BOX_HEIGHT/2;
		// get half the width of GraphicsProgram
		double centerX = GraphicsProgram.getWidth()/2;
		//get half the height of GraphicsProgram
		double centerY = GraphicsProgram.getAscent()/2;
		// to get a, get center width of box, and subtract half of width of GraphicsProgram, to move half to one side, half to other
		double a = (getHalfWidth - centerX);
		//to get b, get center height of box, and subtract half the height of GraphicsProgram, to move it up to middle
		double b = (getHalfHeight + centerY);
		//move the word GraphicsProgram by what a and b is
		GraphicsProgram.move(a, b);
	}
	private void addDialog() {
		//create Box, same y as Console and Graphics, x is 20 greater than console
		int x = getWidth()/2 - BOX_WIDTH/2 + BOX_WIDTH + 20;
		int y = getHeight()/2 + BOX_HEIGHT;
		GRect rectDialog = new GRect(x, y, BOX_WIDTH, BOX_HEIGHT);
		add(rectDialog);
		// creating text DialogProgram, putting it at same x and y as rectDialog
		GLabel dialog = new GLabel("DialogProgram", x, y);
		add(dialog);
		//get half width of box 
		double getHalfWidth = BOX_WIDTH/2;
		//get half height of box
		double getHalfHeight = BOX_HEIGHT/2;
		//get half width of dialog
		double centerX = dialog.getWidth()/2;
		//get half the height of dialog
		double centerY = dialog.getAscent()/2;
		// to get a, get center width of box, and subtract half of width of dialog, to move half to one side, half to other
		double a = (getHalfWidth - centerX);
		//to get b, get center height of box, and subtract half the height of dialog, to move it up to middle
		double b = (getHalfHeight + centerY);
		//move the word program by what a and b is
		dialog.move(a, b);
	}
	private void consoleLine() {
		//creating a line from Program to Console Program
		//get x coordinate of Console program and program, divide it by two to get middle of box
		//we getWidth, and subtract BOXWIDTH/2, to get x point of program box, we then add boxwidth/2, to get point in middle
		int x = (getWidth()/2 - (BOX_WIDTH/2)+ BOX_WIDTH/2);
		//get y-coordinate of program, and get the bottom of it
		int y = getHeight()/2;
		//get y-coordinate of ConsoleProgram
		int y1 = getHeight()/2 + BOX_HEIGHT;
		//make line ConsoleLine
		GLine consoleLine = new GLine(x, y, x, y1);
		add(consoleLine);
	}
	private void graphicsLine() {
		int x = getWidth()/2 - (BOX_WIDTH/2) +(BOX_WIDTH/2);
		int y = getHeight()/2;
		//to get x1, we get width, divide it by 2, and subtract half width of box.
		//we now have x-coordinate of Program. Next, we subtract 20, to move it left 20, since that is the side distance
		//between middle and left, and subtract half box width, to get middle of Graphic box
		int x1 = getWidth()/2 -BOX_WIDTH/2 - 20 - (BOX_WIDTH/2);
		int y1 = getHeight()/2 + BOX_HEIGHT;
		GLine graphicLine = new GLine(x, y, x1, y1);
		add(graphicLine);
		
	}
	private void dialogLine() {
		//get center x of program box
		int x = getWidth()/2 - (BOX_WIDTH/2) +(BOX_WIDTH/2);
		int y = getHeight()/2;
		//we get middle point of DIalog same way we get graphics middle point ,but we add BOXWIDTH/2 and 20, to get it 
		//on the right side
		int x1 = getWidth()/2 +(BOX_WIDTH/2) + 20 +(BOX_WIDTH/2);
		int y1 = getHeight()/2 + BOX_HEIGHT;
		GLine dialog = new GLine(x, y, x1, y1);
		add(dialog);
			
	}
}
	


