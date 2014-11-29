/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;


public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame() {
		//Array that holds the dice roll for a player
		int[] diceRoll = new int[N_DICE];
		//Array that holds players and their category scores
		playerCategoryScores = new int[nPlayers][N_CATEGORIES];
		//Array that holds the total score for each player
		playerTotalScores = new int[nPlayers];
		boolean[][] categoriesSelected = new boolean[nPlayers][N_CATEGORIES];
		for(int round = 0; round < N_SCORING_CATEGORIES; round++) {
			for(int turn = 0; turn < nPlayers; turn++) {
				//Tell player, with their name, to roll
				display.printMessage(playerNames[turn] + "'s turn. Click \"Roll Dice \" to roll.");
				display.waitForPlayerToClickRoll(turn + 1);
				//Put in all the dice rolls into the diceRoll array
				diceRoll = generateDiceRoll(diceRoll.length);
				display.displayDice(diceRoll);
				//Rerolling dice, player must select reroll
				for (int i = 0; i < 2; i++) {
					display.printMessage("Choose dice to reroll and click roll again twice, even if dice not selected.");
					display.waitForPlayerToSelectDice();
					display.displayDice(reRollDice(diceRoll));
				}
				//Compute score for the individual player
				int score = computeScores(diceRoll, categoriesSelected, turn);
				//Add that score to the players total score
				playerTotalScores[turn] += score;
				//Update the score for the individual player in the ctaegory they choose
				display.updateScorecard(N_CATEGORIES, turn + 1, playerTotalScores[turn]);
			}
		}
		//Print out the lower and upperscore and the bonus for each player
		for(int playerIndex = 0; playerIndex < nPlayers; playerIndex++) {
			//Calculate the lower and upper scores for each player
			int playerUpperScore = calculateUpperScore(playerCategoryScores, playerIndex); 
			int playerLowerScore = calculateLowerScore(playerCategoryScores, playerIndex);
			//Update lower and upper scores on scorecard
			display.updateScorecard(UPPER_SCORE, playerIndex + 1, playerUpperScore);
			display.updateScorecard(LOWER_SCORE, playerIndex + 1, playerLowerScore);
			//If player's upper score is greater than or 63, add 35 to total and bonus
			if( playerUpperScore >= 63) {
				playerTotalScores[playerIndex] += 35;
				display.updateScorecard(UPPER_BONUS, playerIndex + 1, 35);
			}
			else {
				display.updateScorecard(UPPER_BONUS, playerIndex + 1, 0);
			}
			//For that player, update their total score
			display.updateScorecard(TOTAL, playerIndex + 1, playerTotalScores[playerIndex]);
			printWinner(playerNames, playerTotalScores);
		}
	}
	/** This method generates a dice roll
	 * @param numDice The number of dice to generate for rolls for
	 * @return An array that holds the dice rolls
	 */
	private int[] generateDiceRoll(int numDice) {
		int[] rolls = new int[numDice];
		//For each die, go through and assign it a random number between 1 & 6
		for(int dieNumber = 0; dieNumber < numDice; dieNumber++) {
			rolls[dieNumber] = rgen.nextInt(1, 6);
		}
		return rolls;
	}
	/** A method that rerolls dice if the user chooses to do so
	 * @param dice The array that holds the diceRolls to be edited
	 * @return
	 */
	private int[] reRollDice(int[] dice) {
		//Goes through each die, and rerolls it if the die is selected
		for(int dieIndex = 0; dieIndex < dice.length; dieIndex++) {
			if(display.isDieSelected(dieIndex)) {
				dice[dieIndex] = rgen.nextInt(1, 6);
			}
		}
		return dice;
	}
	/** Method that computes the score of a category, and updates the scorecard to the new score
	 * @param dice The array that holds all the rolls
	 * @param categoriesSelected A 2D array that holds players and categories, at lets us know if the category has been selected
	 * @param playerIndex The player that we check all of this under
	 * @return
	 */
	private int computeScores(int[] dice, boolean[][] categoriesSelected, int playerIndex) {
		int category;
		display.printMessage("Select a scoring category for this roll.");
		while(true) {
			category = display.waitForPlayerToSelectCategory();
			//If the current player has already selected the chosen category, they need to choose again.
			if(categoriesSelected[playerIndex][category - 1]) {
			display.printMessage("That category is already selected! Select another one!");
			} 
			else {
				//Set the category to be chosen once the player chooses a new one
				categoriesSelected[playerIndex][category -1] = true;
				break;
			}
		}
		int score = 0;
		//Get an array that holds a count of all the number of outcomes, between 1 - 6, that the dice array has
		int[] numOfNumbers = numOfNumbers(dice);
		//Determine the score for the category the user has chosen
		switch(category) {
		//For ones and sixes, add all the numbers that are in that category, such as 2 6's being rolled adding up to 12 in category SIXES
		case ONES: 
			score = numOfNumbers[ONES - 1];
			break;
		case TWOS: 
			score = numOfNumbers[TWOS - 1] * 2;
			break;
		case THREES: 
			score = numOfNumbers[THREES - 1] * 3;
			break;
		case FOURS:
			score = numOfNumbers[FOURS - 1] * 4;
			break;
		case FIVES:
			score = numOfNumbers[FIVES - 1] * 5;
			break;
		case SIXES: 
			score = numOfNumbers[SIXES - 1] * 6;
			break;
		//For THREE_OF_A_KIND and FOUR_OF_A_KIND, add all the dice together if they are true
		case THREE_OF_A_KIND:
			//Checks that rolls are three of a kind
			if(isThreeOfKind(numOfNumbers)) {
				for(int i = 0; i < dice.length; i++) {
					score += dice[i];
				}
			}
			break;
		//Checks if rolls are four of a kind
		case FOUR_OF_A_KIND: 
			if(isFourOfKind(numOfNumbers)) {
				for(int i = 0; i < dice.length; i++) {
					score += dice[i];
				}
			}
			break;
		case FULL_HOUSE:
			//Check if rolls are a full house
			if(isFullHouse(numOfNumbers)) {
				score = 25;
			}
			break;
		case SMALL_STRAIGHT:
			//Check if rolls are a small straight
			if(isSmallStraight(numOfNumbers)) {
				score = 30;
			}
			break;
		//Check if rolls are a large straight
		case LARGE_STRAIGHT: 
			if(isLargeStraight(numOfNumbers)) {
				score = 40;
			}
			break;
		
		case YAHTZEE:
			//Check if all the rolls are the same 
			for(int i = 0; i < dice.length; i++) {
				if(numOfNumbers[i] == 5) {
					score = 50;
					break;
				}
			}
			break;
		//Simply add the value of al lthe dice together
		case CHANCE:
			for(int i = 0; i < dice.length; i++) {
				score += dice[i];
			}
			break;
		}
		playerCategoryScores[playerIndex][category] = score;
		display.updateScorecard(category, playerIndex + 1, score);
		return score;
	}
	/** Method that takes all the rolls, and returns an array that has a count for each value, 0 - 5, for rolls 1-6
	 * @param dice The array that holds all the dice rolls
	 * @return An array that holds a count of each roll
	 */
	private int[] numOfNumbers(int[] dice) {
		int[] numCount = new int[6];
		int number;
		//Go through each die, and assign the index of it's value to the new array numcount
		for(int i = 0; i < dice.length; i++) {
			number = dice[i];
			numCount[number - 1]++;
		}
		return numCount;
	}
	/** Method that checks if a set of rolled dice is a full house
	 * @param numOfNumbers The array that holds a count of each value that is rolled
	 * @return True if the array is a full house
	 */
	private boolean isFullHouse(int[] numOfNumbers) {
		boolean threeCount = false;
		boolean twoCount = false;
		//Go through each index of the numOfNumbers array, and check if one them contains two or three
		for(int index = 0; index < numOfNumbers.length; index++) {
			if(numOfNumbers[index] == 2) {
				twoCount = true;
			}
			else if(numOfNumbers[index] == 3) {
				threeCount = true;
			}
			//If they contain two of a kind and three of a kind, return true
			if(twoCount && threeCount) {
				return true;
			}
		}
		return false;
	}
	/** This method checks if a set of rolled dice is a three of a kind
	 * @param numOfNumbers Array that holds count of each value that is rolled
	 * @return True if the array is a three of a kind
	 */
	private boolean isThreeOfKind(int[] numOfNumbers) {
		//Check if any index is greater than or equal to three, so check if any number, such as 5, shows up 3 or more times
		for(int i = 0; i < numOfNumbers.length; i++) {
			if(numOfNumbers[i] >= 3) {
				return true;
			}
		}
		return false;
	}
	/** This method checks if a set of rolled dice is a four of a kind
	 * @param numOfNumbers Array that holds count of each value that is rolled
	 * @return True if the array is a four of a kind
	 */
	private boolean isFourOfKind(int[] numOfNumbers) {
		//Check if any index is greater than or equal to three, so check if any number, such as 5, shows up 3 or more times
		for(int i = 0; i < numOfNumbers.length; i++) {
			if(numOfNumbers[i] >= 4) {
				return true;
			}
		}
		return false;
	}
	/** This method checks if a set of rolled dice is a small straight
	 * @param numOfNumbers Array that holds counts of each value that is rolled
	 * @return True if the array is a small straight
	 */
	private boolean isSmallStraight(int[] numOfNumbers) {
		//Check for each possibility of a small straight, and see if it is true
		if(numOfNumbers[0] > 0 && numOfNumbers[1] > 0 && numOfNumbers[2] > 0 && numOfNumbers[3] > 0) {
			return true;
		}
		else if(numOfNumbers[1] > 0 && numOfNumbers[2] > 0 && numOfNumbers[3] > 0 && numOfNumbers[4] > 0) {
			return true;
		}
		else if(numOfNumbers[2] > 0 && numOfNumbers[3] > 0 && numOfNumbers[4] > 0 && numOfNumbers[5] > 0) {
			return true;
		}
		return false;
	}
	/** This method checks if a set of rolled dice is a large straight
	 * @param numOfNumbers Array that holds counts of each value that is rolled
	 * @return True if the array is a large straight
	 */
	private boolean isLargeStraight(int[] numOfNumbers) {
		//Check for each possibility of a large straight, and see if it is true
		if(numOfNumbers[0] > 0 && numOfNumbers[1] > 0 && numOfNumbers[2] > 0 && numOfNumbers[3] > 0 && numOfNumbers[4] > 0) {
			return true;
		}
		else if(numOfNumbers[1] > 0 && numOfNumbers[2] > 0 && numOfNumbers[3] > 0 && numOfNumbers[4] > 0 && numOfNumbers[5] > 0) {
			return true;
		}
		return false;
	}
	/** This method calculates the upper score of a player
	 * @param playerCategoryScores The array that holds all the players and their scores
	 * @param playerIndex The index of the first dimension, which is the player, to calculate score
	 * @return The upper score for an individual player
	 */
	private int calculateUpperScore(int[][] playerCategoryScores, int playerIndex) {
		int upperScore = 0;
		//Go through ONES - SIXES categories, and add their scores to upper score
		for(int categoryIndex = 0; categoryIndex <= SIXES; categoryIndex++) {
				upperScore += playerCategoryScores[playerIndex][categoryIndex];
			}
		return upperScore;
	}
	/** This method calculates the lower score for a player
	 * @param playerCategoryScores The array that holds all the players and their scores
	 * @param playerIndex The index of the first dimension, which is a layer
	 * @return The upper score for an individual player
	 */
	private int calculateLowerScore(int[][] playerCategoryScores, int playerIndex) {
		int lowerScore = 0;
		//Go through THREE_OF_A_KIND to CHANCE, and add up all those scores
		for(int categoryIndex = THREE_OF_A_KIND - 1; categoryIndex <= CHANCE; categoryIndex++) {
			lowerScore += playerCategoryScores[playerIndex][categoryIndex];
		}
	return lowerScore;
	}
	/** This method decides the winner, and prints a message telling them their score */
	private void printWinner(String[] playerNames, int[] playerTotalScores) {
		int topScore = 0;
		String topPlayer ="";
		//Scan through all the scores
		for(int playerIndex = 0; playerIndex < playerTotalScores.length; playerIndex++) {
			//If one score is higher than another, replace topScore and topPlayer with the new player.
			if(playerTotalScores[playerIndex] > topScore) {
				topScore = playerTotalScores[playerIndex];
				topPlayer = playerNames[playerIndex];
			}
		}
		//Print the message
		display.printMessage("Congratulations, " + topPlayer +", you're the winner with a total score of " + topScore + "!");
	}
/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private int[][] playerCategoryScores;
	private int[] playerTotalScores;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();

}
