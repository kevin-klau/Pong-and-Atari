import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

public class PongPanel extends JPanel{
	// Properties
	int intP1Y = 260;
	int intP2Y = 260;
	int intBallX = 393;
	int intBallY = 293;
	int intBallDefX = -10;
	int intBallDefY = 10;
	int intP1Def=0;
	int intP2Def = 0;
	int intPlayer1Points = 0;
	int intPlayer2Points = 0;
	int intMidLineCount;	
	int intBallUpDown;
	boolean blnRestart = false;
	int intStart = 0;
	int intBallStartOrder;
	int intBallStartUpDown;
	boolean blnDoneAnimation = false;
	int intLetterPY = -155;
	int intLetterOY = 1235;
	int intLetterNY = -1055;
	int intLetterGX = 3105;
	int intLetterPONX = 105;
	Font newFont;
	String strNoob = "";
	
	// Methods
	public void paintComponent (Graphics g){
		super.paintComponent(g);
		
		// Load Font
		// Load Font
		try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream("ArcadeFont.ttf"));
			
			newFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
		} catch (IOException e) {
			System.out.println ("IOEXCEPTION!");
		} catch (FontFormatException e){
			System.out.println ("ERROR LOADING FONT");
		}
		
		// OPENING ANIMATION
		// SO 'P', 'O', 'N' ALTERNATE FALLING OR RISING UP FROM THE MIDDLE OF NO WHERE AND G THE G FLIES IN FROM THE RIGHT AND PUSHES EVERYTHING TO THE LEFT
		// Background
		g.setColor (Color.BLACK);
		g.fillRect (0,0,800,600);
	
		// Letters + Opening Animation
		g.setFont(newFont.deriveFont(Font.PLAIN, 150));
		g.setColor (Color.WHITE);
		
		if (intLetterPY < 335){
			intLetterPY = intLetterPY + 20;
		}
		if (intLetterOY > 355){
			intLetterOY = intLetterOY - 20;
		} else if (intLetterOY == 355){
			intLetterOY = 345;
		}
		if (intLetterNY < 335){
			intLetterNY = intLetterNY + 20;
		}
		if (intLetterGX > 555){
			intLetterGX = intLetterGX - 30;
		}
		
		if (intLetterGX <= 555 && intLetterGX > -500){
			intLetterPONX = intLetterPONX - 40;
			intLetterGX = intLetterGX - 40;
		} else if (intLetterGX < -500){
			blnDoneAnimation = true;
		}
		
		g.drawString ("P", intLetterPONX, intLetterPY);
		g.drawString ("O", intLetterPONX + 148, intLetterOY);
		g.drawString ("N", intLetterPONX + 296, intLetterNY);
		g.drawString ("G", intLetterGX, 345);
		
		
		//blnDoneAnimation = true;
		
		if (blnDoneAnimation == true){
			// Background
			g.setColor (Color.BLACK);
			g.fillRect (0,0,800,600);
			g.setColor (Color.WHITE);
			g.fillRect(20, 20, 760, 560);
			g.setColor (Color.BLACK);
			g.fillRect(0,30,800,540);
			g.setColor (Color.WHITE);
			
			for (intMidLineCount = 0; intMidLineCount < 18; intMidLineCount ++){
				g.fillRect (398,intMidLineCount*30 + 37,5,15);
			}	
			
			// Start Function
			if (intStart == 0){
				// Tell them to click Space
				g.setColor (Color.WHITE);
				g.fillRect (60,480,670,75);
				g.setColor (Color.BLACK);
				g.fillRect (65,485,660,65);
			
				g.setColor (Color.WHITE);
				
				// Text
				g.setFont(newFont.deriveFont(Font.PLAIN, 27));
				g.drawString ("Press 'Space' to Start!", 85,530);
				
				
				intBallDefX = 0;
				intBallDefY = 0;
			} else if (intStart == 1){
				// Determine who gets the ball first (if number is 0, p1 gets it, if number is 1, p2 gets it)
				intBallStartOrder = (int)(Math.random()*2);
				if (intBallStartOrder == 0){
					intBallDefX = -10;
				}else if (intBallStartOrder == 1){
					intBallDefX = 10;
				}
				
				// Determine which way the ball goes (if number = 0, ball goes up, vice versa if number = 1)
				intBallStartUpDown = (int)(Math.random()*2);
				if (intBallStartUpDown == 0){
					intBallDefY = -10;
				}else if (intBallStartUpDown == 1){
					intBallDefY = 10;
				}
				
				// Make sure this doesn't run after the first time
				intStart = 2;
			}
			
			
			//Add Points (extra && conditions to make it tick once since the ball goes up to -60 before resetting)
			if (intBallX <= -10 && intBallX >= -20){
				intPlayer2Points++;
			}else if (intBallX>=810 && intBallX <= 820){
				intPlayer1Points++;
			}
			
			// If Player 1 or 2 Gets 5 Points, add YOU WIN!
			if (intPlayer1Points == 5){
				// Background
				g.setColor (Color.WHITE);
				g.fillRect (70, 355, 300, 190);
				g.setColor (Color.BLACK);
				g.fillRect (72, 357, 296, 186);
				g.setColor (Color.WHITE);
				g.fillRect (74, 359, 292, 182);
				g.setColor (Color.BLACK);
				g.fillRect (76, 361, 288, 178);
				
				// Text
				g.setColor (Color.WHITE);
				g.setFont(newFont.deriveFont(Font.PLAIN, 80));
				g.drawString ("P1", 150, 450);
				g.setFont(newFont.deriveFont(Font.PLAIN, 55));
				g.drawString ("WINS!", 95, 520);
				blnRestart = true;
			}else if (intPlayer2Points == 5){
				// Background
				g.setColor (Color.WHITE);
				g.fillRect (430, 355, 305, 190);
				g.setColor (Color.BLACK);
				g.fillRect (432, 357, 301, 186);
				g.setColor (Color.WHITE);
				g.fillRect (434, 359, 297, 182);
				g.setColor (Color.BLACK);
				g.fillRect (436, 361, 293, 178);
				
				// Text
				g.setColor (Color.WHITE);
				g.setFont(newFont.deriveFont(Font.PLAIN, 80));
				g.drawString ("P2", 510, 450);
				g.setFont(newFont.deriveFont(Font.PLAIN, 55));
				g.drawString ("WINS!", 455, 520);
				blnRestart = true;
			}		
			
			if (strNoob.equalsIgnoreCase ("1")){
				// If Player 2 steamrolls
				g.setColor (new Color (131,148,205));
				g.setFont(newFont.deriveFont(Font.PLAIN, 90));
				g.drawString ("N", 80, 240);
				g.drawString ("O", 140, 345);
				g.drawString ("O", 200, 430);
				g.drawString ("B", 260, 525);
			}else if (strNoob.equalsIgnoreCase ("2")){
				// if Player 1 steamrolls
				g.setColor (new Color (131,148,205));
				g.setFont(newFont.deriveFont(Font.PLAIN, 90));
				g.drawString ("N", 440, 240);
				g.drawString ("O", 500, 345);
				g.drawString ("O", 560, 430);
				g.drawString ("B", 620, 525);
			}
				
			// P1 Paddle
			g.setColor(Color.WHITE);
			g.fillRect(50,intP1Y,10,80);
			
			// P2 Paddle
			g.fillRect (740,intP2Y, 10,80);
			
			// Drawing the Ball
			g.setColor (Color.WHITE);
			g.fillOval(intBallX, intBallY, 15,15);
			
			// Display P1 and P2 Points
			g.setFont(newFont.deriveFont(Font.PLAIN, 80));
			g.drawString (""+intPlayer1Points, 170,125);
			g.drawString (""+intPlayer2Points, 560,125);
			
			// Ball Movement (Both X and Y)
			intBallX = intBallX + intBallDefX;
			intBallY = intBallY + intBallDefY;

			// Setting the Ball to Bounce off the Y boundaries
			if (intBallY > 545){
				intBallDefY = -10;
			}else if (intBallY < 45){
				intBallDefY = 10;
			}
			
			// Paddle Movement
			intP1Y = intP1Y + intP1Def;
			intP2Y = intP2Y + intP2Def;	
			
			// Left Padlle Collision Detection
			int intPaddleDeflection1 = 15;
			
			if (intP1Def != 0 ){
				intPaddleDeflection1 = intPaddleDeflection1 + 15;
			}
			
			if (intBallX <= 70 && intBallX >= 50 && intBallY + intPaddleDeflection1 >= intP1Y && intBallY <= intP1Y + intPaddleDeflection1 + 65){
				intBallDefX = 10;
			}
			
			// Right Paddle Collilsion Detection			
			int intPaddleDeflection2 = 15;
			
			if (intP2Def != 0 ){
				intPaddleDeflection2 = intPaddleDeflection2 + 15;
			}
			
			if (intBallX >= 720 && intBallX <=740 && intBallY + intPaddleDeflection2 >= intP2Y && intBallY <= intP2Y + intPaddleDeflection2 + 65){
				intBallDefX = -10;
			}
			
			// Set Boundaries for Paddles 1 and 2
			if (intP1Y + intP1Def < 30){
				intP1Y = 30;
				intP1Def = 0;
			}else if (intP1Y + intP1Def > 490){
				intP1Y = 490;
				intP1Def = 0;
			}
			
			if (intP2Y + intP2Def < 30){
				intP2Y = 30;
				intP2Def = 0;
			}else if (intP2Y + intP2Def > 490){
				intP2Y = 490;
				intP2Def = 0;
			}
			
			// If the ball passes through the sides, set it back a random spot (set it higher than boundaries to stall 3 seconds)
			if (intBallX <= -180 || intBallX >= 980){
				// Set ball at centre (X-Axis)
				intBallX = 395;
				
				// Randomize the Y-Axis Ball Spawn Point (between 50 and 550 (don't set it too close to the edges))
				intBallY = (int)((Math.random()*500))+50;
				
				// Determine wheter the ball goes towards p1 or  p2 (goes left, right, left, right, left right pattern)
				// If the total points is even, the person who first got the ball gets the ball, if the total points is odd, than the other player gets the ball (So it alternates)
				if (intBallStartOrder == 0){
					if ((intPlayer1Points + intPlayer2Points) % 2 == 0 ){
						intBallDefX = -10;
					}else if ((intPlayer1Points + intPlayer2Points) % 2 == 1){
						intBallDefX = 10;
					}
				}else if (intBallStartOrder == 1){
					if ((intPlayer1Points + intPlayer2Points) % 2 == 0 ){
						intBallDefX = 10;
					}else if ((intPlayer1Points + intPlayer2Points) % 2 == 1){
						intBallDefX = -10;
					}
				}
				
				// Determine wheter it goes up or down or not (use math.random to choose a number between 0-1 if the number is 0, go up, if the number is 1, go down
				intBallUpDown = (int)(Math.random()*2);
				if (intBallUpDown == 0){
					intBallDefY = -10;
				}else if (intBallUpDown == 1){
					intBallDefY = 10;
				}
			}
			// Restart Function
			if (intPlayer1Points == 5 || intPlayer2Points == 5){
				// Box in bakcground
				g.setColor (Color.WHITE);
				g.fillRect (160,190,470,110);
				g.setColor (Color.BLACK);
				g.fillRect (165,195,460,100);
				
				// Text
				g.setColor (Color.WHITE);
				g.setFont(newFont.deriveFont(Font.PLAIN, 33)); 
				g.drawString ("Press 'Space'", 190,240);
				g.drawString ("To Restart", 240,280);
				
				if (!strNoob.equalsIgnoreCase ("")){
					strNoob = "";
				}
				
				// EASTER EGG: ADD NOOB ON THE PERSON THAT LOST 5-0
				if (intPlayer1Points - intPlayer2Points == 5){
					// Player 2 gets noob screen
					strNoob = "2";
				} else if (intPlayer2Points - intPlayer1Points == 5){
					// Player 1 gets noob screen
					strNoob = "1";
				}
				
			}
		}
	}
	
	// Constructor
	public PongPanel(){
		super();
	}
}
