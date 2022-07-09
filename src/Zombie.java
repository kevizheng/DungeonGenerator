import java.util.HashMap;
import java.util.Map;

public class Zombie extends Monster {


	
	public Zombie() {
		super("Zombie", 15, 3, 0, 5, 7);
		Map<String, Integer> attacks = new HashMap<String, Integer>();
		attacks.put("Hit", 2);
		setAttacks(attacks);
	}

	@Override
	public void reset() {
		setCurrentHP(15);
		
	}

}
