import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JOptionPane;

public class Player {
	private static final int HP_SHIFT = 5;
	private static final int STAT_SHIFT = 1;
	private int strength, dexterity, constitution, currentHP, row, column, exp, maxHP, maxMP, currentMP;
	private int level = 1;
	private int money = 0;
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private int nextLevelEXP;
	private Set<Spell> magicList;
	private Set<Spell> allSpells;
	
	public Player() {
		Random random = new Random();
		strength = random.nextInt(5) + STAT_SHIFT;
		dexterity = random.nextInt(5) + STAT_SHIFT;
		constitution = random.nextInt(5) + STAT_SHIFT;
		calculateHP();
		calculateMP();
		magicList = new HashSet<Spell>();
		allSpells = new HashSet<Spell>();
		allSpells.add(new Fireball());
		allSpells.add(new Cure());
		allSpells.add(new Oblivion());
		nextLevelEXP = 4 * level / 5;
	}

	public void movePlayer(Direction direction) {
		switch(direction) {
		case UP:
			row--;
			break;
		case DOWN:
			row++;
			break;
		case LEFT:
			column--;
			break;
		case RIGHT:
			column++;
			break;
		default:
			break;
		}
	}
	
	public void addTreasureMoney() {
		Random random = new Random();
		this.money += random.nextInt(15) + 5;
	}
	
	public void addMoney(int money) {
		this.money += money;
	}
	
	public boolean addEXP(int exp) {
		this.exp += exp;
		boolean levelUp = false;
		while(this.exp > nextLevelEXP) {
			level++;
			levelUp = true;
			Random random = new Random();
			strength += random.nextInt(3);
			constitution += random.nextInt(3);
			dexterity += random.nextInt(3);
			calculateHP();
			nextLevelEXP = Math.round((float) (4 * Math.pow(level, 3) / 5));
			for(Spell spell : allSpells) {
				if(spell.getLevelRequirement() < level && !magicList.contains(spell)) {
					magicList.add(spell);
					JOptionPane.showConfirmDialog(null, "You learned a new spell!", "Magic Acquired", JOptionPane.CLOSED_OPTION);
				}
			}
		}
		return levelUp;
	}
	
	public void addItem(Item item) {
		inventory.add(item);
	}

	public void damageCalculation(int incomingDamage) {
		currentHP -= Math.round(incomingDamage - ((0.7 * strength + 0.3 * constitution) / 10));
	}
	
	public void draw(int cellWidth, int cellHeight, int xOffset, int yOffset, Graphics g) {
		g.setColor(Color.CYAN);
		g.drawOval(xOffset, yOffset , cellWidth, cellHeight);
		g.fillOval(xOffset, yOffset, cellWidth, cellHeight);
			
	}
	
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setColumn(int column) {
		this.column = column;
	}
	
	public void setCurrentHP(int damage) {
		currentHP -= damage;
		if(currentHP > maxHP) {
			currentHP = maxHP;
		}
	}
	
	public void setCurrentMP(int reduce) {
		currentMP -= reduce;
		if(currentMP > maxMP) {
			currentMP = maxMP;
		}
	}
	
	private void calculateMP() {
		maxMP = Math.round((2 * constitution) * level / 100 + level + 5);
		currentMP = maxMP;
	}
	private void calculateHP() {
		maxHP = Math.round((2 * constitution + HP_SHIFT) * level / 100 + level + 10);
		currentHP = maxHP;
	}
	
	
	public ArrayList<Item> getInventory(){
		return inventory;
	}
	
	public Set<Spell> getMagicList(){
		return magicList;
	}
	
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	
	public int getStrength() {
		return strength;
	}

	public int getDexterity() {
		return dexterity;
	}

	public int getConstitution() {
		return constitution;
	}

	public int getCurrentHP() {
		return currentHP;
	}
	public int getMaxHP() {
		return maxHP;
	}

	public int getLevel() {
		return level;
	}

	public int getMoney() {
		return money;
	}
	
	public int getEXP() {
		return exp;
	}

	public int getMaxMP() {
		return maxMP;
	}

	public int getCurrentMP() {
		return currentMP;
	}
	
}
