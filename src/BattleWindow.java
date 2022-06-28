import java.awt.BorderLayout;

import javax.swing.JFrame;

public class BattleWindow extends JFrame{
	Player player;
	Monster monster;
	BattleOptions options;
	
	public BattleWindow(Player player, Monster monster) {
		this.player = player;
		this.monster = monster;
		options = new BattleOptions(player, monster);
		this.add(options, BorderLayout.SOUTH);
	}
}
