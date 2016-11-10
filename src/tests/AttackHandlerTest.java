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
import kth.firestone.minion.MinionRace;
import kth.firestone.minion.MinionState;
import kth.firestone.player.GamePlayer;
import kth.firestone.player.Player;

public class AttackHandlerTest {
	private List<Player> createPlayers(){
		//Mock data
		List<Player> players = new ArrayList<>();
		Player player1 = new GamePlayer("1", new FirestoneHero("1", 30));
		Player player2 = new GamePlayer("2", new FirestoneHero("2", 30));
		
		List<MinionState> state0 = new ArrayList<>();
		FirestoneMinion minionPlayer1 = new FirestoneMinion("100", "Boulderfist Ogre", 8,8,6,6, MinionRace.NONE, state0 );
		minionPlayer1.setSleepy(false);
		FirestoneMinion minionPlayer11 = new FirestoneMinion("110", "Imp", 1,1,1,1,MinionRace.DEMON, state0);
		List<MinionState> state1 = new ArrayList<>();
		state1.add(MinionState.FROZEN);
		FirestoneMinion minionPlayer12 = new FirestoneMinion("120", "Imp", 1,1,1,1,MinionRace.DEMON, state1);
		minionPlayer12.setSleepy(false);
		minionPlayer12.getStates().add((MinionState.FROZEN));
		
		List<MinionState> state2 = new ArrayList<>();
		FirestoneMinion minionPlayer2 = new FirestoneMinion("200", "Boulderfist Ogre", 7,7,6,6, MinionRace.NONE, state2 );
		minionPlayer2.setSleepy(false);
		List<MinionState> state3 = new ArrayList<>();
		state3.add(MinionState.TAUNT);
		FirestoneMinion minionPlayer21 = new FirestoneMinion("210", "Imp", 1,1,1,1,MinionRace.DEMON, state3);
		minionPlayer21.setSleepy(false);

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
	
	@Test
	public void testgetAdversaryMinionswithTaunt() {
		List<Player> players = createPlayers();
		AttackHandler ah = new AttackHandler(players);
		List<Minion> minionsTaunt = ah.getAdversaryMinionsWithTaunt(players.get(1));
		assertEquals(minionsTaunt.size(), 1);
		
		List<Minion> minionsNonTaunt = ah.getAdversaryMinionsWithTaunt(players.get(0));
		assertEquals(minionsNonTaunt.size(), 0);
	}
	
	@Test
	public void testfindMinion() {
		List<Player> players = new ArrayList<>();
		AttackHandler ah = new AttackHandler(players);
		List<Minion> minions = new ArrayList<>();
		List<MinionState> state = new ArrayList<>();
		
		FirestoneMinion minionPlayer1 = new FirestoneMinion("100", "Boulderfist Ogre", 7,7,6,6, MinionRace.NONE, state );
		minions.add(minionPlayer1);
		assertEquals(minionPlayer1, ah.findMinion(minions, "100"));

	}
	
	@Test
	public void testisMinionAttackValid() {
		List<Player> players = createPlayers();
		AttackHandler ah = new AttackHandler(players);
		
		//Test if minion with Taunt as a state is attackable
		assertEquals(true, ah.isMinionAttackValid(players.get(0), "100", "210"));
		//Test if minion, which is not Taunt but there is a Taunt on the board, is attackable
		assertEquals(false, ah.isMinionAttackValid(players.get(0), "100", "200"));
		//Test if the hero, but a Taunt is on the board, is attackable
		assertEquals(false, ah.isMinionAttackValid(players.get(0), "100", "2"));
		
		players.get(1).getActiveMinions().get(1).getStates().clear(); //Taunt is no more on the board
		//Test if minion is attackable
		assertEquals(true, ah.isMinionAttackValid(players.get(0), "100", "200"));
		//Test if the hero of the adversary is attackable
		assertEquals(true, ah.isMinionAttackValid(players.get(0), "100", "2"));
		
		//Test if a minion of the player is attackable
		assertEquals(false, ah.isMinionAttackValid(players.get(0), "100", "120"));
		//Test if the hero of the player is attackable
		assertEquals(false, ah.isMinionAttackValid(players.get(0), "100", "1"));
	}
	
	@Test
	public void testattack() {
		List<Player> players = createPlayers();
		AttackHandler ah = new AttackHandler(players);
		
		// Test to kill the Taunt minion
		ah.attack(players.get(0), "100", "210");
		assertEquals(players.get(1).getActiveMinions().size(), 1);
		assertEquals(players.get(0).getActiveMinions().get(0).getHealth(), 7);
		
		//Test to attack other Ogre see that health is reduced
		ah.attack(players.get(0), "100", "200");
		assertEquals(players.get(1).getActiveMinions().get(0).getHealth(), 1);
		assertEquals(players.get(0).getActiveMinions().get(0).getHealth(), 1);
		
		//Test to attack the hero see that health is reduced
		ah.attack(players.get(0), "100", "2");
		assertEquals(players.get(1).getHero().getHealth(), 24);
	}
	
	
}
