import java.util.HashMap;
import java.util.Map;

public abstract class Monster {
	private String name;
	private int maxHP = 5;
	private int currentHP = 5;
	private int moneyYield;
	private int power;
	private int defense;
	private int expYield;
	private Map<String, Integer> attacks = new HashMap<String, Integer>();
	
	
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
	
	public int getDefense() {
		return defense;
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
	
	public int getPower() {
		return power;
	}
	
	public abstract void reset();
}
