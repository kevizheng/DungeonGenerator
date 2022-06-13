import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Player {
	
	private int HP_SHIFT = 5;
	private int strength, dexterity, constitution, hp, row, column, exp;
	private int money = 0;
	
	public Player(int row, int column) {
		this.column = column;
		this.row = row;
		Random random = new Random();
		strength = random.nextInt(21);
		dexterity = random.nextInt(21);
		constitution = random.nextInt(21);
		hp = Math.round( (float) (25 * Math.log10(constitution + HP_SHIFT)));
	}

	public void movePlayer(Direction direction) {
		switch(direction) {
		case UP:
			row--;
		case DOWN:
			row++;
		case LEFT:
			column--;
		case RIGHT:
			column++;
		}
	}
	
	public void draw(int cellWidth, int cellHeight, int xOffset, int yOffset, Graphics g) {
		g.setColor(Color.CYAN);
		g.drawOval(xOffset, yOffset , cellWidth, cellHeight);
		g.fillOval(xOffset, yOffset, cellWidth, cellHeight);
			
	}
}
