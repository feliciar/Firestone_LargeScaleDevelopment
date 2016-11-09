package kth.firestone;

import java.util.HashMap;

public class GameData {
	public final HashMap<String, HashMap<String, String>> cards 
						= new HashMap<String, HashMap<String, String>>();
	
	public void populate() {
		HashMap<String, String> card = new HashMap<>();
		card.put("name", "Imp");
		card.put("health", "1");
		card.put("attack", "1");
		card.put("mana", "1");
		card.put("race", "DEMON");
		card.put("description", "");
		card.put("state", "");
		card.put("buff", "");
		card.put("type", "MINION");
		cards.put(card.get("name"), card);
		
		card = new HashMap<>();
		
		card.put("name", "War Golem");
		card.put("health", "7");
		card.put("attack", "7");
		card.put("mana", "7");
		card.put("race", "NONE");
		card.put("description", "");
		card.put("state", "");
		card.put("buff", "");
		card.put("type", "MINION");
		cards.put(card.get("name"), card);
		
		card = new HashMap<>();
		
		card.put("name", "Boulderfist Ogre");
		card.put("health", "7");
		card.put("attack", "6");
		card.put("mana", "6");
		card.put("race", "NONE");
		card.put("description", "");
		card.put("state", "");
		card.put("buff", "");
		card.put("type", "MINION");
		cards.put(card.get("name"), card);
		
		card = new HashMap<>();
		
		card.put("name", "Ironforge Rifleman");
		card.put("health", "2");
		card.put("attack", "2");
		card.put("mana", "3");
		card.put("race", "NONE");
		card.put("description", "");
		card.put("state", "");
		card.put("buff", "Battlecry: Deal 1 damage.");
		card.put("type", "MINION");
		cards.put(card.get("name"), card);
		
		card = new HashMap<>();
		
		card.put("name", "Blackwing Corruptor");
		card.put("health", "4");
		card.put("attack", "5");
		card.put("mana", "5");
		card.put("race", "NONE");
		card.put("description", "");
		card.put("state", "");
		card.put("buff", "Battlecry: If you're holding a Dragon, deal 3 damage.");
		card.put("type", "MINION");
		cards.put(card.get("name"), card);
		
		card = new HashMap<>();
		
		card.put("name", "Twilight Drake");
		card.put("health", "1");
		card.put("attack", "4");
		card.put("mana", "4");
		card.put("race", "DRAGON");
		card.put("description", "");
		card.put("state", "");
		card.put("buff", "Battlecry: Gain +1 Health for each card in your hand.");
		card.put("type", "MINION");
		cards.put(card.get("name"), card);
		
	}
	
	public HashMap<String, HashMap<String, String>> getCards() {
		return cards;
	}
}