package kth.firestone;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import kth.firestone.Action.Type;
import kth.firestone.buff.BuffHandler;
import kth.firestone.card.Card;
import kth.firestone.card.PlayCardHandler;
import kth.firestone.deck.FirestoneDeck;
import kth.firestone.hero.FirestoneHero;
import kth.firestone.hero.HeroPower;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.minion.Minion;
import kth.firestone.player.Player;
import kth.firestone.player.GamePlayer;

public class FirestoneGame extends Observable implements Game {
	private List<Player> players;
	private List<Event> events;
	private PlayCardHandler playCardHandler;
	private AttackHandler attackHandler;
	private BuffHandler buffHandler;
	private int playerIndexInTurn = 1;	// first player starts by default
	private final String PLAYER_1_ID = "1";
	//private final String PLAYER_2_ID = "2";
	private List<Minion> observers;
	
	public FirestoneGame(List<Player> players, 
			PlayCardHandler playCardHandler,
			AttackHandler attackHandler,
			BuffHandler buffHandler) {
		this.players = players;
		this.playCardHandler = playCardHandler;
		this.attackHandler = attackHandler;
		this.buffHandler = buffHandler;
		this.events = new ArrayList<>();
		observers = new ArrayList<>();
	}
	
	@Override
	public List<Player> getPlayers() {
		return players;
	}

	@Override
	public Player getPlayerInTurn() {
		return players.get(playerIndexInTurn-1);
	}

	@Override
	public List<Event> playSpellCard(Player player, Card card) {
		playCardHandler.playSpellCard(player, card);
		Action action = new Action(players, player.getId(), card.getId(), null, -1, null, null, Type.PLAYED_CARD);
		buffHandler.performBuffOnPlayedCard(action);
		this.notifyObservers(action);
		return null;
	}

	
	@Override
	public List<Event> playSpellCard(Player player, Card card, String targetId) {
		playCardHandler.playSpellCard(player, card);
		Action action = new Action(players, player.getId(), card.getId(), null, -1, targetId, null, Type.PLAYED_CARD);
		buffHandler.performBuffOnPlayedCard(action);
		this.notifyObservers(action);
		return null;
	}

	@Override
	public List<Event> playMinionCard(Player player, Card card, int position) {
		System.out.println("playminion card: "+card.getName() +" at position: "+position);
		List<Minion> minionsBefore = new ArrayList<>(player.getActiveMinions());
		playCardHandler.playMinionCard(player, card, position);
		
		Minion playedMinion = null;
		for(Minion m : player.getActiveMinions()){
			if(!minionsBefore.contains(m)){
				playedMinion = m;
			}
		}
		Action action = new Action(players, player.getId(), card.getId(), playedMinion.getId(), position, null, null, Type.PLAYED_CARD);
		buffHandler.performBuffOnPlayedCard(action);
		this.notifyObservers(action);
		if(playedMinion != null){
			this.addObserver((FirestoneMinion)playedMinion);
		}
		return null;
	}

	@Override
	public List<Event> playMinionCard(Player player, Card card, int position, String targetId) {
		System.out.println("playminion card with target: "+card.getName() +" at position: "+position);
		List<Minion> minionsBefore = new ArrayList<>(player.getActiveMinions());
		playCardHandler.playMinionCard(player, card, position);
		
		Minion playedMinion = null;
		for(Minion m : player.getActiveMinions()){
			if(!minionsBefore.contains(m)){
				playedMinion = m;
			}
		}
		Action action = new Action(players, player.getId(), card.getId(), playedMinion.getId(), position, targetId, null, Type.PLAYED_CARD);
		buffHandler.performBuffOnPlayedCard(action);
		this.notifyObservers(action);
		if(playedMinion != null){
			this.addObserver((FirestoneMinion)playedMinion);
		}
		return null;
	}

	@Override
	public List<Event> playWeaponCard(Player player, Card card) {
		return playCardHandler.playWeaponCard(player, card);
	}

