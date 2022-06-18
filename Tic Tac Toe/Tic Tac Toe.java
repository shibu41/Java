package TicTacToe;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

public class TicTacToe implements ActionListener{
	
	Random random = new Random();
	JFrame frame = new JFrame();
	JPanel titlePanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	JLabel label = new JLabel();
	JButton[] buttons = new JButton[9];
	JButton restart = new JButton("Restart");
	boolean player1_turn;
	
	TicTacToe(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		//frame.getContentPane().setBackground(new Color(50, 50, 50));
		//frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		
		//set label background to black
		label.setBackground(new Color(0, 0, 0));
		//set label font color to teal type
		label.setForeground(new Color(174, 254, 255));
		//configure choco-cooky font for label
		try {
			label.setFont(NewFont.getFont(Font.BOLD, 75));
		} catch (FontFormatException | IOException e1) {
			e1.printStackTrace();
		}
		//align label to center
		label.setHorizontalAlignment(JLabel.CENTER);
		//set label
		label.setText("Tic-Tac-Toe");
		//below line actually paints the label background black
		label.setOpaque(true);
		
		
		titlePanel.setLayout(new BorderLayout());
		titlePanel.setBounds(0, 0, 800, 100);
		
		//sets the 3x3 grid layout for entering X's and O's
		buttonPanel.setLayout(new GridLayout(3, 3));
		//sets the grid color
		buttonPanel.setBackground(new Color(150, 150, 150));
		
		//add and display buttons in the Panel
		for(int i=0; i<9; i++) {
			buttons[i] = new JButton();
			buttons[i].setBackground(new Color(25, 25, 25));
			buttonPanel.add(buttons[i]);
			//configure choco-cooky font for buttons
			try {
				buttons[i].setFont(NewFont.getFont(Font.BOLD, 120));
			} catch (FontFormatException | IOException e) {
				e.printStackTrace();
			}
			buttons[i].setFocusable(false);
			buttons[i].addActionListener(this);
		}
		
		//configure restart button
		restart.setSize(70, 70);
		restart.setBorderPainted(false);
		restart.setForeground(new Color(174, 254, 255));
		restart.setBackground(new Color(0, 0, 0));
		titlePanel.add(label);
		frame.add(titlePanel, BorderLayout.NORTH);
		frame.add(buttonPanel);
		
		firstTurn();
		for(int i=0; i<9; i++) {
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getSource() == restart) {
			frame.setVisible(false);
			new TicTacToe();
		}
		
		for(int i=0; i<9; i++) {
			if(ae.getSource() == buttons[i]) {
					if(player1_turn) {
						if(buttons[i].getText() == "") {
							buttons[i].setForeground(new Color(179, 84, 30));
							buttons[i].setText("X");
							player1_turn = false;
							label.setText("O turn");
							check();
						}
					}
					else{
						if(buttons[i].getText() == "") {
							buttons[i].setForeground(new Color(50, 82, 136));
							buttons[i].setText("O");
							player1_turn = true;
							label.setText("X turn");
							check();
						}	
					}
			}
		}
		
	}

	public void firstTurn() {
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(random.nextInt(2) == 0) {
			player1_turn = true;
			label.setText("X turn");
		}
		else {
			player1_turn = false;
			label.setText("O turn");
		}
	}
	
	public void check() {
		
		if((buttons[0].getText()=="X") && (buttons[1].getText()=="X") && (buttons[2].getText()=="X")) {
			xWins(0, 1, 2);
		}
		else if((buttons[3].getText()=="X") && (buttons[4].getText()=="X") && (buttons[5].getText()=="X")) {
			xWins(3, 4, 5);
		}
		else if((buttons[6].getText()=="X") && (buttons[7].getText()=="X") && (buttons[8].getText()=="X")) {
			xWins(6, 7, 8);
		}
		else if((buttons[0].getText()=="X") && (buttons[3].getText()=="X") && (buttons[6].getText()=="X")) {
			xWins(0, 3, 6);
		}
		else if((buttons[1].getText()=="X") && (buttons[4].getText()=="X") && (buttons[7].getText()=="X")) {
			xWins(1, 4, 7);
		}
		else if((buttons[2].getText()=="X") && (buttons[5].getText()=="X") && (buttons[8].getText()=="X")) {
			xWins(2, 5, 8);
		}
		else if((buttons[0].getText()=="X") && (buttons[4].getText()=="X") && (buttons[8].getText()=="X")) {
			xWins(0, 4, 8);
		}
		else if((buttons[2].getText()=="X") && (buttons[4].getText()=="X") && (buttons[6].getText()=="X")) {
			xWins(2, 4, 6);
		}
		
		
		if((buttons[0].getText()=="O") && (buttons[1].getText()=="O") && (buttons[2].getText()=="O")) {
			oWins(0, 1, 2);
		}
		else if((buttons[3].getText()=="O") && (buttons[4].getText()=="O") && (buttons[5].getText()=="O")) {
			oWins(3, 4, 5);
		}
		else if((buttons[6].getText()=="O") && (buttons[7].getText()=="O") && (buttons[8].getText()=="O")) {
			oWins(6, 7, 8);
		}
		else if((buttons[0].getText()=="O") && (buttons[3].getText()=="O") && (buttons[6].getText()=="O")) {
			oWins(0, 3, 6);
		}
		else if((buttons[1].getText()=="O") && (buttons[4].getText()=="O") && (buttons[7].getText()=="O")) {
			oWins(1, 4, 7);
		}
		else if((buttons[2].getText()=="O") && (buttons[5].getText()=="O") && (buttons[8].getText()=="O")) {
			oWins(2, 5, 8);
		}
		else if((buttons[0].getText()=="O") && (buttons[4].getText()=="O") && (buttons[8].getText()=="O")) {
			oWins(0, 4, 8);
		}
		else if((buttons[2].getText()=="O") && (buttons[4].getText()=="O") && (buttons[6].getText()=="O")) {
			oWins(2, 4, 6);
		}
	}
	
	public void xWins(int a, int b, int c) {
		
		buttons[a].setBackground(new Color(186, 255, 180));
		buttons[b].setBackground(new Color(186, 255, 180));
		buttons[c].setBackground(new Color(186, 255, 180));
		
		for(int i=0; i<9; i++)
			buttons[i].setEnabled(false);
		label.setText("X wins");
		restart.addActionListener(this);
		titlePanel.add(restart, BorderLayout.EAST);
	}
	
	public void oWins(int a, int b, int c) {
		
		buttons[a].setBackground(new Color(174, 254, 255));
		buttons[b].setBackground(new Color(174, 254, 255));
		buttons[c].setBackground(new Color(174, 254, 255));
		
		for(int i=0; i<9; i++)
			buttons[i].setEnabled(false);
		label.setText("O wins");
		restart.addActionListener(this);
		titlePanel.add(restart, BorderLayout.EAST);
	}
}
