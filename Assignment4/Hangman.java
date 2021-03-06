/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;

public class Hangman extends ConsoleProgram {
	private static final int MAX_TURNS = 8;
	private static final int APPLICATION_WIDTH = 800;
	private static final int APPLICATION_HEIGHT = 700;
	
    public void run() {
    	/** While this loop runs, the setup and game play */
    	while(true) {
    	setUpGame();
    	playHangman();
    	/** Loop to get the users input */
    	while (true) {
    	String yesOrNo = readLine("Enter yes or no to play again.");
   
    		/** If the users input is not yes or no, make them retype it */
    		if(!(yesOrNo.equalsIgnoreCase("Yes") || yesOrNo.equalsIgnoreCase("No"))) {
    			println("You must enter yes or no.");
    		}
    		/** If the users input is yes,  playAgain is true, and the user input loop exits */
    		else if(yesOrNo.equalsIgnoreCase("Yes")) {
    			this.playAgain = true;
    			break;
    		}
    		/** If user enters no, the playAgain loop is false, and user input loop exits */
    		else if(yesOrNo.equalsIgnoreCase("No")){
    			this.playAgain = false;
    			break;
    			}
    		}
    	/** If the playAgain is false, which means the user no longer wants to play, exit the loop of the program. 
    	 * Else, continue to play the game.
    	 */
    	if(!playAgain) {
    		break;
    		}
    	else {
    		println("---------------");
    		println("---------------");
    		}
    	}
    }
    /** Method that sets up the beginning of the game */
    private void setUpGame() {
    	/** Choosing a random word */
    	this.word  = chooseWord();
    	/** Making another String that holds the dashes */
    	makeDashedWord(this.word);
    	/** Setting the guessCount to 8, so that the user can start of with max turns */
    	this.guessCount = MAX_TURNS;
    	/**Set the window size to the size we want, then pause it for 1 second */
    	setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
    	pause(1000);
    	println("Welcome to Hangman!");
    	this.allGuessedLetters = "";
    	canvas.reset();
    	/** Set the word on the canvas to the dashedWord */
    	canvas.displayWord(dashedWord);
    }
    
    /** This method chooses a random word that the player will have to guess
     * @return The random word that was chosen 
     */
    private String chooseWord() {
    	HangmanLexicon lexicon = new HangmanLexicon();
    	/** Subtract 1 from the possible values, since java counts begin from 0 */
    	int randomIndex = rgen.nextInt(0, lexicon.getWordCount()-1);
    	/** Find a word at randomIndex */
    	String randomWord = lexicon.getWord(randomIndex);
    	/** Make the word upper case */
    	randomWord.toUpperCase();
    	return randomWord;
    }
    
    /**This method takes a String, and creates a String that is a dashed version of it
     * @param word The word the client wants to be dashed 
     */
    private void makeDashedWord(String word) {
    	String dashes = "";
    	/** For every letter in the word, make a dash to replace it */
    	for(int i = 0; i < word.length(); i++) {
    		dashes += "-";
    	}
    	dashedWord = dashes;
    }
    
