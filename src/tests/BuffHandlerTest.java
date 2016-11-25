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
import kth.firestone.FirestoneBuilder;
import kth.firestone.GameData;
import kth.firestone.buff.BuffDescription;
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

public class BuffHandlerTest {
	
	@Mock
	DamageHandler damageHandler;
	
	@Mock
	BuffMethods buffMethods;
	
	@Mock
	GamePlayer p1, p2;
	
	Card spellCard, minionCard;
	
	List<Player> players;
	List<Minion> minions1, minions2;
	
	BuffHandler buffHandler;
	List<Card> discardPile;
	
	public BuffHandlerTest() {
		MockitoAnnotations.initMocks(this);
		players = new ArrayList<>();
		players.add(p1);
		players.add(p2);
		minions1 = new ArrayList<>();
		minions2 = new ArrayList<>();
		String buff = "After you cast a spell, deal 1 damage to ALL minions.";
		String buff2 = "Battlecry: Deal 1 damage.";
		minions1.add(new FirestoneMinion("uniqueId", "Wild Pyromancer",2,2,0,0,MinionRace.NONE,new ArrayList<>(), buff, buffHandler));
		minions2.add(new FirestoneMinion("uniqueId2","Minion",2,2,1,1,MinionRace.NONE,new ArrayList<>(),buff2, buffHandler));
		minionCard = new FirestoneCard("uniqueId3","","0","0","0","MINION",buff2);
		spellCard = new FirestoneCard("","","0","0","0","SPELL","");
		
		when(p1.getId()).thenReturn("1");
		when(p2.getId()).thenReturn("2");
		
		buffHandler = new BuffHandler(buffMethods);
		
		discardPile = new ArrayList<Card>();
		discardPile.add(spellCard);
		discardPile.add(minionCard);
	}
	
	@Test
	public void testPerformBuffOnPlayedCard() {
		
	}
	@Test
	public void testPerformBuffOnPlayedMinionCard(){
		
		when(p1.getDiscardPile()).thenReturn(discardPile);
		when(p1.getActiveMinions()).thenReturn(minions1);
		when(p2.getActiveMinions()).thenReturn(minions2);
		
		
		Action action = new Action(players, players.get(0).getId(), minionCard.getId(), minions1.get(0).getId(), 
				-1, null, Action.Type.PLAYED_CARD);
		Minion m = minions1.get(0);
		buffHandler.performBuffOnPlayedCard(action);
		verify(buffMethods).dealOneDamage(action,m);
	}
	
	@Test
	public void testPerformBuffOnPlayedSpellCard(){
		when(p1.getDiscardPile()).thenReturn(discardPile);
		when(p1.getActiveMinions()).thenReturn(minions1);
		when(p2.getActiveMinions()).thenReturn(minions2);
		
		
		Action action = new Action(players, players.get(0).getId(), minionCard.getId(), null, 
				-1, null, Action.Type.PLAYED_CARD);
		
		buffHandler.performBuffOnPlayedCard(action);
		verify(buffMethods).dealOneDamage(action,null);
	}

	
	@Test
	public void testPerformBuff() {
		when(p1.getActiveMinions()).thenReturn(minions1);
		when(p2.getActiveMinions()).thenReturn(minions2);
		
		Action action = new Action(players, players.get(0).getId(), minionCard.getId(), null, 
				-1, null, Action.Type.PLAYED_CARD);
		
		buffHandler.performBuff(action, minions1.get(0));
		
		verify(buffMethods).afterSpellDealOneDamageToAllMinions(action, minions1.get(0));
		
		buffHandler.performBuff(action, minions2.get(0));
		verify(buffMethods, times(0)).dealOneDamage(action, minions2.get(0));
		
	}
	
	

}