	@Override
	public List<Event> playWeaponCard(Player player, Card card, String targetId) {
		return playCardHandler.playWeaponCard(player, card, targetId);
	}

	@Override
	public boolean canAttack(Player player, Minion minion) {
		if(! player.equals(getPlayerInTurn())){
			return false;
		}
		return attackHandler.canAttack(player.getActiveMinions(), minion);
	}

	@Override
	public boolean isAttackValid(Player player, String attackerId, String targetId) {
		if(! player.equals(getPlayerInTurn())){
			return false;
		}
		return attackHandler.isAttackValid(player, attackerId, targetId);
	}

	@Override
	public boolean isPlayCardValid(Player player, Card card) {
		if(! player.getId().equals(getPlayerInTurn().getId())){
			return false;
		}
		Action action = new Action(players, player.getId(), card.getId(), null, -1, null, null, Type.PLAYED_CARD);
		if(! buffHandler.isPerformBuffValid(action)){
    		return false;
    	}
		if(card.getName().equals("Imp"));
			//System.out.println("Is play card valid: "+ playCardHandler.isPlayCardValid(player, card)+ " for player: "+player.getId());
		return playCardHandler.isPlayCardValid(player, card);
	}

	@Override
	public boolean isPlayCardValid(Player player, Card card, String targetId) {
		if(! player.getId().equals(getPlayerInTurn().getId())){
			return false;
		}
		Action action = new Action(players, player.getId(), card.getId(), null, -1, targetId, null, Type.PLAYED_CARD);
		if(! buffHandler.isPerformBuffValid(action)){
    		return false;
    	}
		if(card.getName().equals("Imp"));
			//System.out.println("Is play card valid: "+ playCardHandler.isPlayCardValid(player, card, targetId)+ " at target: "+targetId+ " for player: "+player.getId());
		return playCardHandler.isPlayCardValid(player, card, targetId);
	}

	@Override
	public List<Event> attack(Player player, String attackerId, String targetId) {
		return attackHandler.attack(player, attackerId, targetId);
	}

	@Override
	public boolean isUseOfHeroPowerValid(Player player) {
		HeroPower power = player.getHero().getHeroPower();
		if(! player.equals(getPlayerInTurn())){
			return false;
		}
		if(power.getManaCost() > player.getHero().getMana()){
			return false;
		}
		if(((FirestoneHero)player.getHero()).hasUsedHeroPower()){
			return false;
		}
		Action action = new Action(players, player.getId(), null, null, -1, null, null, Type.HERO_POWER);
		return buffHandler.isPerformHeroPowerValid(action);
	}

	@Override
	public boolean isUseOfHeroPowerValid(Player player, String targetId) {
		HeroPower power = player.getHero().getHeroPower();
		if(! player.equals(getPlayerInTurn())){
			return false;
		}
		if(power.getManaCost() > player.getHero().getMana()){
			return false;
		}
		if(((FirestoneHero)player.getHero()).hasUsedHeroPower()){
			return false;
		}
		Action action = new Action(players, player.getId(), null, null, -1, targetId, null, Type.HERO_POWER);
		return buffHandler.isPerformHeroPowerValid(action);
	}

	@Override
	public List<Event> useHeroPower(Player player) {
		((FirestoneHero)player.getHero()).setHasUsedHeroPower(true);
		int mana = player.getHero().getMana() - player.getHero().getHeroPower().getManaCost(); 
		((FirestoneHero)player.getHero()).setMana(mana);
		Action action = new Action(players, player.getId(), null, null, -1, null, null, Type.HERO_POWER);
		buffHandler.performHeroPower(action);
		return null;
	}

	@Override
	public List<Event> useHeroPower(Player player, String targetId) {
		((FirestoneHero)player.getHero()).setHasUsedHeroPower(true);
		int mana = player.getHero().getMana() - player.getHero().getHeroPower().getManaCost(); 
		((FirestoneHero)player.getHero()).setMana(mana);
		Action action = new Action(players, player.getId(), null, null, -1, targetId, null, Type.HERO_POWER);
		buffHandler.performHeroPower(action);
		return null;
	}

