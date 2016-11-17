package kth.firestone.buff;

public class FirestoneBuffDescription implements BuffDescription{
	private String name;
	private String description;
	
	public FirestoneBuffDescription(String name, String description) {
		this.name = name;
		this.description = description;
	}
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

}
