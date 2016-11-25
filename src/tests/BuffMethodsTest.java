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
		
	}
	
	@Test
	public void testDealTwoDamageToUndamagedMinion() {
		
	}
	
	@Test
	public void testChangeHealthOfAllMinionsToOne() {
		
	}
	
}
