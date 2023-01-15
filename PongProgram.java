// Program: Pong
// Created By: Kevin Lau
// Creation Date: May 12th, 2022
// Version 1.0 

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class PongProgram implements ActionListener, KeyListener{
    // Properties
    JFrame theFrame = new JFrame("Atari Pong");
    PongPanel thePanel = new PongPanel();
    Timer theTimer = new Timer(1000/60, this); // 60 Frames
    
    // Methods
    public void actionPerformed(ActionEvent evt){
        // Add the 60 frames per second
        if(evt.getSource() == theTimer){
			// If p1 or p2 gets 5 points, the repainting stops (so the ball and paddle stop moving in the background) and only continues when the points are reset (when they click space)
            if (thePanel.intPlayer1Points < 5 && thePanel.intPlayer2Points < 5){
				thePanel.repaint();
			}
        }
    }
    
    public void keyReleased(KeyEvent evt){
		// When they release the p1 or p2 movement keys, the paddle stops moving 
		if (evt.getKeyChar()=='w'){	
			thePanel.intP1Def = 0;
		}else if(evt.getKeyChar() == 's'){
			thePanel.intP1Def = 0;
		}else if(evt.getKeyCode() == 38){
			thePanel.intP2Def = 0;
		}else if(evt.getKeyCode() == 40){
			thePanel.intP2Def = 0;
		}
	}
	
	public void keyPressed(KeyEvent evt){		
		if (evt.getKeyChar()=='w'){
			// P1 Up
			thePanel.intP1Def = -10;
		}else if(evt.getKeyChar() == 's'){
			// P1 Down
			thePanel.intP1Def = +10;
		}else if(evt.getKeyCode() == 38){
			// P2 Up
			thePanel.intP2Def = -10;
		}else if(evt.getKeyCode() == 40){
			// P2 Down
			thePanel.intP2Def = +10;
		}
		
		// Only if they get 5 points (bln = true), it will reset the points to = 0, allowing the panel to continue repainting
		if (evt.getKeyChar() == ' '){
			if (thePanel.blnRestart == true){
				thePanel.intPlayer1Points = 0;
				thePanel.intPlayer2Points = 0;
				thePanel.blnRestart = false;
			}
			
			if (thePanel.intStart == 0){
				thePanel.intStart = 1 ;
			}
		}
	}
	
	public void keyTyped(KeyEvent evt){
	}
	
    // Constructor
    public PongProgram(){
		// Set Dimension as 800 x 600 (was 720x480)
        thePanel.setPreferredSize(new Dimension(800, 600));
       
		// Setting things for the frame (putting in frame, close program after exit, set visible, and unresizable)
        theFrame.setContentPane(thePanel);
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        theFrame.setVisible(true);
        theFrame.setResizable(false);

        theFrame.pack();
        
        // Add Listeners
        theFrame.addKeyListener(this);
        
        // Add Timers
        theTimer.start();
        
        // Tell them how to go up or down
        System.out.println ("For the Left Paddle. Hold 'W' to go up, and hold 's' to go down");
        System.out.println ("For the Right Paddle. Hold 'UP ARROW' to go up, and hold 'DOWN ARROW' to go down");
    }
    
    // Main method
    public static void main(String[] args){
        new PongProgram();
    }
}
