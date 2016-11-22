package kth.firestone;

import java.util.ArrayList;

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
	 * Removes the dead minions from the board.
	 */
	public void removeDeadMinions(Minion attacker, Minion target, Player player, Player adversary){
		if (target.getHealth() <= 0) {
			adversary.getActiveMinions().remove(target);
		}
		if (attacker.getHealth() <= 0) {
			player.getActiveMinions().remove(attacker);
		}
	}
	
	public boolean checkDefeat(){
		//TODO check if a Hero is defeated
		
		return false;
	}
	
	
}
