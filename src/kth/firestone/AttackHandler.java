package kth.firestone;

import java.util.List;

import kth.firestone.minion.Minion;
import kth.firestone.player.Player;

public class AttackHandler {
	public boolean canAttack(Player player, Minion minion){
		// kan ej attackera om FROZEN
		// Kan ej attackera om sleepy
		// 
		return false;
	}
	
	public boolean isAttackValid(Player player, String attackerId, String targetId){
		return false;
	}
	
	public List<Event> attack(Player player, String attackerId, String targetId) {
		return null;
	}
}
