package kth.firestone;

import java.util.HashMap;

public class GameData {
	public final HashMap<String, HashMap<String, String>> cards = new HashMap<String, HashMap<String, String>>();

	public GameData() {
		populate();
	}

	public void populate() {
		HashMap<String, String> card = new HashMap<>();

		// ----------- MINIONS -----------

		card.put("name", "Imp");
		card.put("health", "1");
		card.put("attack", "1");
		card.put("mana", "1");
		card.put("race", "DEMON");
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
		card.put("state", "");
		card.put("buff", "Battlecry: Gain +1 Health for each card in your hand.");
		card.put("type", "MINION");
		cards.put(card.get("name"), card);

		card = new HashMap<>();

		card.put("name", "Edwin VanCleef");
		card.put("health", "2");
		card.put("attack", "2");
		card.put("mana", "3");
		card.put("race", "NONE");
		card.put("state", "");
		card.put("buff", "Combo: Gain +2/+2 for each card played earlier this turn.");
		card.put("type", "MINION");
		cards.put(card.get("name"), card);

		card = new HashMap<>();

		card.put("name", "Midnight Drake");
		card.put("health", "4");
		card.put("attack", "1");
		card.put("mana", "4");
		card.put("race", "DRAGON");
		card.put("state", "");
		card.put("buff", "Battlecry: Gain +1 Attack for each other card in your hand.");
		card.put("type", "MINION");
		cards.put(card.get("name"), card);

		card = new HashMap<>();

		card.put("name", "Wild Pyromancer");
		card.put("health", "2");
		card.put("attack", "3");
		card.put("mana", "2");
		card.put("race", "NONE");
		card.put("state", "");
		card.put("buff", "After you cast a spell, deal 1 damage to ALL minions.");
		card.put("type", "MINION");
		cards.put(card.get("name"), card);

		card = new HashMap<>();

		card.put("name", "Alexstrasza");
		card.put("health", "8");
		card.put("attack", "8");
		card.put("mana", "9");
		card.put("race", "DRAGON");
		card.put("state", "");
		card.put("buff", "Battlecry: Set a hero's remaining Health to 15.");
		card.put("type", "MINION");
		cards.put(card.get("name"), card);

		card = new HashMap<>();

		card.put("name", "Acolyte of Pain");
		card.put("health", "3");
		card.put("attack", "1");
		card.put("mana", "3");
		card.put("race", "NONE");
		card.put("state", "");
		card.put("buff", "Whenever this minion takes damage, draw a card.");
		card.put("type", "MINION");
		cards.put(card.get("name"), card);

		card = new HashMap<>();

		card.put("name", "Lorewalker Cho");
		card.put("health", "4");
		card.put("attack", "0");
		card.put("mana", "2");
		card.put("race", "NONE");
		card.put("state", "");
		card.put("buff", "Whenever a player casts a spell, put a copy into the other player's hand.");
		card.put("type", "MINION");
		cards.put(card.get("name"), card);

		card = new HashMap<>();

		card.put("name", "King Krush");
		card.put("health", "8");
		card.put("attack", "8");
		card.put("mana", "9");
		card.put("race", "BEAST");
		card.put("state", "");
		card.put("buff", "Charge");
		card.put("type", "MINION");
		cards.put(card.get("name"), card);

		card = new HashMap<>();

		card.put("name", "Voodoo Doctor");
		card.put("health", "1");
		card.put("attack", "2");
		card.put("mana", "1");
		card.put("race", "NONE");
		card.put("state", "");
		card.put("buff", "Battlecry: Restore 2 Health.");
		card.put("type", "MINION");
		cards.put(card.get("name"), card);

		// ----------- SPELLS -----------

		card = new HashMap<>();

		card.put("name", "Frostbolt");
		card.put("health", "0");
		card.put("attack", "0");
		card.put("mana", "2");
		card.put("race", "NONE");
		card.put("state", "");
		card.put("buff", "Deal 3 damage to a character and Freeze it.");
		card.put("type", "SPELL");
		cards.put(card.get("name"), card);

		card = new HashMap<>();

		card.put("name", "Shadowstep");
		card.put("health", "0");
		card.put("attack", "0");
		card.put("mana", "0");
		card.put("race", "NONE");
		card.put("state", "");
		card.put("buff", "Return a friendly minion to your hand. It costs (2) less.");
		card.put("type", "SPELL");
		cards.put(card.get("name"), card);

		card = new HashMap<>();

		card.put("name", "Backstab");
		card.put("health", "0");
		card.put("attack", "0");
		card.put("mana", "0");
		card.put("race", "NONE");
		card.put("state", "");
		card.put("buff", "Deal 2 damage to an undamaged minion.");
		card.put("type", "SPELL");
		cards.put(card.get("name"), card);

		card = new HashMap<>();

		card.put("name", "Equality");
		card.put("health", "0");
		card.put("attack", "0");
		card.put("mana", "2");
		card.put("race", "NONE");
		card.put("state", "");
		card.put("buff", "Change the Health of ALL minions to 1.");
		card.put("type", "SPELL");
		cards.put(card.get("name"), card);

		card = new HashMap<>();

		card.put("name", "Shadowform");
		card.put("health", "0");
		card.put("attack", "0");
		card.put("mana", "3");
		card.put("race", "NONE");
		card.put("state", "");
		card.put("buff", "Your Hero Power becomes 'Deal 2 damage'. If already in Shadowform: 3 damage.");
		card.put("type", "SPELL");
		cards.put(card.get("name"), card);

		// ----------- HERO POWERS -----------

		card = new HashMap<>();

		card.put("name", "Life Tap");
		card.put("health", "0");
		card.put("attack", "0");
		card.put("mana", "2");
		card.put("race", "NONE");
		card.put("state", "");
		card.put("buff", "Hero Power Draw a card and take 2 damage.");
		card.put("type", "HERO_POWER");
		card.put("class", "WARLOCK");
		cards.put(card.get("name"), card);

		card = new HashMap<>();

		card.put("name", "Fireblast");
		card.put("health", "0");
		card.put("attack", "0");
		card.put("mana", "2");
		card.put("race", "NONE");
		card.put("state", "");
		card.put("buff", "Hero Power Deal 1 damage.");
		card.put("type", "HERO_POWER");
		card.put("class", "MAGE");
		cards.put(card.get("name"), card);

		card = new HashMap<>();

		card.put("name", "Lesser Heal");
		card.put("health", "0");
		card.put("attack", "0");
		card.put("mana", "2");
		card.put("race", "NONE");
		card.put("state", "");
		card.put("buff", "Hero Power Restore 2 Health.");
		card.put("type", "HERO_POWER");
		card.put("class", "PRIEST");
		cards.put(card.get("name"), card);

		card = new HashMap<>();

		card.put("name", "Steady Shot");
		card.put("health", "0");
		card.put("attack", "0");
		card.put("mana", "2");
		card.put("race", "NONE");
		card.put("state", "");
		card.put("buff", "Hero Power Deal 2 damage to the enemy hero.");
		card.put("type", "HERO_POWER");
		card.put("class", "HUNTER");
		cards.put(card.get("name"), card);

		card = new HashMap<>();

		card.put("name", "Mind Spike");
		card.put("health", "0");
		card.put("attack", "0");
		card.put("mana", "2");
		card.put("race", "NONE");
		card.put("state", "");
		card.put("buff", "Hero Power Deal 2 damage.");
		card.put("type", "HERO_POWER");
		card.put("class", "PRIEST");
		cards.put(card.get("name"), card);

		card = new HashMap<>();

		card.put("name", "Mind Shatter");
		card.put("health", "0");
		card.put("attack", "0");
		card.put("mana", "2");
		card.put("race", "NONE");
		card.put("state", "");
		card.put("buff", "Hero Power Deal 3 damage.");
		card.put("type", "HERO_POWER");
		card.put("class", "PRIEST");
		cards.put(card.get("name"), card);
	}

	public HashMap<String, HashMap<String, String>> getCards() {
		return cards;
	}
}
