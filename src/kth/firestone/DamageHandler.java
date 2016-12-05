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
		Action action1 = new Action(((FirestoneObservable)observable).getPlayers(), ((FirestoneObservable)observable).getCurrentPlayerId(), null, null, -1, null, attacker.getId(), Type.DAMAGE);
		Action action2 = new Action(((FirestoneObservable)observable).getPlayers(), ((FirestoneObservable)observable).getCurrentPlayerId(), null, null, -1, null, target.getId(), Type.DAMAGE);
		observable.notifyObservers(action1); //men nu kan de ha dött, men ej rensats än
		observable.notifyObservers(action2);
	}
	
	/**
	 * Deals damage to minions when two minions battle.
	 */
	public void dealDamageToTwoMinions(Minion attacker, Minion target) {
		((FirestoneMinion) target).reduceHealth(attacker.getAttack());
		((FirestoneMinion) attacker).reduceHealth(target.getAttack());
		Action action1 = new Action(((FirestoneObservable)observable).getPlayers(), ((FirestoneObservable)observable).getCurrentPlayerId(), null, null, -1, null, attacker.getId(), Type.DAMAGE);
		Action action2 = new Action(((FirestoneObservable)observable).getPlayers(), ((FirestoneObservable)observable).getCurrentPlayerId(), null, null, -1, null, target.getId(), Type.DAMAGE);
		observable.notifyObservers(action1); //men nu kan de ha dött, men ej rensats än
		observable.notifyObservers(action2);
	}
	
	/**
	 * Deals specified amount of damage to a minion.
	 */
	public void dealDamageToOneMinion(Minion minion, int damage) {
		((FirestoneMinion) minion).reduceHealth(damage);
		Action action = new Action(((FirestoneObservable)observable).getPlayers(), ((FirestoneObservable)observable).getCurrentPlayerId(), null, null, -1, null, minion.getId(), Type.DAMAGE);
		observable.notifyObservers(action); //men nu kan de ha dött, men ej rensats än
	}
	
	/**
	 * Deals specified amount of damage to a hero.
	 */
	public void dealDamageToHero(Hero hero, int damage) {
		((FirestoneHero) hero).reduceHealth(damage);
		Action action = new Action(((FirestoneObservable)observable).getPlayers(), ((FirestoneObservable)observable).getCurrentPlayerId(), null, null, -1, null, hero.getId(), Type.DAMAGE);
		observable.notifyObservers(action); //men nu kan de ha dött, men ej rensats än
	}
	
	/**
	 * Deals one damage to several minions.
	 */
	public void dealOneDamageToSeveralMinions(List<Minion> minions) {
		for (Minion m : minions){
			((FirestoneMinion) m).reduceHealth(1);
		}
		//TODO Städa minions
		for (Minion m : minions){
			Action action = new Action(((FirestoneObservable)observable).getPlayers(), ((FirestoneObservable)observable).getCurrentPlayerId(), null, null, -1, null, m.getId(), Type.DAMAGE);
			observable.notifyObservers(action); //men nu kan de ha dött, men ej rensats än
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
