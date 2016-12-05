package kth.firestone.buff;

import java.util.ArrayList;
import java.util.List;
import kth.firestone.Action;
import kth.firestone.DamageHandler;
import kth.firestone.card.Card;
import kth.firestone.card.FirestoneCard;
import kth.firestone.deck.FirestoneDeck;
import kth.firestone.hero.FirestoneHero;
import kth.firestone.hero.FirestoneHeroPower;
import kth.firestone.hero.Hero;
import kth.firestone.hero.HeroPower;
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
	 */
	public boolean dealOneDamage(Action action, Minion minion, boolean performBuff) {
		if (minion != null) return false;
		return dealDamage(action, performBuff, 1);
	}
	
	/**
	 * Method for performing buff: "Battlecry: If you're holding a Dragon, deal 3 damage."
	 * @param action the action that just took place
	 */
	public boolean whenHoldingDragonDealThreeDamage(Action action, Minion minion, boolean performBuff) {
		if (minion != null) return false;
		Player currentPlayer = getCurrentPlayer(action);
		for (Card c : currentPlayer.getHand()) {
			if (c.getRace().get().equals(MinionRace.DRAGON)) {
				// if target is a hero
				for (Player p : action.getPlayers()) {
					if (p.getHero().getId().equals(action.getTargetId())) {
						if (performBuff) {
							damageHandler.dealDamageToHero(p.getHero(), 3);
						}
						return true;
					}
				}
				// if target is a minion
				for (Player p : action.getPlayers()) {
					for (Minion m : p.getActiveMinions()) {
						if (m.getId().equals(action.getTargetId())) {
							if (performBuff) {
								damageHandler.dealDamageToOneMinion(m, 3);
								damageHandler.removeDeadMinions(p.getActiveMinions());
							}
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Method for performing buff: "Battlecry: Gain +1 Health for each card in your hand."
	 * @param action the action that just took place
	 */
	public boolean gainOneHealthForCardsInHand(Action action, Minion minion, boolean performBuff) {
		if (minion != null || action.getTargetId() != null) return false;
		if (performBuff) {
			Player currentPlayer = getCurrentPlayer(action);
			int heroHealth = ((FirestoneHero) currentPlayer.getHero()).getHealth();
			heroHealth += currentPlayer.getHand().size();
			((FirestoneHero) currentPlayer.getHero()).setHealth(heroHealth);
		}
		return true;
	}
	
	
	/**
	 * Method for performing buff: "Combo: Gain +2/+2 for each card played earlier this turn."
	 * @param action the action that just took place
	 */
	public boolean gainTwoForCardsPlayedThisTurn(Action action, Minion minion, boolean performBuff) {
		if (minion != null || action.getTargetId() != null) return false;
		Player currentPlayer = getCurrentPlayer(action);
		List<Card> cardsPlayed = ((GamePlayer) currentPlayer).getDiscardPileThisTurn();
		if (cardsPlayed.size() > 0 && performBuff) {
			// must have played other cards before this one
			int increase = cardsPlayed.size()*2;
			FirestoneHero hero = ((FirestoneHero) currentPlayer.getHero());
			hero.setHealth(hero.getHealth() + increase);
			hero.setAttack(hero.getAttack() + increase);
		}
		return true;
	}
	
	/**
	 * Method for performing buff: "Battlecry: Gain +1 Attack for each other card in your hand."
	 * @param action the action that just took place
	 * @param minion the minion that this buff belongs to
	 */
	public boolean gainOneAttackForEachOtherCardInHand(Action action, Minion minion, boolean performBuff) {
		if (minion != null || action.getTargetId() != null) return false;
		if (performBuff) {
			Player currentPlayer = getCurrentPlayer(action);
			for (Minion m : currentPlayer.getActiveMinions()) {
				if (m.getId().equals(action.getMinionCreatedId())) {
					((FirestoneMinion) m).setAttack(m.getAttack() + currentPlayer.getHand().size());
				}
			}
		}
		return true;
	}
	
	/**
	 * Method for performing buff: "After you cast a spell, deal 1 damage to ALL minions."
	 * @param action the action that just took place
	 * @param minion the minion that this buff belongs to
	 */
	public boolean afterSpellDealOneDamageToAllMinions(Action action, Minion minion, boolean performBuff) {
		if (minion == null || action.getTargetId() != null) return false;
		Player currentPlayer = getCurrentPlayer(action);
		Card playedCard = null;
		for (Card c : ((GamePlayer) currentPlayer).getDiscardPileThisTurn()) {
			if (c.getId().equals(action.getPlayedCardId())) {
				playedCard = c;
				break;
			}
		}
		if (playedCard.getType().equals(Card.Type.SPELL)) {
			if (performBuff) {
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
			return true;
		}
		return false;
	}
	
	/**
	 * Method for performing buff: "Deal 3 damage to a character and Freeze it."
	 * @param action the action that just took place
	 */
	public boolean dealThreeDamageToAndFreezeCharacter(Action action, Minion minion, boolean performBuff) {
		if (minion != null) return false;
		for (Player p : action.getPlayers()) {
			// if target is a hero
			if (p.getHero().getId().equals(action.getTargetId())) {
				if (performBuff) {
					damageHandler.dealDamageToHero(p.getHero(), 3);
					p.getHero().getStates().add(HeroState.FROZEN);
				}
				return true;
			}
			// if target is a minion
			for (Minion m : p.getActiveMinions()) {
				if (m.getId().equals(action.getTargetId())) {
					if (performBuff) {
						damageHandler.dealDamageToOneMinion(m, 3);
						damageHandler.removeDeadMinion(p.getActiveMinions(), m);
						m.getStates().add(MinionState.FROZEN);
					}
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Method for performing buff: "Return a friendly minion to your hand. It costs (2) less."
	 * @param action the action that just took place
	 */
	public boolean returnOwnMinionToHand(Action action, Minion minion, boolean performBuff) {
		if (minion != null) return false;
		Player currentPlayer = getCurrentPlayer(action);
		for (Minion m : currentPlayer.getActiveMinions()) {
			if (m.getId().equals(action.getTargetId())) {
				// target id is the id of the minion that player wants to return to hand
				List<List<Card>> pile = ((GamePlayer) currentPlayer).getDiscardPile();
				for (List<Card> turnPile: pile) {
					for (Card c : turnPile) {
						if (c.getName().equals(m.getName())) {
							if (performBuff) {
								// this is the card we want to return
								int mana = Math.max(0, c.getOriginalManaCost() - 2);
								((FirestoneCard) c).setManaCost(mana);
								currentPlayer.getHand().add(c);
								// remove minion from the board
								currentPlayer.getActiveMinions().remove(m);
							}
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Method for performing buff: "Deal 2 damage to an undamaged minion."
	 * @param action the action that just took place
	 */
	public boolean dealTwoDamageToUndamagedMinion(Action action, Minion minion, boolean performBuff) {
		if (minion != null) return false;
		for (Player p : action.getPlayers()) {
			for (Minion m : p.getActiveMinions()) {
				if (m.getId().equals(action.getTargetId())) {
					// this is the minion we want to deal damage to
					if (m.getOriginalHealth() == m.getHealth()) {
						if (performBuff) {
							damageHandler.dealDamageToOneMinion(m, 2);
							damageHandler.removeDeadMinion(p.getActiveMinions(), m);
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Method for performing buff: "Change the Health of ALL minions to 1."
	 * @param action the action that just took place
	 */
	public boolean changeHealthOfAllMinionsToOne(Action action, Minion minion, boolean performBuff) {
		if (minion != null || action.getTargetId() != null) return false;
		if (performBuff) {
			for (Player p : action.getPlayers()) {
				for (Minion m : p.getActiveMinions()) {
					((FirestoneMinion) m).setHealth(1);
				}
			}
		}
		return true;
	}
	
	/**
	 * Method for performing buff: "Your Hero Power becomes 'Deal 2 damage'. If 
	 * already in Shadowform: 3 damage."
	 * @param action the action that just took place
	 */
	public boolean changeHeroPowerToDealTwoOrThreeDamage(Action action, Minion minion, boolean performBuff) {
		if (minion != null || action.getTargetId() != null) return false;
		Player currentPlayer = getCurrentPlayer(action);
		Hero hero = currentPlayer.getHero(); 
		if (performBuff) {
			if (hero.getHeroPower().getName().equals("Mind Spike")) {
				// 3 damage
				((FirestoneHero) hero).setHeroPower(new FirestoneHeroPower("Mind Shatter", "2"));
			} else {
				// 2 damage
				((FirestoneHero) hero).setHeroPower(new FirestoneHeroPower("Mind Spike", "2"));
			}
		}
		return true;
	}
	
	/**
	 * Method for performing buff: "Battlecry: Set a hero's remaining Health to 15."
	 * @param action the action that just took place
	 */
	public boolean setHeroHealthTo15(Action action, Minion minion, boolean performBuff) {
		if (minion != null) return false;
		for(Player p : action.getPlayers()) {
			if (p.getHero().getId().equals(action.getTargetId())) {
				if (performBuff) {
					FirestoneHero hero = (FirestoneHero) p.getHero();
					if (hero.getMaxHealth() < 15) {
						hero.setMaxHealth(15);
					}
					hero.setHealth(15);
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method for performing buff: "Whenever this minion takes damage, draw a card."
	 * @param action the action that just took place
	 */
	public boolean drawCardWhenThisMinionTakesDamage(Action action, Minion minion, boolean performBuff) {
		if (minion == null) return false; // TODO: add || action.getTargetId() != null
		Player currentPlayer = getCurrentPlayer(action);
		if (action.getActionType().equals(Action.Type.DAMAGE)) {
			if (minion.getId().equals(action.getTargetId())) { // TODO: change to action.getDamagedCharacterId
				if (performBuff) {
					Card drawnCard = ((FirestoneDeck) currentPlayer.getDeck()).getCards().pop();
					currentPlayer.getHand().add(drawnCard);
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method for performing buff: "Whenever a player casts a spell, put a copy into 
	 * the other player's hand."
	 * @param action the action that just took place
	 */
	public boolean copyOpponentsPlayedSpellCardIntoHand(Action action, Minion minion, boolean performBuff) {
		if (minion != null || action.getTargetId() != null) return false;
		Player currentPlayer = getCurrentPlayer(action);
		for (Card c : ((GamePlayer) currentPlayer).getDiscardPileThisTurn()) {
			if (c.getId().equals(action.getPlayedCardId())) {
				if (c.getType().equals(Card.Type.SPELL)) {
					if (performBuff) {
						if (action.getPlayers().get(0).getId().equals(currentPlayer.getId())) {
							action.getPlayers().get(1).getHand().add(c);
						} else {
							action.getPlayers().get(0).getHand().add(c);
						}
					}
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Method for performing hero power: "Life Tap: Draw a card and take 2 damage."
	 * @param action the action that just took place
	 */
	public boolean lifeTap(Action action, Minion minion, boolean performBuff){
		if(!action.getActionType().equals(Action.Type.HERO_POWER) || minion != null 
				|| action.getTargetId() != null){
			return false;
		}
		Player currentPlayer = getCurrentPlayer(action);
		FirestoneDeck deck = (FirestoneDeck) currentPlayer.getDeck();
		if (deck.size() > 0) {
			if(performBuff) {
				Card c = deck.getCards().pop();
				currentPlayer.getHand().add(c);
				((FirestoneHero) currentPlayer.getHero()).reduceHealth(2);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Method for performing hero power: "Fireblast: Deal 1 damage."
	 * @param action the action that just took place
	 */
	public boolean fireblast(Action action, Minion minion, boolean performBuff){
		if(!action.getActionType().equals(Action.Type.HERO_POWER) || minion != null){
			return false;
		}
		Player currentPlayer = getCurrentPlayer(action);
		for (Minion m : currentPlayer.getActiveMinions()) {
			if (m.getId().equals(action.getMinionCreatedId())) {
				return dealOneDamage(action, m, performBuff);
			}
		}
		return false;
	}
	
	/**
	 * Method for performing hero power: "Lesser Heal: Restore 2 Health."
	 * @param action the action that just took place
	 */
	public boolean lesserHeal(Action action, Minion minion, boolean performBuff){
		if(!action.getActionType().equals(Action.Type.HERO_POWER) || minion != null){
			return false;
		}
		// if target is a hero
		for (Player p : action.getPlayers()) {
			if (p.getHero().getId().equals(action.getTargetId())) {
				if (performBuff) {
					FirestoneHero hero = (FirestoneHero) p.getHero();
					if (hero.getMaxHealth() >= hero.getHealth() + 2) {
						hero.setHealth(hero.getHealth() + 2);
					}
				}
				return true;
			}
		}
		// if target is a minion
		for (Player p : action.getPlayers()) {
			for (Minion m : p.getActiveMinions()) {
				if (m.getId().equals(action.getTargetId())) {
					if (performBuff) {
						if (m.getMaxHealth() >= m.getHealth() + 2) {
							((FirestoneMinion) m).setHealth(m.getHealth() + 2);
						}
					}
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Method for performing hero power: "Steady Shot: Deal 2 damage to the enemy hero."
	 * @param action the action that just took place
	 */
	public boolean steadyShot(Action action, Minion minion, boolean performBuff){
		if(!action.getActionType().equals(Action.Type.HERO_POWER) || minion != null 
				|| action.getTargetId() != null){
			return false;
		}
		for (Player p : action.getPlayers()) {
			if (!p.getId().equals(action.getCurrentPlayerId())) {
				if (performBuff) {
					damageHandler.dealDamageToHero(p.getHero(), 2);
				}
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Method for performing hero power: "Mind Spike: Deal 2 damage."
	 * @param action the action that just took place
	 */
	public boolean mindSpike(Action action, Minion minion, boolean performBuff){
		if(!action.getActionType().equals(Action.Type.HERO_POWER) || minion != null){
			return false;
		}
		return dealDamage(action, performBuff, 2);
	}
	
	/**
	 * Method for performing hero power: "Mind Shatter: Deal 3 damage."
	 * @param action the action that just took place
	 */
	public boolean mindShatter(Action action, Minion minion, boolean performBuff){
		if(!action.getActionType().equals(Action.Type.HERO_POWER) || minion != null){
			return false;
		}
		return dealDamage(action, performBuff, 3);
	}
	
	/**
	 * Deals specific amount of damage.
	 */
	private boolean dealDamage(Action action, boolean performBuff, int damage) {
		// if target is a hero
		for (Player p : action.getPlayers()) {
			if (p.getHero().getId().equals(action.getTargetId())) {
				if (performBuff) {
					damageHandler.dealDamageToHero(p.getHero(), damage);
				}
				return true;
			}
		}
		// if target is a minion
		for (Player p : action.getPlayers()) {
			for (Minion m : p.getActiveMinions()) {
				if (m.getId().equals(action.getTargetId())) {
					if (performBuff) {
						damageHandler.dealDamageToOneMinion(m, damage);
						damageHandler.removeDeadMinion(p.getActiveMinions(), m);
					}
					return true;
				}
			}
		}
		return false;
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