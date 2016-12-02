package tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
	@Mock
	DamageHandler damageHandler;
	
	@Mock
	GamePlayer p1, p2;
	
	@Mock
	FirestoneHero heroP1, heroP2;
	
	List<Player> players;
	List<Minion> minionsP1;
	List<Minion> minionsP2;
	FirestoneMinion minionPlayer1;
	FirestoneMinion minionPlayer11;
	FirestoneMinion minionPlayer12;
	FirestoneMinion minionPlayer2;
	FirestoneMinion minionPlayer21;
	AttackHandler ah;
	
	public AttackHandlerTest() {
		MockitoAnnotations.initMocks(this);
		players = new ArrayList<>();
		players.add(p1);
		players.add(p2);
		minionsP1 = new ArrayList<>();
		minionsP2 = new ArrayList<>();
		
		minionPlayer1 = new FirestoneMinion("100", "Boulderfist Ogre", 7,7,6,6, MinionRace.NONE, new ArrayList<>(), null, null);
		minionPlayer1.setSleepy(false);
		minionsP1.add(minionPlayer1);
		minionPlayer11 = new FirestoneMinion("110", "Imp", 1,1,1,1,MinionRace.DEMON, new ArrayList<>(), null, null);
		minionsP1.add(minionPlayer11);
		List<MinionState> state = new ArrayList<>();
		state.add(MinionState.FROZEN);
		minionPlayer12 = new FirestoneMinion("120", "Imp", 1,1,1,1,MinionRace.DEMON, state, null, null);
		minionPlayer12.setSleepy(false);
		minionsP1.add(minionPlayer12);
		
		minionPlayer2 = new FirestoneMinion("200", "Boulderfist Ogre", 7,7,6,6, MinionRace.NONE, new ArrayList<>(), null, null);
		minionPlayer2.setSleepy(false);
		minionsP2.add(minionPlayer2);
		List<MinionState> state1 = new ArrayList<>();
		state1.add(MinionState.TAUNT);
		minionPlayer21 = new FirestoneMinion("210", "Imp", 1,1,1,1,MinionRace.DEMON, state1, null, null);
		minionPlayer21.setSleepy(false);
		minionsP2.add(minionPlayer21);
		
		ah = new AttackHandler(players, damageHandler);
	}
	
	@Test 
	public void testcanAttack() {		
		//Test minion which is not sleepy, not frozen and not the adversary's
		assertTrue(ah.canAttack(minionsP1, minionPlayer1));
		
		//Test sleepy minion to attack
		assertFalse(ah.canAttack(minionsP1, minionPlayer11));
		
		//Test for frozen minion to attack
		assertFalse(ah.canAttack(minionsP1, minionPlayer12));
		
		// Tests if minion of adversary is attacker
		assertFalse(ah.canAttack(minionsP1, minionPlayer2));	
		
		//Test if minion that has already attacked tries to attack
		minionPlayer1.setUsedThisTurn(true);
		assertFalse(ah.canAttack(minionsP1, minionPlayer1));
	}
	
	@Test
	public void testisHeroAttackValid() {
		//TODO implement when we have hero attacks
	}
	
	@Test 
	public void testgetAdversary() {		
		when(p1.getId()).thenReturn("1");
		when(p2.getId()).thenReturn("2");
		
		Player adversary = ah.getAdversary("1");
		assertEquals(adversary.getId(), "2");
	}
	
	@Test 
	public void testgetAdversaryMinionswithTaunt() {
		List<Minion> minionsTaunt = ah.getAdversaryMinionsWithTaunt(minionsP2);
		assertEquals(minionsTaunt.size(), 1);
		
		List<Minion> minionsNonTaunt = ah.getAdversaryMinionsWithTaunt(minionsP1);
		assertEquals(minionsNonTaunt.size(), 0);
	}
	
	@Test 
	public void testfindMinion() {
		assertEquals(minionPlayer1, ah.findMinion(minionsP1, "100"));
		assertEquals(null, ah.findMinion(minionsP1, "900"));
	}
	
	@Test 
	public void testisMinionAttackValid() {		
		when(p1.getActiveMinions()).thenReturn(minionsP1);
		when(p2.getActiveMinions()).thenReturn(minionsP2);
		when(p1.getId()).thenReturn("1");
		when(heroP1.getId()).thenReturn("1");
		when(heroP2.getId()).thenReturn("2");
		
		//Test if minion, which is not Taunt but there is a Taunt on the board, is attackable
		assertFalse(ah.isMinionAttackValid(p1, "100", "200"));
		//Test if the hero, but a Taunt is on the board, is attackable
		assertFalse(ah.isMinionAttackValid(p1, "100", "2"));
		//Test if minion with Taunt as a state is attackable
		assertTrue(ah.isMinionAttackValid(p1, "100", "210"));
		
		minionPlayer21.getStates().clear(); //Taunt is no more on the board
		//Test if minion is attackable
		assertTrue(ah.isMinionAttackValid(p1, "100", "200"));
		//Test if the hero of the adversary is attackable
		assertTrue(ah.isMinionAttackValid(p1, "100", "2"));
		
		//Test if a minion of the player is attackable, which is ok
		assertTrue(ah.isMinionAttackValid(p1, "100", "120"));
		//Test if the hero of the player is attackable, which is ok
		assertTrue(ah.isMinionAttackValid(p1, "100", "1"));
		//Test if an minion that does not exist for the player is used to attack
		assertFalse(ah.isMinionAttackValid(p1, "200", "210"));
	}
	
	@Test
	public void testattack_AttackerNonValid() {
		when(p1.getActiveMinions()).thenReturn(minionsP1);
		when(p2.getActiveMinions()).thenReturn(minionsP2);
		when(p1.getId()).thenReturn("1");
		when(p1.getHero()).thenReturn(heroP1);
		when(p2.getHero()).thenReturn(heroP2);
		when(heroP1.getId()).thenReturn("1");
		when(heroP2.getId()).thenReturn("2");
		
		// Test to attack the Taunt minion with non valid attacker
		ah.attack(p1, "110", "210");
		verify(damageHandler, never()).dealDamageToTwoMinions(minionPlayer11, minionPlayer21);
		verify(damageHandler, never()).removeDeadMinions(minionsP1);
		verify(damageHandler, never()).removeDeadMinions(minionsP2);		
	}
	
	@Test
	public void testattack_attackerValidTargetNonValid() {
		when(p1.getActiveMinions()).thenReturn(minionsP1);
		when(p2.getActiveMinions()).thenReturn(minionsP2);
		when(p1.getId()).thenReturn("1");
		when(p1.getHero()).thenReturn(heroP1);
		when(p2.getHero()).thenReturn(heroP2);
		when(heroP1.getId()).thenReturn("1");
		when(heroP2.getId()).thenReturn("2");
		
		// Test to attack the minion which is not TAUNT, with a valid attacker
		ah.attack(p1, "100", "200");
		verify(damageHandler, never()).dealDamageToTwoMinions(minionPlayer1, minionPlayer2);
		verify(damageHandler, never()).removeDeadMinions(minionsP1);
		verify(damageHandler, never()).removeDeadMinions(minionsP2);
	}
	
	@Test
	public void testattack_AttackerValidTargetValidTaunt() {
		when(p1.getActiveMinions()).thenReturn(minionsP1);
		when(p2.getActiveMinions()).thenReturn(minionsP2);
		when(p1.getId()).thenReturn("1");
		when(p1.getHero()).thenReturn(heroP1);
		when(p2.getHero()).thenReturn(heroP2);
		when(heroP1.getId()).thenReturn("1");
		when(heroP2.getId()).thenReturn("2");
		
		// Test to kill the Taunt minion
		ah.attack(p1, "100", "210");
		verify(damageHandler).dealDamageToTwoMinions(minionPlayer1, minionPlayer21);
		verify(damageHandler).removeDeadMinions(minionsP2);
		verify(damageHandler).removeDeadMinions(minionsP1);
	}
	
	@Test
	public void testattack_AttackerValidTargetValid() {
		when(p1.getActiveMinions()).thenReturn(minionsP1);
		when(p2.getActiveMinions()).thenReturn(minionsP2);
		when(p1.getId()).thenReturn("1");
		when(p1.getHero()).thenReturn(heroP1);
		when(p2.getHero()).thenReturn(heroP2);
		when(heroP1.getId()).thenReturn("1");
		when(heroP2.getId()).thenReturn("2");
		
		//Test to attack other Ogre 
		minionPlayer21.getStates().clear(); //Taunt is no more on the board
		ah.attack(p1, "100", "200");
		verify(damageHandler).dealDamageToTwoMinions(minionPlayer1, minionPlayer2);
		verify(damageHandler).removeDeadMinions(minionsP1);
		verify(damageHandler).removeDeadMinions(minionsP2);
	}
	
	@Test
	public void testattack_AttackerValidTargetHero() {
		when(p1.getActiveMinions()).thenReturn(minionsP1);
		when(p2.getActiveMinions()).thenReturn(minionsP2);
		when(p1.getId()).thenReturn("1");
		when(p1.getHero()).thenReturn(heroP1);
		when(p2.getHero()).thenReturn(heroP2);
		when(heroP1.getId()).thenReturn("1");
		when(heroP2.getId()).thenReturn("2");
		
		minionPlayer21.getStates().clear(); //Taunt is no more on the board
		//Test to attack the hero see that health is reduced
		ah.attack(p1, "100", "2");
		verify(damageHandler).dealDamageToMinionAndHero(minionPlayer1, heroP2);

	}
}