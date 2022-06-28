import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BattleOptions extends JPanel implements ActionListener{
	JButton attack = new JButton("Attack");
	JButton items = new JButton("Items");
	JButton magic = new JButton("Magic");
	JButton run = new JButton("Run");
	Player player;
	Monster monster;
	
	public BattleOptions(Player player, Monster monster) {
		setLayout(new GridLayout(2, 2));
		add(attack);
		add(magic);
		add(items);
		add(run);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == attack) {
			
		}
		
	}
	
	
}
