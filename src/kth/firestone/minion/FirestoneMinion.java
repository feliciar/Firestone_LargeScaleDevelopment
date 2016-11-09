package kth.firestone.minion;

import java.util.List;
import kth.firestone.GameData;
import kth.firestone.buff.BuffDescription;
import kth.firestone.card.Card;

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
	
	public FirestoneMinion(String id, Card card, GameData gameData){
		this.id = id;
		this.name = card.getName();
		this.health = card.getHealth().get();
		this.maxHealth = this.health; //TODO This might be something else
		this.originalHealth = card.getOriginalHealth().get();
		this.originalAttack = card.getOriginalAttack().get();
		this.attack = card.getAttack().get();
		this.race = MinionRace.valueOf(gameData.getCards().get(this.name).get("race"));
		//Add the states
		String stateStrings = gameData.getCards().get(this.name).get("state");
		if(stateStrings!=null && !stateStrings.equals("")){
			for(String state : stateStrings.split(", ")){
				this.states.add(MinionState.valueOf(state));
			}
		}
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

	public String toString(){
		return name+" ("+health+"/"+maxHealth+", "+attack+"/"+originalAttack+") race: "+race+ "is sleepy: "+sleepy;
	}

}
