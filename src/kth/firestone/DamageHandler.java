package kth.firestone;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import kth.firestone.hero.FirestoneHero;
import kth.firestone.hero.Hero;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.minion.Minion;

public class DamageHandler {

	/**
	 * Deals damage when a minion and hero battles.
	 */
	public void dealDamageToMinionAndHero(Minion attacker, Hero target) {
		((FirestoneHero) target).reduceHealth(attacker.getAttack());
		((FirestoneMinion) attacker).reduceHealth(target.getAttack());
	}
	
	/**
	 * Deals damage to minions when two minions battle.
	 */
	public void dealDamageToTwoMinions(Minion attacker, Minion target) {
		((FirestoneMinion) target).reduceHealth(attacker.getAttack());
		((FirestoneMinion) attacker).reduceHealth(target.getAttack());
	}
	
	/**
	 * Deals specified amount of damage to a minion.
	 */
	public void dealDamageToOneMinion(Minion minion, int damage) {
		((FirestoneMinion) minion).reduceHealth(damage);
	}
	
	/**
	 * Deals specified amount of damage to a hero.
	 */
	public void dealDamageToHero(Hero hero, int damage) {
		((FirestoneHero) hero).reduceHealth(damage);
	}
	
	/**
	 * Deals one damage to several minions.
	 */
	public void dealOneDamageToSeveralMinions(List<Minion> minions) {
		for (Minion m : minions){
			((FirestoneMinion) m).reduceHealth(1);
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
		}
	}
	
	public boolean checkDefeat(){
		//TODO check if a Hero is defeated
		
		return false;
	}
	
	
}
