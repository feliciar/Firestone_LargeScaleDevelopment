package tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import kth.firestone.DamageHandler;
import kth.firestone.FirestoneBuilder;
import kth.firestone.GameData;
import kth.firestone.buff.BuffDescription;
import kth.firestone.buff.BuffHandler;
import kth.firestone.card.Card;
import kth.firestone.card.FirestoneCard;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.minion.Minion;
import kth.firestone.minion.MinionRace;
import kth.firestone.minion.MinionState;
import kth.firestone.player.Player;

public class BuffHandlerTest {
	
	@Mock
	DamageHandler damageHandler;
	
	@Mock
	Player p1, p2;
	
	Card spellCard, minionCard;
	
	List<Player> players;
	List<Minion> minions1, minions2;
	
	public BuffHandlerTest() {
		MockitoAnnotations.initMocks(this);
		players = new ArrayList<>();
		players.add(p1);
		players.add(p2);
		minions1 = new ArrayList<>();
		minions2 = new ArrayList<>();
		String buff = "After you cast a spell, deal 1 damage to ALL minions.";
		minions1.add(new FirestoneMinion("uniqueId", "Wild Pyromancer",2,2,0,0,MinionRace.NONE,new ArrayList<>(), buff));
		minions2.add(new FirestoneMinion("uniqueId2","Minion",2,2,1,1,MinionRace.NONE,new ArrayList<>(),""));
		minionCard = new FirestoneCard("","","0","0","0","MINION","Battlecry: Deal 1 damage.");
		spellCard = new FirestoneCard("","","0","0","0","SPELL","");
	}
	
	@Test
	public void testPerformAllBuffs() {
		
	}

	/*@Test
	public void testPerformBuffsAfterCardPlayed() {
		//minions1.add(new FirestoneMinion("uniqueId", "DamageDealer",2,2,0,0,MinionRace.NONE,new ArrayList<MinionState>(), new ArrayList<>()));
		//when(players.get(0).getActiveMinions()).thenReturn(minions1);
		when(players.get(1).getActiveMinions()).thenReturn(minions2);
		BuffHandler bh = new BuffHandler(damageHandler);
		bh.createBuffMethods();
		bh.performBuffsAfterCardPlayed(players, players.get(0), minionCard, 0,"uniqueId2");
		assertEquals(minions2.get(0).getHealth(), 1);
	}*/

	/*@Test
	public void testPerformBuffsForAllMinions() {
		when(players.get(0).getActiveMinions()).thenReturn(minions1);
		when(players.get(1).getActiveMinions()).thenReturn(minions2);
		
		BuffHandler bh = new BuffHandler(damageHandler);
		bh.createBuffMethods();
		
		bh.performBuffsAfterCardPlayed(players, players.get(0), spellCard, 0,"");
		
		assertEquals(minions2.get(0).getHealth(), 1);
		assertEquals(minions1.get(0).getHealth(), 2);
	}
*/
	@Test
	public void testCreateBuffMethods() {
		//TODO implement
	}

}
