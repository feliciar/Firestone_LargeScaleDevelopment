package kth.firestone;

import java.util.ArrayList;
import java.util.List;

import kth.firestone.hero.FirestoneHero;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.minion.Minion;
import kth.firestone.player.Player;

public class DamageHandler {

	/*
	 * Deals damage when a minion and hero battles.
	 */
	public void dealDamageToMinionAndHero(FirestoneMinion attacker, FirestoneHero target){
		target.reduceHealth(attacker.getAttack());
		attacker.reduceHealth(target.getAttack());
	}
	
	/*
	 * Deals damage to minions when two minions battle.
	 */
	public void dealDamageToMinions(FirestoneMinion attacker, FirestoneMinion target){
		target.reduceHealth(attacker.getAttack());
		attacker.reduceHealth(target.getAttack());
	}
	
	/*
	 * Deals specified amount of damage to a minion.
	 */
	public void dealDamageToOneMinion(FirestoneMinion minion, int damage) {
		minion.reduceHealth(damage);
	}
	
	/*
	 * Deals one damage to several minions.
	 */
	public void dealOneDamageToSeveralMinions(ArrayList<FirestoneMinion> minions){
		for (FirestoneMinion m : minions){
			m.reduceHealth(1);
		}
	}
	
	/*
	 * Help method to find all dead minions in a list.
	 */
	public List<Minion> findDeadMinions(List<Minion> minions){
		List<Minion> deadMinions = new ArrayList<Minion>();
		for (Minion m : minions){
			if(m.getHealth() <= 0) {
				deadMinions.add(m);
			}
		}
		return deadMinions;
	}
	
	/*
	 * Removes all dead minions in a list of minions.
	 */
	public void removeDeadMinions(List<Minion> minions){
		List<Minion> deadMinions = findDeadMinions(minions);
		for (Minion dead : deadMinions){
			minions.remove(dead);
		}
		
	}
	
	public boolean checkDefeat(){
		//TODO check if a Hero is defeated
		
		return false;
	}
	
	
}
