package kth.firestone;

import java.util.List;

import kth.firestone.card.Card;
import kth.firestone.minion.Minion;
import kth.firestone.player.Player;

public class FirestoneGame implements Game {

	@Override
	public List<Player> getPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player getPlayerInTurn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> playSpellCard(Player player, Card card) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> playSpellCard(Player player, Card card, String targetId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> playMinionCard(Player player, Card card, int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> playMinionCard(Player player, Card card, int position, String targetId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> playWeaponCard(Player player, Card card) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> playWeaponCard(Player player, Card card, String targetId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canAttack(Player player, Minion minion) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAttackValid(Player player, String attackerId, String targetId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPlayCardValid(Player player, Card card) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPlayCardValid(Player player, Card card, String targetId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Event> attack(Player player, String attackerId, String targetId) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void start(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

}
