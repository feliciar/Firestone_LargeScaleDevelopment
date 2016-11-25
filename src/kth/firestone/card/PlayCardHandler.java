package kth.firestone.card;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import kth.firestone.Event;
import kth.firestone.GameData;
import kth.firestone.buff.BuffHandler;
import kth.firestone.hero.FirestoneHero;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.minion.Minion;
import kth.firestone.minion.MinionRace;
import kth.firestone.minion.MinionState;
import kth.firestone.player.Player;

public class PlayCardHandler {
	
	List<Event> events = new ArrayList<>();
	GameData gameData;
	BuffHandler buffHandler;
	public static int MAX_CARDS_ALLOWED_ON_THE_BOARD = 8;
	
	public PlayCardHandler(GameData gameData, BuffHandler buffHandler){
		this.gameData = gameData;
		this.buffHandler = buffHandler;
	}
	
	/**
     * Playing a spell card without a target.
     * 
     * @param player the player playing the card 
     * @param card the card to play
     * @return a list of all events that has happened.
     */
    public List<Event> playSpellCard(Player player, Card card){
    	decreaseMana((FirestoneHero) player.getHero(), card.getManaCost());
    	player.getHand().remove(card);
    	return events;
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
    	decreaseMana((FirestoneHero) player.getHero(), card.getManaCost());
    	player.getHand().remove(card);
    	return events;
    }

    /**
     * Playing a minion without a target.
     * Removes the card from the players hand,
     * reduces the mana,
     * creates the minion, and puts it on the board
     * Then discard the card
     * 
     * @param player the player playing the card 
     * @param card the card to play
     * @param position the position on the board where the minion will be positioned
     * @return a list of all events that has happened.
     */
    public List<Event> playMinionCard(Player player, Card card, int position){
    	decreaseMana((FirestoneHero) player.getHero(), card.getManaCost());
    	player.getHand().remove(card);
    	
    	MinionRace race = MinionRace.valueOf(gameData.getCards().get(card.getName()).get("race"));
    	List<MinionState> states = getMinionStates(card);
    	
    	Minion minion = new FirestoneMinion(UUID.randomUUID().toString(), card.getName(), 
    			card.getHealth().get(), card.getOriginalHealth().get(), card.getOriginalAttack().get(), 
    			card.getAttack().get(), race, states, card.getDescription(), buffHandler);
    	
    	int cardPosition = position;
    	if (position > player.getActiveMinions().size() + 1) {
    		cardPosition = player.getActiveMinions().size(); // add to end
    	}
    	player.getActiveMinions().add(cardPosition, minion);
    	
    	//TODO Put the card in a discard pile
    	return events;
    }

    /**
     * Playing a minion that requires a target.
     * Removes the card from the players hand,
     * creates the minion, and puts it on the board
     * performs its buff on the target.
     * Then discard the card
     * 
     * @param player the player playing the card
     * @param card the card to play
     * @param position the position on the board where the minion will be positioned
     * @param targetId the id of the target of the card
     * @return a list of all events that has happened.
     */
    public List<Event> playMinionCard(Player player, Card card, int position, String targetId){
    	//TODO discard the played card
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
    
    /**
     * Determines if the given card can be played without a target.
     * 
     * @param player the player playing the card
     * @param card the card to be played
     * @return true if the play is valid
     */
    public boolean isPlayCardValid(Player player, Card card){
    	//Check if the player has less than 8 cards
    	//Check if the player has enough mana
    	if(player.getHand().size() >= MAX_CARDS_ALLOWED_ON_THE_BOARD){
    		//TODO throw an appropriate error
    		return false;
    	}
    	if(player.getHero().getMana() < card.getManaCost()){
    		//TODO throw an error
    		return false;
    	}
    	
    	return true;
    }

    /**
     * Tells if a card is valid to play at this stage.
     * 
     * @param player the player playing the card
     * @param card the card to be played
     * @param targetId the id of the target that of the card
     * @return true if the play is valid
     */
    public boolean isPlayCardValid(Player player, Card card, String targetId){
    	//TODO
    	return true;
    }
    
    public List<MinionState> getMinionStates(Card card) {
    	List<MinionState> states = new ArrayList<>();
    	String stateStrings = gameData.getCards().get(card.getName()).get("state");
		if(stateStrings!=null && !stateStrings.equals("")){
			for(String state : stateStrings.split(", ")){
				states.add(MinionState.valueOf(state));
			}
		}
		return states;
    }
    
    public void decreaseMana(FirestoneHero h, int manaCost) {
    	h.setMana(h.getMana() - manaCost);
    }
    
}
