package kth.firestone.buff;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kth.firestone.Action;
import kth.firestone.DamageHandler;
import kth.firestone.Main;
import kth.firestone.card.Card;
import kth.firestone.hero.FirestoneHero;
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
		for(Player p : action.getPlayers()){
			if(p.getId().equals(action.getCurrentPlayerId())){
				for(Card c : ((GamePlayer) p).getDiscardPile()){
					if(c.getId().equals(action.getPlayedCardId())){
						buff = c.getDescription();
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
		invokeBuff(action, minion, buff);
	}
	
	/**
	 * Perform a buff when something has happened on the board
	 * @param action
	 */
	public void performBuff(Action action, Minion minion){
		String buff = ((FirestoneMinion) minion).getBuff();
		if( ! buff.startsWith("Battlecry: ")){
			invokeBuff(action, minion, buff);
		}
	}
	
	public void invokeBuff(Action action, Minion minion, String buff){
		try {
			if( ! methodMap.containsKey(buff)){
				System.err.println("The buff did not exist in BuffHandler's method map. Buff: "+buff);
				return;
			}
			methodMap.get(buff).invoke(buffMethods, action, minion);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	private Map<String, Method> createMethodMap() {
		Map<String, Method> methodMap = new HashMap<>();
		try {
			methodMap.put("Battlecry: Deal 1 damage.", 
					BuffMethods.class.getDeclaredMethod("dealOneDamage", Action.class, Minion.class));
			methodMap.put("After you cast a spell, deal 1 damage to ALL minions.", 
					BuffMethods.class.getDeclaredMethod("afterSpellDealOneDamageToAllMinions", Action.class, Minion.class));
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return methodMap;
	}

}
