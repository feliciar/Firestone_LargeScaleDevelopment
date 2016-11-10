package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import kth.firestone.AttackHandler;
import kth.firestone.GameData;
import kth.firestone.card.Card;
import kth.firestone.card.FirestoneCard;
import kth.firestone.hero.FirestoneHero;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.player.GamePlayer;
import kth.firestone.player.Player;

public class AttackHandlerTest {
	//private List<Player> players = createPlayers();
	
	@Test
	public void testcanAttack() {
		List<Player> players = createPlayers();
		AttackHandler ah = new AttackHandler(players);
		boolean possibleAttack= ah.canAttack(players.get(0), players.get(0).getActiveMinions().get(0));
		assertEquals(possibleAttack, true);
		possibleAttack= ah.canAttack(players.get(0), players.get(0).getActiveMinions().get(1));
		assertEquals(possibleAttack, false);
	}
	
	@Test
	public void testisAttackValid() {
		
	}
	
	public void testgetAdversary() {
		
	}
	
	public void testgetAdversaryMinionswithTaunt() {
		
	}
	
	public void testfindMinion() {
		
	}
	
	public void testattack() {
		
	}
	
	private List<Player> createPlayers(){
		GameData gameData = new GameData();
		gameData.populate();
		List<Player> players = new ArrayList<>();
		Player player1 = new GamePlayer("1", new FirestoneHero("1", 30));
		Player player2 = new GamePlayer("2", new FirestoneHero("2", 30));
		Card cardPlayer1 = new FirestoneCard("10", "Boulderfist Ogre", 
				"7", "6", "6", "MINION", "");
		Card cardPlayer11 = new FirestoneCard("11", "Imp", 
				"1", "1", "1", "MINION", "");
		Card cardPlayer2 = new FirestoneCard("20", "Boulderfist Ogre", 
				"7", "6", "6", "MINION", "");
		FirestoneMinion minionPlayer1 = new FirestoneMinion("100", cardPlayer1, gameData);
		minionPlayer1.setSleepy(false);
		FirestoneMinion minionPlayer11 = new FirestoneMinion("110", cardPlayer11, gameData);
		FirestoneMinion minionPlayer2 = new FirestoneMinion("200", cardPlayer2, gameData);
		minionPlayer2.setSleepy(false);
		player1.getActiveMinions().add(0, minionPlayer1);
		player1.getActiveMinions().add(1, minionPlayer11);
		player2.getActiveMinions().add(0, minionPlayer2);
		players.add(player1);
		players.add(player2);
		return players;
	}
}
