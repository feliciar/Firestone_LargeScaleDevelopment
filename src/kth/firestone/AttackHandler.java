package kth.firestone;

import java.util.ArrayList;
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
		Minion minion = findMinion(activeMinions, attackerId);
		
		if(minion == null){
			System.err.println("This minion did not exist and therefore cannot attack another minion");
			return false;
		}
		
		if(canAttack(player, minion)) {
			Player adversary = getAdversary(player.getId());
		
			// Find if the adversary has a TAUNT on the board and who it is.
			List<Minion> tauntingMinions = getAdversaryMinionswithTaunt(adversary);
			
			if (tauntingMinions.size() != 0){// There was at least one taunt minion
				for(Minion m : tauntingMinions){
					if (m.getId().equals(targetId)){
						return true;
					}
				}	
				return false;
			}
			return true;
		}
		return false;
	}
	
	private Player getAdversary(String playerId){
		Player adversary;
		if(playerId.equals(players.get(0).getId())) {
			adversary = players.get(1);
		}else{
			adversary = players.get(0);
		}
		return adversary;
	}
	
	private List<Minion> getAdversaryMinionswithTaunt(Player adversary){
		List<Minion> adversaryActiveMinions = adversary.getActiveMinions();
		List<Minion> tauntingMinions = new ArrayList<Minion>();
		for(Minion m : adversaryActiveMinions){
			if (m.getStates().contains("TAUNT")){
				tauntingMinions.add(m);
			}	
		}
		
		return tauntingMinions;
	}
	
	private Minion findMinion(List<Minion> activeMinions, String minionId){
		Minion minion = null;
		//Find the minion chosen to attack.
		for(Minion m : activeMinions) {
			if (m.getId().equals(minionId)) {
				minion = m;
				break;
			}	
		}
		return minion;
	}
	
	public List<Event> attack(Player player, String attackerId, String targetId) {
		List<Event> eventList = new ArrayList<Event>();
		boolean acceptedAttack = isAttackValid(player, attackerId, targetId);
		if (acceptedAttack){
			Minion attacker = findMinion(player.getActiveMinions(), attackerId);
			Player adversary = getAdversary(player.getId());
			Minion target = findMinion(adversary.getActiveMinions(), targetId);
			Hero targetHero = null;
			if (target == null){// det var inte en minion som var target
				if (adversary.getHero().getId().equals(targetId)){
					targetHero = adversary.getHero();
				}				
			}
			//TODO gör klart själva attacken
		}
		return eventList;
	}
}
