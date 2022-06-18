import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel{
	private Grid grid;
	private Player player;
	private JLabel hp;
	private JLabel money;
	private JLabel exp;
	private JLabel strength;
	private JLabel dexterity;
	private JLabel constitution;
	private JLabel level;
	
	public InfoPanel() {
	}
	
	public void initialize() {
		this.removeAll();
		this.setLayout(new GridLayout(3, 3));
		hp = new JLabel("HP: " + player.getCurrentHP() + "/" + player.getMaxHP());
		money = new JLabel("Money: " + player.getMoney());
		exp = new JLabel("EXP: " + player.getEXP());
		strength = new JLabel("Strength: " + player.getStrength());
		dexterity = new JLabel("Dexterity: " + player.getDexterity());
		constitution = new JLabel("Constituion: " + player.getConstitution());
		level = new JLabel("Level: " + player.getLevel());
		add(level);
		add(hp);
		add(exp);
		add(money);
		add(strength);
		add(constitution);
		add(dexterity);
		setVisible(true);
		repaint();
	}
	
	public void setGrid(Grid game) {
		grid = game;
		player = game.getPlayer();
	}
}
