// Program: Breakout
// Created By: Kevin Lau
// Creation Date: May 12th, 2022
// Version 2.0 

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class BreakoutProgram implements MouseListener, ActionListener, MouseMotionListener{
	// Properties
	JFrame theFrame = new JFrame ("Breakout");
	BreakoutPanel thePanel = new BreakoutPanel ();
	Timer theTimer = new Timer (1000/60,this);
	
	// Methods
	public void actionPerformed(ActionEvent evt){
		if (evt.getSource() == theTimer){
			thePanel.repaint();
		}
	}
	
	public void mouseMoved (MouseEvent evt){
		thePanel.intMouseX = evt.getX();
	}
	
	public void mouseDragged (MouseEvent evt){
		thePanel.intMouseX = evt.getX();
	}
	
	public void mouseEntered (MouseEvent evt){
		thePanel.blnMouseOn = true;
	}
	
	public void mouseExited (MouseEvent evt){	
		thePanel.blnMouseOn = false;
	}
	
	public void mouseClicked (MouseEvent evt){	
		// When the click the mouse button, release the ball
		if (thePanel.intBallReleased == 0){
			thePanel.intBallReleased = 1;
		}
		
		// If they are on the win or lose screen, if they click something, they will restart the game (set default stuff)
		if (thePanel.blnGaming == false){
			thePanel.blnBlocksFilled = true;
			thePanel.blnGameStart = false;
			thePanel.blnGaming = true;
			thePanel.intBallReleased = 0;
		}
	}
	
    public void mousePressed (MouseEvent evt){
		// Powerup
		if (thePanel.intPowerUpCount > 0 && thePanel.intBallReleased == 2){
			theTimer.setDelay (1000/180);
			thePanel.intPowerUpCount--;
		}
		
		
		
	}
	
	public void	mouseReleased (MouseEvent evt){	
		theTimer.setDelay (1000/60);
	}
	
	// Constructor
	public BreakoutProgram () {
		// Set the Panel Size
        thePanel.setPreferredSize(new Dimension(800, 600));
       
		// Setting things for the frame (putting in frame, close program after exit, set visible, and unresizable)
        theFrame.setContentPane(thePanel);
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        theFrame.setVisible(true);
        theFrame.setResizable(false);

        theFrame.pack();
        
        // Add Listeners
        theFrame.addMouseListener(this);
        theFrame.addMouseMotionListener(this);
        
        // Add Timers
        theTimer.start();
	}
	
	// Main Program
	public static void main (String[]args){
		new BreakoutProgram();
	}

}
