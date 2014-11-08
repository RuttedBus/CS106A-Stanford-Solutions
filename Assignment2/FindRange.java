/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	public void run() {
		println("This program finds the largest and smallest numbers.");
		readNumbersDoMath();
	}
	private static final int SENTINAL = 0;
	private void readNumbersDoMath() {
		 int largeInt = 0;
		 int smallInt = 0;
		while (true) {
			 int input = readInt("?");
			 if(input == SENTINAL) break;
			 if(largeInt == 0) {
				 largeInt = input;
				 smallInt = input;
			 }
			 if( input > largeInt) {
				 largeInt = 0;
				 largeInt = input;
						 
			 }
			 if(input < smallInt) {
				 smallInt = 0;
				 smallInt = input;
			 }
			
		}
		if(largeInt == 0 && smallInt == 0) {
			println("You entered SENTINAL first!");
		}
		else {
			println("smallest: " +smallInt);
			println("largest: " + largeInt);
		}
	}
}

