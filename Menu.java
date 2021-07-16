package ticTacToeGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class Menu implements ActionListener {

	static Random rand = new Random();
	JFrame menu = new JFrame("Menu");
	private JButton[] buttons = new JButton[3];
	private JPanel buttonsPanel = new JPanel();
	private JLabel title = new JLabel("Tic-Tac-Toe");
	static boolean playerXcpuOn = false, cpuXcpuOn = false, firstMatch = true, player1Turn;
	static int secondInstance = 0, cpuPlayFirst, cpuIsO, randomSide;

	
	Menu() {
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setLayout(new BorderLayout());
		menu.setSize(750, 500);
		
		title.setOpaque(true);
		title.setFont(new Font(null, Font.PLAIN, 50));
		title.setBackground(Color.black);
		title.setForeground(Color.GREEN);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setPreferredSize(new Dimension(0, 75));
		
		buttonsPanel.setLayout(new GridLayout(3, 1));
		buttonsPanel.setPreferredSize(new Dimension(0, 300));
		
		for(int i = 0; i < 3; i++) {
			buttons[i] = new JButton();
			buttons[i].setFont(new Font(null, Font.PLAIN, 50));
			buttons[i].setFocusable(false);
			buttons[i].addActionListener(this);
			buttonsPanel.add(buttons[i]);
		}
		
		buttons[0].setText("Player vs CPU");
		buttons[1].setText("Player vs Player");
		buttons[2].setText("CPU vs CPU");
		
		menu.add(title, BorderLayout.NORTH);
		menu.add(buttonsPanel);
		menu.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		menu.dispose();
		if(Gameboard.frame.isVisible() == false & secondInstance == 1) {
			Gameboard.frame.setVisible(true);
		}	
		else {
			new Gameboard();
		}
		if(e.getSource() == buttons[0]) {
			cpuIsO = rand.nextInt(2);
			playerXcpuMode(cpuPlayFirst, cpuIsO);
		}
		else if(e.getSource() == buttons[1]) {
			firstTurn();
		}
		else if(e.getSource() == buttons[2]) {
			cpuXcpuMode();
		}
	}
	
	public static void firstTurn() {
		if(firstMatch == true) {
			randomSide = rand.nextInt(2);
		}
		if(randomSide == 0 & playerXcpuOn == false | cpuIsO == 1 & playerXcpuOn == true) {
			player1Turn = true;
			Gameboard.textField.setText("X's Turn");
		}
		else if (randomSide == 1 & playerXcpuOn == false | cpuIsO == 0 & playerXcpuOn == true){
			player1Turn = false;
			Gameboard.textField.setText("O's Turn");
		}
	}
	
	public static void playerXcpuMode(int cpuPlayFirst, int cpuIsO) {
		playerXcpuOn = true;
		cpuPlayFirst = rand.nextInt(2);
		if(cpuPlayFirst == 0) {
			if(cpuIsO == 1) {
				Gameboard.cpuMove("O");
				Gameboard.textField.setText("X's Turn");
				Gameboard.player1Turn = true;
				
			}
			else {
				Gameboard.cpuMove("X");
				Gameboard.textField.setText("O's Turn");
				Gameboard.player1Turn = false;
			}
		}
		else {
			Gameboard.firstTurn();
		}
	}

	public static void cpuXcpuMode() {
		cpuXcpuOn = true;
		cpuIsO = rand.nextInt(2);
		for(JButton b : Gameboard.buttons) {
			while(b.getText() == "" & Gameboard.endGame == false) {
				if(cpuIsO == 0) {
					Gameboard.cpuMove("X");
					Gameboard.checkGameState();
					cpuIsO = 1;
				}
				else {
					Gameboard.cpuMove("O");
					Gameboard.checkGameState();
					cpuIsO = 0;
				}
			}	
		}
	}
}