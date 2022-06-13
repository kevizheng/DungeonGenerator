import java.util.Random;

public class Player {
	
	private int strength, dexterity, constitution, hp, row, column;
	private int money = 0;
	
	public Player(int row, int column) {
		this.column = column;
		this.row = row;
		Random random = new Random();
		strength = random.nextInt(21);
		dexterity = random.nextInt(21);
		constitution = random.nextInt(21);
		hp = Math.round( (float) (25 * Math.log10(constitution + 5)));
	}

}
