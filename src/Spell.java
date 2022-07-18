
public abstract class Spell {

	String name;
	int manaCost;
	int intensity;
	Effect effect;
	int levelRequirement;
	
	public Spell(String name, int manaCost, Effect effect, int levelRequirement, int intensity) {
		this.name = name;
		this.manaCost = manaCost;
		this.effect = effect;
		this.levelRequirement = levelRequirement;
		this.intensity = intensity;
	}
	
	public int getLevelRequirement() {
		return levelRequirement;
	}
	
	public String getName() {
		return name;
	}

	public int getManaCost() {
		return manaCost;
	}

	public Effect getEffect() {
		return effect;
	}
	
	public int getIntensity() {
		return intensity;
	}
}
