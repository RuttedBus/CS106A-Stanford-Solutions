/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 *
 * This file will eventually implement the game of Breakout.
 */
 
import acm.graphics.*;
import acm.program.*;
import acm.util.*;
 
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
 
public class Breakout extends GraphicsProgram {
 
    /**
     * Width and height of application window in pixels
     */
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 600;
 
    /**
     * Dimensions of game board (usually the same)
     */
    private static final int WIDTH = APPLICATION_WIDTH;
    private static final int HEIGHT = APPLICATION_HEIGHT;
 
    /**
     * Dimensions of the paddle
     */
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 10;
 
    /**
     * Offset of the paddle up from the bottom
     */
    private static final int PADDLE_Y_OFFSET = 30;
 
    /**
     * Number of bricks per row
     */
    private static final int NBRICKS_PER_ROW = 10;
 
    /**
     * Number of rows of bricks
     */
    private static final int NBRICK_ROWS = 10;
 
    /**
     * Separation between bricks
     */
    private static final int BRICK_SEP = 4;
 
    /**
     * Width of a brick
     */
    private static final int BRICK_WIDTH =
            (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;
 
    /**
     * Height of a brick
     */
    private static final int BRICK_HEIGHT = 8;
 
    /**
     * Radius of the ball in pixels
     */
    private static final int BALL_RADIUS = 10;
 
    /**
     * Offset of the top brick row from the top
     */
    private static final int BRICK_Y_OFFSET = 70;
 
    /**
     * Number of turns
     */
    private static final int NTURNS = 3;
   
    /**
     * The delay of the ball animation
     */
    private static final int BALL_ANIMATION_DELAY = 10;
    
/* Method: run() */
 
    /**
     * Runs the Breakout program.
     */
        public void run() {
            resize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
           
            for(int timesPlayed = 0; timesPlayed < NTURNS; timesPlayed++) {
            	setup();
            	addMouseListeners();
            	waitForClick();
            	playGame();
            	if(bricksHit == (NBRICKS_PER_ROW * NBRICK_ROWS)) {
            		playerWon();
            		break;
            	}
            	else if(timesPlayed == 2) {
            		playerLost();
            	}
            }
            
            
        }
        /** This method detects a mouse input, and every time it is detected, it moves the GRect. */
 
    public void mouseMoved(MouseEvent e) {
        /** This if statement tests whether or not the mouse xLocation is greater than or equal to half the PADDLE_WDITH, so
         * so that is does not go off screen.
         * It also checks that it is less than the width of the screen minus half of the paddle width, since the paddle can't
         * go off screen on the right side, and because the mouseX is in the middle of the paddle
         */
        if (e.getX() >= (PADDLE_WIDTH / 2) && e.getX() <= (WIDTH - (PADDLE_WIDTH / 2))) {
            /**To move the paddle, it takes in variable lastX, and subtract that from e.getX(). lastX is the last known location
             * in the x direction the mouse was.
             * For example, say lastX == 0. Player move mouse to 90, so e.getX() == 90. Since lastX was 100, and e.getX() == 90, that means the user moved
             * their paddle to the left 10 pixels. So 90 - 100 == -10. Since paddle.move() moves the paddle in x number of pixels, and y number of pixels, it moves 10 to the left, since -10 means lastX - 10.
             * It may seem tricky, but if they moved 10 to the right, and lastX == 100, e.getX == 110. 110 - 100 == 10, so the paddle moves 10 pixels to the right
             * the reason 0 is the parameter for the y- movement, is because the paddle never moves up or down.
             */
            paddle.move(e.getX() - lastX, 0);
            /** We set this.lastX to the mouseX(e.getX(), so that the next time this method is called, it knows the last location of where the mouse was, and can do the math correctly. */
            score.move(e.getX() - lastX, 0);
            this.lastX = e.getX();
        }
    }
 
    /*This method sets up the bricks and the paddle. */
    private void setup() {
        createPaddle(PADDLE_WIDTH, PADDLE_HEIGHT);
        createBall(BALL_RADIUS);
        /** Place bricks over ball, so getElementAt detects the bricks */
        createBricks(BRICK_WIDTH, BRICK_HEIGHT, NBRICKS_PER_ROW, NBRICK_ROWS, BRICK_SEP);
        /** Counter resets  each time setup is called ,in which means the ball went offscreen */
        this.bricksHit = 0;
        createScore();
    }
 
    /**
     * This method uses the GRect paddle, and centers it in the screen in the beginning, and sets instance variable lastX
     * to middle of paddle.
     *
     * @param paddleWidth  Takes in the width of the paddle the client wants it to be.
     * @param paddleHeight Takes in the height of the paddle the client wants it to be.
     */
    private void createPaddle(int paddleWidth, int paddleHeight) {
        this.paddle = new GRect(paddleWidth, paddleHeight);
        this.paddle.setLocation(WIDTH / 2 - paddleWidth / 2, HEIGHT - paddleHeight - PADDLE_Y_OFFSET);
        this.paddle.setFilled(true);
        add(this.paddle);
        this.lastX = WIDTH / 2;
    }
 
    /**
     * This method uses the instance variable GOval ball, and creates a ball that is set in the middle of the screen.
     * The ball is filled, and added using setLocation.
     *
     * @param ballRadius The radius the client wants the ball to be.
     */
    private void createBall(int ballRadius) {
        this.ball = new GOval(ballRadius * 2, ballRadius * 2);
        this.ball.setLocation((WIDTH / 2) - BALL_RADIUS, (HEIGHT / 2) - BALL_RADIUS);
        this.ball.setFilled(true);
        add(this.ball);
    }
   
    /** This method draws all the rows and columns of bricks, and colors them accordingly
     *
     * @param brickWidth The width of the bricks
     * @param brickHeight The height of the bricks
     * @param bricksPerRow The number of bricks in each row
     * @param brickRowNums The number of rows
     * @param brickSep The amount of pixels in between each brick
     */
   
    private void createBricks(int brickWidth, int brickHeight, int bricksPerRow, int brickRowNums, int brickSep) {
        /** The first counter for the row number */
        for (int i = 0; i < brickRowNums; i++) {
                /** The counter for each individual brick in the row, starting from left. */
                for(int j = 0; j < bricksPerRow; j++) {
                        /** To get the first y coordinate for the brick, we add the brick offset from the top. To get each new brick y coordinate, we multiply the row number(i), and add that to the y offset. We then also add
                         * the brick separation, to separate each brick by 4 pixels. */
                        int y = ((i * brickHeight) + (i * brickSep)) + BRICK_Y_OFFSET;
                        /** To find the first x coordinate, it divides the window in half, and subtracts hallf of the bricks length in total, to put half on one side. Since the first brick does not need separation, we do
                         * one less brick sep for the total amount of bricks in the row, and divide that in half, to get half of the separations. We then add the brick column number times the brick width, so the first x is
                         * 0, and add the brick column number times the separation
                         */
                        int x =  WIDTH/2 - (bricksPerRow * brickWidth)/2 - ((bricksPerRow - 1)*brickSep)/2 + j*brickWidth + j*brickSep;
                        GRect brick = new GRect(x, y, brickWidth, brickHeight);
                        brick.setFilled(true);
                        /**     Deciding what the bricks color will be, depending on what row they are in.
                          using a switch statement */
                                switch(i) {
                                case 0: case 1:
                                        brick.setColor(Color.RED);
                                        break;
                                case 2: case 3:
                                        brick.setColor(Color.ORANGE);
                                        break;
                                case 4: case 5:
                                        brick.setColor(Color.YELLOW);
                                        break;
                                case 6: case 7:
                                        brick.setColor(Color.GREEN);
                                        break;
                                case 8: case 9:
                                        brick.setColor(Color.CYAN);
                                        break;
                                }
                        add(brick);
                }
        }
    }
    /** Method that plays the game */
    private void playGame() {
        chooseVelocity();
        while(true) {
                moveBall();
                bounceBall();
                bounceOffObject();
                if(ball.getY() > HEIGHT || bricksHit == NBRICK_ROWS*NBRICKS_PER_ROW) {
                	removeAll();
                	break;
                }         
        }
    }
   
    private void chooseVelocity() {
        /** The y velocity is always 3.0 */
        this.vy = 3.0;
        /** The x velocity is between 1.0 pixels per move, or 3.0 pixels for move */
        this.vx = rgen.nextDouble(1.0, 3.0);
        /** If this random boolean test is true, the ball starts of moving to the left */
        if(rgen.nextBoolean(0.5)) {
                vx = -vx;
        }
    }
    /** This method moves the  ball */
    private void moveBall() {
       /** Moves the ball in x pixels and y pixels, plus or minus */
    	ball.move(this.vx, this.vy);
        pause(BALL_ANIMATION_DELAY);
    }
   
    /** Method that keeps track of the ball, and bounces when it hits a wall. */
    private void bounceBall() {
    	/** If the ball hits the left wall, or 0 in this case, reverse its direction.
    	 * If it hits the right wall, which is WIDTH, reverse its direction.
    	 */
    	if(ball.getX() <= 0 || (ball.getX() + BALL_RADIUS*2) >= WIDTH) {
                this.vx = -vx;
        }
        /** To bounce the ball of the top, if the top of the ball(ball.getY()) Ever passes the height of
         * the screen, I reverse the y- velocity(vy) so that it starts going down
         */
        if((ball.getY()) <= 0) {
                this.vy = -vy;
        }
    }
    /** Method that checks if the ball has collided with any object */
    private GObject getCollidingObject() {
        /** Checking top left corner of ball */
        if(getElementAt(ball.getX(), ball.getY()) != null) {
                return getElementAt(ball.getX(), ball.getY());
        }
        /** Checking bottom left corner of ball */
        else if(getElementAt(ball.getX(), (ball.getY() +(BALL_RADIUS*2))) != null) {
                return getElementAt(ball.getX(), (ball.getY() +(BALL_RADIUS*2)));
        }
        /** Checking top right corner of ball */
        else if(getElementAt((ball.getX() + (BALL_RADIUS*2)), ball.getY()) != null) {
                return getElementAt((ball.getX() + (BALL_RADIUS*2)), ball.getY());
        }
        /** Checking bottom right corner of ball. */
        else if(getElementAt(ball.getX() + (BALL_RADIUS*2), ball.getY() +(BALL_RADIUS *2 )) != null) {
                return getElementAt(ball.getX() + (BALL_RADIUS*2), ball.getY() +(BALL_RADIUS *2 ));
        }
        else {
              /** Ball hit nothing */
                 return null;      
        }
    }
   
    /** Method that bounces ball of paddle or brick */
    private void bounceOffObject() {
        /** Assigning a variable to hold the object of collision */
        GObject collider = getCollidingObject();
       /** What happens if the collider is a paddle */
        if(collider  == this.paddle) {
            /** We add 3, to make it seem so that if it does touch the paddle, it can reverse without glitching. 
             * Also does not allow player to hit with side of paddle. So, the reverse velocity y can escape the paddle */    
        	if(ball.getY() + BALL_RADIUS*2  <= paddle.getY() +vy ) {
        		 this.vy = - vy;
                }
        }
        /** In this case the collider would be a brick */
        else if(collider != null && collider != score) {
                remove(collider);
                this.vy = -vy;
                /** Adding the number of bricks hit, for each time it hits a brick */
                this.bricksHit++;
                /** Updating the score */
                scoreUpdater(collider.getColor());
                /** Setting the ball to the brick's it hit color */ 
                ball.setColor(collider.getColor());
        }
    }
    
    /** Method that bounces ball of paddle, pertaining the x coordinate, not the y */
    private void bounceOffPaddleX() {
    	/** First condition checks if ball is going right
    	 * Second condition checks if the middle of the ball is on the left side of the paddle, not including middle point
    	 * of the paddle. Reverses the vx to bounce back in opposite direction if true
    	 */
    	if(vx > 0 && ball.getX() + BALL_RADIUS < paddle.getX() + PADDLE_WIDTH/2) {
    		vx = -vx;
    	}
    	/** First condition checks if ball is moving to the right
    	 * Second condition checks if it hits the middle or right side of the paddle to reverse the vx if true.
    	 */
    	else if(vx < 0 && ball.getX() + BALL_RADIUS >= paddle.getX() + PADDLE_WIDTH/2) {
    		vx = -vx;
    	}
    }
    
    
    /** Method that updates the score, under the paddle */
    	private void createScore() {
    		/** Sets score to zero, since game has not started */
    		scoreCounter = 0;
    		score = new GLabel("" + scoreCounter);
    		/** Sets score in middle of screen pertaining x */
    		double x = (WIDTH/2 - score.getWidth()/2);
    		/** Sets score 15 pixels under paddle */
    		double y = (paddle.getY() + PADDLE_HEIGHT + 15);
    		score.setLocation(x, y);
    		score.setColor(Color.RED);
    		add(score);
    	}
    	/** Decides how much the score will be, depending on the brick it hit 
    	 * @param brickColor the color of the bric kit hit, to decide what points the player receives 
    	 */
    	private void scoreUpdater(Color brickColor) {
    		int addScore;
    		if(brickColor == Color.CYAN) {
    			addScore = 10;
    		}
    		else if(brickColor == Color.GREEN) {
    			addScore = 20;
    		}
    		else if(brickColor == Color.YELLOW) {
    			addScore = 30;
    		}
    		else if(brickColor == Color.ORANGE) {
    			addScore = 40;
    		}
    		else if(brickColor == Color.RED) {
    			addScore = 50;
    		}
    		else {
    			addScore = 0;
    		}
    		/** Adding the points to the score, even if it is zero */
    		this.scoreCounter += addScore;
    		score.setLabel("" + scoreCounter);
    		/** Setting the new label x, due to the label size increasing if the score goes up */
    		score.setLocation(paddle.getX() + (PADDLE_WIDTH/2) - (score.getWidth()/2), paddle.getY() + PADDLE_HEIGHT + 15);
    	}
    
    
    /** Displays the winning GLabel if player wins */
    private void playerWon() {
    	GLabel youWin = new GLabel("You win the game!");
    	youWin.setColor(Color.BLUE);
    	/** setting the message in middle of screen */
    	double x = WIDTH/2 - (youWin.getWidth()/2);
    	double y = (HEIGHT/2) - (youWin.getAscent()/2);
    	youWin.setLocation(x, y);
    	add(youWin);
    }
   
    /** Displays the losing GLabel if player loses */
    private void playerLost() {
    	GLabel youLose = new GLabel("You lose! Sorry!");
    	youLose.setColor(Color.RED);
    	double x = WIDTH/2 - (youLose.getWidth()/2);
    	double y = HEIGHT/2 - (youLose.getAscent()/2);
    	youLose.setLocation(x, y);
    	add(youLose);
    }
        /*Instance variables below */
    /**
     * The last location the mouse was
     */
    private double lastX;
 
    /**
     * The paddle the player uses
     */
    private GRect paddle;
 
    /**
     * The ball that moves on the screen
     */
    private GOval ball;
   
    /**
     * The velocities of the ball
     */
    private double vx, vy;
   
    /**
     * The number count of bricks hit 
     */
    private int bricksHit;
    
    /**  
     *  The way to get random numbers
     */
    private RandomGenerator rgen = RandomGenerator.getInstance();  
    
    /**
     * The score label
     */
    GLabel score;
    /** The score the player has */
    private int scoreCounter;
}