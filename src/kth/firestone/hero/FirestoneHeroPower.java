package kth.firestone.hero;

public class FirestoneHeroPower implements HeroPower {
	private String name;
	private int manaCost;
	private int originalManaCost;
	
	public FirestoneHeroPower(String name, String manaCost){
		this.name = name;
		this.manaCost = Integer.parseInt(manaCost);
		this.originalManaCost = this.manaCost;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getManaCost() {
		return manaCost;
	}

	@Override
	public int getOriginalManaCost() {
		return originalManaCost;
	}
	
	public void setManaCost(int newManaCost){
		manaCost = newManaCost;
	}

}
