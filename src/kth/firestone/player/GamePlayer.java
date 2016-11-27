package kth.firestone.player;

import java.util.ArrayList;
import java.util.List;

import kth.firestone.card.Card;
import kth.firestone.deck.Deck;
import kth.firestone.hero.Hero;
import kth.firestone.minion.Minion;

public class GamePlayer implements Player {
	private String id;
	private Hero hero;
	private List<Card> hand;
	private List<Minion> activeMinions;
	private Deck deck;
	private List<Card> discardPileThisTurn;
	private List<List<Card>> discardPile;
	
	
	public GamePlayer(String id, Hero hero) {
		this.id = id;
		this.hero = hero;
		hand = new ArrayList<Card>();
		activeMinions = new ArrayList<Minion>();
		discardPile = new ArrayList<>();
		discardPileThisTurn = new ArrayList<>();
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public Hero getHero() {
		return hero;
	}

	@Override
	public List<Card> getHand() {
		return hand;
	}
	
	public void setHand(List<Card> hand) {
		this.hand = hand;
	}

	@Override
	public List<Minion> getActiveMinions() {
		return activeMinions;
	}
	
	public void setActiveMinions(List<Minion> activeMinions) {
		this.activeMinions = activeMinions;
	}

	@Override
	public Deck getDeck() {
		return deck;
	}
	
	public void setDeck(Deck deck) {
		this.deck = deck;
	}
	
	public List<List<Card>> getDiscardPile(){
		return discardPile;
	}
	
	public List<Card> getDiscardPileThisTurn(){
		return discardPileThisTurn;
	}
	
}
