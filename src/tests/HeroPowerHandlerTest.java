package tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import kth.firestone.Action;
import kth.firestone.buff.BuffHandler;
import kth.firestone.hero.FirestoneHero;
import kth.firestone.hero.HeroPower;
import kth.firestone.hero.HeroPowerHandler;
import kth.firestone.player.Player;

public class HeroPowerHandlerTest {
	
	@Mock
	Player p1, p2;
	
	@Mock
	FirestoneHero hero;
	
	@Mock
	HeroPower heroPower;
	
	@Mock
	BuffHandler buffHandler;
	

	
	List<Player> players;
	HeroPowerHandler heroPowerHandler;

	public HeroPowerHandlerTest(){
		MockitoAnnotations.initMocks(this);
		players = new ArrayList<>();
		players.add(p1);
		players.add(p2);
		when(p1.getId()).thenReturn("id122912");
		when(p1.getHero()).thenReturn(hero);	
		heroPowerHandler = new HeroPowerHandler(buffHandler);
		when(hero.getHeroPower()).thenReturn(heroPower);
		when(heroPower.getManaCost()).thenReturn(2);
	}
	
	@Test
	public void testIsUseOfHeroPowerValid() {
		when(hero.getMana()).thenReturn(2);
		when(hero.hasUsedHeroPower()).thenReturn(true);
		assertFalse(heroPowerHandler.isUseOfHeroPowerValid(players, p1));
		
		when(hero.getMana()).thenReturn(1);
		when(hero.hasUsedHeroPower()).thenReturn(false);
		assertFalse(heroPowerHandler.isUseOfHeroPowerValid(players, p1));
		
		when(hero.getMana()).thenReturn(2);
		when(hero.hasUsedHeroPower()).thenReturn(false);
		when(buffHandler.isPerformHeroPowerValid(any(Action.class))).thenReturn(true);
		assertTrue(heroPowerHandler.isUseOfHeroPowerValid(players, p1));
		
		when(hero.getMana()).thenReturn(2);
		when(hero.hasUsedHeroPower()).thenReturn(false);
		when(buffHandler.isPerformHeroPowerValid(any(Action.class))).thenReturn(true);
		assertTrue(heroPowerHandler.isUseOfHeroPowerValid(players, p1, "targetId"));
	}
	
	@Test
	public void testUseHeroPower(){
		int mana = 2;
		when(hero.getMana()).thenReturn(mana);
	
		
		heroPowerHandler.useHeroPower(players, p1, "targetId");
		verify(hero, times(1)).setHasUsedHeroPower(true);
		verify(hero).setMana(mana-2);
		verify(buffHandler).performHeroPower(any(Action.class));
	}

}
