package kth.firestone;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import kth.firestone.Action.Type;
import kth.firestone.hero.FirestoneHero;
import kth.firestone.hero.Hero;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.minion.Minion;

public class DamageHandler {
	private Observable observable;
	
	public DamageHandler(Observable observable){
		this.observable = observable;
	}

	/**
	 * Deals damage when a minion and hero battles.
	 */
	public void dealDamageToMinionAndHero(Minion attacker, Hero target) {
		((FirestoneHero) target).reduceHealth(attacker.getAttack());
		((FirestoneMinion) attacker).reduceHealth(target.getAttack());
		if(((FirestoneObservable)observable).getPlayers().get(0).getActiveMinions().contains(attacker)){
			removeDeadMinion(((FirestoneObservable)observable).getPlayers().get(0).getActiveMinions(), attacker);
		} else {
			removeDeadMinion(((FirestoneObservable)observable).getPlayers().get(1).getActiveMinions(), attacker);
		}
		Action action1 = new Action(((FirestoneObservable)observable).getPlayers(), ((FirestoneObservable)observable).getCurrentPlayerId(), null, null, -1, null, attacker.getId(), Type.DAMAGE);
		Action action2 = new Action(((FirestoneObservable)observable).getPlayers(), ((FirestoneObservable)observable).getCurrentPlayerId(), null, null, -1, null, target.getId(), Type.DAMAGE);
		observable.notifyObservers(action1);
		observable.notifyObservers(action2);
	}
	
	/**
	 * Deals damage to minions when two minions battle.
	 */
	public void dealDamageToTwoMinions(Minion attacker, Minion target) {
		((FirestoneMinion) target).reduceHealth(attacker.getAttack());
		((FirestoneMinion) attacker).reduceHealth(target.getAttack());
		removeDeadMinions(((FirestoneObservable)observable).getPlayers().get(0).getActiveMinions());
		removeDeadMinions(((FirestoneObservable)observable).getPlayers().get(1).getActiveMinions());
		Action action1 = new Action(((FirestoneObservable)observable).getPlayers(), ((FirestoneObservable)observable).getCurrentPlayerId(), null, null, -1, null, attacker.getId(), Type.DAMAGE);
		Action action2 = new Action(((FirestoneObservable)observable).getPlayers(), ((FirestoneObservable)observable).getCurrentPlayerId(), null, null, -1, null, target.getId(), Type.DAMAGE);
		observable.notifyObservers(action1);
		observable.notifyObservers(action2);
	}
	
	/**
	 * Deals specified amount of damage to a minion.
	 */
	public void dealDamageToOneMinion(Minion minion, int damage) {
		((FirestoneMinion) minion).reduceHealth(damage);
		if(((FirestoneObservable)observable).getPlayers().get(0).getActiveMinions().contains(minion)){
			removeDeadMinion(((FirestoneObservable)observable).getPlayers().get(0).getActiveMinions(), minion);
		} else {
			removeDeadMinion(((FirestoneObservable)observable).getPlayers().get(1).getActiveMinions(), minion);
		}
		
		Action action = new Action(((FirestoneObservable)observable).getPlayers(), ((FirestoneObservable)observable).getCurrentPlayerId(), null, null, -1, null, minion.getId(), Type.DAMAGE);
		observable.notifyObservers(action);
	}
	
	/**
	 * Deals specified amount of damage to a hero.
	 */
	public void dealDamageToHero(Hero hero, int damage) {
		((FirestoneHero) hero).reduceHealth(damage);
		Action action = new Action(((FirestoneObservable)observable).getPlayers(), ((FirestoneObservable)observable).getCurrentPlayerId(), null, null, -1, null, hero.getId(), Type.DAMAGE);
		observable.notifyObservers(action);
	}
	
	/**
	 * Deals one damage to several minions.
	 */
	public void dealOneDamageToSeveralMinions(List<Minion> minions) {
		for (Minion m : minions){
			((FirestoneMinion) m).reduceHealth(1);
		}
		removeDeadMinions(((FirestoneObservable)observable).getPlayers().get(0).getActiveMinions());
		removeDeadMinions(((FirestoneObservable)observable).getPlayers().get(1).getActiveMinions());
		for (Minion m : minions){
			Action action = new Action(((FirestoneObservable)observable).getPlayers(), ((FirestoneObservable)observable).getCurrentPlayerId(), null, null, -1, null, m.getId(), Type.DAMAGE);
			observable.notifyObservers(action);
		}
	}
	
	/**
	 * Help method to find all dead minions in a list.
	 */
	public List<Minion> findDeadMinions(List<Minion> minions) {
		List<Minion> deadMinions = new ArrayList<Minion>();
		for (Minion m : minions){
			if(m.getHealth() <= 0) {
				deadMinions.add(m);
			}
		}
		return deadMinions;
	}
	
	/**
	 * Removes all dead minions in a list of minions.
	 */
	public void removeDeadMinions(List<Minion> minions) {
		List<Minion> deadMinions = findDeadMinions(minions);
		for (Minion dead : deadMinions){
			minions.remove(dead);
			((FirestoneObservable)observable).deleteObserver((FirestoneMinion)dead);
		}
		
	}
	
	/**
	 * Checks if a minion is dead.
	 */
	public boolean isDead(Minion minion) {
		if (minion.getHealth() <= 0) {
			return true;
		}
		return false;
	}
	/**
	 * Removes a dead minion.
	 */
	public void removeDeadMinion(List<Minion> minions, Minion minion) {
		if (isDead(minion)) {
			minions.remove(minion);
			((FirestoneObservable)observable).deleteObserver((FirestoneMinion)minion);
		}
	}
	
	
}
