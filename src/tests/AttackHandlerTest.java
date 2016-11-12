package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import kth.firestone.AttackHandler;
import kth.firestone.hero.FirestoneHero;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.minion.Minion;
import kth.firestone.minion.MinionRace;
import kth.firestone.minion.MinionState;
import kth.firestone.player.GamePlayer;
import kth.firestone.player.Player;

public class AttackHandlerTest {
	
	@Test 
	public void testcanAttack() {
		AttackHandler ah = new AttackHandler(null);
		//Mock player's minions
		List<MinionState> state0 = new ArrayList<>();
		FirestoneMinion minionPlayer1 = new FirestoneMinion("100", "Boulderfist Ogre", 8,8,6,6, MinionRace.NONE, state0 );
		minionPlayer1.setSleepy(false);
		FirestoneMinion minionPlayer11 = new FirestoneMinion("110", "Imp", 1,1,1,1,MinionRace.DEMON, state0);
		List<MinionState> state1 = new ArrayList<>();
		state1.add(MinionState.FROZEN);
		FirestoneMinion minionPlayer12 = new FirestoneMinion("120", "Imp", 1,1,1,1,MinionRace.DEMON, state1);
		minionPlayer12.setSleepy(false);
		
		List<Minion> playersActiveMinions = new ArrayList<>();
		playersActiveMinions.add(0, minionPlayer1);
		playersActiveMinions.add(1, minionPlayer11);
		playersActiveMinions.add(2, minionPlayer12);
		
		//Mock adversary's minion
		FirestoneMinion minionPlayer2 = new FirestoneMinion("200", "Boulderfist Ogre", 7,7,6,6, null, null);
		minionPlayer2.setSleepy(false);
		
		//Test minion which is not sleepy, not frozen and not the adversary's
		boolean possibleAttack = ah.canAttack(playersActiveMinions, minionPlayer1);
		assertEquals(possibleAttack, true);
		
		//Test sleepy minion to attack
		possibleAttack = ah.canAttack(playersActiveMinions, minionPlayer11);
		assertEquals(possibleAttack, false);
		
		//Test for frozen minion to attack
		possibleAttack = ah.canAttack(playersActiveMinions, minionPlayer12 );
		assertEquals(possibleAttack, false);
		
		// Tests if minion of adversary is attacker
		possibleAttack = ah.canAttack(playersActiveMinions, minionPlayer2);
		assertEquals(possibleAttack, false);	
	}
	
	@Test
	public void testisHeroAttackValid() {
		//TODO implement test
	}
	
	@Test 
	public void testgetAdversary() {
		List<Player> players = new ArrayList<>();
		Player player1 = new GamePlayer("1", null);
		Player player2 = new GamePlayer("2", null);
		players.add(player1);
		players.add(player2);
		AttackHandler ah = new AttackHandler(players);
		
		Player adversary = ah.getAdversary("1");
		assertEquals(adversary.getId(), "2");
	}
	
	@Test 
	public void testgetAdversaryMinionswithTaunt() {
		//Mock minions of adversary
		List<MinionState> state2 = new ArrayList<>();
		FirestoneMinion minionPlayer2 = new FirestoneMinion("200", "Boulderfist Ogre", 7,7,6,6, MinionRace.NONE, state2 );
		minionPlayer2.setSleepy(false);
		List<MinionState> state3 = new ArrayList<>();
		state3.add(MinionState.TAUNT);
		FirestoneMinion minionPlayer21 = new FirestoneMinion("210", "Imp", 1,1,1,1,MinionRace.DEMON, state3);
		minionPlayer21.setSleepy(false);
		List<Minion> adversaryActiveMinions = new ArrayList<>();
		adversaryActiveMinions.add(0, minionPlayer2);
		adversaryActiveMinions.add(1, minionPlayer21);
		
		//Mock minions of player
		List<MinionState> state0 = new ArrayList<>();
		FirestoneMinion minionPlayer1 = new FirestoneMinion("100", "Boulderfist Ogre", 8,8,6,6, MinionRace.NONE, state0 );
		minionPlayer1.setSleepy(false);
		FirestoneMinion minionPlayer11 = new FirestoneMinion("110", "Imp", 1,1,1,1,MinionRace.DEMON, state0);
		List<MinionState> state1 = new ArrayList<>();
		state1.add(MinionState.FROZEN);
		FirestoneMinion minionPlayer12 = new FirestoneMinion("120", "Imp", 1,1,1,1,MinionRace.DEMON, state1);
		minionPlayer12.setSleepy(false);
		List<Minion> playersActiveMinions = new ArrayList<>();
		playersActiveMinions.add(0, minionPlayer1);
		playersActiveMinions.add(1, minionPlayer11);
		playersActiveMinions.add(2, minionPlayer12);
		
		AttackHandler ah = new AttackHandler(null);
		List<Minion> minionsTaunt = ah.getAdversaryMinionsWithTaunt(adversaryActiveMinions);
		assertEquals(minionsTaunt.size(), 1);
		
		List<Minion> minionsNonTaunt = ah.getAdversaryMinionsWithTaunt(playersActiveMinions);
		assertEquals(minionsNonTaunt.size(), 0);
	}
	
