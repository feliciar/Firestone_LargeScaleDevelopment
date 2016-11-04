package kth.firestone;

import kth.firestone.card.Card;
import kth.firestone.minion.Minion;
import kth.firestone.player.Player;

import java.util.List;

/**
 * The main API of the game.
 */
public interface Game {
	
	/**
	 * @return a list of the players of the game.
	 */
    public List<Player> getPlayers();

    /**
     * @return the player in turn
     */
    public Player getPlayerInTurn();

    /**
     * Playing a spell card without a target.
     * 
     * @param player the player playing the card 
     * @param card the card to play
     * @return a list of all events that has happened.
     */
    public List<Event> playSpellCard(Player player, Card card);
    /**
     * Playing a spell card with a target.
     * 
     * @param player the player playing the card 
     * @param card the card to play
     * @param targetId the id of the target of the card
     * @return a list of all events that has happened.
     */
    public List<Event> playSpellCard(Player player, Card card, String targetId);

    /**
     * Playing a minion without a target.
     * 
     * @param player the player playing the card 
     * @param card the card to play
     * @param position the position on the board where the minion will be positioned
     * @return a list of all events that has happened.
     */
    public List<Event> playMinionCard(Player player, Card card, int position);

    /**
     * Playing a minion that requires a target.
     * 
     * @param player the player playing the card
     * @param card the card to play
     * @param position the position on the board where the minion will be positioned
     * @param targetId the id of the target of the card
     * @return a list of all events that has happened.
     */
    public List<Event> playMinionCard(Player player, Card card, int position, String targetId);

    /**
     * Playing a weapon card without a target.
     * 
     * @param player the player playing the card
     * @param card the card to play
     * @return a list of all events that has happened.
     */
    public List<Event> playWeaponCard(Player player, Card card);

    /**
     * Playing a weapon card with a target.
     * 
     * @param player the player playing the card
     * @param card the card to play
     * @param targetId the id of the target of the card
     * @return a list of all events that has happened.
     */
    public List<Event> playWeaponCard(Player player, Card card, String targetId);

    
    /**
     * Tells if the given player can perform an attack with the given minion or not.
     * 
     * @param player the player wanting to attack
     * @param minion the minion trying to attack
     */
    public boolean canAttack(Player player, Minion minion);

    /**
     * Tells if the given attack is valid or not.
     * 
     * @param player the player initiating the attack
     * @param attackerId the id of the attacker
     * @param targetId the id of the target
     * @return true if the attack is valid
     */
    public boolean isAttackValid(Player player, String attackerId, String targetId);

    /**
     * Determines if the given card can be played without a target.
     * 
     * @param player the player playing the card
     * @param card the card to be played
     * @return true if the play is valid
     */
    public boolean isPlayCardValid(Player player, Card card);

    /**
     * Tells if a card is valid to play at this stage.
     * 
     * @param player the player playing the card
     * @param card the card to be played
     * @param targetId the id of the target that of the card
     * @return true if the play is valid
     */
    public boolean isPlayCardValid(Player player, Card card, String targetId);

    /**
     * If the attack is valid, performs an attack.
     * 
     * @param player the player initiating the attack
     * @param attackerId the id of the attacker
     * @param targetId the id of the target
     * @return a list of events that happened
     */
    public List<Event> attack(Player player, String attackerId, String targetId);

    /**
     * Determines if the hero power can be used without a target.
     *
     * @param player the player playing the card
     * @return true if the play is valid
     */
	public boolean isUseOfHeroPowerValid(Player player);

    /**
     * Determines if the hero power can be used with a target.
     * 
     * @param player the player 
     * @param targetId the id of the target of the hero power
     * @return true if the play is valid
     */
	public boolean isUseOfHeroPowerValid(Player player, String targetId);

    /**
     * Use the hero power without a target.
     * 
     * @param player the player 
     * @return a list of events that happened
     */
	public List<Event> useHeroPower(Player player);

	/**
     * Use the hero power with a target.
     * 
     * @param player the player 
     * @param targetId the id of the target of the hero power
     * @return a list of events that happened
	 */
	public List<Event> useHeroPower(Player player, String targetId);
	
    /**
     * Ends the turn of a player.
     * 
     * @param player the player ending the turn.
     * @return a list of events that happened
     */
    public List<Event> endTurn(Player player);

    /**
     * Starts the game.
     */
    public void start();

    /**
     * Starts the game.
     * 
     * @param player the starting player 
     */
    public void start(Player player);

    /**
     * @return true if the game is active.
     */
    public boolean isActive();
}
