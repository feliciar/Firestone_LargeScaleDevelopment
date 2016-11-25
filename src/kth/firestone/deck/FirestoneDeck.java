package kth.firestone.deck;

import java.util.ArrayDeque;
import java.util.Deque;

import kth.firestone.card.Card;

public class FirestoneDeck implements Deck {
	
	private ArrayDeque<Card> cards;
	
	public FirestoneDeck(Deque<Card> cards) {
		this.cards = new ArrayDeque<>(cards);
	}
	
	@Override
	public int size() {
		return cards.size();
	}
	
	public ArrayDeque<Card> getCards() {
		return cards;
	}

}
