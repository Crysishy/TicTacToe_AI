/*
 * Programmer: Yang Hong
 * Class: TextAreaView.java
 * Description: A GUI which extends JPanel for class TicTacToeGame.java.
 * 				This is the second view of TicTacToeGUI which allows player
 * 				to play Tic Tac Toe with inputted text, in this case, rows and cols.
 * 				It is synchronized with the first view - ButtonView.java 
 */
package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.OurObserver;
import model.ComputerPlayer;
import model.TicTacToeGame;

public class TextAreaView extends JPanel implements OurObserver {

	private TicTacToeGame theGame;
	private JLabel labelRow = new JLabel("row");
	private JLabel labelCol = new JLabel("column");
	private JTextField fieldRow = new JTextField();
	private JTextField fieldCol = new JTextField();
	private JButton moveButton = new JButton("Make your move");
	private ButtonListener buttonListener = new ButtonListener();
	private JTextArea statusArea = new JTextArea();
	private ComputerPlayer computerPlayer;
	private int height, width;

	public TextAreaView(TicTacToeGame newGame, int width, int height) {
		this.theGame = newGame;
		this.height = height;
		this.width = width;
		this.computerPlayer = theGame.getComputerPlayer();
		setUpLayout();
	}

	private void setUpLayout() {
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(null);
		labelRow.setSize(60, 30);
		labelRow.setLocation(10, 5);
		this.add(labelRow);
		fieldRow.setSize(60, 30);
		fieldRow.setLocation(70, 5);
		this.add(fieldRow);
		labelCol.setSize(60, 30);
		labelCol.setLocation(10, 40);
		this.add(labelCol);
		fieldCol.setSize(60, 30);
		fieldCol.setLocation(70, 40);
		this.add(fieldCol);
		moveButton.setSize(155, 65);
		moveButton.setLocation(135, 5);
		moveButton.addActionListener(buttonListener);
		this.add(moveButton);
		statusArea.setSize(200, 220);
		statusArea.setLocation(50, 80);
		statusArea.setEditable(false);
		statusArea.setFont(new Font("Courier", Font.BOLD, 36));
		statusArea.setText(" " + theGame.toString().trim());
		this.add(statusArea);
	}

	@Override
	public void update() {
		updateGameStatusArea();
		moveButton.setText("Click your move");
		if (theGame.maxMovesRemaining() == theGame.size() * theGame.size())
			moveButton.setEnabled(true);
		else if (!theGame.stillRunning()) {
			moveButton.setEnabled(false);
			if (theGame.tied())
				moveButton.setText("Tied");
			else if (theGame.didWin('X'))
				moveButton.setText("X wins");
			else
				moveButton.setText("O wins");
		}	
	}

	public void updateGameStatusArea() {
		statusArea.setText(" " + theGame.toString().trim());
	}

	private class ButtonListener implements ActionListener {

		@Override
		@SuppressWarnings("resource")
		public void actionPerformed(ActionEvent e) {
			int row = -1, col = -1;
			Scanner scanRow = new Scanner(fieldRow.getText());
			Scanner scanCol = new Scanner(fieldCol.getText());
			if (!scanRow.hasNextInt() || !scanCol.hasNextInt()) {
				JOptionPane.showMessageDialog(null, "Selection not available");
				return;
			}
			row = scanRow.nextInt();
			col = scanCol.nextInt();
			scanRow.close();
			scanCol.close();
			fieldRow.setText("");
			fieldCol.setText("");

			if (!theGame.available(row, col)) {
				JOptionPane.showMessageDialog(null, "Selection not available");
				return;
			}

			theGame.choose(row, col);

			if (theGame.tied()) {
				moveButton.setText("Tied");
				updateGameStatusArea();
			} else if (theGame.didWin('X')) {
				moveButton.setText("X wins");
				updateGameStatusArea();
			} else {
				// If the game is not over, let the computer player choose
				// This algorithm assumes the computer player always
				// goes after the human player and is represented by 'O', not
				// 'X'
				Point play = computerPlayer.desiredMove(theGame);
				theGame.choose(play.x, play.y);
				if (theGame.didWin('O')) {
					moveButton.setText("O wins");
					updateGameStatusArea();
				}
			}
		}
	}

}
