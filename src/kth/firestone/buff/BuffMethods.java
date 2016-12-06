package kth.firestone.buff;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
		if (minion != null || action.getTargetId() == null) return false;
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
							}
							return true;
						}
					}
				}
			} else if(!performBuff) { // if not holding a dragon
				return true;
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
			for(Minion m : currentPlayer.getActiveMinions()){
				if(m.getId().equals(action.getMinionCreatedId())){
					int health = m.getHealth();
					health += currentPlayer.getHand().size();
					((FirestoneMinion)m).setHealth(health);
					((FirestoneMinion)m).setMaxHealth(health);
				}
			}
			
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
		
		for (Minion m : currentPlayer.getActiveMinions()) {
			if (m.getId().equals(action.getMinionCreatedId())) {
				if (performBuff) {
					if (cardsPlayed.size() > 1) {
						// must have two cards in the pile, where one of them is this one
						int increase = (cardsPlayed.size() - 1) * 2;
						int health = m.getHealth() + increase;
						int maxHealth = m.getMaxHealth();
						if (health > maxHealth) {
							((FirestoneMinion) m).setMaxHealth(health);
						}
						((FirestoneMinion) m).setHealth(health);
						((FirestoneMinion) m).setAttack(m.getAttack() + increase);
						return true;
					}
				} else {
					if (cardsPlayed.size() > 0) {
						return true;
					}
				}
			}
		}
		return false;
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
		/*
		if(action.getActionType() != Action.Type.PLAYED_CARD){
			return false;
		}*/
		for (Card c : ((GamePlayer) currentPlayer).getDiscardPileThisTurn()) {
			if (c.getId().equals(action.getPlayedCardId())) {
				if (c.getType().equals(Card.Type.SPELL)) {
					if (performBuff) {
						List<Minion> minionsToDealDamageTo = new ArrayList<>();
						for (Player p : action.getPlayers()) {
							for (Minion m : p.getActiveMinions()) {
								if (m.equals(minion)) continue;
								minionsToDealDamageTo.add(m);
							}
						}
						damageHandler.dealOneDamageToSeveralMinions(minionsToDealDamageTo);
					}
					return true;
				}
			}
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
		if (minion != null || action.getTargetId()==null) return false;
		Player currentPlayer = getCurrentPlayer(action);
		for (Minion m : currentPlayer.getActiveMinions()) {
			if (m.getId().equals(action.getTargetId())) {
				// target id is the id of the minion that player wants to return to hand
				List<List<Card>> pile = ((GamePlayer) currentPlayer).getDiscardPile();
				for (List<Card> turnPile : pile) {
					for (Card c : turnPile) {
						if (c.getName().equals(m.getName())) {
							if (performBuff) {
								// this is the card we want to return
								int mana = Math.max(0, c.getOriginalManaCost() - 2);
								((FirestoneCard) c).setManaCost(mana);
								currentPlayer.getHand().add(c);
								// remove minion from the board
								currentPlayer.getActiveMinions().remove(m);
								damageHandler.removeMinionAsObserver(m);
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
		if (minion == null || action.getTargetId() != null) return false;
		if (action.getActionType().equals(Action.Type.DAMAGE) && minion.getId().equals(action.getDamagedCharacterId())) {
			for (Player p : action.getPlayers()) {
				for (Minion m : p.getActiveMinions()) {
					if (m.getId().equals(minion.getId())) {
						if (p.getDeck().size() > 0) {
							if (performBuff) {
								Card drawnCard = ((FirestoneDeck) p.getDeck()).getCards().pop();
								p.getHand().add(drawnCard);
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
	 * Method for performing buff: "Whenever a player casts a spell, put a copy into 
	 * the other player's hand."
	 * @param action the action that just took place
	 */
	public boolean copyOpponentsPlayedSpellCardIntoHand(Action action, Minion minion, boolean performBuff) {
		if (minion == null) return false;
		Player currentPlayer = getCurrentPlayer(action);
		for (Card c : ((GamePlayer) currentPlayer).getDiscardPileThisTurn()) {
			if (c.getId().equals(action.getPlayedCardId())) {
				if (c.getType().equals(Card.Type.SPELL)) {
					if (performBuff) {
						Card newCard = new FirestoneCard(UUID.randomUUID().toString(), c.getName(),""+c.getHealth().get(), ""+c.getAttack().get(), ""+c.getManaCost(), c.getType().toString(), c.getDescription(), ""+c.getRace().get());
						if (action.getPlayers().get(0).getId().equals(currentPlayer.getId())) {
							action.getPlayers().get(1).getHand().add(newCard);
						} else {
							action.getPlayers().get(0).getHand().add(newCard);
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
					int health = hero.getHealth() + 2;
					int maxHealth = hero.getMaxHealth();
					if (health > maxHealth) {
						health = maxHealth;
					}
					hero.setHealth(health);
				}
				return true;
			}
		}
		// if target is a minion
		for (Player p : action.getPlayers()) {
			for (Minion m : p.getActiveMinions()) {
				if (m.getId().equals(action.getTargetId())) {
					if (performBuff) {
						int health = m.getHealth() + 2;
						int maxHealth = m.getMaxHealth();
						if (health > maxHealth) {
							health = maxHealth;
						}
						((FirestoneMinion) m).setHealth(health);
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
		if(!action.getActionType().equals(Action.Type.HERO_POWER) || minion != null
				|| action.getTargetId() == null){
			return false;
		}
		return dealDamage(action, performBuff, 2);
	}
	
	/**
	 * Method for performing hero power: "Mind Shatter: Deal 3 damage."
	 * @param action the action that just took place
	 */
	public boolean mindShatter(Action action, Minion minion, boolean performBuff){
		if(!action.getActionType().equals(Action.Type.HERO_POWER) || minion != null
				|| action.getTargetId() == null){
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