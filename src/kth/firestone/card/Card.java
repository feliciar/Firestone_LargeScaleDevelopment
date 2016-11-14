package kth.firestone.card;

import java.util.Optional;

import kth.firestone.minion.MinionRace;

/**
 * Describes a playable card.
 */
public interface Card {
    /**
     * The types of a card.
     */
    public enum Type {
        MINION,
        SPELL,
        WEAPON
    }

    /**
     * @return the name of the card.
     */
    public String getName();

    /**
     * @return an unique id in the context of a {@link kth.firestone.Game}.
     */
    public String getId();

    /**
     * @return how much mana the card currently costs to play.
     */
    public int getManaCost();

    /**
     * @return how much mana the card originally costs to play.
     */
    public int getOriginalManaCost();

    /**
     * Gets the attack of the minion of this card.
     * Only available for cards of type MINION.
     * @return the attack of the minion spawned by the card.
     */
    public Optional<Integer> getAttack();

    /**
     * Gets the health of the minion of this card.
     * Only available for cards of type MINION.
     * @return the health of the minion spawned by the card.
     */
    public Optional<Integer> getHealth();

    /**
     * Gets the original attack of the minion of this card.
     * Only available for cards of type MINION.
     * @return the original attack of the minion spawned by the card.
     */
	public Optional<Integer> getOriginalAttack();

	/**
     * Gets the original health of the minion of this card.
     * Only available for cards of type MINION.
     * @return the original health of the minion spawned by the card.
     */
	public Optional<Integer> getOriginalHealth();

    /**
     * @return the type of the card.
     */
    public Type getType();

    /**
     * @return the description of the card.
     */
    public String getDescription();
    
    /**
     * @return the minion race of the card if the card is of type minion
     */
    public Optional<MinionRace> getRace();
}
