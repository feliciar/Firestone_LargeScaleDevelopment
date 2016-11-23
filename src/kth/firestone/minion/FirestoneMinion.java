package kth.firestone.minion;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import kth.firestone.buff.BuffDescription;

public class FirestoneMinion implements Minion, Observer {
	private String id;
	private String name;
	private int health;
	private int maxHealth;
	private int originalHealth;
	private int originalAttack;
	private int attack;
	private MinionRace race;
	private List<MinionState> states;
	private List<BuffDescription> buffDescriptions;
	private boolean sleepy = true;
	
	public FirestoneMinion(String id, String name, int health, int originalHealth, 
			int originalAttack, int attack, MinionRace race, List<MinionState> states, 
			List<BuffDescription> buffDescriptions) {
		this.id = id;
		this.name = name;
		this.health = health;
		this.maxHealth = health;
		this.originalHealth = originalHealth;
		this.originalAttack = originalAttack;
		this.attack = attack;
		this.race = race;
		this.states = states;
		this.buffDescriptions = buffDescriptions;

		//TODO get buffDescriptions and if it should be sleepy (should not be sleepy if is has BattleCry)		
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
	public int getHealth() {
		return health;
	}

	@Override
	public int getMaxHealth() {
		return maxHealth;
	}

	@Override
	public int getOriginalHealth() {
		return originalHealth;
	}

	@Override
	public int getOriginalAttack() {
		return originalAttack;
	}

	@Override
	public int getAttack() {
		return attack;
	}

	@Override
	public MinionRace getRace() {
		return race;
	}

	@Override
	public List<MinionState> getStates() {
		return states;
	}

	@Override
	public List<BuffDescription> getBuffDescriptions() {
		return buffDescriptions;
	}

	@Override
	public boolean isSleepy() {
		return sleepy;
	}
	
	public void setSleepy(boolean sleepy){
		this.sleepy = sleepy;
	}
	
	public void setHealth(int newHealth){
		health = newHealth;
	}
	
	public void setAttack(int newAttack){
		attack = newAttack;
	}

	public String toString(){
		String raceString = (race == MinionRace.NONE) ? "" : race.toString();
		String sleepString = isSleepy() ? "Zzz" : "";
		return name+" ("+health+"/"+maxHealth+", "+attack+"/"+originalAttack+") " + raceString +" " + sleepString;
	}
	
	public void reduceHealth(int val) {
		health -= val;
	}

	@Override
	public void update(Observable observable, Object arg) {
		// TODO
	}
	
}