    /** This method plays the Hang man game */
    private void playHangman() {
    	while(guessCount > 0) {
    		println("The word now looks like this: " + dashedWord);
    		/** If only one guess is remaining change the println for showing guesses remaining */
    		if(guessCount == 1) {
    			println("You have one guess left.");
    		}
    		else {	
    		println("You have " + guessCount + " guesses left.");
    		}
    		/** While loop that runs while the guess is not valid */
    		while(true) {
    			String guess = readLine("Your guess: ");
    			/** Checks if the guess is allowed in hang man */
    			/** If guess is not allowed, run the loop again */
    			if(!isValidString(guess)) {
    				println("That guess is invalid! Only one letter guesses are allowed!");
    			}
    			/** IF the player already guessed the letter, tell them they already guessed, and make them guess again */
    			/** Temporarily make the guess upper case, so that it can be found in the all upper case String */
    			else if(this.allGuessedLetters.indexOf(guess.toUpperCase()) != -1) {
    				println("You already guessed that letter! Guess again!");
    			}
    			/** If guess if allowed, break out of the loop */
    			else {
    				/** Assigns the letterGuessed variable to an upper case version of the letter guess, if not upper case already */
    				this.letterGuessed = guess.toUpperCase();
    				break;
    			}
    		}
    		/** Adds the letter to a list of guessed letters, so users cannot reguess it */
    		this.allGuessedLetters += letterGuessed;
    		/** If the letter is in the word, replace the dashedString, and add the letters, into it */
    		if(checkLetterInString(this.word, this.letterGuessed)) {
    			this.dashedWord = addLetterToString(this.word, this.letterGuessed);
    			println("That guess is correct.");
    			/** Update the word on the canvas */
    			canvas.displayWord(dashedWord);
    			/** If the player has guessed the word, the player wins and the loop breaks */
    			if(word.equals(dashedWord)) {
    				println("You win!");
    				println("The word was: " + word);
    				break;
    			}
    		}
    		/** If the guess was incorrect, the number of guesses remaining goes down */
    		else {
    			println("There are no " + letterGuessed + "'s in the word");
    			println("That guess is incorrect.");
    			/** On the String with incorrect guesses on the canvas, add the letter to it */
    			canvas.noteIncorrectGuess(letterGuessed.charAt(0));
    			this.guessCount--;
    		}
    	}
    	/** If the game ended and no guesses remain, tell the player they lost */
    	if(guessCount == 0) {
    		println("You are completely hung.");
    		println("You lose.");
    		println("The word was: " + word);
    	}
    }
    /** This method checks whether or not a String is a valid hang man guess
     * @param str The string the client wants to check for validity
     * @return If the string is valid, return true 
     */
    private boolean isValidString(String str) {
    	/** If the string is more than one character, or if the String contains any non-letters, the if statement is true */
    	if(str.length() != 1 || !Character.isLetter(str.charAt(0))) {
    		return false;
    	}
    	return true;
    }
    
    /** Method that checks if a letter is in a String 
     * @param str The String the user wants to search through
     * @param letter The letter the user wants to find
     * @return True if the letter is in the String
     */
    private boolean checkLetterInString(String str, String letter) {
    	/** If the letter is in the word, return true */
    	if(str.indexOf(letter) != -1) {
    		return true;
    	}
    	return false;
    }
    /** Method that adds letter to a String, if it is in there 
     * @param word The word the client wants letters added to 
     * @param letter The letter the client wants to add
     * @return The new word with added letters
     */
    private String addLetterToString(String word, String letter) {
    	String newWord = dashedWord;
    	for(int i = 0; i < word.length(); i++ ) {
    		char ch = word.charAt(i);
    		/** If the letter is in the word at 0, then add the dashes to the letter */
    		if(ch == letter.charAt(0)) {
    			/** If the letter is the first letter, simply add empty space to it, so newWord is only that letter for first run */
    			if(i == 0) {
    				newWord = letter + newWord.substring(1);
    			}
    			/** IF the letter is not first, add the substring behind the letter index, and the letter, and then add everything after it. */
    			else if(i > 0) {
    				newWord = newWord.substring(0, i) + letter + newWord.substring(i+ 1);
    			}
    		}
    	}
    	return newWord;
    }
    
    public void init() {
    	canvas = new HangmanCanvas();
    	add(canvas);
    }
    
    /** Instance Variables below */
    
    /** Random Generator variable */
    RandomGenerator rgen = RandomGenerator.getInstance();
    /** Variable that holds the number of guesses player has left. */
    private int guessCount;
    /** Instance variable that holds the word user is trying to guess */
    private String word;
    /**Instance variable that holds the dashed String */
    private String dashedWord;
    /** The letter that the player guessed */
    private String letterGuessed;
    /** The canvas to call in between methods */
    private HangmanCanvas canvas;
    /** A string containing all letters guess */
    private String allGuessedLetters;
    /** Boolean for yes or no to whether or not the player wants to replay */
    private boolean playAgain;
}
