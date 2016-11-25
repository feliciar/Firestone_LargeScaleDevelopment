package kth.firestone;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import kth.firestone.Action.Type;
import kth.firestone.buff.BuffHandler;
import kth.firestone.card.Card;
import kth.firestone.card.PlayCardHandler;
import kth.firestone.deck.FirestoneDeck;
import kth.firestone.hero.FirestoneHero;
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
	private final String PLAYER_2_ID = "2";
	
	public FirestoneGame(List<Player> players, 
			PlayCardHandler playCardHandler,
			AttackHandler attackHandler,
			BuffHandler buffHandler) {
		this.players = players;
		this.playCardHandler = playCardHandler;
		this.attackHandler = attackHandler;
		this.buffHandler = buffHandler;
		this.events = new ArrayList<>();
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
		Action action = new Action(players, player.getId(), card.getId(), null, -1, null, Type.PLAYED_CARD);
		buffHandler.performBuffOnPlayedCard(action);
		this.notifyObservers(action);
		return null;
	}

	@Override
	public List<Event> playSpellCard(Player player, Card card, String targetId) {
		playCardHandler.playSpellCard(player, card);
		Action action = new Action(players, player.getId(), card.getId(), null, -1, targetId, Type.PLAYED_CARD);
		buffHandler.performBuffOnPlayedCard(action);
		this.notifyObservers(action);
		return null;
	}

	@Override
	public List<Event> playMinionCard(Player player, Card card, int position) {
		List<Minion> minionsBefore = new ArrayList<>(player.getActiveMinions());
		playCardHandler.playMinionCard(player, card, position);
		
		Minion playedMinion = null;
		for(Minion m : player.getActiveMinions()){
			if(!minionsBefore.contains(m)){
				playedMinion = m;
			}
		}
		Action action = new Action(players, player.getId(), card.getId(), playedMinion.getId(), position, null, Type.PLAYED_CARD);
		buffHandler.performBuffOnPlayedCard(action);
		this.notifyObservers(action);
		if(playedMinion != null){
			this.addObserver((FirestoneMinion)playedMinion);
		}
		return null;
	}

	@Override
	public List<Event> playMinionCard(Player player, Card card, int position, String targetId) {
		List<Minion> minionsBefore = new ArrayList<>(player.getActiveMinions());
		playCardHandler.playMinionCard(player, card, position);
		
		Minion playedMinion = null;
		for(Minion m : player.getActiveMinions()){
			if(!minionsBefore.contains(m)){
				playedMinion = m;
			}
		}
		Action action = new Action(players, player.getId(), card.getId(), playedMinion.getId(), position, targetId, Type.PLAYED_CARD);
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
		return attackHandler.canAttack(player.getActiveMinions(), minion);
	}

	@Override
	public boolean isAttackValid(Player player, String attackerId, String targetId) {
		return attackHandler.isAttackValid(player, attackerId, targetId);
	}

	@Override
	public boolean isPlayCardValid(Player player, Card card) {
		return playCardHandler.isPlayCardValid(player, card);
	}

	@Override
	public boolean isPlayCardValid(Player player, Card card, String targetId) {
		return playCardHandler.isPlayCardValid(player, card, targetId);
	}

	@Override
	public List<Event> attack(Player player, String attackerId, String targetId) {
		return attackHandler.attack(player, attackerId, targetId);
	}

	@Override
	public boolean isUseOfHeroPowerValid(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUseOfHeroPowerValid(Player player, String targetId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Event> useHeroPower(Player player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> useHeroPower(Player player, String targetId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> endTurn(Player player) {
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
		start(players.get(new Random().nextInt(2)));
	}

	@Override
	public void start(Player player) {
		((GamePlayer) player).setHand(createHand(((FirestoneDeck) player.getDeck()).getCards(), 4));
		int otherPlayerIndex = 0;
		if (player.getId().equals(PLAYER_1_ID)) {
			otherPlayerIndex = 1;
		}
		Player otherPlayer = players.get(otherPlayerIndex);
		((GamePlayer) otherPlayer).setHand(createHand(((FirestoneDeck) otherPlayer.getDeck()).getCards(), 3));
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

}
