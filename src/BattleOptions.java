import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class BattleOptions extends JFrame implements ActionListener{
	JButton attack = new JButton("Attack");
	JButton items = new JButton("Items");
	JButton magic = new JButton("Magic");
	JButton run = new JButton("Run");
	
	public BattleOptions() {
		setLayout(new GridLayout(2, 2));
		add(attack);
		add(magic);
		add(items);
		add(run);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
