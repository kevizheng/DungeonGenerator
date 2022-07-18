
public abstract class Item {
	private String name;
	private int intensity;
	private Effect effect;
	
	public Item(String name, int intensity, Effect effect) {
		this.name = name;
		this.intensity = intensity;
		this.effect = effect;
	}
	
	public int getIntensity() {
		return intensity;
	}
	
	public String getName() {
		return name;
	}
	
	public Effect getEffect() {
		return effect;
	}
}
