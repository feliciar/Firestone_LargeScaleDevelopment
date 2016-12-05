package kth.firestone.hero;

import java.util.ArrayList;
import java.util.List;

public class FirestoneHero implements Hero {
	private String id;
	private String name;
	private int health;
	private int maxHealth;
	private int maxMana;
	private int mana;
	private int attack;
	private HeroPower heroPower;
	private boolean hasUsedHeroPower = false;
	private List<HeroState> states;
	private Weapon weapon;
	
	public FirestoneHero(String id, String name, int health, HeroPower heroPower) {
		this.id = id;
		this.name = name;
		this.health = health;
		this.maxHealth = health;
		weapon = new FirestoneWeapon();
		states = new ArrayList<>();
		this.heroPower = heroPower;
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getArmor() {
		return 0;
	}

	@Override
	public int getAttack() {
		return attack;
	}

	@Override
	public int getMaxHealth() {
		return maxHealth;
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public int getMaxMana() {
		return maxMana;
	}

	@Override
	public int getMana() {
		return mana;
	}

	@Override
	public List<HeroState> getStates() {
		return states;
	}

	@Override
	public HeroPower getHeroPower() {
		return heroPower;
	}

	@Override
	public Weapon getWeapon() {
		return weapon;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	public void setMana(int mana) {
		this.mana = mana;
	}
	
	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}
	
	public void setAttack(int attack) {
		this.attack = attack;
	}
	
	public void setHeroPower(HeroPower newHeroPower){
		heroPower = newHeroPower;
	}
	
	public void setHasUsedHeroPower(boolean hasUsedHeroPower){
		this.hasUsedHeroPower = hasUsedHeroPower;
	}
	
	public boolean hasUsedHeroPower(){
		return hasUsedHeroPower;
	}
	
	public String toString(){
		return "Hero: id: "+id+" name: "+name + " health: " + health+"/"+maxHealth + " attack: "+ attack+ " mana: "+mana;
	}
	
	public void reduceHealth(int val) {
		health -= val;
	}
	
	public void restoreAllMana(){
		mana = maxMana;
	}

}
