import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Player {
	private static final int HP_SHIFT = 5;
	private static final int STAT_SHIFT = 5;
	private int strength, dexterity, constitution, currentHP, row, column, exp, maxHP;
	private int level = 1;
	private int money = 0;
	
	public Player() {
		Random random = new Random();
		strength = random.nextInt(16) + STAT_SHIFT;
		dexterity = random.nextInt(16) + STAT_SHIFT;
		constitution = random.nextInt(16) + STAT_SHIFT;
		maxHP = Math.round((2 * constitution + HP_SHIFT) * level / 100 + level + 10);
		currentHP = maxHP;
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
	
	public void addEXP(int exp) {
		this.exp += exp;
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
}
