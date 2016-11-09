package kth.firestone.card;

import java.util.List;

import kth.firestone.Event;
import kth.firestone.player.Player;

public class PlayCardHandler {

	
	/**
     * Playing a spell card without a target.
     * 
     * @param player the player playing the card 
     * @param card the card to play
     * @return a list of all events that has happened.
     */
    public List<Event> playSpellCard(Player player, Card card){
    	//TODO
    	return null;
    }
    /**
     * Playing a spell card with a target.
     * 
     * @param player the player playing the card 
     * @param card the card to play
     * @param targetId the id of the target of the card
     * @return a list of all events that has happened.
     */
    public List<Event> playSpellCard(Player player, Card card, String targetId){
    	//TODO
    	return null;
    }

    /**
     * Playing a minion without a target.
     * 
     * @param player the player playing the card 
     * @param card the card to play
     * @param position the position on the board where the minion will be positioned
     * @return a list of all events that has happened.
     */
    public List<Event> playMinionCard(Player player, Card card, int position){
    	//TODO
    	return null;
    }

    /**
     * Playing a minion that requires a target.
     * 
     * @param player the player playing the card
     * @param card the card to play
     * @param position the position on the board where the minion will be positioned
     * @param targetId the id of the target of the card
     * @return a list of all events that has happened.
     */
    public List<Event> playMinionCard(Player player, Card card, int position, String targetId){
    	//TODO
    	return null;
    }

    /**
     * Playing a weapon card without a target.
     * 
     * @param player the player playing the card
     * @param card the card to play
     * @return a list of all events that has happened.
     */
    public List<Event> playWeaponCard(Player player, Card card){
    	//TODO
    	return null;
    }

    /**
     * Playing a weapon card with a target.
     * 
     * @param player the player playing the card
     * @param card the card to play
     * @param targetId the id of the target of the card
     * @return a list of all events that has happened.
     */
    public List<Event> playWeaponCard(Player player, Card card, String targetId){
    	//TODO
    	return null;
    }
	
}