	@Test 
	public void testfindMinion() {
		AttackHandler ah = new AttackHandler(null);
		List<Minion> minions = new ArrayList<>();
		
		FirestoneMinion minionPlayer1 = new FirestoneMinion("100", "Boulderfist Ogre", 7,7,6,6, MinionRace.NONE, null );
		minions.add(minionPlayer1);
		assertEquals(minionPlayer1, ah.findMinion(minions, "100"));
		assertEquals(null, ah.findMinion(minions, "900"));

	}
	
	@Test 
	public void testisMinionAttackValid() {
		//Mock players
		List<Player> players = new ArrayList<>();
		Player player1 = new GamePlayer("1", new FirestoneHero("1", 30));
		Player player2 = new GamePlayer("2", new FirestoneHero("2", 30));
		//Mock player1's minion
		List<MinionState> state0 = new ArrayList<>();
		FirestoneMinion minionPlayer1 = new FirestoneMinion("100", "Boulderfist Ogre", 8,8,6,6, MinionRace.NONE, state0 );
		minionPlayer1.setSleepy(false);
		FirestoneMinion minionPlayer11 = new FirestoneMinion("120", "Imp", 1, 1, 1, 1, MinionRace.DEMON, state0);
		minionPlayer11.setSleepy(false);
		//Mock player2's minions
		List<MinionState> state2 = new ArrayList<>();
		FirestoneMinion minionPlayer2 = new FirestoneMinion("200", "Boulderfist Ogre", 7, 7, 6, 6, MinionRace.NONE, state2 );
		minionPlayer2.setSleepy(false);
		List<MinionState> state3 = new ArrayList<>();
		state3.add(MinionState.TAUNT);
		FirestoneMinion minionPlayer21 = new FirestoneMinion("210", "Imp", 1, 1, 1, 1, MinionRace.DEMON, state3);
		minionPlayer21.setSleepy(false);
		
		player1.getActiveMinions().add(0, minionPlayer1);
		player1.getActiveMinions().add(1, minionPlayer11);
		player2.getActiveMinions().add(0, minionPlayer2);
		player2.getActiveMinions().add(1, minionPlayer21);
		players.add(player1);
		players.add(player2);
		
		AttackHandler ah = new AttackHandler(players);
		
		//Test if minion, which is not Taunt but there is a Taunt on the board, is attackable
		assertEquals(false, ah.isMinionAttackValid(players.get(0), "100", "200"));
		//Test if the hero, but a Taunt is on the board, is attackable
		assertEquals(false, ah.isMinionAttackValid(players.get(0), "100", "2"));
		//Test if minion with Taunt as a state is attackable
		assertEquals(true, ah.isMinionAttackValid(players.get(0), "100", "210"));
		
		players.get(1).getActiveMinions().get(1).getStates().clear(); //Taunt is no more on the board
		//Test if minion is attackable
		assertEquals(true, ah.isMinionAttackValid(players.get(0), "100", "200"));
		//Test if the hero of the adversary is attackable
		assertEquals(true, ah.isMinionAttackValid(players.get(0), "100", "2"));
		
		//Test if a minion of the player is attackable
		assertEquals(false, ah.isMinionAttackValid(players.get(0), "100", "120"));
		//Test if the hero of the player is attackable
		assertEquals(false, ah.isMinionAttackValid(players.get(0), "100", "1"));
		//Test if an minion that does not exist for the player is used to attack
		assertEquals(false, ah.isMinionAttackValid(players.get(0), "200", "210"));
	}
	
	@Test
	public void testattack() {
		List<Player> players = new ArrayList<>();
		//Mock players
		Player player1 = new GamePlayer("1", new FirestoneHero("1", 30));
		Player player2 = new GamePlayer("2", new FirestoneHero("2", 30));
		//Player1 minion
		List<MinionState> state0 = new ArrayList<>();
		FirestoneMinion minionPlayer1 = new FirestoneMinion("100", "Boulderfist Ogre", 8,8,6,6, MinionRace.NONE, state0 );
		minionPlayer1.setSleepy(false);
		//Player2 minions
		List<MinionState> state2 = new ArrayList<>();
		FirestoneMinion minionPlayer2 = new FirestoneMinion("200", "Boulderfist Ogre", 7, 7, 6, 6, MinionRace.NONE, state2 );
		minionPlayer2.setSleepy(false);
		FirestoneMinion minionPlayer21 = new FirestoneMinion("210", "Imp", 1, 1, 1, 1, MinionRace.DEMON, state2);
		minionPlayer21.setSleepy(false);
		
		player1.getActiveMinions().add(0, minionPlayer1);
		player2.getActiveMinions().add(0, minionPlayer2);
		player2.getActiveMinions().add(1, minionPlayer21);
		players.add(player1);
		players.add(player2);
		
		AttackHandler ah = new AttackHandler(players);
		
		// Test to kill the Taunt minion
		ah.attack(player1, "100", "210");
		assertEquals(player2.getActiveMinions().size(), 1);
		assertEquals(player1.getActiveMinions().get(0).getHealth(), 7);
		
		//Test to attack other Ogre see that health is reduced
		ah.attack(player1, "100", "200");
		assertEquals(player2.getActiveMinions().get(0).getHealth(), 1);
		assertEquals(player1.getActiveMinions().get(0).getHealth(), 1);
		
		//Test to attack the hero see that health is reduced
		ah.attack(player1, "100", "2");
		assertEquals(player2.getHero().getHealth(), 24);
	}
	
	
}
