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
	
	// Creates the 4 buttons that will be displayed in a battle
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
		
		// action performed when the player clicks attack
		if(e.getSource() == attack) {
			
			// Subtract HP from the monster equivalent to half of the player's strength and constitution minus the monster's defense
			monster.setCurrentHP(monster.getCurrentHP() - (player.getStrength() + player.getConstitution() - monster.getDefense()) / 2);
			
			// If the monster's HP is <= 0, display a message saying the player won the battle and give them rewards
			// Afterwards, dispose the battle window and allow the player to continue moving in addition to reseting the monster's HP
			if(monster.getCurrentHP() <= 0) {
				JOptionPane.showConfirmDialog(null, "You won the battle! Obtained " + monster.getMoney() + " gold and " + monster.getEXP() + " EXP.", "Congratulations!", JOptionPane.CLOSED_OPTION);
				player.addMoney(monster.getMoney());
				if(player.addEXP(monster.getEXP())) {
					JOptionPane.showConfirmDialog(null, "You have leveled up! You are now Level " + player.getLevel() + ".", "Level Up!", JOptionPane.CLOSED_OPTION);
				}
				window.dispose();
				grid.setEnabled(true);
				monster.reset();
				return;
			}
			
			// If the monster still has HP, have it attack the player and repaint the battle window to show
			// updated stats
			attackPlayer();
			screen.revalidate();
			screen.repaint();
		}
		else if (e.getSource() == run) { 
			// Generate a number based on the player's max HP, and if it is less than or equal to their current HP,
			// Display a message box that tells them that they've escaped
			Random random = new Random();
			int nextNumber = random.nextInt(player.getMaxHP());
			if(nextNumber <= player.getCurrentHP()) {
				JOptionPane.showConfirmDialog(null, "You escaped the battle.", "Congratulations!", JOptionPane.CLOSED_OPTION);
				window.dispose();
				grid.setEnabled(true);
				monster.reset();
			}
			else {
				// Otherwise, display a message box that tells them they couldn't escape and damage the player
				JOptionPane.showConfirmDialog(null, "You couldn't escape!", "Oh no!", JOptionPane.CLOSED_OPTION);
				attackPlayer();
				screen.revalidate();
				screen.repaint();
			}
		}
		else if (e.getSource() == items) {
			// Create a new window that will display the player's inventory
			JFrame inventoryWindow = new JFrame("Inventory");
			JPanel buttons = new JPanel();
			ArrayList<Item> items = player.getInventory();
			// if the player's inventory is empty, display a message box stating so and return back to the battle
			if(items.size() == 0) {
				JOptionPane.showConfirmDialog(null, "You have no items in your inventory.", "Empty Inventory", JOptionPane.CLOSED_OPTION);
				return;
			}
			Map<Item, Integer> itemCount = player.getItemCount();
			String[][] data = new String[items.size()][1];
			String[] columnNames = {"Item", "Count", "Effect"};
			int counter = 0;
			// For every item the player has, create a row that will be displayed that contains the item's name, the quantity, and the effect
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
					// When the player clicks use, the selected item heals, damages, or buffs depending on its effect tag
					// and is then removed from the player's inventory. The window is then removed
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
					// If the player clicks cancel, close the inventory window
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
		// The damage the player takes is equivalent to the monster's power minus the 1/8 the players constitution and strength
		int damage = monster.getPower() - (player.getConstitution() + player.getStrength() / 8);
		if(damage <= 0) {
			damage = 1;
		}
		player.setCurrentHP(damage);
		// If the player's current HP is less than or equal to 0, tell them that they've died and close the battle window
		if(player.getCurrentHP() <= 0) {
			JOptionPane.showConfirmDialog(null, "You have died.", "Game Over!", JOptionPane.CLOSED_OPTION);
			window.dispose();
		}
	}
	
	
}
