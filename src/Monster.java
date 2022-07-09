import java.util.HashMap;
import java.util.Map;

public abstract class Monster {
	String name;
	int maxHP = 5;
	int currentHP = 5;
	int moneyYield;
	int power;
	int defense;
	int expYield;
	Map<String, Integer> attacks = new HashMap<String, Integer>();
	
	
	public Monster(String name, int maxHP, int power, int defense, int expYield, int moneyYield) {
		this.name = name;
		this.maxHP = maxHP;
		this.currentHP = maxHP;
		this.power = power;
		this.defense = defense;
		this.expYield = expYield;
		this.moneyYield = moneyYield;
	}
	public void attack() {
	}
	
	public int getMaxHP() {
		return maxHP;
	}
	
	public int getCurrentHP() {
		return currentHP;
	}
	
	public void setCurrentHP(int HP) {
		currentHP = HP;
	}
	
	public void setAttacks(Map<String, Integer> attacks) {
		this.attacks = attacks;
	}
	
	public int getMoney() {
		return moneyYield;
	}
	
	public int getEXP() {
		return expYield;
	}
}
