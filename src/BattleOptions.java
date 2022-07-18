import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
		magic.addActionListener(this);
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
			if(checkMonsterDeath()) {
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
			String[][] data = new String[items.size()][1];
			String[] columnNames = {"Item", "Intensity", "Effect"};
			int counter = 0;
			// For every item the player has, create a row that will be displayed that contains the item's name, the quantity, and the effect
			for (Item item : items) {
				String[] row = new String[3];
				row[0] = item.getName();
				row[1] = String.valueOf(item.getIntensity());
				row[2] = String.valueOf(item.getEffect());
				data[counter] = row;
				counter++;
			}
			JTable table = new JTable(data, columnNames);
			table.setSize(new Dimension(inventoryWindow.getWidth(), inventoryWindow.getHeight() - 100));
			JScrollPane scroll = new JScrollPane(table);
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
								checkMonsterDeath();
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
			inventoryWindow.add(scroll, BorderLayout.NORTH);
			inventoryWindow.add(buttons);
			inventoryWindow.setVisible(true);
			
		}
		else if(e.getSource() == magic) {
			JFrame magicWindow = new JFrame("Magic");
			JPanel buttons = new JPanel();
			Set<Spell> spells = player.getMagicList();
			if(spells.isEmpty()) {
				JOptionPane.showConfirmDialog(null, "You don't know any spells!", "Learn some magic", JOptionPane.CLOSED_OPTION);
				return;
			}
			String[][] data = new String[spells.size()][1];
			String[] columnNames = {"Spell", "Mana Cost", "Effect"};
			int counter = 0;
			for (Spell spell: spells) {
				String[] row = new String[3];
				row[0] = spell.getName();
				row[1] = String.valueOf(spell.getManaCost());
				row[2] = String.valueOf(spell.getEffect());
				data[counter] = row;
				counter++;
			}
			JTable table = new JTable(data, columnNames);
			table.setSize(new Dimension(magicWindow.getWidth(), magicWindow.getHeight() - 100));
			JScrollPane scroll = new JScrollPane(table);
			JButton use = new JButton("Cast");
			JButton cancel = new JButton("Cancel");
			use.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// When the player clicks use, the selected item heals, damages, or buffs depending on its effect tag
					// and is then removed from the player's inventory. The window is then removed
					if(e.getSource() == use) {
						Object name = table.getValueAt(table.getSelectedRow(), 0);
						name = (String) name;
						Spell selectedSpell = new Splash();
						magicWindow.dispose();
						for(Spell spell : spells) {
							if(name.equals(spell.getName())) {
								selectedSpell = spell;
							}
						}
						switch(selectedSpell.getEffect()) {
							case HEAL:
								player.setCurrentHP(-selectedSpell.getIntensity());
								attackPlayer();
								break;
							case DAMAGE:
								monster.setCurrentHP(monster.getCurrentHP() - selectedSpell.getIntensity());
								checkMonsterDeath();
								attackPlayer();
								break;
							// To be implemented
							case BUFF:
								break;
							case NOTHING:
								JOptionPane.showConfirmDialog(null, "Nothing happened.", "Hm?", JOptionPane.CANCEL_OPTION);
						}
						magicWindow.dispose();
					}
				}
			});
			cancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// If the player clicks cancel, close the inventory window
					if(e.getSource() == cancel) {
						magicWindow.dispose();
					}	
				}
			});
			magicWindow.setSize(500, 500);
			buttons.setLayout(new GridLayout(2, 2));
			buttons.add(use);
			buttons.add(cancel);
			magicWindow.add(scroll, BorderLayout.NORTH);
			magicWindow.add(buttons);
			magicWindow.setVisible(true);
		}
		
	}

	private boolean checkMonsterDeath() {
		if(monster.getCurrentHP() <= 0) {
			JOptionPane.showConfirmDialog(null, "You won the battle! Obtained " + monster.getMoney() + " gold and " + monster.getEXP() + " EXP.", "Congratulations!", JOptionPane.CLOSED_OPTION);
			player.addMoney(monster.getMoney());
			if(player.addEXP(monster.getEXP())) {
				JOptionPane.showConfirmDialog(null, "You have leveled up! You are now Level " + player.getLevel() + ".", "Level Up!", JOptionPane.CLOSED_OPTION);
			}
			window.dispose();
			grid.setEnabled(true);
			monster.reset();
			return true;
		}
		return false;
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
