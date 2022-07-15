
public abstract class Item {
	private String name;
	private int intensity;
	private ItemEffect effect;
	
	public Item(String name, int intensity, ItemEffect effect) {
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
	
	public ItemEffect getEffect() {
		return effect;
	}
}
