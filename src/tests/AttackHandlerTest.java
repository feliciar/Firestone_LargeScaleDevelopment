package tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import kth.firestone.AttackHandler;
import kth.firestone.GameData;
import kth.firestone.card.Card;
import kth.firestone.card.FirestoneCard;
import kth.firestone.hero.FirestoneHero;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.minion.Minion;
import kth.firestone.minion.MinionState;
import kth.firestone.player.GamePlayer;
import kth.firestone.player.Player;

public class AttackHandlerTest {
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
		Card cardPlayer12 = new FirestoneCard("11", "Imp", 
				"1", "1", "1", "MINION", "");
		Card cardPlayer2 = new FirestoneCard("20", "Boulderfist Ogre", 
				"7", "6", "6", "MINION", "");
		Card cardPlayer21 = new FirestoneCard("21", "Imp", 
				"1", "1", "1", "MINION", "");
		FirestoneMinion minionPlayer1 = new FirestoneMinion("100", cardPlayer1, gameData);
		minionPlayer1.setSleepy(false);
		FirestoneMinion minionPlayer11 = new FirestoneMinion("110", cardPlayer11, gameData);
		FirestoneMinion minionPlayer12 = new FirestoneMinion("120", cardPlayer12, gameData);
		minionPlayer12.setSleepy(false);
		minionPlayer12.getStates().add((MinionState.FROZEN));
		
		FirestoneMinion minionPlayer2 = new FirestoneMinion("200", cardPlayer2, gameData);
		minionPlayer2.setSleepy(false);
		FirestoneMinion minionPlayer21 = new FirestoneMinion("210", cardPlayer21, gameData);
		minionPlayer21.setSleepy(false);
		minionPlayer21.getStates().add((MinionState.TAUNT));

		player1.getActiveMinions().add(0, minionPlayer1);
		player1.getActiveMinions().add(1, minionPlayer11);
		player1.getActiveMinions().add(2, minionPlayer12);
		player2.getActiveMinions().add(0, minionPlayer2);
		player2.getActiveMinions().add(1, minionPlayer21);
		players.add(player1);
		players.add(player2);
		return players;
	}
	
	@Test
	public void testcanAttack() {
		List<Player> players = createPlayers();
		AttackHandler ah = new AttackHandler(players);
		
		//Test minion which is not sleepy, not frozen and not the adversary's
		boolean possibleAttack = ah.canAttack(players.get(0), players.get(0).getActiveMinions().get(0));
		assertEquals(possibleAttack, true);
		
		//Test sleepy minion to attack
		possibleAttack = ah.canAttack(players.get(0), players.get(0).getActiveMinions().get(1));
		assertEquals(possibleAttack, false);
		
		//Test for frozen minion to attack
		possibleAttack = ah.canAttack(players.get(0), players.get(0).getActiveMinions().get(2));
		assertEquals(possibleAttack, false);
		
		// Tests if minion of adversary is attacker
		possibleAttack = ah.canAttack(players.get(0), players.get(1).getActiveMinions().get(0));
		assertEquals(possibleAttack, false);	
	}
	
	@Test
	public void testisHeroAttackValid() {
		//TODO implement test
	}
	
	@Test
	public void testgetAdversary() {
		List<Player> players = createPlayers();
		AttackHandler ah = new AttackHandler(players);
		
		Player adversary = ah.getAdversary("1");
		assertEquals(adversary.getId(), "2");
	}
	
	public void testgetAdversaryMinionswithTaunt() {
		List<Player> players = createPlayers();
		AttackHandler ah = new AttackHandler(players);
		List<Minion> minionsTaunt = ah.getAdversaryMinionsWithTaunt(players.get(1));
		int count = 0;
		for(Minion m : minionsTaunt){
			if (m.getStates().contains(MinionState.TAUNT)){
				count++;
			}	
		}
		assertEquals(count, 1);
		
		List<Minion> minionsNonTaunt = ah.getAdversaryMinionsWithTaunt(players.get(0));
		count = 0;
		for(Minion m : minionsNonTaunt){
			if (m.getStates().contains(MinionState.TAUNT)){
				count++;
			}	
		}
		assertEquals(count, 0);
	}
	
	@Test
	public void testfindMinion() {
		GameData gameData = new GameData();
		gameData.populate();
		List<Player> players = new ArrayList<>();
		AttackHandler ah = new AttackHandler(players);
		List<Minion> minions = new ArrayList<>();
		
		Card cardPlayer1 = new FirestoneCard("10", "Boulderfist Ogre", 
				"7", "6", "6", "MINION", "");
		FirestoneMinion minionPlayer1 = new FirestoneMinion("100", cardPlayer1, gameData);
		minions.add(minionPlayer1);
		assertEquals(minionPlayer1, ah.findMinion(minions, "100"));

	}
	
	public void testisMinionAttackValid() {
		List<Player> players = createPlayers();
		AttackHandler ah = new AttackHandler(players);
		//TODO
	}
	
	public void testattack() {
		//TODO
	}
	
	
}
