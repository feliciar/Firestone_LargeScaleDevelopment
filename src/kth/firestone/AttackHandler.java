package kth.firestone;

import java.util.List;

import kth.firestone.hero.Hero;
import kth.firestone.minion.Minion;
import kth.firestone.player.Player;

public class AttackHandler {
	private List<Player> players;
	
	public AttackHandler(List<Player> players) {
		this.players = players;
	}
	
	/*
	 * Check if the minion is able to attack.
	 */
	public boolean canAttack(Player player, Minion minion) {
		if (minion.getStates().contains("FROZEN")) {
			return false;
		} else if (minion.isSleepy()){//Utlagd nyss
			return false;
		}
		// Kan ej attackera om redan attackerat
		
		return true;
	}
	
	/*
	 * Check if an attack is valid to perform.
	 */
	public boolean isAttackValid(Player player, String attackerId, String targetId) {
		List<Minion> activeMinions= player.getActiveMinions();
		Minion minion = null;
		//Find the minion chosen to attack, or get the hero.
		for(Minion m : activeMinions) {
			if (m.getId().equals(attackerId)) {
				minion = m;
				break;
			}	
		}
		
		if(canAttack(player, minion)) {
			Player adversary = getAdversary(player.getId());
		
			// Find if the adversary has a TAUNT on the board and who it is.
			List<Minion> adversaryActiveMinions= adversary.getActiveMinions();
			boolean hasTaunt = false;
			Minion adversaryMinion = null;
			for(Minion m : adversaryActiveMinions){
				if (m.getStates().contains("TAUNT")){
					hasTaunt = true;
					adversaryMinion = m;
					break;
				}	
			}
			if (hasTaunt && adversaryMinion.getId().equals(targetId)){// There was a TAUNT and it is the target
				return true;
			}
			
			return false;
		}
		return false;
	}
	
	private Player getAdversary(String attackerId){
		Player adversary;
		if(attackerId.equals(players.get(0).getId())) {
			adversary = players.get(1);
		}else{
			adversary = players.get(0);
		}
		return adversary;
	}
	
	
	public List<Event> attack(Player player, String attackerId, String targetId) {
		return null;
	}
}
