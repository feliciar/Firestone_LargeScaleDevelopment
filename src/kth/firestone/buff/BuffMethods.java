package kth.firestone.buff;

import java.util.ArrayList;
import java.util.List;

import kth.firestone.DamageHandler;
import kth.firestone.card.Card;
import kth.firestone.hero.FirestoneHero;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.minion.Minion;
import kth.firestone.player.GamePlayer;
import kth.firestone.player.Player;

public class BuffMethods {
	
	DamageHandler damageHandler;
	
	public BuffMethods(DamageHandler damageHandler) {
		this.damageHandler = damageHandler;
	}
	
	/**
	 * Method for performing buff: "Battlecry: Deal 1 damage."
	 */
	public void battleCryDealOneDamage(List<Player> players, Player playerInTurn, 
			Card playedCard, String attackerId, String targetId) {
		
		for (Player p : players) {
			for (Minion m : p.getActiveMinions()) {
				if (m.getId().equals(targetId)) {
					damageHandler.dealDamageToOneMinion((FirestoneMinion) m, 1);
				}
			}
			damageHandler.removeDeadMinions(p.getActiveMinions());
		}
	}
	
	/**
	 * Method for performing buff: "After you cast a spell, deal 1 damage to ALL minions."
	 */
	/*public void afterSpellDealOneDamageToAllMinions(List<Player> players, Player playerInTurn, 
			Card playedCard, String attackerId, String targetId) {
		
		if (playedCard.getType().equals(Card.Type.SPELL)) {
			List<Minion> minionsToDealDamageTo = new ArrayList<>();
			for (Player p : players) {
				for (Minion m : p.getActiveMinions()) {
					if (m.getName().equals(???))) continue;
					minionsToDealDamageTo.add(m);
				}
			}
			//damageHandler.dealOneDamageToSeveralMinions(minionsToDealDamageTo);
			//TODO deal damage to all minions and remove dead minions
		}
	}	*/
}
