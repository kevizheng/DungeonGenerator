import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BattleOptions extends JPanel implements ActionListener{
	JButton attack = new JButton("Attack");
	JButton items = new JButton("Items");
	JButton magic = new JButton("Magic");
	JButton run = new JButton("Run");
	BattleWindow window;
	BattleScreen screen;
	Player player;
	Monster monster;
	
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
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == attack) {
			monster.setCurrentHP(monster.getCurrentHP() - (player.getStrength() + player.getConstitution()) / 2);
			if(monster.getCurrentHP() <= 0) {
				JOptionPane.showConfirmDialog(null, "You won the battle! Obtained " + monster.getMoney() + " gold and " + monster.getEXP() + " EXP.", "Congratulations!", JOptionPane.CLOSED_OPTION);
				player.addMoney(monster.getMoney());
				player.addEXP(monster.getEXP());
				window.dispose();
			}
			screen.revalidate();
			screen.repaint();
		}
		
	}
	
	
}
