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
		options = new BattleOptions(player, monster);
		screen = new BattleScreen(player, monster);
		this.add(options, BorderLayout.SOUTH);
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


