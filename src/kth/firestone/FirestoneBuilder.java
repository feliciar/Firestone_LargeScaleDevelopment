package kth.firestone;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import kth.firestone.buff.BuffHandler;
import kth.firestone.buff.BuffMethods;
import kth.firestone.card.Card;
import kth.firestone.card.FirestoneCard;
import kth.firestone.card.PlayCardHandler;
import kth.firestone.deck.Deck;
import kth.firestone.deck.FirestoneDeck;
import kth.firestone.hero.FirestoneHero;
import kth.firestone.player.GamePlayer;
import kth.firestone.player.Player;

public class FirestoneBuilder implements GameBuilder {
	private List<Player> players;
	private GameData gameData;
	private final int HERO_HEALTH = 30;
	private final String PLAYER_1_ID = "1";
	private final String PLAYER_2_ID = "2";
	
	public FirestoneBuilder(GameData gameData) {
		this.gameData = gameData;
		this.players = createPlayers();
	}
	
	@Override
	public GameBuilder setDeck(int playerIndex, List<String> cardNames) {
		if (playerIndex < 1) {
			System.err.println("Error: The given ID of the player must be 1 or higher.");
			System.exit(1);
		}
		Deque<Card> cards = new ArrayDeque<>();
		
		GamePlayer p = (GamePlayer) players.get(playerIndex-1);
		HashMap<String, HashMap<String, String>> t = gameData.getCards();
		for (String s : cardNames) {
			HashMap<String, String> data = t.get(s);
			
			Card card = new FirestoneCard(UUID.randomUUID().toString(), data.get("name"), 
					data.get("health"), data.get("attack"), data.get("mana"), data.get("type"),
					data.get("buff"));
			cards.add(card);
		}
		Deck deck = new FirestoneDeck(cards);
		p.setDeck(deck);
		
		return this;
	}

	@Override
	public GameBuilder setDeck(int playerIndex, int numberOfCards, String cardName) {
		if (playerIndex < 1) {
			System.err.println("Error: The given ID of the player must be 1 or higher.");
			System.exit(1);
		}
		Deque<Card> cards = new ArrayDeque<>();
		
		GamePlayer p = (GamePlayer) players.get(playerIndex-1);
		HashMap<String, HashMap<String, String>> t = gameData.getCards();
		HashMap<String, String> data = t.get(cardName);
		
		for (int i = 0; i < numberOfCards; i++) {			
			Card card = new FirestoneCard(UUID.randomUUID().toString(), data.get("name"), 
					data.get("health"), data.get("attack"), data.get("mana"), data.get("type"),
					data.get("buff"));
			cards.add(card);
		}
		Deck deck = new FirestoneDeck(cards);
		p.setDeck(deck);
	
		return this;
	}

	@Override
	public GameBuilder setDeck(int playerIndex, String... cardNames) {
		if (playerIndex < 1) {
			System.err.println("Error: The given ID of the player must be 1 or higher.");
			System.exit(1);
		}
		
		Deque<Card> cards = new ArrayDeque<>();
		
		GamePlayer p = (GamePlayer) players.get(playerIndex-1);
		HashMap<String, HashMap<String, String>> t = gameData.getCards();
		for (String s : cardNames) {
			HashMap<String, String> data = t.get(s);
			
			Card card = new FirestoneCard(UUID.randomUUID().toString(), data.get("name"), 
					data.get("health"), data.get("attack"), data.get("mana"), data.get("type"),
					data.get("buff"));
			cards.add(card);
		}
		
		Deck deck = new FirestoneDeck(cards);
		p.setDeck(deck);
		
		return this;
	}

	@Override
	public GameBuilder setMaxHealth(int playerIndex, int maxHealth) {
		if (playerIndex < 1) {
			System.exit(1);
		}
		
		GamePlayer p = (GamePlayer) getPlayers().get(playerIndex-1);
		((FirestoneHero) p.getHero()).setMaxHealth(maxHealth);
		((FirestoneHero) p.getHero()).setHealth(maxHealth);
		
		return this;
	}

	@Override
	public GameBuilder setActiveMinions(int playerIndex, List<String> activeMinionNames) {
		// TODO Implement when we want to start the game in the middle
		return null;
	}

	@Override
	public GameBuilder setActiveMinions(int playerIndex, int numberOfMinions, String cardName) {
		// TODO Implement when we want to start the game in the middle
		return null;
	}

	@Override
	public GameBuilder setActiveMinions(int playerIndex, String... activeMinionNames) {
		// TODO Implement when we want to start the game in the middle
		return null;
	}

	@Override
	public GameBuilder setStartingMana(int playerIndex, int startingMana) {
		if (playerIndex < 1) {
			System.exit(1);
		}
		
		GamePlayer p = (GamePlayer) players.get(playerIndex-1);
		((FirestoneHero) p.getHero()).setMaxMana(startingMana);
		((FirestoneHero) p.getHero()).setMana(startingMana);
		
		return this;
	}

	@Override
	public Game build() {
		DamageHandler damageHandler = new DamageHandler();
		BuffMethods buffMethods = new BuffMethods(damageHandler);
		BuffHandler buffHandler = new BuffHandler(buffMethods);
		return new FirestoneGame(players, 
				new PlayCardHandler(gameData, buffHandler), 
				new AttackHandler(players, damageHandler),
				buffHandler);
	}
	
	private List<Player> createPlayers() {
		List<Player> players = new ArrayList<>();
		Player player1 = new GamePlayer(PLAYER_1_ID, new FirestoneHero(PLAYER_1_ID, HERO_HEALTH));
		Player player2 = new GamePlayer(PLAYER_2_ID, new FirestoneHero(PLAYER_2_ID, HERO_HEALTH));
		players.add(player1);
		players.add(player2);
		
		return players;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public void setPlayers(List<Player> players){
		this.players = players;
	}

}
