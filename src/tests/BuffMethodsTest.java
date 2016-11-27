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
import kth.firestone.hero.FirestoneHero;
import kth.firestone.hero.Hero;
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
		minions.add(new FirestoneMinion("uniqueId-3", "Twilight Drake", 1,1,4,4,MinionRace.DRAGON,new ArrayList<>(), "Battlecry: Gain +1 Health for each card in your hand.", buffHandler));
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
		Action action1 = new Action(players, "currentPlayerId", "playedCardId", "minionCreatedId", 0, "targetHero", Action.Type.DAMAGE);
		Action action2 = new Action(players, "currentPlayerId", "playedCardId", "minionCreatedId", 0, "uniqueId-1", Action.Type.DAMAGE);
		
		Minion minion = new FirestoneMinion("", "", 4,4,5,5,MinionRace.NONE, new ArrayList<>(),"", buffHandler);
		Hero hero = new FirestoneHero("targetHero", 4);
		List<Card> hand = new ArrayList<>();
		hand.add(new FirestoneCard("","","1","1","1","MINION","","DRAGON"));
		when(p1.getId()).thenReturn("currentPlayerId");
		when(p1.getHand()).thenReturn(hand);

		// When target is hero
		when(p1.getHero()).thenReturn(hero);
		buffMethods.whenHoldingDragonDealThreeDamage(action1, minion);
		verify(damageHandler).dealDamageToHero(hero, 3);
		
		// When target is minion
		when(p1.getActiveMinions()).thenReturn(minions);
		buffMethods.whenHoldingDragonDealThreeDamage(action2, minion);
		verify(damageHandler).dealDamageToOneMinion(minions.get(0), 3);
		verify(damageHandler).removeDeadMinions(minions);
	}
	
	@Test
	public void testGainOneHealthForCardsInHand() {
		Action action = new Action(players, "currentPlayerId", "playedCardId", "minionCreatedId", 0, "targetHero", Action.Type.DAMAGE);
		Minion minion = new FirestoneMinion("", "", 4,4,5,5,MinionRace.NONE, new ArrayList<>(),"", buffHandler);
		Hero hero = new FirestoneHero("heroId", 4);
		List<Card> hand = new ArrayList<>();
		hand.add(new FirestoneCard("","","1","1","1","MINION","","DRAGON"));
		hand.add(new FirestoneCard("","","1","1","1","MINION","","NONE"));

		when(p1.getId()).thenReturn("currentPlayerId");
		when(p1.getHero()).thenReturn(hero);
		when(p1.getHand()).thenReturn(hand);
		
		buffMethods.gainOneHealthForCardsInHand(action, minion);
		
		assertEquals(6, hero.getHealth());
	}
	
	@Test
	public void testGainTwoForCardsPlayedThisTurn() {
		Action action = new Action(players, "currentPlayerId", "playedCardId", "minionCreatedId", 0, "targetHero", Action.Type.DAMAGE);
		Minion minion = new FirestoneMinion("", "", 4,4,5,5,MinionRace.NONE, new ArrayList<>(),"", buffHandler);
		Hero hero = new FirestoneHero("heroId", 4);
		List<Card> cardsPlayed = new ArrayList<>();
		cardsPlayed.add(new FirestoneCard("","","1","1","1","MINION","","DRAGON"));
		cardsPlayed.add(new FirestoneCard("","","1","1","1","MINION","","NONE"));

		when(p1.getId()).thenReturn("currentPlayerId");
		when(p1.getDiscardPileThisTurn()).thenReturn(cardsPlayed);
		when(p1.getHero()).thenReturn(hero);
		
		buffMethods.gainTwoForCardsPlayedThisTurn(action, minion);
		
		assertEquals(8, hero.getHealth());
		assertEquals(4, hero.getAttack());
	}
	
	@Test
	public void testGainOneAttackForEachOtherCardInHand() {
		Action action = new Action(players, "currentPlayerId", "playedCardId", "minionCreatedId", 0, "targetHero", Action.Type.DAMAGE);
		Minion minion = new FirestoneMinion("", "", 4,4,5,5,MinionRace.NONE, new ArrayList<>(),"", buffHandler);
		List<Card> hand = new ArrayList<>();
		hand.add(new FirestoneCard("","","1","1","1","MINION","","DRAGON"));
		hand.add(new FirestoneCard("","","1","1","1","MINION","","NONE"));

		when(p1.getId()).thenReturn("currentPlayerId");
		when(p1.getHand()).thenReturn(hand);
		
		buffMethods.gainOneAttackForEachOtherCardInHand(action, minion);
		
		assertEquals(7, minion.getAttack());
	}
	
	@Test
	public void testWhenHoldingDragonGainOneAttackAndTaunt() {
		Action action = new Action(players, "currentPlayerId", "playedCardId", "minionCreatedId", 0, null, Action.Type.PLAYED_CARD);
		Minion minion = new FirestoneMinion("minionCreatedId", "Twilight Guardian", 6,6,2,2, MinionRace.DRAGON, new ArrayList<>(),"Battlecry: If you're holding a Dragon, gain +1 Attack and Taunt.", buffHandler);
		List<Card> hand = new ArrayList<Card>();
		hand.add(new FirestoneCard("cardID1", "Midnight Drake", "4", "1", "4", "MINION", null, "DRAGON"));
		
		when(p1.getId()).thenReturn("currentPlayerId");
		when(p1.getHand()).thenReturn(hand);
		
		buffMethods.whenHoldingDragonGainOneAttackAndTaunt(action, minion);
		
		assertEquals(minion.getAttack(), 3);
		assertTrue(minion.getStates().contains(MinionState.TAUNT));
	}
	
	@Test
	public void testAfterSpellDealOneDamageToAllMinions() {
		Action action = new Action(players, "currentPlayerId", "playedCardId", null , -1, null, Action.Type.PLAYED_CARD);
		List<Minion> minionsp1 = new ArrayList<>();
		minionsp1.add(new FirestoneMinion("uniqueId-3", "Wild Pyromancer", 2,2,3,3,MinionRace.NONE,new ArrayList<>(), "After you cast a spell, deal 1 damage to ALL minions.", buffHandler));
		ArrayList<Card> discardPileThisTurn = new ArrayList<Card>();
		discardPileThisTurn.add(new FirestoneCard("playedCardId", "Frostbolt", "0", "0", "2", "SPELL", null, "NONE"));
		
		when(p1.getId()).thenReturn("currentPlayerId");
		when(p1.getDiscardPileThisTurn()).thenReturn(discardPileThisTurn);
		when(p1.getActiveMinions()).thenReturn(minionsp1);
		when(p2.getActiveMinions()).thenReturn(minions);
		
		buffMethods.afterSpellDealOneDamageToAllMinions(action, minionsp1.get(0));
		
		verify(damageHandler).removeDeadMinions(minionsp1);
		verify(damageHandler).removeDeadMinions(minions);
		verify(damageHandler).dealOneDamageToSeveralMinions(minions);
	}
	
	@Test
	public void testDealThreeDamageToAndFreezeCharacter() {
		Action action = new Action(players, "currentPlayerId", "playedCardId", null , -1, "uniqueId-2", Action.Type.PLAYED_CARD);
		FirestoneHero hero = new FirestoneHero("heroId", 30);
		when(p1.getHero()).thenReturn(hero);
		when(p1.getActiveMinions()).thenReturn(minions);
		
		buffMethods.dealThreeDamageToAndFreezeCharacter(action, null);
		
		verify(damageHandler).dealDamageToOneMinion(minions.get(1), 3);
		verify(damageHandler).removeDeadMinion(p1.getActiveMinions(), minions.get(1));
		assertTrue(minions.get(1).getStates().contains(MinionState.FROZEN));
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
