package kth.firestone;

import java.util.ArrayList;
import java.util.List;

import kth.firestone.hero.FirestoneHero;
import kth.firestone.hero.Hero;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.minion.Minion;
import kth.firestone.minion.MinionState;
import kth.firestone.player.Player;

public class AttackHandler {
	private List<Player> players;
	private DamageHandler damageHandler;
	
	public AttackHandler(List<Player> players, DamageHandler damageHandler) {
		this.players = players;
		this.damageHandler = damageHandler;
	}
	
	/**
	 * Check if the minion is able to attack.
	 */
	public boolean canAttack(List<Minion> playersActiveMinions, Minion minion) {
		if (!playersActiveMinions.contains(minion)) {
			return false;
		} else if (minion.getStates().contains(MinionState.FROZEN)) {
			return false;
		} else if (minion.isSleepy()) {
			return false;
		} else if(((FirestoneMinion) minion).wasUsedThisTurn()){
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if a hero can be used to attack.
	 */
	public boolean heroCanAttack(Hero hero){
		//TODO Check if the hero has a weapon (if not, false) and if the hero has been used this turn (if true, false). 
		
		return true;
	}
	
	/**
	 * Check if an attack is valid to perform.
	 */
	public boolean isAttackValid(Player player, String attackerId, String targetId) {
		
		if (player.getHero().getId().equals(attackerId)) {
			return isHeroAttackValid(player, attackerId, targetId);
		} else {
			return isMinionAttackValid(player, attackerId, targetId);
		}
	}
	
	public boolean isHeroAttackValid(Player player, String attackerId, String targetId) {
		Hero hero = player.getHero(); 
		if(hero.getId().equals("attackerId")) {
			if (heroCanAttack(hero)) {
				return true;
			}
		}	
		return false;
	}
	
	public boolean isMinionAttackValid(Player player, String attackerId, String targetId) {
		List<Minion> activeMinions = player.getActiveMinions();

		Minion minion = findMinion(activeMinions, attackerId);
		if(minion == null){
			return false;
		}
		
		if(canAttack(activeMinions, minion)) {
			return true;
		}
		return false;
	}
	
	public Player getAdversary(String playerId) {
		if (playerId.equals(players.get(0).getId())) {
			return players.get(1);
		} 
		return players.get(0);
	}
	
	
	
	public Minion findMinion(List<Minion> activeMinions, String minionId) {
		Minion minion = null;
		for(Minion m : activeMinions) {
			if (m.getId().equals(minionId)) {
				minion = m;
				break;
			}	
		}
		return minion;
	}
	
	public List<Event> attack(Player player, String attackerId, String targetId) {
		List<Event> events = new ArrayList<Event>();
		if (!isAttackValid(player, attackerId, targetId)) {
			return events;
		}
		
		if (player.getHero().getId().equals(attackerId)) {
			//The hero is the attacker
			events = attackPerformedByHero(player, attackerId, targetId);
		} else {
			//Minion is the attacker
			events = attackPerformedByMinion(player, attackerId, targetId);
		}
		
		return events;
	}
	
	public List<Event> attackPerformedByMinion(Player player, String attackerId, String targetId){
		List<Event> events = new ArrayList<Event>();
		
		FirestoneMinion attacker = (FirestoneMinion) findMinion(player.getActiveMinions(), attackerId);
		
		Player adversary = getAdversary(player.getId());
		FirestoneMinion targetMinion = (FirestoneMinion) findMinion(adversary.getActiveMinions(), targetId);
		FirestoneHero targetHero = null;
		if (targetMinion == null) {
			// Target was not a minion
			if (adversary.getHero().getId().equals(targetId)) {
				targetHero = (FirestoneHero) adversary.getHero();
			}else if (player.getHero().getId().equals(targetId)) {
				targetHero = (FirestoneHero) player.getHero();
			}
		}
		
		if (targetMinion != null) {
			// Target was minion
			damageHandler.dealDamageToTwoMinions(attacker, targetMinion);
			attacker.setUsedThisTurn(true);
			
			//Remove the minions that were killed 
			damageHandler.removeDeadMinions(player.getActiveMinions());
			damageHandler.removeDeadMinions(adversary.getActiveMinions());
		} else {
			damageHandler.dealDamageToMinionAndHero(attacker, targetHero);
			attacker.setUsedThisTurn(true);
			damageHandler.removeDeadMinions(player.getActiveMinions());
		}
		
		return events;
	}
	
	public List<Event> attackPerformedByHero(Player player, String attackerId, String targetId){
		List<Event> events = new ArrayList<Event>();
		// TODO make Hero attack a minion/hero
		
		return events;
	}
}
