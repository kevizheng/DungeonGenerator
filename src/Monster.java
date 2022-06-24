import java.util.HashMap;
import java.util.Map;

public abstract class Monster {
	String name;
	int power;
	int defense;
	int expYield;
	Map<String, Integer> attacks = new HashMap<String, Integer>();
	
	public void attack() {
		
	}
}
