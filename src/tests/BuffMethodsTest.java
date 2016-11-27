package tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import kth.firestone.Action;
import kth.firestone.DamageHandler;
import kth.firestone.Action.Type;
import kth.firestone.buff.BuffHandler;
import kth.firestone.buff.BuffMethods;
import kth.firestone.card.Card;
import kth.firestone.card.FirestoneCard;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.minion.Minion;
import kth.firestone.minion.MinionRace;
import kth.firestone.minion.MinionState;
import kth.firestone.player.GamePlayer;
import kth.firestone.player.Player;

public class BuffMethodsTest {
	
	@Mock
	DamageHandler damageHandler;
	
	@Mock
	GamePlayer p1, p2;
	
	@Mock
	BuffHandler buffHandler;
	
	List<Player> players;
	List<Minion> minions;
	BuffMethods buffMethods;
	
	public BuffMethodsTest() {
		MockitoAnnotations.initMocks(this);
		buffMethods = new BuffMethods(damageHandler);
		players = new ArrayList<>();
		players.add(p1);
		players.add(p2);
		minions = new ArrayList<>();
		minions.add(new FirestoneMinion("uniqueId-1", "Imp", 1,1,1,1,MinionRace.DEMON,new ArrayList<>(), "", buffHandler));
		minions.add(new FirestoneMinion("uniqueId-2", "War Golem", 7,7,7,7,MinionRace.NONE,new ArrayList<>(), "", buffHandler));
	}
	
	@Test
	public void testDealOneDamage() {
		Action action = new Action(players, "currentPlayerId", "playedCardId", "minionCreatedId", 0, "uniqueId-1", Action.Type.DAMAGE);
		Minion minion = new FirestoneMinion("uniqueId", "Ironforge Rifleman", 2,2,2,2,MinionRace.NONE, new ArrayList<>(),"Battlecry: Deal 1 damage.", buffHandler);
		when(p1.getActiveMinions()).thenReturn(minions);
		buffMethods.dealOneDamage(action, minion);
		
		verify(damageHandler).dealDamageToOneMinion(minions.get(0), 1);
		verify(damageHandler).removeDeadMinion(p1.getActiveMinions(), minions.get(0));
	}
	
	@Test
	public void testWhenHoldingDragonDealThreeDamage() {
		
	}
	
	@Test
	public void testGainOneHealthForCardsInHand() {
		
	}
	
	@Test
	public void testGainTwoForCardsPlayedThisTurn() {
		
	}
	
	@Test
	public void testGainOneAttackForEachOtherCardInHand() {
		
	}
	
	@Test
	public void testWhenHoldingDragonGainOneAttackAndTaunt() {
		
	}
	
	@Test
	public void testAfterSpellDealOneDamageToAllMinions() {
		
	}
	
	@Test
	public void testDealThreeDamageToAndFreezeCharacter() {
		
	}
	
	@Test
	public void testReturnOwnMinionToHand() {		
		when(p1.getActiveMinions()).thenReturn(minions);
		when(p1.getId()).thenReturn("currentPlayerId");
		when(p1.getHand()).thenReturn(new ArrayList<>());
		List<List<Card>> dp = new ArrayList<>();
		List<Card> discardPileThisTurn = new ArrayList<>();
		dp.add(discardPileThisTurn);
		
		//Test return a friendly minion
		int manaCost = 3;
		Card c = new FirestoneCard("playedCardId", "War Golem", "2", "2", ""+manaCost, Card.Type.MINION.toString(), "", MinionRace.NONE.toString());
		Minion m = minions.get(1);
		discardPileThisTurn.add(c);
		when(p1.getDiscardPile()).thenReturn(dp);
		
		Action action = new Action(players, "currentPlayerId", "playedCardId", null, 0, "uniqueId-2", Action.Type.PLAYED_CARD);
		Minion minion = null;
		
		buffMethods.returnOwnMinionToHand(action, minion);
		
		assertTrue(p1.getHand().contains(c));
		assertEquals(manaCost-2, c.getManaCost());
		assertFalse(p1.getActiveMinions().contains(m));
		
		//Test return a friendly minion with mana less than 2
		action = new Action(players, "currentPlayerId", "playedCardId", null, 0, "uniqueId-1", Action.Type.PLAYED_CARD);
		minion = null;
		
		manaCost = 1;
		c = new FirestoneCard("playedCardId", "Imp", "2", "2", ""+manaCost, Card.Type.MINION.toString(), "", MinionRace.NONE.toString());
		discardPileThisTurn.add(c);
		
		buffMethods.returnOwnMinionToHand(action, minion);
		assertEquals(0, c.getManaCost());		
	}
	
	@Test
	public void testDealTwoDamageToUndamagedMinion() {
		
		Action action = new Action(players, "currentPlayerId", "playedCardId", "minionCreatedId", 0, "uniqueId-1", Action.Type.PLAYED_CARD);
		Minion minion = null;
		when(p1.getActiveMinions()).thenReturn(minions);
		//Verify that the undamaged minion can be killed
		buffMethods.dealTwoDamageToUndamagedMinion(action, minion);
		verify(damageHandler).removeDeadMinion(p1.getActiveMinions(), minions.get(0));
		
		//Verify that the damaged minion cannot be targeted
		action = new Action(players, "currentPlayerId", "playedCardId", "minionCreatedId", 0, "uniqueId-2", Action.Type.PLAYED_CARD);
		int minionHealth = 3;
		((FirestoneMinion)minions.get(1)).setHealth(minionHealth);
		buffMethods.dealTwoDamageToUndamagedMinion(action, minion);
		assertEquals(minionHealth, minions.get(1).getHealth());
		
	}
	
	@Test
	public void testChangeHealthOfAllMinionsToOne() {
		
		Action action = new Action(players, "currentPlayerId", "playedCardId", "minionCreatedId", 0, null, Action.Type.PLAYED_CARD);
		Minion minion = null;
		
		when(p1.getActiveMinions()).thenReturn(minions);
		
		buffMethods.changeHealthOfAllMinionsToOne(action,minion);
		for(Player p : action.getPlayers()){
			for(Minion m : p.getActiveMinions()){
				assertEquals(1, m.getHealth());
			}
		}
		
	}
	
}
