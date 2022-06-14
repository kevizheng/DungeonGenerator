import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Player {
	
	private int HP_SHIFT = 5;
	private int strength, dexterity, constitution, hp, row, column, exp, level;
	private int money = 0;
	
	public Player(int row, int column) {
		this.column = column;
		this.row = row;
		Random random = new Random();
		strength = random.nextInt(21);
		dexterity = random.nextInt(21);
		constitution = random.nextInt(21);
		hp = Math.round((2 * constitution + HP_SHIFT) * level / 100 + level + 10);
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
	
	public void addMoney(int money) {
		this.money += money;
	}
	
	public void damageCalculation(int incomingDamage) {
		hp -= Math.round(incomingDamage - ((0.7 * strength + 0.3 * constitution) / 10));
	}
	
	public void draw(int cellWidth, int cellHeight, int xOffset, int yOffset, Graphics g) {
		g.setColor(Color.CYAN);
		g.drawOval(xOffset, yOffset , cellWidth, cellHeight);
		g.fillOval(xOffset, yOffset, cellWidth, cellHeight);
			
	}
	
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
}
