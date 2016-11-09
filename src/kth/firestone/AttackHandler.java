package kth.firestone;

import java.util.List;

import kth.firestone.minion.Minion;
import kth.firestone.player.Player;

public class AttackHandler {
	public boolean canAttack(Player player, Minion minion){
		
		return false;
	}
	
	public boolean isAttackValid(){
		return false;
	}
	
	public List<Event> attack(Player player, String attackerId, String targetId) {
		return null;
	}
}
