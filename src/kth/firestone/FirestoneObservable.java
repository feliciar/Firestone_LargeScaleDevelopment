package kth.firestone;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import kth.firestone.minion.Minion;
import kth.firestone.player.Player;

public class FirestoneObservable extends Observable {

	private List<Minion> observers;
	private List<Player> players;
	private String playerInTurnId;

	public FirestoneObservable(List<Player> players) {
		observers = new ArrayList<>();
		this.players = players;
	}

	@Override
	public void notifyObservers(Object o) {
		Action action = (Action) o;
		// action.setPlayers(players);
		// action.setCurrentPlayerId(playerInTurnId);
		setChanged();
		super.notifyObservers(action);
	}

	@Override
	public void addObserver(Observer o) {
		super.addObserver(o);
		observers.add((Minion) o);
	}

	@Override
	public void deleteObserver(Observer o) {
		super.deleteObserver(o);
		observers.remove((Minion) o);
	}

	public void setCurrentPlayerId(String playerInTurnId) {
		this.playerInTurnId = playerInTurnId;
	}

	public String getCurrentPlayerId() {
		return playerInTurnId;
	}

	public List<Player> getPlayers() {
		return players;
	}

	/*
	 * public void updateObserverList(){ List<Minion> observersToRemove = new
	 * ArrayList<>(); for(Minion m : observers){
	 * if(!players.get(0).getActiveMinions().contains(m) &&
	 * !players.get(1).getActiveMinions().contains(m)){
	 * this.deleteObserver((FirestoneMinion)m); observersToRemove.add(m); } }
	 * observers.removeAll(observersToRemove); }
	 */

}
