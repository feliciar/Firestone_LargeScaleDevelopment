package kth.firestone.buff;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kth.firestone.Action;
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
	 * @param action the action that just took place
	 * @param minion the minion that this buff belongs to
	 */
	public void dealOneDamage(Action action, Minion minion) {
		if (minion == null) return;
		for (Player p : action.getPlayers()) {
			for (Minion m : p.getActiveMinions()) {
				if (m.getId().equals(action.getTargetId())) {
					damageHandler.dealDamageToOneMinion(m, 1);
					damageHandler.removeDeadMinion(p.getActiveMinions(), m);
					break;
				}
			}
		}
	}
	
	/**
	 * Method for performing buff: "Battlecry: If you're holding a Dragon, deal 3 damage."
	 * @param action the action that just took place
	 * @param minion the minion that this buff belongs to
	 */
	public void whenHoldingDragonDealThreeDamage(Action action, Minion minion) {
		Player currentPlayer = getCurrentPlayer(action);
		if (currentPlayer == null) {
			System.err.println("Error: There is no current player.");
			System.exit(1);
		}
/*		for (Iterator itr = currentPlayer.getDeck().iterator(); itr.hasNext();) {
			
		}*/

	}
	
	/**
	 * Method for performing buff: "Battlecry: Gain +1 Health for each card in your hand."
	 * @param action the action that just took place
	 * @param minion the minion that this buff belongs to
	 */
	public void gainOneHealthForCardsInHand(Action action, Minion minion) {
		Player currentPlayer = getCurrentPlayer(action);
		if (currentPlayer == null) {
			System.err.println("Error: There is no current player.");
			System.exit(1);
		}
		int heroHealth = ((FirestoneHero) currentPlayer.getHero()).getHealth();
		heroHealth += currentPlayer.getHand().size();
		((FirestoneHero) currentPlayer.getHero()).setHealth(heroHealth);
	}
	
	/**
	 * Method for performing buff: "Combo: Gain +2/+2 for each card played earlier this turn."
	 * @param action the action that just took place
	 * @param minion the minion that this buff belongs to
	 */
	public void gainTwoForCardsPlayedThisTurn(Action action, Minion minion) {
		
		//TODO Find the played minion
		//int increase = ((GamePlayer) playerInTurn).getDiscardPile().size();
		//FirestoneMinion m = ((FirestoneMinion) minion);
		//Increase health and attack
	}
	
	/**
	 * Method for performing buff: "Battlecry: Gain +1 Attack for each other card in your hand."
	 * @param action the action that just took place
	 * @param minion the minion that this buff belongs to
	 */
	public void gainOneAttackForEachOtherCardInHand(Action action, Minion minion) {
		
	}
	
	/**
	 * Method for performing buff: "Battlecry: If you're holding a Dragon, gain +1 Attack and Taunt."
	 * @param action the action that just took place
	 * @param minion the minion that this buff belongs to
	 */
	public void whenHoldingDragonGainOneAttackAndTaunt(Action action, Minion minion) {
		
	}
	
	/**
	 * Method for performing buff: "After you cast a spell, deal 1 damage to ALL minions."
	 * @param action the action that just took place
	 * @param minion the minion that this buff belongs to
	 */
	public void afterSpellDealOneDamageToAllMinions(Action action, Minion minion) {
		// TODO
		/*if (minion == null) return;
		if (playedCard.getType().equals(Card.Type.SPELL)) {
			List<Minion> minionsToDealDamageTo = new ArrayList<>();
			for (Player p : players) {
				for (Minion m : p.getActiveMinions()) {
					if (m.equals(minion)) continue;
					minionsToDealDamageTo.add(m);
				}
			}
			damageHandler.dealOneDamageToSeveralMinions(minionsToDealDamageTo);
			for (Player p : players) {
				damageHandler.removeDeadMinions(p.getActiveMinions());
			}
		}*/
	}
	
	/**
	 * Method for performing buff: "Deal 3 damage to a character and Freeze it."
	 * @param action the action that just took place
	 */
	public void dealThreeDamageToAndFreezeCharacter(Action action, Minion minion) {
		
		// character is any minion or hero
		
	}
	
	/**
	 * Method for performing buff: "Return a friendly minion to your hand. It costs (2) less."
	 * @param action the action that just took place
	 */
	public void returnOwnMinionToHand(Action action, Minion minion) {
		
		// change mana of returned minion to mana-2
		
	}
	
	/**
	 * Method for performing buff: "Deal 2 damage to an undamaged minion."
	 * @param action the action that just took place
	 */
	public void dealTwoDamageToUndamagedMinion(Action action, Minion minion) {
		
	}
	
	/**
	 * Method for performing buff: "Change the Health of ALL minions to 1."
	 * @param action the action that just took place
	 */
	public void changeHealthOfAllMinionsToOne(Action action, Minion minion) {
		
	}
	
	private Player getCurrentPlayer(Action action) {
		for (Player p : action.getPlayers()) {
			if (p.getId().equals(action.getCurrentPlayerId())) {
				return p;
			}
		}
		return null;
	}
}