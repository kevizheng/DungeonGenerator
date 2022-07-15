import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

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
		run.addActionListener(this);
		items.addActionListener(this);
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
			attackPlayer();
			screen.revalidate();
			screen.repaint();
		}
		else if (e.getSource() == run) { 
			Random random = new Random();
			int nextNumber = random.nextInt(player.getMaxHP());
			if(nextNumber <= player.getCurrentHP()) {
				JOptionPane.showConfirmDialog(null, "You escaped the battle.", "Congratulations!", JOptionPane.CLOSED_OPTION);
				window.dispose();
				grid.setEnabled(true);
				monster.reset();
			}
			else {
				JOptionPane.showConfirmDialog(null, "You couldn't escape!", "Oh no!", JOptionPane.CLOSED_OPTION);
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
		else if (e.getSource() == items) {
			JFrame inventoryWindow = new JFrame("Inventory");
			JPanel buttons = new JPanel();
			ArrayList<Item> items = player.getInventory();
			if(items.size() == 0) {
				JOptionPane.showConfirmDialog(null, "You have no items in your inventory.", "Empty Inventory", JOptionPane.CLOSED_OPTION);
				return;
			}
			Map<Item, Integer> itemCount = player.getItemCount();
			String[][] data = new String[items.size()][1];
			String[] columnNames = {"Item", "Count", "Effect"};
			int counter = 0;
			for (Item item : items) {
				String[] row = new String[3];
				row[0] = item.getName();
				row[1] = String.valueOf(itemCount.get(item));
				row[2] = String.valueOf(item.getEffect());
				data[counter] = row;
				counter++;
			}
			JTable table = new JTable(data, columnNames);
			JButton use = new JButton("Use");
			JButton cancel = new JButton("Cancel");
			use.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getSource() == use) {
						Item item = items.get(table.getSelectedRow());
						switch(item.getEffect()) {
							case HEAL:
								player.setCurrentHP(-item.getIntensity());
								attackPlayer();
								break;
							case DAMAGE:
								monster.setCurrentHP(monster.getCurrentHP() - item.getIntensity());
								attackPlayer();
								break;
							// To be implemented
							case BUFF:
								break;
						}
						items.remove(table.getSelectedRow());
						inventoryWindow.dispose();
					}
					
				}
				
			});
			cancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getSource() == cancel) {
						inventoryWindow.dispose();
					}	
				}
			});
			inventoryWindow.setSize(500, 500);
			buttons.setLayout(new GridLayout(2, 2));
			buttons.add(use);
			buttons.add(cancel);
			inventoryWindow.add(table, BorderLayout.NORTH);
			inventoryWindow.add(buttons, BorderLayout.SOUTH);
			inventoryWindow.setVisible(true);
			
		}
		
	}
	private void attackPlayer() {
		int damage = monster.getPower() - (player.getConstitution() + player.getStrength() / 8);
		if(damage <= 0) {
			damage = 1;
		}
		player.setCurrentHP(damage);
		if(player.getCurrentHP() <= 0) {
			JOptionPane.showConfirmDialog(null, "You have died.", "Game Over!", JOptionPane.CLOSED_OPTION);
			window.dispose();
		}
	}
	
	
}
