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
		
		Card playedCard =getPlayedCard(action);
		String buff = playedCard.getDescription();
		
		if(playedCard.getType()==Card.Type.SPELL || buff.startsWith("Battlecry") || buff.startsWith("Combo"))
			invokeBuff(action, null, buff, true);
	}
	
	public boolean isPerformBuffValid(Action action){
		Card playedCard = null;
		
		for(Player p : action.getPlayers()){
			if(p.getId().equals(action.getCurrentPlayerId())){
				for(Card c : p.getHand()){
					if(c.getId().equals(action.getPlayedCardId())){
						playedCard = c;
					}
				}
			}
		}
		
		String buff = playedCard.getDescription();
		if(buff.equals("")){
			if(action.getTargetId() == null){
				return true;
			}else{
				return false;				
			}
		}
		return invokeBuff(action, null, buff, false);
		
	}
	
	/**
	 * Perform a buff when something has happened on the board
	 * @param action
	 */
	public void performBuff(Action action, Minion minion){
		String buff = ((FirestoneMinion) minion).getBuff();
		if(! buff.equals("") && ! buff.startsWith("Battlecry: ") && ! buff.startsWith("Combo: ")){
			invokeBuff(action, minion, buff, true);
		}
	}
	
	public boolean isPerformHeroPowerValid(Action action){
		Player player;
		if(action.getCurrentPlayerId().equals("1")){
			player = action.getPlayers().get(0);
		}else{
			player = action.getPlayers().get(1);
		}
		String buff = player.getHero().getHeroPower().getName();
		return invokeBuff(action, null, buff, false);
	}
	
	public void performHeroPower(Action action){
		Player player;
		if(action.getCurrentPlayerId().equals("1")){
			player = action.getPlayers().get(0);
		}else{
			player = action.getPlayers().get(1);
		}
		String buff = player.getHero().getHeroPower().getName();
		invokeBuff(action, null, buff, true);
	}
	
	public boolean invokeBuff(Action action, Minion minion, String buff, boolean performBuff){
		try {
			if( ! methodMap.containsKey(buff)){
				System.err.println("The buff did not exist in BuffHandler's method map. Buff: "+buff);
				return false;
			}
			if(! buff.equals("")){
				return (boolean) methodMap.get(buff).invoke(buffMethods, action, minion, performBuff);
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
					BuffMethods.class.getDeclaredMethod("dealOneDamage", Action.class, Minion.class, boolean.class));
			methodMap.put("Battlecry: If you're holding a Dragon, deal 3 damage.", 
					BuffMethods.class.getDeclaredMethod("whenHoldingDragonDealThreeDamage", Action.class, Minion.class, boolean.class));
			methodMap.put("Battlecry: Gain +1 Health for each card in your hand.",
					BuffMethods.class.getDeclaredMethod("gainOneHealthForCardsInHand", Action.class, Minion.class, boolean.class));
			methodMap.put("Combo: Gain +2/+2 for each card played earlier this turn.",
					BuffMethods.class.getDeclaredMethod("gainTwoForCardsPlayedThisTurn", Action.class, Minion.class, boolean.class));
			methodMap.put("Battlecry: Gain +1 Attack for each other card in your hand.",
					BuffMethods.class.getDeclaredMethod("gainOneAttackForEachOtherCardInHand", Action.class, Minion.class, boolean.class));
			methodMap.put("After you cast a spell, deal 1 damage to ALL minions.", 
					BuffMethods.class.getDeclaredMethod("afterSpellDealOneDamageToAllMinions", Action.class, Minion.class, boolean.class));
			methodMap.put("Deal 3 damage to a character and Freeze it.",
					BuffMethods.class.getDeclaredMethod("dealThreeDamageToAndFreezeCharacter", Action.class, Minion.class, boolean.class));
			methodMap.put("Return a friendly minion to your hand. It costs (2) less.",
					BuffMethods.class.getDeclaredMethod("returnOwnMinionToHand", Action.class, Minion.class, boolean.class));
			methodMap.put("Deal 2 damage to an undamaged minion.",
					BuffMethods.class.getDeclaredMethod("dealTwoDamageToUndamagedMinion", Action.class, Minion.class, boolean.class));
			methodMap.put("Change the Health of ALL minions to 1.",
					BuffMethods.class.getDeclaredMethod("changeHealthOfAllMinionsToOne", Action.class, Minion.class, boolean.class));
			methodMap.put("Your Hero Power becomes 'Deal 2 damage'. If already in Shadowform: 3 damage.",
					BuffMethods.class.getDeclaredMethod("changeHeroPowerToDealTwoOrThreeDamage", Action.class, Minion.class, boolean.class));
			methodMap.put("Battlecry: Set a hero's remaining Health to 15.",
					BuffMethods.class.getDeclaredMethod("setHeroHealthTo15", Action.class, Minion.class, boolean.class));
			methodMap.put("Whenever this minion takes damage, draw a card.",
					BuffMethods.class.getDeclaredMethod("drawCardWhenThisMinionTakesDamage", Action.class, Minion.class, boolean.class));
			methodMap.put("Whenever a player casts a spell, put a copy into the other player's hand.",
					BuffMethods.class.getDeclaredMethod("copyOpponentsPlayedSpellCardIntoHand", Action.class, Minion.class, boolean.class));
			methodMap.put("Life Tap", 
					BuffMethods.class.getDeclaredMethod("lifeTap", Action.class, Minion.class, boolean.class));
			methodMap.put("Fireblast", 
					BuffMethods.class.getDeclaredMethod("fireblast", Action.class, Minion.class, boolean.class));
			methodMap.put("Lesser Heal", 
					BuffMethods.class.getDeclaredMethod("lesserHeal", Action.class, Minion.class, boolean.class));
			methodMap.put("Steady Shot", 
					BuffMethods.class.getDeclaredMethod("steadyShot", Action.class, Minion.class, boolean.class));
			methodMap.put("Mind Spike", 
					BuffMethods.class.getDeclaredMethod("mindSpike", Action.class, Minion.class, boolean.class));
			methodMap.put("Mind Shatter", 
					BuffMethods.class.getDeclaredMethod("mindShatter", Action.class, Minion.class, boolean.class));

		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return methodMap;
	}
	
	public Card getPlayedCard(Action action){
		for(Player p : action.getPlayers()){
			if(p.getId().equals(action.getCurrentPlayerId())){
				for(Card c : ((GamePlayer) p).getDiscardPileThisTurn()){
					if(c.getId().equals(action.getPlayedCardId())){
						return c;
					}
				}
			}
		}
		return null;
	}
}
