package kth.firestone;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import kth.firestone.card.Card;
import kth.firestone.card.FirestoneCard;
import kth.firestone.deck.Deck;
import kth.firestone.deck.FirestoneDeck;
import kth.firestone.hero.FirestoneHero;
import kth.firestone.player.GamePlayer;
import kth.firestone.player.Player;

public class FirestoneBuilder implements GameBuilder {
	private List<Player> players;
	private GameData gameData;
	
	public FirestoneBuilder(GameData gameData) {
		this.gameData = gameData;
		gameData.populate();
		this.players = createPlayers();
	}
	
	@Override
	public GameBuilder setDeck(int playerIndex, List<String> cardNames) {
		return null;
	}

	@Override
	public GameBuilder setDeck(int playerIndex, int numberOfCards, String cardName) {
		return null;
	}

	@Override
	public GameBuilder setDeck(int playerIndex, String... cardNames) {
		if (playerIndex < 1) {
			System.exit(1);
		}
		
		Collection<Card> cards = new ArrayList<>();
		
		GamePlayer p = (GamePlayer) players.get(playerIndex-1);
		for (String s : cardNames) {
			HashMap<String, HashMap<String, String>> t = gameData.getCards();
			HashMap<String, String> data = t.get(s);	
			
			Card card = new FirestoneCard(data.get("name"), data.get("health"),
					data.get("attack"), data.get("mana"), data.get("type"),
					data.get("description"));
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
		
		GamePlayer p = (GamePlayer) players.get(playerIndex-1);
		((FirestoneHero) p.getHero()).setMaxHealth(maxHealth);
		((FirestoneHero) p.getHero()).setHealth(maxHealth);
		
		return this;
	}

	@Override
	public GameBuilder setActiveMinions(int playerIndex, List<String> activeMinionNames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameBuilder setActiveMinions(int playerIndex, int numberOfMinions, String cardName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameBuilder setActiveMinions(int playerIndex, String... activeMinionNames) {
		// TODO Auto-generated method stub
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
		Game game = new FirestoneGame(players);
		
		return game;
	}
	
	private List<Player> createPlayers() {
		List<Player> players = new ArrayList<>();
		Player player1 = new GamePlayer("1", new FirestoneHero());
		Player player2 = new GamePlayer("2", new FirestoneHero());
		players.add(player1);
		players.add(player2);
		
		return players;
	}
	
	public List<Player> getPlayers() {
		return players;
	}

}
