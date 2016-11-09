package kth.firestone;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import kth.firestone.card.Card;
import kth.firestone.card.PlayCardHandler;
import kth.firestone.deck.FirestoneDeck;
import kth.firestone.minion.Minion;
import kth.firestone.player.Player;
import kth.firestone.player.GamePlayer;

public class FirestoneGame implements Game {
	private List<Player> players;
	private List<Event> events;
	private PlayCardHandler playCardHandler;
	private AttackHandler attackHandler;
	private int playerIndexInTurn = 1;	// first player starts by default
	
	public FirestoneGame(List<Player> players, 
			PlayCardHandler playCardHandler,
			AttackHandler attackHandler) {
		this.players = players;
		this.playCardHandler = playCardHandler;
		this.attackHandler = attackHandler;
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
		return playCardHandler.playSpellCard(player, card);
	}

	@Override
	public List<Event> playSpellCard(Player player, Card card, String targetId) {
		return playCardHandler.playSpellCard(player, card, targetId);
	}

	@Override
	public List<Event> playMinionCard(Player player, Card card, int position) {
		return playCardHandler.playMinionCard(player, card, position);
	}

	@Override
	public List<Event> playMinionCard(Player player, Card card, int position, String targetId) {
		return playCardHandler.playMinionCard(player, card, position, targetId);
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
		return attackHandler.canAttack(player, minion);
	}

	@Override
	public boolean isAttackValid(Player player, String attackerId, String targetId) {
		return attackHandler.isAttackValid(player, attackerId, targetId);
	}

	@Override
	public boolean isPlayCardValid(Player player, Card card) {
		return false; //playCardHandler.isPlayCardValid(player, card);
	}

	@Override
	public boolean isPlayCardValid(Player player, Card card, String targetId) {
		return false;//playCardHandler.isPlayCardValid(player, card, targetId);
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
		if (player.getId().equals("1")) {
			playerIndexInTurn = 2;
		} else {
			playerIndexInTurn = 1;
		}
		// draw new card for next player
		Card drawnCard = ((FirestoneDeck) player).getCards().pop();
		List<Card> hand = player.getHand();
		hand.add(drawnCard);
		((GamePlayer) player).setHand(hand);
		
		return events;
	}

	@Override
	public void start() {
		start(players.get(0));
		
	}

	@Override
	public void start(Player player) {
		// deal cards at the start of the game
		int nCardsToDeal = 4;
		if (player.getId().equals("2")) {
			nCardsToDeal = 3;
		}
		
		List<Card> hand = new ArrayList<>();
		ArrayDeque<Card> deck = ((FirestoneDeck) player.getDeck()).getCards();
		if (player.getHand().size() != 0 || deck.size() < 4) {
			System.exit(1);
		}
		for (int i = 0; i < nCardsToDeal; i++) {
			hand.add(deck.pop());
		}
		((GamePlayer) player).setHand(hand);
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

}
