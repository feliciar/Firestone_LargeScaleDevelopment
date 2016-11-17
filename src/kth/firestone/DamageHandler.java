package kth.firestone;

import kth.firestone.hero.FirestoneHero;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.minion.Minion;
import kth.firestone.player.Player;

public class DamageHandler {

	public void dealDamageToMinionAndHero(FirestoneMinion attacker, FirestoneHero target){
		target.reduceHealth(attacker.getAttack());
		attacker.reduceHealth(target.getAttack());
	}
	
	public void dealDamageToMinions(FirestoneMinion attacker, FirestoneMinion target){
		target.reduceHealth(attacker.getAttack());
		attacker.reduceHealth(target.getAttack());
	}
	
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
