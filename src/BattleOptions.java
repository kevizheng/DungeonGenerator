import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BattleOptions extends JPanel implements ActionListener{
	private JButton attack = new JButton("Attack");
	private JButton items = new JButton("Items");
	private JButton magic = new JButton("Magic");
	private JButton run = new JButton("Run");
	private BattleWindow window;
	private BattleScreen screen;
	private Player player;
	private Monster monster;
	private Grid grid;
	
	public BattleOptions(Player player, Monster monster) {
		setLayout(new GridLayout(2, 2));
		this.player = player;
		this.monster = monster;
		attack.addActionListener(this);
		add(attack);
		add(magic);
		add(items);
		add(run);
	}

	public void setWindow(BattleWindow window) {
		this.window = window;
	}
	public void setScreen(BattleScreen screen) {
		this.screen = screen;
	}
	
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == attack) {
			monster.setCurrentHP(monster.getCurrentHP() - (player.getStrength() + player.getConstitution() - monster.getDefense()) / 2);
			if(monster.getCurrentHP() <= 0) {
				JOptionPane.showConfirmDialog(null, "You won the battle! Obtained " + monster.getMoney() + " gold and " + monster.getEXP() + " EXP.", "Congratulations!", JOptionPane.CLOSED_OPTION);
				player.addMoney(monster.getMoney());
				player.addEXP(monster.getEXP());
				window.dispose();
				grid.setEnabled(true);
				monster.reset();
				return;
			}
			int damage = monster.getPower() - (player.getConstitution() + player.getStrength() / 8);
			if(damage <= 0) {
				damage = 1;
			}
			player.setCurrentHP(damage);
			if(player.getCurrentHP() <= 0) {
				JOptionPane.showConfirmDialog(null, "You have died.", "Game Over!", JOptionPane.CLOSED_OPTION);
				window.dispose();
			}
			screen.revalidate();
			screen.repaint();
		}
		
	}
	
	
}