	@Override
	public List<Event> endTurn(Player player) {
		System.out.println("Ended turn");
		if (getPlayerInTurn().equals(player)) {
			if (player.getId().equals(PLAYER_1_ID)) {
				playerIndexInTurn = 2;
			} else {
				playerIndexInTurn = 1;
			}
			Player nextPlayer = getPlayers().get(playerIndexInTurn-1);
			FirestoneHero hero = (FirestoneHero) nextPlayer.getHero();
			// Draw card if there is one to draw
			if (((FirestoneDeck) nextPlayer.getDeck()).size() == 0) {
				hero.reduceHealth(1);
			} else {
				// Draw new card for next player
				Card drawnCard = ((FirestoneDeck) nextPlayer.getDeck()).getCards().pop();
		
				List<Card> hand = nextPlayer.getHand();
				hand.add(drawnCard);
				((GamePlayer) nextPlayer).setHand(hand);
			}
			// Make all of this player's minions not sleepy
			for (Minion m : nextPlayer.getActiveMinions()) {
				((FirestoneMinion) m).setSleepy(false);
			}
			// Make all of this player's minions not used
			for (Minion m : nextPlayer.getActiveMinions()) {
				((FirestoneMinion) m).setUsedThisTurn(false);
			}
			
			// Increase mana
			if (hero.getMaxMana() < 10) {
				hero.setMaxMana(hero.getMaxMana()+1);
			}
			// Restore mana of the hero
			hero.restoreAllMana();
			// Restore hero power use of the hero
			hero.setHasUsedHeroPower(false);
			// save discard pile for this turn and clear
			((GamePlayer) player).getDiscardPile().add(((GamePlayer) player).getDiscardPileThisTurn());
			((GamePlayer) player).getDiscardPileThisTurn().clear();
		} else {
			System.err.println("The player trying to end turn was not the player in turn");
		}
		return events;
	}

	@Override
	public void start() {
		System.out.println("Started the game");
		start(players.get(new Random().nextInt(2)));
	}

	@Override
	public void start(Player player) {
		//player = players.get(1);
		System.out.println("Started the game with player: "+player.getId());
		((GamePlayer) player).setHand(createHand(((FirestoneDeck) player.getDeck()).getCards(), 4));
		int otherPlayerIndex = 0;
		int mana = player.getHero().getMana()+1;
		((FirestoneHero)player.getHero()).setMana(mana);
		((FirestoneHero)player.getHero()).setMaxMana(mana);
		
		if (player.getId().equals(PLAYER_1_ID)) {
			otherPlayerIndex = 1;
		}
		Player otherPlayer = players.get(otherPlayerIndex);
		((GamePlayer) otherPlayer).setHand(createHand(((FirestoneDeck) otherPlayer.getDeck()).getCards(), 3));
		playerIndexInTurn = Integer.parseInt(player.getId());
	}

	@Override
	public boolean isActive() {
		if (players.get(0).getHero().getHealth() > 0 &&
			players.get(1).getHero().getHealth() > 0) {
			return true;
		}
		return false;
	}
	
	public List<Card> createHand(ArrayDeque<Card> deck, int nCardsToDeal) {
		List<Card> hand = new ArrayList<>();
		for (int i = 0; i < nCardsToDeal; i++) {
			hand.add(deck.pop());
		}
		return hand;
	}
	
	@Override
	public void notifyObservers(Object o){
		updateObserverList();
		setChanged();
		super.notifyObservers(o);
		updateObserverList();
	}
	
	@Override
	public void addObserver(Observer o){
		super.addObserver(o);
		observers.add((Minion)o);
	}
	
	public void updateObserverList(){
		List<Minion> observersToRemove = new ArrayList<>();
		for(Minion m : observers){
			if(!players.get(0).getActiveMinions().contains(m) && !players.get(1).getActiveMinions().contains(m)){
				this.deleteObserver((FirestoneMinion)m);
				observersToRemove.add(m);
			}
		}
		observers.removeAll(observersToRemove);
	}

}
