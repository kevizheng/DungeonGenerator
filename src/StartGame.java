import java.awt.BorderLayout;

import javax.swing.JFrame;

public class StartGame extends JFrame{

	public StartGame() {
		Grid game = Grid.getGame();
		InfoPanel info = new InfoPanel();
		game.initialize();
		info.setGrid(game);
		info.initialize();
		add(game);
		add(info, BorderLayout.SOUTH);
		this.setSize(750, 750);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		StartGame game = new StartGame();
	}
}
