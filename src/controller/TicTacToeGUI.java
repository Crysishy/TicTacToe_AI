/*
 * Programmer: Yang Hong
 * Class: TicTacToeGUI.java
 * Description: A GUI which extends JFrame for class TicTacToeGame.java.
 * Modifications: 1) Added a sub-menu: view and two options: Jbutton, JTextField under it.
 * 				  2) Added actionListeners for these two options which is able to
 * 					 switch views back and forth.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import model.RandomAI;
import model.StopperAI;
import model.TicTacToeGame;
import view.ButtonView;
import view.GraphicView;
import view.TextAreaView;

/**
 * Play TicTacToe the computer that can have different AIs to beat you.  Select the Options menus to begin
 * a new game, switch strategies for the computer player (BOT or AI), and to switch between the two views
 * 
 * This class represents an event-driven program with a graphical user interface as a controller
 * between the view and the model. It has listeners to mediate between the view and the model.
 * 
 * This controller employs the Observer design pattern that updates two views every time the
 * state of the tic tac toe game changes:
 * 
 *    1) whenever you make a move by clicking a button or an area of either view
 *    2) whenever the computer AI makes a move
 *    3) whenever there is a win or a tie
 *    
 * You can also select two different strategies to play against from the menus
 * 
 * @author mercer
 */
public class TicTacToeGUI extends JFrame {

  public static void main(String[] args) {
    TicTacToeGUI g = new TicTacToeGUI();
    g.setVisible(true);
  }

  private TicTacToeGame theGame;
  private ButtonView buttonView;
  private TextAreaView textView;
  private GraphicView graphicView;
  private JPanel currentView;
  public static final int width = 300;
  public static final int height = 360;

  public TicTacToeGUI() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(width, height);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    this.setTitle("Tic Tac Toe");

    setupMenus();
    initializeGameForTheFirstTime();
    buttonView = new ButtonView(theGame, width, height);
    addObservers(buttonView);
    textView = new TextAreaView(theGame, width, height);
    addObservers(textView);
    graphicView = new GraphicView(theGame, width, height);
    addObservers(graphicView);
    // Set default view
    setViewTo(graphicView);
  }

  private void addObservers(OurObserver observer) {
    theGame.addObserver(observer);
  }

  public void initializeGameForTheFirstTime() {
    theGame = new TicTacToeGame();
    // This event driven program will always have
    // a computer player who takes the second turn
    theGame.setComputerPlayerStrategy(new RandomAI());
  }

  private void setupMenus() {
    JMenuItem menu = new JMenu("Options");
    JMenuItem newGame = new JMenuItem("New Game");
    menu.add(newGame);
    // Add two Composites to a Composite
    JMenuItem jmi2Nest = new JMenu("Stategies");
    menu.add(jmi2Nest);
    JMenuItem beginner = new JMenuItem("RandomAI");
    jmi2Nest.add(beginner);
    JMenuItem intermediate = new JMenuItem("Stopper");
    jmi2Nest.add(intermediate);
    // Add view options
    JMenuItem viewOption = new JMenu("Views");
    menu.add(viewOption);
    JMenuItem toButtonView = new JMenuItem("JButton");
    viewOption.add(toButtonView);
    JMenuItem toTextFieldView = new JMenuItem("JTextField");
    viewOption.add(toTextFieldView);
    JMenuItem toGraphicView = new JMenuItem("JGraphic");
    viewOption.add(toGraphicView);

    // Set the menu bar
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);
    menuBar.add(menu);

    // Add the same listener to all menu items requiring action
    MenuItemListener menuListener = new MenuItemListener();
    newGame.addActionListener(menuListener);
    beginner.addActionListener(menuListener);
    intermediate.addActionListener(menuListener);
    toButtonView.addActionListener(menuListener);
    toTextFieldView.addActionListener(menuListener);
    toGraphicView.addActionListener(menuListener);
  }

  private void setViewTo(JPanel newView) {
    if (currentView != null)
      remove(currentView);
    currentView = newView;
    add(currentView);
    currentView.repaint();
    validate();
  }

  private class MenuItemListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      // Find out the text of the JMenuItem that was just clicked
      String text = ((JMenuItem) e.getSource()).getText();

      if (text.equals("JButton"))
    	  setViewTo(buttonView);
      
      if (text.equals("JTextField"))
    	  setViewTo(textView);
      
      if (text.equals("JGraphic"))
    	  setViewTo(graphicView);

      if (text.equals("New Game")) 
    	  theGame.startNewGame(); // The computer player has been set and should not change.
      

      if (text.equals("Stopper")) 
    	  theGame.setComputerPlayerStrategy(new StopperAI());
      

      if (text.equals("RandomAI")) 
    	  theGame.setComputerPlayerStrategy(new RandomAI());
    }
  }
}