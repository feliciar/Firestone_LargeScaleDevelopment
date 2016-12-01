package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import kth.firestone.DamageHandler;
import kth.firestone.hero.FirestoneHero;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.minion.Minion;
import kth.firestone.minion.MinionRace;

import org.junit.Test;


public class DamageHandlerTest {

	@Test
	public void testDealDamageToMinionAndHero() {
		DamageHandler dh = new DamageHandler();
		
		//Mocked target - Hero
		FirestoneHero hero = new FirestoneHero("2", 30, null);
		//Mock attacker - Minion
		FirestoneMinion minionPlayer1 = new FirestoneMinion("100", "Boulderfist Ogre", 7,7,6,6, MinionRace.NONE, null, null, null);
		minionPlayer1.setSleepy(false);
		
		dh.dealDamageToMinionAndHero(minionPlayer1, hero);
		assertEquals(hero.getHealth(), 24);
		assertEquals(minionPlayer1.getHealth(),7);
	}
	
	@Test
	public void testDealDamageToMinions() {
		DamageHandler dh = new DamageHandler();
		//Minions
		FirestoneMinion minionPlayer1 = new FirestoneMinion("100", "Boulderfist Ogre", 7,7,6,6, MinionRace.NONE, null, null, null);
		minionPlayer1.setSleepy(false);
		FirestoneMinion minionPlayer2 = new FirestoneMinion("200", "Boulderfist Ogre", 7, 7, 6, 6, MinionRace.NONE, null, null, null);
		minionPlayer2.setSleepy(false);
		
		//Test to attack other Ogre see that health is reduced
		dh.dealDamageToTwoMinions(minionPlayer1, minionPlayer2);
		assertEquals(minionPlayer1.getHealth(), 1);
		assertEquals(minionPlayer2.getHealth(), 1);
		
	}
	
	@Test
	public void testDealDamageToOneMinion() {
		DamageHandler dh = new DamageHandler();
		FirestoneMinion minion = new FirestoneMinion(null, "Boulderfist Ogre", 7,7,6,6, null, null, null, null);
		
		dh.dealDamageToOneMinion(minion, 2);
		assertEquals(minion.getHealth(), 5);
	}
	
	@Test
	public void testdealOneDamageToSeveralMinions() {
		DamageHandler dh = new DamageHandler();
		Minion minion1 = new FirestoneMinion(null, "Boulderfist Ogre", 7,7,6,6, null, null, null, null);
		Minion minion2 = new FirestoneMinion(null, "Boulderfist Ogre", 7,7,6,6, null, null, null, null);
		Minion minion3 = new FirestoneMinion(null, "Imp", 1, 1, 1, 1, null, null, null, null);
		ArrayList<Minion> minionList = new ArrayList<Minion>();
		minionList.add(minion1);
		minionList.add(minion2);
		minionList.add(minion3);
		dh.dealOneDamageToSeveralMinions(minionList);
		
		assertEquals(minion1.getHealth(), 6);
		assertEquals(minion2.getHealth(), 6);
		assertEquals(minion3.getHealth(), 0);
	}
	
	@Test
	public void testFindDeadMinions(){
		DamageHandler dh = new DamageHandler();
		
		FirestoneMinion minion1 = new FirestoneMinion("200", "Boulderfist Ogre", 7, 7, 6, 6, MinionRace.NONE, null, null, null);
		minion1.setSleepy(false);
		FirestoneMinion minion2 = new FirestoneMinion("210", "Imp", 0, 1, 1, 1, MinionRace.DEMON, null, null, null);
		minion2.setSleepy(false);
		
		List<Minion> minionList = new ArrayList<Minion>();
		minionList.add(minion1);
		minionList.add(minion2);
		
		assertEquals(dh.findDeadMinions(minionList).size(), 1);
		
	}
	
	@Test
	public void testRemoveDeadMinions() {
		DamageHandler dh = new DamageHandler();
		
		//Mocked minions
		FirestoneMinion minionPlayer1 = new FirestoneMinion("100", "Boulderfist Ogre", 7,7,6,6, MinionRace.NONE, null, null, null);
		minionPlayer1.setSleepy(false);
		FirestoneMinion minionPlayer2 = new FirestoneMinion("200", "Boulderfist Ogre", 7, 7, 6, 6, MinionRace.NONE, null, null, null);
		minionPlayer2.setSleepy(false);
		FirestoneMinion minionPlayer21 = new FirestoneMinion("210", "Imp", 0, 1, 1, 1, MinionRace.DEMON, null, null, null);
		minionPlayer21.setSleepy(false);
		// Add minions to the players activeMinions list
		List<Minion> activeMinionsP1 = new ArrayList<Minion>();
		List<Minion> activeMinionsP2 = new ArrayList<Minion>();
		activeMinionsP1.add(0, minionPlayer1);
		activeMinionsP2.add(0, minionPlayer2);
		activeMinionsP2.add(1, minionPlayer21);
		
		dh.removeDeadMinions(activeMinionsP1);
		dh.removeDeadMinions(activeMinionsP2);
		assertEquals(activeMinionsP1.size(), 1);
		assertEquals(activeMinionsP2.size(), 1);
		assertFalse(activeMinionsP2.contains(minionPlayer21));
	}
	
	@Test
	public void checkDefeat() {
		//TODO Check defeat for heros
	}
}