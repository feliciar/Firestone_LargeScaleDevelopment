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
	
	public AttackHandler(List<Player> players) {
		this.players = players;
	}
	
	/**
	 * Check if the minion is able to attack.
	 */
	public boolean canAttack(List<Minion> playersActiveMinions, Minion minion) {
		if (!playersActiveMinions.contains(minion)) {
			System.err.println("Minion does not belong to player. Cannot attack");
			return false;
		} else if (minion.getStates().contains(MinionState.FROZEN)) {
			System.err.println("Minion was FROZEN, cannot attack");
			return false;
		} else if (minion.isSleepy()) {
			System.err.println("Minion was sleepy, cannot attack");
			return false;
		}
		// TODO check if player has used this minion this turn.
		
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
		// TODO implement hero attacks
		return false;
	}
	
	public boolean isMinionAttackValid(Player player, String attackerId, String targetId) {
		List<Minion> activeMinions = player.getActiveMinions();
		if (player.getHero().getId().equals(targetId) || findMinion(activeMinions, targetId) != null) {
			System.err.println("Target belongs to player. No valid attack");
			return false;
		}

		Minion minion = findMinion(activeMinions, attackerId);
		if(minion == null){
			System.err.println("This minion did not exist and therefore cannot attack another minion");
			return false;
		}
		
		if(canAttack(activeMinions, minion)) {
			Player adversary = getAdversary(player.getId());
		
			// Find if the adversary has a TAUNT on the board and who it is.
			List<Minion> tauntingMinions = getAdversaryMinionsWithTaunt(adversary.getActiveMinions());
			
			if (tauntingMinions.size() != 0) {
				// There was at least one taunt minion
				for (Minion m : tauntingMinions){
					if (m.getId().equals(targetId)){
						return true;
					}
				}	
				System.err.println("Could not find a TAUNT target, cannot attack");
				return false;
			}
			return true;
		}
		System.err.println("Could not find target, cannot attack");
		return false;
	}
	
	public Player getAdversary(String playerId) {
		if (playerId.equals(players.get(0).getId())) {
			return players.get(1);
		} 
		return players.get(0);
	}
	
	public List<Minion> getAdversaryMinionsWithTaunt(List<Minion> adversaryActiveMinions) {
		List<Minion> tauntingMinions = new ArrayList<Minion>();
		for(Minion m : adversaryActiveMinions){
			if (m.getStates().contains(MinionState.TAUNT)){
				tauntingMinions.add(m);
			}	
		}
		
		return tauntingMinions;
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
		// TODO check if hero is attacking.
		
		FirestoneMinion attacker = (FirestoneMinion) findMinion(player.getActiveMinions(), attackerId);
		Player adversary = getAdversary(player.getId());
		FirestoneMinion targetMinion = (FirestoneMinion) findMinion(adversary.getActiveMinions(), targetId);
		FirestoneHero targetHero = null;
		if (targetMinion == null) {
			// Target was not a minion
			if (adversary.getHero().getId().equals(targetId)) {
				targetHero = (FirestoneHero) adversary.getHero();
			}				
		}
		
		if (targetMinion != null) {
			// Target was minion
			targetMinion.reduceHealth(attacker.getAttack());
			attacker.reduceHealth(targetMinion.getAttack());
			
			//Remove the minions that were killed 
			if (targetMinion.getHealth() <= 0) {
				adversary.getActiveMinions().remove(targetMinion);
			}
			if (attacker.getHealth() <= 0) {
				player.getActiveMinions().remove(attacker);
			}
		} else {
			targetHero.reduceHealth(attacker.getAttack());
			attacker.reduceHealth(targetHero.getAttack());
			
			//TODO check for defeat (hero)
		}
		return events;
	}
}
