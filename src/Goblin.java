import java.util.HashMap;
import java.util.Map;

public class Goblin extends Monster {

	Map<String, Integer> attacks = new HashMap<String, Integer>();
	public Goblin() {
		super("Goblin", 10, 3, 0, 5, 5);
		attacks.put("Hit", 2);
		setAttacks(attacks);
	}
}
