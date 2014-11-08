/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	public void run() {
		int input = readInt("Enter an integer: ");
		while(input != 1) {
			if(input % 2 == 0) {
				print(input + " is even, so I take half: "  );
				input /= 2;
				println(input);
				
			}
			else {
				print(input +" is odd, so I make 3n + 1: " );
				input = (input  * 3) + 1;
				println(input);
			}
				
		}
	}
}

