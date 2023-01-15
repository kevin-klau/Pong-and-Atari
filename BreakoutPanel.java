import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class BreakoutPanel extends JPanel {
	// Properties
	int intPaddleX = 343;
	int intPaddleMovement = 10;
	int intMouseX = 0;
	
	int intBallX = 400;
	int intBallY = 525;
	int intBallMovementX = 0;
	int intBallMovementY = 0;
	int intBallWillMovementX = 0;
	int intBallWillMovementY = 0;
	
	int intBallReleased = 0;
	
	String strBlocks[][] = new String [10][3];
	
	boolean blnBlocksFilled = true;
	
	int intBlocksCount = 30;
	int intLives = 3;
	
	BufferedImage Paddle;
	BufferedImage Ball;
	BufferedImage Back1;
	BufferedImage Back2;
	BufferedImage Back3;
	BufferedImage Win;
	BufferedImage Lose;
	
	boolean blnMouseOn = true;
	boolean blnGameStart = false;
	boolean blnGaming = true; 
	
	Font newFont = null;
	
	int intPowerUpCount = 3;
	// Methods
	public void paintComponent (Graphics g){
		super.paintComponent(g);		
		
			if (blnGaming == true){
			// Set the values of strBlocks[][] to just be "Filled"
			int intCount;
			int intCounter;
			
			if (blnBlocksFilled == true){
				for (intCounter = 0; intCounter < 3; intCounter++){
					for (intCount = 0; intCount < 10; intCount++){
						strBlocks[intCount][intCounter] = "filled";
					}		
				}
				
				blnBlocksFilled = false;
				
				// Reset the Values
				intBlocksCount = 30;
				intLives = 3;
				intPowerUpCount = 3;			
			}
			
			// Make the Background
			if (intBlocksCount <= 30 && intBlocksCount >= 21){
				g.drawImage (Back1, 0,0,this);
			} else if (intBlocksCount < 21 && intBlocksCount >= 11){
				g.drawImage (Back2, 0,0,this);
			} else if (intBlocksCount < 11){
				g.drawImage (Back3, 0,0,this);
			}
			
			//g.setColor (Color.BLACK);
			//g.fillRect (0,0,800,45);
			g.setColor (Color.WHITE);
			g.fillRect (0,45,800,5);
			
			// Draw the String stating the amount of blocks and lives left
			
			g.setFont(newFont.deriveFont(Font.PLAIN, 23));
			g.setColor (Color.WHITE);
			g.drawString ("Blocks Left: "+intBlocksCount, 10,33);
			g.drawString ("Lives Left: "+intLives, 485,33);
			
			
			// Draw the Rectangles
			for (intCounter = 0; intCounter < 3; intCounter++){
				for (intCount = 0; intCount < 10; intCount++){
					if (strBlocks[intCount][intCounter].equalsIgnoreCase ("Filled")){
						if (intCounter == 0){
							g.setColor (new Color (255,71,83));
						} else if (intCounter == 1){
							g.setColor (new Color (239,226,53));
						} else if (intCounter == 2){
							g.setColor (new Color (72,78,225));
						}
						g.fillRect (intCount*80, intCounter*35+60+50, 80, 35);
					}
				}
			}		
			
			// Change the Paddle Add or Subtract (make sure the centre of the paddle follows the mouse)
			if (intMouseX > intPaddleX+75){
				intPaddleMovement = 15;
			}else if(intMouseX <intPaddleX+60){
				intPaddleMovement = -15;
			}else{
				intPaddleMovement=0;
			}
			
			// Set the Boundaries for Paddle
			if (intPaddleX + intPaddleMovement < 0){
				intPaddleX = 0;
				intPaddleMovement = 0;
			}else if (intPaddleX + intPaddleMovement > 800-115){
				intPaddleX = 800-115;
				intPaddleMovement = 0;
			}
			
			// Set the boundaries for the ball
			if (intBallX + intBallMovementX > 800 - 15){
				intBallMovementX = -10;
			} else if (intBallX + intBallMovementX < 0){
				intBallMovementX = 10;
			}
			
			if (intBallY + intBallMovementY < 0+50){
				intBallMovementY = 10;
			}
			
			// Set Ball Interaction With Paddle
			if (intBallY + intBallMovementY > 540-15 && intBallX+intBallMovementX + 15 > intPaddleX+intPaddleMovement && intBallX+intBallMovementX < intPaddleX+intPaddleMovement+115 && intBallY + intBallMovementY < 540){
				intBallMovementY = -10;
			}
			
			boolean blnOneTime = false;
			// Set Ball Interaction with the blocks
			for (intCounter = 0; intCounter < 3; intCounter++){
				for (intCount = 0; intCount < 10; intCount++){
					if (strBlocks[intCount][intCounter].equalsIgnoreCase ("Filled") && blnOneTime == false){
						if (intBallX+intBallMovementX < intCount*80+80 && intBallX+intBallMovementX > intCount*80+65 && intBallY < intCounter*35+60+50+35 && intBallY+intBallMovementY > intCounter*35+60+50 - 15){
							// Block Right Side Collision
							intBallWillMovementX = 10;
							intBallWillMovementY = intBallMovementY;
							strBlocks[intCount][intCounter] = "Empty";
							//System.out.println ("RIGHT SIDE: ROW"+ intCount + " | Column : "+ intCounter);
							blnOneTime = true;
						}else if (intBallX+intBallMovementX + 15 > intCount*80 && intBallX+intBallMovementX + 15 < intCount*80+11 && intBallY+intBallMovementY  < intCounter*35+60+50+35 && intBallY+intBallMovementY > intCounter*35+60+50-15){
							// Block Left Side Collision
							intBallWillMovementX = -10;
							intBallWillMovementY = intBallMovementY;
							strBlocks[intCount][intCounter] = "Empty";
							//System.out.println ("LEFT SIDE: Row "+ intCount + " | Column : "+ intCounter);
							blnOneTime = true;
						}else if (intBallY+intBallMovementY < intCounter*35+60+50+35 && intBallY+intBallMovementY > intCounter*35+60+50+24 && intBallX+intBallMovementX > intCount*80 - 14 && intBallX+intBallMovementX < intCount*80+80){
							// Block Bottom Side Collision
							intBallWillMovementY = 10;
							intBallWillMovementX = intBallMovementX;
							strBlocks[intCount][intCounter] = "Empty";
							//System.out.println ("BOTTOM SIDE: ROW"+ intCount + " | Column : "+ intCounter);
							blnOneTime = true;
						}else if (intBallY+intBallMovementY > intCounter*35+60+50-15 && intBallY+intBallMovementY < intCounter*35+60+50-4 && intBallX+intBallMovementX > intCount*80 - 14 && intBallX+intBallMovementX< intCount*80+80){
							// Block Top Side Collision
							intBallWillMovementY = -10;
							intBallWillMovementX = intBallMovementX;
							strBlocks[intCount][intCounter] = "Empty";
							//System.out.println ("TOP SIDE: ROW"+ intCount + " | Column : "+ intCounter);
							blnOneTime = true;
						}
					}
				}
			}
			
			// Turn the Will Movement Into The Actual Movement
			if (blnOneTime == true){
				intBallMovementX = intBallWillMovementX;
				intBallMovementY = intBallWillMovementY;
				intBlocksCount--;
			}
			
			// Calculate the Paddle Movement
			intPaddleX = intPaddleX + intPaddleMovement;
			
			// Calculate the Ball Movement
			intBallX = intBallX + intBallMovementX;
			intBallY = intBallY + intBallMovementY;
			
			// Make the ball stick to the paddle until the player clicks space
			if (intBallReleased == 0){
				intBallX = intPaddleX + 50;
				intBallY = 525;
			} else if (intBallReleased == 1){
				// If the player clicks the button and releases the ball, add the movement things
				intBallMovementX = 10;
				intBallMovementY = -10;
				intBallReleased = 2;
				blnGameStart = true;
			}
			
			// When The Player Loses A Life
			if (intBallY > 800){
				if (intLives > 0){
					intBallReleased = 0;
				}
				intLives--;
				
				// Set the ball back (since when the game is over, it doesn't do that and docks a life off)
				intBallX = intPaddleX + 50;
				intBallY = 525;
			}
			
			if (blnMouseOn == false){
				// Tell them to put the mouse back
				g.setColor (Color.RED);
				g.fillRect (40,400, 720, 80);
				
				g.setColor (Color.BLACK);
				g.fillRect (45,405, 710,70);
				
				g.setFont(newFont.deriveFont(Font.PLAIN, 27));
				g.setColor (Color.RED);
				g.drawString ("Put Mouse Back On Screen!", 70,450);
			}
			
			if (blnGameStart == false){
				// Intro Screen		
				// Welcome to Breakout
				g.setColor (Color.WHITE);
				g.fillRect (20,120, 760, 70);
				
				g.setColor (Color.BLACK);
				g.fillRect (25,125, 750,60);
				
				g.setFont(newFont.deriveFont(Font.PLAIN, 33));
				g.setColor (Color.WHITE);
				g.drawString ("Welcome To BREAKOUT!!!", 50,170);
				
				// Press Left Click To Release The Ball
				g.setColor (Color.WHITE);
				g.fillRect (50,230+15+10,330+35-30-5,200+30-15-30);
				
				g.setColor (Color.BLACK);
				g.fillRect (55,235+15+10,320+35-30-5,190+30-15-30);
				
				g.setFont(newFont.deriveFont(Font.PLAIN, 28));
				g.setColor (Color.WHITE);
				g.drawString ("Press", 20+80+50-10,250+50-10+10);
				g.setFont(newFont.deriveFont(Font.PLAIN, 25));
				g.drawString ("Mouse Button", 20+50-3,295+50-5-10+10);
				g.setFont(newFont.deriveFont(Font.PLAIN, 28));
				g.drawString ("to release", 20+50,340+50-10-10+10);
				g.drawString ("the ball", 20+25+50,385+50-15-10+10);	
				
				// Hold Left Click To Use The Powerup (3 Times)
				g.setColor (Color.WHITE);
				g.fillRect (50+400,230+15-20,330+35-30-10,200+30-15-30+90-10);
				
				g.setColor (Color.BLACK);
				g.fillRect (55+400,235+15-20,320+35-30-10,190+30-15-30+90-10);
				
				g.setFont(newFont.deriveFont(Font.PLAIN, 28));
				g.setColor (Color.WHITE);
				g.drawString ("HOLD", 20+80+50-10+400+13,250+50-10-20);
				g.setFont(newFont.deriveFont(Font.PLAIN, 25));
				g.drawString ("Mouse Button", 20+50+400-5,295+50-5-10-20);
				g.setFont(newFont.deriveFont(Font.PLAIN, 28));
				g.drawString ("to use the", 20+50+400,340+50-10-10-20);
				g.drawString ("POWERUP", 20+25+50+400+20,385+50-15-10-20);
				g.drawString ("(3 Times", 20+25+50+400,385+50-15-10+45-5-20);
				g.drawString ("Use)", 20+25+50+400+50+15,385+50-15-10+45+45-10-20);	
				
				intBallX = intPaddleX + 50;
				intBallY = 525;
			}
			
			// Draw the Paddle
			g.drawImage (Paddle,intPaddleX, 540,this);
			
			// Draw the Ball
			g.drawImage (Ball,intBallX, intBallY,this);
			
			// When they win, make the gaming portion over and transition to win/loss screen
			if (intBlocksCount == 0 || intLives == 0){
				blnGaming = false;
			}
			
		}else if (blnGaming == false){
			// Win Screen
			if (intBlocksCount == 0){
				g.drawImage (Win,0,0,this);
				g.setFont(newFont.deriveFont(Font.PLAIN, 30));
				
				g.setColor (Color.WHITE);
				g.fillRect (10+20,400+20+20,300+130,100+5);
				g.setColor (Color.BLACK);
				g.fillRect (15+20,405+20+20,290+130,90+5);
				
				g.setColor (Color.WHITE);
				g.drawString ("LEFT CLICK to", 20+10+20,450+20+20);
				g.drawString ("continue!", 20+40+20+10+20,450+35+20+20);
			} else if (intLives == 0){
				// Loss Screen
				g.drawImage (Lose,0,0,this);
				
				g.setFont(newFont.deriveFont(Font.PLAIN, 30));
				
				g.setColor (Color.WHITE);
				g.fillRect (10+20,400+20+20+20,300+130+300+20,100+5-35);
				g.setColor (Color.BLACK);
				g.fillRect (15+20,405+20+20+20,290+130+300+20,90+5-35);
				
				g.setColor (Color.WHITE);
				g.drawString ("LEFT CLICK to continue!!", 20+10+20,450+20+20+20);
			}
			
			
		
		
		}
		
	}
	
	// Constructor
	public BreakoutPanel(){
		super();
		// Load Images
		try{
			Paddle = ImageIO.read(new File("Paddle.png"));
		}catch(IOException e){
			System.out.println("IMAGE UNABLE TO LOAD");
		}
		
		try{
			Ball = ImageIO.read(new File("Ball.png"));
		}catch(IOException e){
			System.out.println("IMAGE UNABLE TO LOAD");
		}
		
		try{
			Back1 = ImageIO.read(new File("Background1.png"));
		}catch(IOException e){
			System.out.println("IMAGE UNABLE TO LOAD");
		}
		
		try{
			Back2 = ImageIO.read(new File("Background2.png"));
		}catch(IOException e){
			System.out.println("IMAGE UNABLE TO LOAD");
		}
		
		try{
			Back3 = ImageIO.read(new File("Background3.png"));
		}catch(IOException e){
			System.out.println("IMAGE UNABLE TO LOAD");
		}
		
		try{
			Win = ImageIO.read(new File("Win.png"));
		}catch(IOException e){
			System.out.println("IMAGE UNABLE TO LOAD");
		}
		
		try{
			Lose = ImageIO.read(new File("Loss.png"));
		}catch(IOException e){
			System.out.println("IMAGE UNABLE TO LOAD");
		}
		
		// Load Font
		try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream("ArcadeFont.ttf"));
			
			newFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
		} catch (IOException e) {
			System.out.println ("IOEXCEPTION!");
		} catch (FontFormatException e){
			System.out.println ("ERROR LOADING FONT");
		}
	}
	
	
}
