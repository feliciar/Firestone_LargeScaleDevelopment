package kth.firestone;

import java.util.List;

/**
 * Used in order to build a {@link kth.firestone.Game}.
 */
public interface GameBuilder {

    /**
     * Sets a deck for the given player.
     * 
     * @param playerIndex the index of the player. The first player has index 1
     *            and so on
     * @param cardNames a list of cards to be set in the deck
     * @return the builder for chaining
     */
    public GameBuilder setDeck(int playerIndex, List<String> cardNames);

    /**
     * Sets a deck for the given player.
     * 
     * @param playerIndex the index of the player. The first player has index 1
     *            and so on
     * @param numberOfCards determines how many of cardName that should be set
     *            in the deck
     * @param cardName the name of the card
     * @return the builder for chaining
     */
    public GameBuilder setDeck(int playerIndex, int numberOfCards, String cardName);

    /**
     * Sets a deck for the given player.
     * 
     * @param playerIndex the index of the player. The first player has index 1
     *            and so on
     * @param cardNames the name of the cards to be set in the deck
     * @return the builder for chaining
     */
    public GameBuilder setDeck(int playerIndex, String... cardNames);

    /**
     * Sets the starting health of the hero
     * 
     * @param playerIndex the index of the player. The first player has index 1
     *            and so on
     * @param maxHealth the health to set
     * @return the builder for chaining
     */
    public GameBuilder setMaxHealth(int playerIndex, int maxHealth);

    /**
     * Sets the active minions on the board. The minions will trigger their
     * properties as if they were played.
     * 
     * @param playerIndex the index of the player. The first player has index 1
     *            and so on
     * @param activeMinionNames the names of the minions
     * @return the builder for chaining
     */
    public GameBuilder setActiveMinions(int playerIndex, List<String> activeMinionNames);

    /**
     * Sets the active minions on the board. The minions will trigger their
     * properties as if they were played.
     * 
     * @param playerIndex the index of the player. The first player has index 1
     *            and so on
     * @param numberOfMinions
     * @param cardName the name of the minion
     * @return the builder for chaining
     */
    public GameBuilder setActiveMinions(int playerIndex, int numberOfMinions, String cardName);

    /**
     * Sets the active minions on the board. The minions will trigger their
     * properties as if they were played.
     * 
     * @param playerIndex the index of the player. The first player has index 1
     *            and so on
     * @param activeMinionNames the names of the minions
     * @return the builder for chaining
     */
    public GameBuilder setActiveMinions(int playerIndex, String... activeMinionNames);

    /**
     * Sets the starting mana for the hero.
     * 
     * @param playerIndex the index of the player. The first player has index 1
     *            and so on
     * @param startingMana the mana to set
     * @return the builder for chaining
     */
    public GameBuilder setStartingMana(int playerIndex, int startingMana);

    /**
     * Creates the game from the given state.
     * 
     * @return the created game.
     */
    public Game build();

}