import java.awt.BorderLayout;

import javax.swing.JFrame;

// Class that initializes the game
public class StartGame extends JFrame{

	// Add the grid and player information to a JFrame and display it to the player
	public StartGame() {
		Grid game = Grid.getGame();
		InfoPanel info = new InfoPanel();
		game.initialize();
		info.setGrid(game);
		info.initialize();
		game.setInfoPanel(info);
		add(game);
		add(info, BorderLayout.SOUTH);
		this.setSize(650, 650);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		StartGame game = new StartGame();
	}
}
