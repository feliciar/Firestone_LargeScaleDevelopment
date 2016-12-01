package kth.firestone.buff;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import kth.firestone.Action;
import kth.firestone.card.Card;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.minion.Minion;
import kth.firestone.player.GamePlayer;
import kth.firestone.player.Player;

public class BuffHandler {

	Map<String, Method> methodMap;
	BuffMethods buffMethods;
	
	public BuffHandler(BuffMethods buffMethods){
		this.buffMethods = buffMethods;	
		methodMap = createMethodMap();
	}

	/**
	 * Perform a buff when a card is played
	 * @param action
	 */
	public void performBuffOnPlayedCard(Action action){
		//Find the buff and the minion (if there was one)
		String buff = "";
		Minion minion = null;
		Card playedCard =null;
		for(Player p : action.getPlayers()){
			if(p.getId().equals(action.getCurrentPlayerId())){
				for(Card c : ((GamePlayer) p).getDiscardPileThisTurn()){
					if(c.getId().equals(action.getPlayedCardId())){
						buff = c.getDescription();
						playedCard = c;
					}
				}
				if(action.getMinionCreatedId()!=null){
					for(Minion  m : p.getActiveMinions()){
						if(m.getId().equals(action.getMinionCreatedId())){
							minion = m;
						}
					}
				}
			}
			
		}
		if(playedCard.getType()==Card.Type.SPELL || buff.startsWith("Battlecry") || buff.startsWith("Combo"))
			invokeBuff(action, minion, buff);
	}
	
	/**
	 * Perform a buff when something has happened on the board
	 * @param action
	 */
	public void performBuff(Action action, Minion minion){
		String buff = ((FirestoneMinion) minion).getBuff();
		if( ! buff.startsWith("Battlecry: ") && ! buff.startsWith("Combo: ")){
			invokeBuff(action, minion, buff);
		}
	}
	
	public boolean invokeBuff(Action action, Minion minion, String buff){
		try {
			if( ! methodMap.containsKey(buff)){
				System.err.println("The buff did not exist in BuffHandler's method map. Buff: "+buff);
				return false;
			}
			if(! buff.equals("")){
				methodMap.get(buff).invoke(buffMethods, action, minion);
				return true;
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public Map<String, Method> createMethodMap() {
		Map<String, Method> methodMap = new HashMap<>();
		try {
			methodMap.put("Battlecry: Deal 1 damage.", 
					BuffMethods.class.getDeclaredMethod("dealOneDamage", Action.class, Minion.class));
			methodMap.put("Battlecry: If you're holding a Dragon, deal 3 damage.", 
					BuffMethods.class.getDeclaredMethod("whenHoldingDragonDealThreeDamage", Action.class, Minion.class));
			methodMap.put("Battlecry: Gain +1 Health for each card in your hand.",
					BuffMethods.class.getDeclaredMethod("gainOneHealthForCardsInHand", Action.class, Minion.class));
			methodMap.put("Combo: Gain +2/+2 for each card played earlier this turn.",
					BuffMethods.class.getDeclaredMethod("gainTwoForCardsPlayedThisTurn", Action.class, Minion.class));
			methodMap.put("Battlecry: Gain +1 Attack for each other card in your hand.",
					BuffMethods.class.getDeclaredMethod("gainOneAttackForEachOtherCardInHand", Action.class, Minion.class));
			methodMap.put("Battlecry: If you're holding a Dragon, gain +1 Attack and Taunt.",
					BuffMethods.class.getDeclaredMethod("whenHoldingDragonGainOneAttackAndTaunt", Action.class, Minion.class));
			methodMap.put("After you cast a spell, deal 1 damage to ALL minions.", 
					BuffMethods.class.getDeclaredMethod("afterSpellDealOneDamageToAllMinions", Action.class, Minion.class));
			methodMap.put("Deal 3 damage to a character and Freeze it.",
					BuffMethods.class.getDeclaredMethod("dealThreeDamageToAndFreezeCharacter", Action.class, Minion.class));
			methodMap.put("Return a friendly minion to your hand. It costs (2) less.",
					BuffMethods.class.getDeclaredMethod("returnOwnMinionToHand", Action.class, Minion.class));
			methodMap.put("Deal 2 damage to an undamaged minion.",
					BuffMethods.class.getDeclaredMethod("dealTwoDamageToUndamagedMinion", Action.class, Minion.class));
			methodMap.put("Change the Health of ALL minions to 1.",
					BuffMethods.class.getDeclaredMethod("changeHealthOfAllMinionsToOne", Action.class, Minion.class));
			methodMap.put("Your Hero Power becomes 'Deal 2 damage'. If already in Shadowform: 3 damage.",
					BuffMethods.class.getDeclaredMethod("changeHeroPowerToDealTwoOrThreeDamage", Action.class, Minion.class));
			methodMap.put("Battlecry: Set a hero's remaining Health to 15.",
					BuffMethods.class.getDeclaredMethod("setHeroHealthTo15", Action.class, Minion.class));
			methodMap.put("Whenever this minion takes damage, draw a card.",
					BuffMethods.class.getDeclaredMethod("drawCardWhenThisMinionTakesDamage", Action.class, Minion.class));
			methodMap.put("Whenever a player casts a spell, put a copy into the other player's hand.",
					BuffMethods.class.getDeclaredMethod("copyOponentsPlayedSpellCardIntoHand", Action.class, Minion.class));
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return methodMap;
	}

}
