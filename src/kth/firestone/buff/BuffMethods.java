package kth.firestone.buff;

import java.util.ArrayList;
import java.util.List;
import kth.firestone.Action;
import kth.firestone.DamageHandler;
import kth.firestone.card.Card;
import kth.firestone.card.FirestoneCard;
import kth.firestone.deck.FirestoneDeck;
import kth.firestone.hero.FirestoneHero;
import kth.firestone.hero.HeroState;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.minion.Minion;
import kth.firestone.minion.MinionRace;
import kth.firestone.minion.MinionState;
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
					return;
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
		if (minion == null) return;
		Player currentPlayer = getCurrentPlayer(action);
		for (Card c : currentPlayer.getHand()) {
			if (c.getRace().get().equals(MinionRace.DRAGON)) {
				for (Player p : action.getPlayers()) {
					// if target is a hero
					if (p.getHero().getId().equals(action.getTargetId())) {
						damageHandler.dealDamageToHero(p.getHero(), 3);
						return;
					}
					// if target is a minion
					for (Minion m : p.getActiveMinions()) {
						if (m.getId().equals(action.getTargetId())) {
							damageHandler.dealDamageToOneMinion(m, 3);
							damageHandler.removeDeadMinions(p.getActiveMinions());
							return;
						}
					}
				}
			}
		}
	}
	
	/**
	 * Method for performing buff: "Battlecry: Gain +1 Health for each card in your hand."
	 * @param action the action that just took place
	 * @param minion the minion that this buff belongs to
	 */
	public void gainOneHealthForCardsInHand(Action action, Minion minion) {
		Player currentPlayer = getCurrentPlayer(action);
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
		Player currentPlayer = getCurrentPlayer(action);
		List<Card> cardsPlayed = ((GamePlayer) currentPlayer).getDiscardPileThisTurn();
		if (cardsPlayed.size() > 0) {
			int increase = cardsPlayed.size()*2;
			FirestoneHero hero = ((FirestoneHero) currentPlayer.getHero());
			hero.setHealth(hero.getHealth() + increase);
			hero.setAttack(hero.getAttack() + increase);
		} else {
			System.err.println("Error: No cards were played earlier.");
		}
	}
	
	/**
	 * Method for performing buff: "Battlecry: Gain +1 Attack for each other card in your hand."
	 * @param action the action that just took place
	 * @param minion the minion that this buff belongs to
	 */
	public void gainOneAttackForEachOtherCardInHand(Action action, Minion minion) {
		if (minion == null) return;
		Player currentPlayer = getCurrentPlayer(action);
		((FirestoneMinion) minion).setAttack(minion.getAttack() + currentPlayer.getHand().size());
	}
	
	/**
	 * Method for performing buff: "After you cast a spell, deal 1 damage to ALL minions."
	 * @param action the action that just took place
	 * @param minion the minion that this buff belongs to
	 */
	public void afterSpellDealOneDamageToAllMinions(Action action, Minion minion) {
		if (minion == null) return;
		Player currentPlayer = getCurrentPlayer(action);
		Card playedCard = null;
		for (Card c : ((GamePlayer) currentPlayer).getDiscardPileThisTurn()) {
			if (c.getId().equals(action.getPlayedCardId())) {
				playedCard = c;
				break;
			}
		}
		if (playedCard.getType().equals(Card.Type.SPELL)) {
			List<Minion> minionsToDealDamageTo = new ArrayList<>();
			for (Player p : action.getPlayers()) {
				for (Minion m : p.getActiveMinions()) {
					if (m.equals(minion)) continue;
					minionsToDealDamageTo.add(m);
				}
			}
			damageHandler.dealOneDamageToSeveralMinions(minionsToDealDamageTo);
			for (Player p : action.getPlayers()) {
				damageHandler.removeDeadMinions(p.getActiveMinions());
			}
		}
	}
	
	/**
	 * Method for performing buff: "Deal 3 damage to a character and Freeze it."
	 * @param action the action that just took place
	 */
	public void dealThreeDamageToAndFreezeCharacter(Action action, Minion minion) {
		if (minion != null) return;
		for (Player p : action.getPlayers()) {
			// if target is a hero
			if (p.getHero().getId().equals(action.getTargetId())) {
				damageHandler.dealDamageToHero(p.getHero(), 3);
				p.getHero().getStates().add(HeroState.FROZEN);
				return;
			}
			// if target is a minion
			for (Minion m : p.getActiveMinions()) {
				if (m.getId().equals(action.getTargetId())) {
					damageHandler.dealDamageToOneMinion(m, 3);
					damageHandler.removeDeadMinion(p.getActiveMinions(), m);
					m.getStates().add(MinionState.FROZEN);
					return;
				}
			}
		}
	}
	
	/**
	 * Method for performing buff: "Return a friendly minion to your hand. It costs (2) less."
	 * @param action the action that just took place
	 */
	public void returnOwnMinionToHand(Action action, Minion minion) {
		if (minion != null) return;
		Player currentPlayer = getCurrentPlayer(action);
		for (Minion m : currentPlayer.getActiveMinions()) {
			if (m.getId().equals(action.getTargetId())) {
				// target id is the id of the minion that player wants to return to hand
				List<List<Card>> pile = ((GamePlayer) currentPlayer).getDiscardPile();
				for (List<Card> turnPile: pile) {
					for (Card c : turnPile) {
						if (c.getName().equals(m.getName())) {
							// this is the card we want to return
							int mana = Math.max(0, c.getOriginalManaCost() - 2);
							((FirestoneCard) c).setManaCost(mana);
							currentPlayer.getHand().add(c);
							// remove minion from the board
							currentPlayer.getActiveMinions().remove(m);
							return;
						}
					}
				}
			}
		}
	}
	
	/**
	 * Method for performing buff: "Deal 2 damage to an undamaged minion."
	 * @param action the action that just took place
	 */
	public void dealTwoDamageToUndamagedMinion(Action action, Minion minion) {
		if (minion != null) return;
		for (Player p : action.getPlayers()) {
			for (Minion m : p.getActiveMinions()) {
				if (m.getId().equals(action.getTargetId())) {
					// this is the minion we want to deal damage to
					if (m.getOriginalHealth() == m.getHealth()) {
						damageHandler.dealDamageToOneMinion(m, 2);
						damageHandler.removeDeadMinion(p.getActiveMinions(), m);
						return;
					}
				}
			}
		}
	}
	
	/**
	 * Method for performing buff: "Change the Health of ALL minions to 1."
	 * @param action the action that just took place
	 */
	public void changeHealthOfAllMinionsToOne(Action action, Minion minion) {
		if (minion != null) return;
		for (Player p : action.getPlayers()) {
			for (Minion m : p.getActiveMinions()) {
				((FirestoneMinion) m).setHealth(1);
			}
		}
	}
	
	/**
	 * Method for performing buff: "Your Hero Power becomes 'Deal 2 damage'. If 
	 * already in Shadowform: 3 damage."
	 * @param action the action that just took place
	 */
	public void changeHeroPowerToDealTwoOrThreeDamage(Action action, Minion minion) {
		if (minion != null) return;
		// TODO
	}
	
	/**
	 * Method for performing buff: "Battlecry: Set a hero's remaining Health to 15."
	 * @param action the action that just took place
	 */
	public void setHeroHealthTo15(Action action, Minion minion) {
		if (minion == null) return;
		for(Player p : action.getPlayers()) {
			if (p.getHero().getId().equals(action.getTargetId())) {
				FirestoneHero hero = (FirestoneHero) p.getHero();
				if (hero.getMaxHealth() < 15) {
					hero.setMaxHealth(15);
				}
				hero.setHealth(15);
				return;
			}
		}
	}
	
	/**
	 * Method for performing buff: "Whenever this minion takes damage, draw a card."
	 * @param action the action that just took place
	 */
	public void drawCardWhenThisMinionTakesDamage(Action action, Minion minion) {
		if (minion == null) return;
		Player currentPlayer = getCurrentPlayer(action);
		if (action.getActionType().equals(Action.Type.DAMAGE)) {
			Card drawnCard = ((FirestoneDeck) currentPlayer.getDeck()).getCards().pop();
			currentPlayer.getHand().add(drawnCard);
		}
	}
	
	/**
	 * Method for performing buff: "Whenever a player casts a spell, put a copy into 
	 * the other player's hand."
	 * @param action the action that just took place
	 */
	public void copyOponentsPlayedSpellCardIntoHand(Action action, Minion minion) {
		if (minion == null) return;
		Player currentPlayer = getCurrentPlayer(action);
		Card playedCard = null;
		for (Card c : ((GamePlayer) currentPlayer).getDiscardPileThisTurn()) {
			if (c.getId().equals(action.getPlayedCardId())) {
				playedCard = c;
				break;
			}
		}
		if (playedCard.getType().equals(Card.Type.SPELL)) {
			if (action.getPlayers().get(0).getId().equals(currentPlayer.getId())) {
				action.getPlayers().get(1).getHand().add(playedCard);
			} else {
				action.getPlayers().get(0).getHand().add(playedCard);
			}
		}
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