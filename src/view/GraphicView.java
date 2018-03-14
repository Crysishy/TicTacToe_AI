/*
 * Programmer: Yang Hong
 * Class: GraphicView.java
 * Description: A GUI which extends JPanel for class TicTacToeGame.java.
 * 				This is the third view of TicTacToeGUI which allows player
 * 				to play Tic Tac Toe with the action of mouse, in this case,
 * 				mouse clicks. It is synchronized with the first view - ButtonView.java
 * 				and the second view - TextAreaView.java. It implements OurObserver
 * 				and MouseListener. It overrides paintComponent(Graphics g) method
 * 				in order to generate expected graphics.
 */
package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import controller.OurObserver;
import model.ComputerPlayer;
import model.TicTacToeGame;

@SuppressWarnings("serial")
public class GraphicView extends JPanel implements OurObserver, MouseListener {

	private TicTacToeGame theGame;
	private ComputerPlayer computerPlayer;
	private int height, width;
	private static final int OFFSET = 35;

	public GraphicView(TicTacToeGame newGame, int width, int height) {
		this.theGame = newGame;
		this.height = height;
		this.width = width;
		this.computerPlayer = theGame.getComputerPlayer();
		addMouseListener(this);
		this.setBackground(Color.CYAN);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.drawLine(100, 0, 100, 360);
		g2.drawLine(200, 0, 200, 360);
		g2.drawLine(0, 100, 300, 100);
		g2.drawLine(0, 210, 300, 210);
		g2.setFont(new Font("Arial", Font.TRUETYPE_FONT, 50));
		char[][] temp = theGame.getTicTacToeBoard();
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[i].length; j++) {
				if (temp[i][j] == 'O') {
					g2.setColor(Color.BLUE);
					g2.drawString(temp[i][j] + "", (j * 100) + OFFSET, (i * 100) + 2 * OFFSET);
				} else if (temp[i][j] == 'X') {
					g2.setColor(Color.RED);
					g2.drawString(temp[i][j] + "", (j * 100) + OFFSET, (i * 100) + 2 * OFFSET);
				}
			}
		}
		g2.setColor(Color.WHITE);
		if (theGame.tied())
			g2.drawString("Tie!", 80, 120);
		if (theGame.didWin('X'))
			g2.drawString("X wins!", 70, 120);
		if (theGame.didWin('O'))
			g2.drawString("O wins!", 70, 120);
	}

	@Override
	public void update() {
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (theGame.stillRunning()) {
			int x = e.getX();
			int y = e.getY();
			int row;
			if (x <= 100)
				row = 0;
			else if (x <= 210)
				row = 1;
			else
				row = 2;

			int col;
			if (y <= 100)
				col = 0;
			else if (y <= 200)
				col = 1;
			else
				col = 2;

			if (theGame.available(col, row))
				theGame.choose(col, row);

			if (theGame.tied())
				repaint();
			else if (theGame.didWin('X'))
				repaint();
			else {
				Point play = computerPlayer.desiredMove(theGame);
				theGame.choose(play.x, play.y);
				if (theGame.didWin('O'))
					repaint();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
