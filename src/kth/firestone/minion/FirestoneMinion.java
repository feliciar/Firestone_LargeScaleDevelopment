package kth.firestone.minion;

import java.util.List;

import kth.firestone.buff.BuffDescription;

public class FirestoneMinion implements Minion {
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

}
