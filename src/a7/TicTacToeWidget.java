package a7;

import java.awt.BorderLayout; 
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class TicTacToeWidget extends JPanel implements ActionListener, SpotListener{

	// Enum to identify player
	private enum Player {WHITE, BLACK};
	
	private JSpotBoard board; //Spotboard play area
	private JLabel message; // Label for messages
	private boolean gameWon; // Game won variable
	private Player nextToPlay; // Identifies next player
	private boolean gameDrawn; // Game draw variable

	
	
	public TicTacToeWidget() {
		// create spotboard and message label
		board = new JSpotBoard(3,3);
		message = new JLabel();


		// set layout and place spotboard at center
		setLayout(new BorderLayout());
		add(board, BorderLayout.CENTER);
		
		// create subpanel for message area and reset button
		JPanel resetMessagePanel = new JPanel();
		resetMessagePanel.setLayout(new BorderLayout());
		
		// reset button and ourselves as action listener
		
		JButton resetButton = new JButton("Restart");
		resetButton.addActionListener(this);
		resetMessagePanel.add(resetButton, BorderLayout.EAST);
		resetMessagePanel.add(message, BorderLayout.CENTER);
		
		// Add subpanel in south area of layout
		
		add(resetMessagePanel, BorderLayout.SOUTH);
		
		board.addSpotListener(this);
		resetGame();
	}
	
	
	
	@Override
	public void spotClicked(Spot spot) {
		// If game already won, do nothing
		
		if (gameWon) {
			return;
		}
		if (gameDrawn) {
			return;
		}
		if (!spot.isEmpty()) {
			return;
		}
		
		// set up player and next player names as well as player colors
		
		String playerName = null;
		String nextPlayerName = null;
		Color playerColour = null;
		
		if(nextToPlay == Player.BLACK) {
			playerColour = Color.BLACK;
			playerName = "Black";
			nextPlayerName = "White";
			nextToPlay = Player.WHITE;
		} else {
			playerColour = Color.WHITE;
			playerName = "White";
			nextPlayerName = "Black";
			nextToPlay = Player.BLACK;
		}
		
		
		// set spots graphics and status as 
		
		spot.setSpotColor(playerColour);
		spot.setSpot();
		
		// check rows for win
		
	
		for(int i = 0;i < board.getSpotWidth(); i++) {
			
		
			if (!(board.getSpotAt(0, i).isEmpty() && board.getSpotAt(1, i).isEmpty() && board.getSpotAt(2, i).isEmpty())) {
		
				if ((board.getSpotAt(0, i).getSpotColor() == board.getSpotAt(1, i).getSpotColor()) 
					&& (board.getSpotAt(0, i)).getSpotColor() == board.getSpotAt(2, i).getSpotColor()) {
					
						gameWon = true;
				}
			}
		}
		
		// check columns for win
		
		for(int i = 0;i < board.getSpotHeight(); i++) {
			
			
			if (!(board.getSpotAt(i, 0).isEmpty() && board.getSpotAt(i, 1).isEmpty() && board.getSpotAt(i, 2).isEmpty())) {
				if ((board.getSpotAt(i, 0).getSpotColor() == board.getSpotAt(i, 1).getSpotColor()) 
					&& (board.getSpotAt(i, 0)).getSpotColor() == board.getSpotAt(i,2).getSpotColor()) {
						gameWon = true;
				}
			}
		}
		
		
		// check diagonals for win
		if (!(board.getSpotAt(0, 0).isEmpty() && board.getSpotAt(1, 1).isEmpty() && board.getSpotAt(2, 2).isEmpty())) {
			if ((board.getSpotAt(0, 0).getSpotColor() == board.getSpotAt(1, 1).getSpotColor()) && (board.getSpotAt(0, 0).getSpotColor() == board.getSpotAt(2, 2).getSpotColor())) {
				gameWon = true;
			}
		}
		if (!(board.getSpotAt(2, 0).isEmpty() && board.getSpotAt(1, 1).isEmpty() && board.getSpotAt(0, 2).isEmpty())) {
			if ((board.getSpotAt(2, 0).getSpotColor() == board.getSpotAt(1, 1).getSpotColor()) && (board.getSpotAt(2, 0).getSpotColor() == board.getSpotAt(0, 2).getSpotColor())) {
				gameWon = true;
			}
		}
		
		// checks for a draw
		if (!gameWon) {
			for (Spot s: board) {
				gameDrawn = true;
				if (s.isEmpty()) {
					gameDrawn = false;
					break;
				}
			}
		}
		// update message based on what happened
		// if a win was found says who won
		// if a draw is found says the game is drawn
		// if neither then it says who will play next
		
		if (gameWon) {
			message.setText(playerName + " wins. Congrats you just won a children's game");
		}
		if (gameDrawn) {
			message.setText("Draw game");
		}
		
		if (!gameWon && !gameDrawn) {
			message.setText(nextPlayerName + " to play. Good luck, you're going to need it, you dolt");
		}
			
	}
		
		
		
	

	@Override
	public void spotEntered(Spot spot) {
		
		if (gameWon) {
			return;
		}
		if (gameDrawn) {
			return;
		}
		if (spot.isEmpty())
			spot.highlightSpot();
	}

	@Override
	public void spotExited(Spot spot) {
	
		spot.unhighlightSpot();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		resetGame();
	}
	
	
	public void resetGame() {
	
		for (Spot s : board) {
			s.clearSpot();
			s.setBackground(Color.PINK);
			s.setSpotColor(Color.PINK);
		}
		
		gameWon = false;
		gameDrawn = false;
		nextToPlay = Player.WHITE;
		
		message.setText("Welcome to Tic Tac Toe, literally the most dumb, simple game you can play. White to play.");
	}


}
