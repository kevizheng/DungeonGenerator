import java.awt.BorderLayout;

import javax.swing.JFrame;

public class BattleWindow extends JFrame{
	Player player;
	Monster monster;
	BattleOptions options;
	BattleScreen screen;
	
	public BattleWindow(Player player, Monster monster) {
		this.player = player;
		this.monster = monster;
		options = new BattleOptions(this.player, this.monster);
		options.setWindow(this);
		screen = new BattleScreen(this.player, this.monster);
		options.setScreen(screen);
		this.add(options, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(750, 750);
		this.add(screen);
		this.setVisible(true);
	}
	
	
	public void setOptions(BattleOptions options) {
		this.options = options;
	}
	
	public static void main(String[] args) {
		Player player = new Player();
		Monster monster = new Goblin();
		BattleWindow window = new BattleWindow(player, monster);
	}
}


