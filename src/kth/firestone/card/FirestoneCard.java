package kth.firestone.card;

import java.util.Optional;

public class FirestoneCard implements Card {
	
	private String id;
	private String name;
	private Optional<Integer> health;
	private int manaCost;
	private int originalManaCost;
	private Optional<Integer> originalHealth;
	private Optional<Integer> originalAttack;
	private Optional<Integer> attack;
	private Type type;
	private String description;
	
	public FirestoneCard(String id, String name, String health, String attack, 
			String manaCost, String type, String description) {
		this.id = id;
		this.name = name;
		this.originalHealth = Optional.of(Integer.parseInt(health));
		this.health = Optional.of(Integer.parseInt(health));
		this.originalAttack = Optional.of(Integer.parseInt(attack));
		this.attack = Optional.of(Integer.parseInt(attack));
		this.manaCost = Integer.parseInt(manaCost);
		this.type = Type.valueOf(type);
		this.description = description;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public int getManaCost() {
		return manaCost;
	}

	@Override
	public int getOriginalManaCost() {
		return originalManaCost;
	}

	@Override
	public Optional<Integer> getAttack() {
		return attack;
	}

	@Override
	public Optional<Integer> getHealth() {
		return health;
	}

	@Override
	public Optional<Integer> getOriginalAttack() {
		return originalAttack;
	}

	@Override
	public Optional<Integer> getOriginalHealth() {
		return originalHealth;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	public String toString(){
		return name+" ("+health.get()+"/"+originalHealth.get()+", "+attack.get()+"/"+originalAttack.get()+")";
	}

}
