/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	public void run() {
		println("Enter values to compute Pythagorean thereom.");
		getIntsDoCalculate();
		
		
	}
	private void getIntsDoCalculate() {
		int a = readInt("a:");
		int b = readInt("b:");
		double c = (double) (a * a) + (b * b);
		double sqrt = Math.sqrt(c);
		println("c = " + sqrt);
	}

}
