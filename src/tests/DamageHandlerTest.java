package tests;

import static org.junit.Assert.*;
import kth.firestone.DamageHandler;
import kth.firestone.hero.FirestoneHero;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.minion.MinionRace;
import kth.firestone.player.GamePlayer;
import kth.firestone.player.Player;

import org.junit.Test;


public class DamageHandlerTest {

	@Test
	public void testDealDamageToMinionAndHero(){
		DamageHandler dh = new DamageHandler();
		
		//Mocked target - Hero
		FirestoneHero hero = new FirestoneHero("2", 30);
		//Mock attacker - Minion
		FirestoneMinion minionPlayer1 = new FirestoneMinion("100", "Boulderfist Ogre", 7,7,6,6, MinionRace.NONE, null );
		minionPlayer1.setSleepy(false);
		
		dh.dealDamageToMinionAndHero(minionPlayer1, hero);
		//Test to attack the hero see that health is reduced
		assertEquals(hero.getHealth(), 24);
	}
	
	@Test
	public void testDealDamageToMinions(){
		DamageHandler dh = new DamageHandler();
		//Minions
		FirestoneMinion minionPlayer1 = new FirestoneMinion("100", "Boulderfist Ogre", 7,7,6,6, MinionRace.NONE, null );
		minionPlayer1.setSleepy(false);
		FirestoneMinion minionPlayer2 = new FirestoneMinion("200", "Boulderfist Ogre", 7, 7, 6, 6, MinionRace.NONE, null );
		minionPlayer2.setSleepy(false);
		
		//Test to attack other Ogre see that health is reduced
		dh.dealDamageToMinions(minionPlayer1, minionPlayer2);
		assertEquals(minionPlayer1.getHealth(), 1);
		assertEquals(minionPlayer2.getHealth(), 1);
		
	}
	@Test
	public void testRemoveDeadMinions(){
		DamageHandler dh = new DamageHandler();
		
		//Mock players
		Player player1 = new GamePlayer("1", new FirestoneHero("1", 30));
		Player player2 = new GamePlayer("2", new FirestoneHero("2", 30));
		//Mocked minions
		FirestoneMinion minionPlayer1 = new FirestoneMinion("100", "Boulderfist Ogre", 7,7,6,6, MinionRace.NONE, null );
		minionPlayer1.setSleepy(false);
		FirestoneMinion minionPlayer2 = new FirestoneMinion("200", "Boulderfist Ogre", 7, 7, 6, 6, MinionRace.NONE, null );
		minionPlayer2.setSleepy(false);
		FirestoneMinion minionPlayer21 = new FirestoneMinion("210", "Imp", 0, 1, 1, 1, MinionRace.DEMON, null);
		minionPlayer21.setSleepy(false);
		// Add minions to the players activeMinions list
		player1.getActiveMinions().add(0, minionPlayer1);
		player2.getActiveMinions().add(0, minionPlayer2);
		player2.getActiveMinions().add(1, minionPlayer21);
		
		dh.removeDeadMinions(minionPlayer1, minionPlayer21, player1, player2);
		assertEquals(player1.getActiveMinions().size(), 1);
		assertEquals(player2.getActiveMinions().size(), 1);
		assertFalse(player2.getActiveMinions().contains(minionPlayer21));
	}
	
	@Test
	public void checkDefeat(){
		//TODO
	}
}