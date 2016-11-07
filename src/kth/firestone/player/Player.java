package kth.firestone.player;

import kth.firestone.card.Card;
import kth.firestone.deck.Deck;
import kth.firestone.hero.Hero;
import kth.firestone.minion.Minion;

import java.util.List;
//
/**
 * A representation of a player in firestone.
 */
public interface Player {
	
	/**
	 * The unique identifier in the context of all entities in a game.
	 * 
	 * @return the id
	 */
    public String getId();

    /**
     * Gets the hero of the player
     * 
     * @return the hero
     */
    public Hero getHero();

    /**
     * The list of cards that the player has in the hand.
     * 
     * @return the list of cards
     */
    public List<Card> getHand();

    /**
     * Gets the minion that the player has on the board.
     * 
     * @return a list of minions
     */
    public List<Minion> getActiveMinions();

    /**
     * Get the deck of the player
     * 
     * @return the deck
     */
    public Deck getDeck();
}
