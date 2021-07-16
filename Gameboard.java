package ticTacToeGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Gameboard extends Menu implements ActionListener {
	
	private static int xScore = 0;
	private static int oScore = 0;
	static boolean endGame = false;
	static JFrame frame = new JFrame("Tic-Tac-Toe");
	private static JPanel titlePanel = new JPanel(), buttonPanel = new JPanel(), scorePanel = new JPanel();
	protected static JButton[] buttons = new JButton[11];
	protected static JLabel textField = new JLabel();
	static JLabel[] scoreField = new JLabel[2];
	
		
	Gameboard() {
		menu.dispose();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 750);
		frame.setLayout(new BorderLayout(5, 5));
		
		textField.setForeground(Color.WHITE);
		textField.setHorizontalAlignment(JLabel.CENTER);
		textField.setFont(new Font(null, Font.PLAIN, 50));
		textField.setOpaque(true);
		textField.setBackground(Color.black);
		
		titlePanel.setLayout(new BorderLayout());
		titlePanel.setPreferredSize(new Dimension(1200, 100));
		
		scorePanel.setPreferredSize(new Dimension(395, 640));
		scorePanel.setLayout(new GridLayout(2, 2));
		
		buttonPanel.setLayout(new GridLayout(3, 3));
		buttonPanel.setPreferredSize(new Dimension(800, 650));
		
		for(int i = 0; i < 2; i++) {
			scoreField[i] = new JLabel(); 
			scoreField[i].setFont(new Font(null, Font.PLAIN, 30));
			scorePanel.add(scoreField[i]);
		}
		
		for(int i = 0; i < 11; i++) {
			buttons[i] = new JButton();
			buttons[i].setFont(new Font("", Font.PLAIN, 200));
			buttons[i].setFocusable(false);
			buttons[i].addActionListener(this);
			if(i <= 8) {
				buttonPanel.add(buttons[i]);
			}
			else if(i > 8) {
				scorePanel.add(buttons[i]);
				buttons[i].setFont(new Font(null, Font.PLAIN, 25));
			}
		}
		buttons[9].setText("Reset");
		buttons[10].setText("Menu");
		
		frame.add(buttonPanel, BorderLayout.WEST);
		frame.add(titlePanel, BorderLayout.NORTH);
		frame.add(scorePanel);
		titlePanel.add(textField);
		
		frame.setVisible(true);
		
		refreshScore();
	}
	
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i < 9; i++) {
			if(e.getSource() == buttons[i]) {
				if(buttons[i].getText() == "" & cpuXcpuOn == false) {
					if(player1Turn == true) {
						buttons[i].setText("X");
						buttons[i].setForeground(Color.BLUE);
						textField.setText("O's Turn");
						checkGameState();
						if(playerXcpuOn & endGame == false) {
							cpuMove("O");
							checkGameState();
						}
						else {
							player1Turn = false;
						}
					}		
					else {
						buttons[i].setText("O");
						buttons[i].setForeground(Color.ORANGE);
						textField.setText("X's Turn");
						checkGameState();
						if(playerXcpuOn & endGame == false) {
							cpuMove("X");
							checkGameState();
						}
						else {
							player1Turn = true;
						}
					}
				}
			}	
		}
		if(e.getSource() == buttons[9]) {
			reset();
		}
		if(e.getSource() == buttons[10]) {
			menu();
		}
	}
	
	public static void menu() {
		frame.setVisible(false);
		secondInstance = 1;
		oScore = 0;
		xScore = 0;
		playerXcpuOn = false;
		cpuXcpuOn = false;
		endGame = false;
		refreshScore();
		reset();
		new Menu();
	}
	
	public static void reset() {
		endGame = false;
		textField.setForeground(Color.WHITE);
		for(int i = 0; i < 9; i++) {
			buttons[i].setEnabled(true);
			buttons[i].setText("");
			buttons[i].setBackground(null);
		}
		if(playerXcpuOn == true) {
			firstMatch = false;
			playerXcpuMode(cpuPlayFirst, cpuIsO);
		}
		else if(cpuXcpuOn == true) {
			cpuXcpuMode();
		}
		else {
			firstMatch = true;
			firstTurn();
		}
		refreshScore();
	}
	
	public static void refreshScore() {
		scoreField[0].setText("Player X: " + xScore);
		scoreField[1].setText("Player O: " + oScore);
	}

	public static void cpuMove (String side) {
		if(side.equals("X") & endGame == false) {
			while(true) {
				int a = rand.nextInt(9);
				if(buttons[a].getText().equals("")) {
					textField.setText("O's Turn");
					buttons[a].setText("X");
					buttons[a].setForeground(Color.BLUE);
					player1Turn = false;
					break;
				}	
			}
		}
		else if(side.equals("O") & endGame == false) {
			while(true) {
				int a = rand.nextInt(9);
				if(buttons[a].getText().equals("")) {
					textField.setText("X's Turn");
					buttons[a].setText("O");
					buttons[a].setForeground(Color.ORANGE);
					player1Turn = true;
					break;
				}	
			}
		}
	}	
	
	public static void xWins(int a, int b, int c) {
		buttons[a].setBackground(Color.GREEN);
		buttons[b].setBackground(Color.GREEN);
		buttons[c].setBackground(Color.GREEN);
		
		for(int i = 0; i < 9; i++) {
			buttons[i].setEnabled(false);
		}
		textField.setText("X Wins!");
		textField.setForeground(Color.BLUE);
		endGame = true;
		xScore++;
		refreshScore();
	}
	public static void oWins(int a, int b, int c) {
		buttons[a].setBackground(Color.GREEN);
		buttons[b].setBackground(Color.GREEN);
		buttons[c].setBackground(Color.GREEN);
		
		for(int i = 0; i < 9; i++) {
			buttons[i].setEnabled(false);
		}
		textField.setText("O Wins!");
		textField.setForeground(Color.ORANGE);
		endGame = true;
		oScore++;
		refreshScore();
	}
	public static boolean drawCondition() {
		for(JButton t : buttons) {
			if(t.getText() == "") {
				return false;
			}
		}
		endGame = true;
		return true;
	}
	public static void checkGameState() {
		//X win condition
		if		(buttons[0].getText() == "X" & 
				 buttons[1].getText() == "X" & 
				 buttons[2].getText() == "X") {
			xWins(0, 1, 2);
		}
		else if (buttons[3].getText() == "X" & 
				 buttons[4].getText() == "X" & 
				 buttons[5].getText() == "X") {
			xWins(3, 4, 5);
		}
		else if (buttons[6].getText() == "X" & 
				 buttons[7].getText() == "X" & 
				 buttons[8].getText() == "X") {
			xWins(6, 7, 8);
		}
		else if (buttons[0].getText() == "X" & 
				 buttons[3].getText() == "X" & 
				 buttons[6].getText() == "X") {
			xWins(0, 3, 6);
		}
		else if (buttons[1].getText() == "X" & 
				 buttons[4].getText() == "X" & 
				 buttons[7].getText() == "X") {
			xWins(1, 4, 7);
		}
		else if (buttons[2].getText() == "X" & 
				 buttons[5].getText() == "X" & 
				 buttons[8].getText() == "X") {
			xWins(2, 5, 8);
		}
		else if (buttons[0].getText() == "X" & 
				 buttons[4].getText() == "X" & 
				 buttons[8].getText() == "X") {
			xWins(0, 4, 8);
		}
		else if (buttons[2].getText() == "X" & 
				 buttons[4].getText() == "X" & 
				 buttons[6].getText() == "X") {
			xWins(2, 4, 6);
		}
		//O win condition
		else if (buttons[0].getText() == "O" & 
				 buttons[1].getText() == "O" & 
				 buttons[2].getText() == "O") {
			oWins(0, 1, 2);
		}
		else if (buttons[3].getText() == "O" & 
				 buttons[4].getText() == "O" & 
				 buttons[5].getText() == "O") {
			oWins(3, 4, 5);
		}
		else if (buttons[6].getText() == "O" & 
				 buttons[7].getText() == "O" & 
				 buttons[8].getText() == "O") {
			oWins(6, 7, 8);
		}
		else if (buttons[0].getText() == "O" & 
				 buttons[3].getText() == "O" & 
				 buttons[6].getText() == "O") {
			oWins(0, 3, 6);
		}
		else if (buttons[1].getText() == "O" & 
				 buttons[4].getText() == "O" & 
				 buttons[7].getText() == "O") {
			oWins(1, 4, 7);
		}
		else if (buttons[2].getText() == "O" & 
				 buttons[5].getText() == "O" & 
				 buttons[8].getText() == "O") {
			oWins(2, 5, 8);
		}
		else if (buttons[0].getText() == "O" & 
				 buttons[4].getText() == "O" & 
				 buttons[8].getText() == "O") {
			oWins(0, 4, 8);
		}
		else if (buttons[2].getText() == "O" & 
				 buttons[4].getText() == "O" & 
				 buttons[6].getText() == "O") {
			oWins(2, 4, 6);
		}
		//Draw condition
		else if (drawCondition()) {
			textField.setText("Draw!");
			for(int k = 0; k < 9; k++) {
				buttons[k].setEnabled(false);
			}
		}
	}
	public static void main(String[] args) {
		new Menu();
	}
}

