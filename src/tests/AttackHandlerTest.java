package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import kth.firestone.AttackHandler;
import kth.firestone.DamageHandler;
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
		AttackHandler ah = new AttackHandler(null, null);
		//Mock player's minions
		List<MinionState> state0 = new ArrayList<>();
		FirestoneMinion minionPlayer1 = new FirestoneMinion("100", "Boulderfist Ogre", 8,8,6,6, MinionRace.NONE, state0, null, null);
		minionPlayer1.setSleepy(false);
		FirestoneMinion minionPlayer11 = new FirestoneMinion("110", "Imp", 1,1,1,1,MinionRace.DEMON, state0, null, null);
		List<MinionState> state1 = new ArrayList<>();
		state1.add(MinionState.FROZEN);
		FirestoneMinion minionPlayer12 = new FirestoneMinion("120", "Imp", 1,1,1,1,MinionRace.DEMON, state1, null, null);
		minionPlayer12.setSleepy(false);
		
		List<Minion> playersActiveMinions = new ArrayList<>();
		playersActiveMinions.add(0, minionPlayer1);
		playersActiveMinions.add(1, minionPlayer11);
		playersActiveMinions.add(2, minionPlayer12);
		
		//Mock adversary's minion
		FirestoneMinion minionPlayer2 = new FirestoneMinion("200", "Boulderfist Ogre", 7,7,6,6, null, null, null, null);
		minionPlayer2.setSleepy(false);
		
		//Test minion which is not sleepy, not frozen and not the adversary's
		boolean possibleAttack = ah.canAttack(playersActiveMinions, minionPlayer1);
		assertTrue(possibleAttack);
		
		//Test sleepy minion to attack
		possibleAttack = ah.canAttack(playersActiveMinions, minionPlayer11);
		assertFalse(possibleAttack);
		
		//Test for frozen minion to attack
		possibleAttack = ah.canAttack(playersActiveMinions, minionPlayer12 );
		assertFalse(possibleAttack);
		
		// Tests if minion of adversary is attacker
		possibleAttack = ah.canAttack(playersActiveMinions, minionPlayer2);
		assertFalse(possibleAttack);	
		
		//Test if minion that has already attacked tries to attack
		minionPlayer1.setUsedThisTurn(true);
		possibleAttack = ah.canAttack(playersActiveMinions, minionPlayer1);
		assertFalse(possibleAttack);
	}
	
	@Test
	public void testisHeroAttackValid() {
		//TODO implement when we have hero attacks
	}
	
	@Test 
	public void testgetAdversary() {
		List<Player> players = new ArrayList<>();
		Player player1 = new GamePlayer("1", null);
		Player player2 = new GamePlayer("2", null);
		players.add(player1);
		players.add(player2);
		AttackHandler ah = new AttackHandler(players, null);
		
		Player adversary = ah.getAdversary("1");
		assertEquals(adversary.getId(), "2");
	}
	
	@Test 
	public void testgetAdversaryMinionswithTaunt() {
		//Mock minions of adversary
		List<MinionState> state2 = new ArrayList<>();
		FirestoneMinion minionPlayer2 = new FirestoneMinion("200", "Boulderfist Ogre", 7,7,6,6, MinionRace.NONE, state2, null, null);
		minionPlayer2.setSleepy(false);
		List<MinionState> state3 = new ArrayList<>();
		state3.add(MinionState.TAUNT);
		FirestoneMinion minionPlayer21 = new FirestoneMinion("210", "Imp", 1,1,1,1,MinionRace.DEMON, state3, null, null);
		minionPlayer21.setSleepy(false);
		List<Minion> adversaryActiveMinions = new ArrayList<>();
		adversaryActiveMinions.add(0, minionPlayer2);
		adversaryActiveMinions.add(1, minionPlayer21);
		
		//Mock minions of player
		List<MinionState> state0 = new ArrayList<>();
		FirestoneMinion minionPlayer1 = new FirestoneMinion("100", "Boulderfist Ogre", 8,8,6,6, MinionRace.NONE, state0, null, null);
		minionPlayer1.setSleepy(false);
		FirestoneMinion minionPlayer11 = new FirestoneMinion("110", "Imp", 1,1,1,1,MinionRace.DEMON, state0, null, null);
		List<MinionState> state1 = new ArrayList<>();
		state1.add(MinionState.FROZEN);
		FirestoneMinion minionPlayer12 = new FirestoneMinion("120", "Imp", 1,1,1,1,MinionRace.DEMON, state1, null, null);
		minionPlayer12.setSleepy(false);
		List<Minion> playersActiveMinions = new ArrayList<>();
		playersActiveMinions.add(0, minionPlayer1);
		playersActiveMinions.add(1, minionPlayer11);
		playersActiveMinions.add(2, minionPlayer12);
		
		AttackHandler ah = new AttackHandler(null, null);
		List<Minion> minionsTaunt = ah.getAdversaryMinionsWithTaunt(adversaryActiveMinions);
		assertEquals(minionsTaunt.size(), 1);
		
		List<Minion> minionsNonTaunt = ah.getAdversaryMinionsWithTaunt(playersActiveMinions);
		assertEquals(minionsNonTaunt.size(), 0);
	}
	
	@Test 
	public void testfindMinion() {
		AttackHandler ah = new AttackHandler(null, null);
		List<Minion> minions = new ArrayList<>();
		
		FirestoneMinion minionPlayer1 = new FirestoneMinion("100", "Boulderfist Ogre", 7,7,6,6, MinionRace.NONE, null, null, null);
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
		FirestoneMinion minionPlayer1 = new FirestoneMinion("100", "Boulderfist Ogre", 8,8,6,6, MinionRace.NONE, state0, null, null);
		minionPlayer1.setSleepy(false);
		FirestoneMinion minionPlayer11 = new FirestoneMinion("120", "Imp", 1, 1, 1, 1, MinionRace.DEMON, state0, null, null);
		minionPlayer11.setSleepy(false);
		//Mock player2's minions
		List<MinionState> state2 = new ArrayList<>();
		FirestoneMinion minionPlayer2 = new FirestoneMinion("200", "Boulderfist Ogre", 7, 7, 6, 6, MinionRace.NONE, state2, null, null);
		minionPlayer2.setSleepy(false);
		List<MinionState> state3 = new ArrayList<>();
		state3.add(MinionState.TAUNT);
		FirestoneMinion minionPlayer21 = new FirestoneMinion("210", "Imp", 1, 1, 1, 1, MinionRace.DEMON, state3, null, null);
		minionPlayer21.setSleepy(false);
		
		player1.getActiveMinions().add(0, minionPlayer1);
		player1.getActiveMinions().add(1, minionPlayer11);
		player2.getActiveMinions().add(0, minionPlayer2);
		player2.getActiveMinions().add(1, minionPlayer21);
		players.add(player1);
		players.add(player2);
		
		AttackHandler ah = new AttackHandler(players, null);
		
		//Test if minion, which is not Taunt but there is a Taunt on the board, is attackable
		assertFalse(ah.isMinionAttackValid(player1, "100", "200"));
		//Test if the hero, but a Taunt is on the board, is attackable
		assertFalse(ah.isMinionAttackValid(player1, "100", "2"));
		//Test if minion with Taunt as a state is attackable
		assertTrue(ah.isMinionAttackValid(player1, "100", "210"));
		
		player2.getActiveMinions().get(1).getStates().clear(); //Taunt is no more on the board
		//Test if minion is attackable
		assertTrue(ah.isMinionAttackValid(player1, "100", "200"));
		//Test if the hero of the adversary is attackable
		assertTrue(ah.isMinionAttackValid(player1, "100", "2"));
		
		//Test if a minion of the player is attackable, which is ok
		assertTrue(ah.isMinionAttackValid(player1, "100", "120"));
		//Test if the hero of the player is attackable, which is ok
		assertTrue(ah.isMinionAttackValid(player1, "100", "1"));
		//Test if an minion that does not exist for the player is used to attack
		assertFalse(ah.isMinionAttackValid(player1, "200", "210"));
	}
	
	@Test
	public void testattack() {
		List<Player> players = new ArrayList<>();
		//Mock players
		Player player1 = new GamePlayer("1", new FirestoneHero("1", 30));
		Player player2 = new GamePlayer("2", new FirestoneHero("2", 30));
		//Player1 minion
		List<MinionState> state0 = new ArrayList<>();
		FirestoneMinion minionPlayer1 = new FirestoneMinion("100", "Boulderfist Ogre", 8,8,6,6, MinionRace.NONE, state0, null, null);
		minionPlayer1.setSleepy(false);
		FirestoneMinion minionPlayer11 = new FirestoneMinion("110", "Imp", 1,1,1,1,MinionRace.DEMON, state0, null, null);
		//Player2 minions
		List<MinionState> state2 = new ArrayList<>();
		FirestoneMinion minionPlayer2 = new FirestoneMinion("200", "Boulderfist Ogre", 7, 7, 6, 6, MinionRace.NONE, state2, null, null);
		minionPlayer2.setSleepy(false);
		List<MinionState> state3 = new ArrayList<>();
		state3.add(MinionState.TAUNT);
		FirestoneMinion minionPlayer21 = new FirestoneMinion("210", "Imp", 1, 1, 1, 1, MinionRace.DEMON, state3, null, null);
		minionPlayer21.setSleepy(false);
		
		
		player1.getActiveMinions().add(0, minionPlayer1);
		player1.getActiveMinions().add(1, minionPlayer11);
		player2.getActiveMinions().add(0, minionPlayer2);
		player2.getActiveMinions().add(1, minionPlayer21);
		players.add(player1);
		players.add(player2);
		
		DamageHandler dh = new DamageHandler();
		AttackHandler ah = new AttackHandler(players, dh);
		
		// Test to attack the Taunt minion with non valid attacker
		ah.attack(player1, "110", "210");
		assertEquals(player1.getActiveMinions().size(), 2);
		assertEquals(player2.getActiveMinions().size(), 2);
		assertEquals(player1.getActiveMinions().get(1).getHealth(), 1);
		assertEquals(player2.getActiveMinions().get(1).getHealth(), 1);
		
		// Test to attack the minion which is not TAUNT, with a valid attacker
		ah.attack(player1, "100", "200");
		assertEquals(player1.getActiveMinions().size(), 2);
		assertEquals(player2.getActiveMinions().size(), 2);
		assertEquals(player1.getActiveMinions().get(0).getHealth(), 8);
		assertEquals(player2.getActiveMinions().get(0).getHealth(), 7);
		
		// Test to kill the Taunt minion
		ah.attack(player1, "100", "210");
		minionPlayer1.setUsedThisTurn(false);
		assertEquals(player2.getActiveMinions().size(), 1);
		assertEquals(player1.getActiveMinions().get(0).getHealth(), 7);
		
		//Test to attack other Ogre see that health is reduced
		ah.attack(player1, "100", "200");
		minionPlayer1.setUsedThisTurn(false);
		assertEquals(player2.getActiveMinions().get(0).getHealth(), 1);
		assertEquals(player1.getActiveMinions().get(0).getHealth(), 1);
		
		//Test to attack the hero see that health is reduced
		ah.attack(player1, "100", "2");
		assertEquals(player2.getHero().getHealth(), 24);
	}
	
	
}
