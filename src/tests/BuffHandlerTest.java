package tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import kth.firestone.buff.BuffDescription;
import kth.firestone.buff.BuffHandler;
import kth.firestone.buff.FirestoneBuffDescription;
import kth.firestone.card.FirestoneCard;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.minion.Minion;
import kth.firestone.minion.MinionRace;
import kth.firestone.minion.MinionState;
import kth.firestone.player.Player;

public class BuffHandlerTest {


	@Test
	public void testPerformBuffForPlayedCard() {
		List<Player> players = new ArrayList<Player>();
		players.add(mock(Player.class));
		players.add(mock(Player.class));
		
		
		//minions1.add(new FirestoneMinion("uniqueId", "DamageDealer",2,2,0,0,MinionRace.NONE,new ArrayList<MinionState>(), new ArrayList<>()));
		List<Minion> minions2 = new ArrayList<Minion>();
		minions2.add(new FirestoneMinion("uniqueId2","Minion",2,2,1,1,MinionRace.NONE,new ArrayList<MinionState>(),new ArrayList<>()));
		//when(players.get(0).getActiveMinions()).thenReturn(minions1);
		when(players.get(1).getActiveMinions()).thenReturn(minions2);
		
		BuffHandler bh = new BuffHandler();
		bh.createBuffMethods();
		
		bh.performAllBuffs(players, null, new FirestoneCard("","","0","0","0","MINION","Battlecry: Deal 1 damage."), 0,"","uniqueId2");
		
		assertEquals(minions2.get(0).getHealth(), 1);
	}

	@Test
	public void testPerformBuffsForAllMinions() {
		List<Player> players = new ArrayList<Player>();
		players.add(mock(Player.class));
		players.add(mock(Player.class));
		
		List<BuffDescription> buffDescriptions = new ArrayList<BuffDescription>();
		buffDescriptions.add(new FirestoneBuffDescription("", "After you cast a spell, deal 1 damage to ALL minions."));
		List<Minion> minions1 = new ArrayList<Minion>();
		minions1.add(new FirestoneMinion("uniqueId", "Wild Pyromancer",2,2,0,0,MinionRace.NONE,new ArrayList<MinionState>(), buffDescriptions));
		List<Minion> minions2 = new ArrayList<Minion>();
		minions2.add(new FirestoneMinion("uniqueId2","Minion",2,2,1,1,MinionRace.NONE,new ArrayList<MinionState>(),new ArrayList<BuffDescription>()));
		when(players.get(0).getActiveMinions()).thenReturn(minions1);
		when(players.get(1).getActiveMinions()).thenReturn(minions2);
		
		BuffHandler bh = new BuffHandler();
		bh.createBuffMethods();
		
		bh.performAllBuffs(players, null, new FirestoneCard("","","0","0","0","SPELL",""), 0,"","");
		
		assertEquals(minions2.get(0).getHealth(), 1);
		assertEquals(minions1.get(0).getHealth(), 2);
	}

	@Test
	public void testCreateBuffMethods() {
		//TODO implement
	}

}
