package tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import kth.firestone.FirestoneBuilder;
import kth.firestone.GameData;
import kth.firestone.deck.FirestoneDeck;
import kth.firestone.hero.FirestoneHero;
import kth.firestone.hero.Hero;
import kth.firestone.player.GamePlayer;
import kth.firestone.player.Player;

public class FirestoneBuilderTest {
	
	@Mock
	GameData gameData;
	
	FirestoneBuilder fb;
	
	public FirestoneBuilderTest() {
		MockitoAnnotations.initMocks(this);
		fb = new FirestoneBuilder(gameData);
	}
	
	@Test
	public void testSetDeckIntStringArray() {
		
		HashMap<String, String> data = new HashMap<String,String>();
		String name = "name1";
		data.put("name", name);
		data.put("health", "10");
		data.put("attack", "10");
		data.put("mana", "10");
		data.put("type", "MINION");
		data.put("description", "description1");
		
		HashMap<String, HashMap<String, String>> container = new HashMap<>();
		container.put(name, data);

		when(gameData.getCards()).thenReturn(container);
		fb.setDeck(1, name);
		
		verify(gameData).getCards();
		
		Player p = fb.getPlayers().get(0);
		FirestoneDeck deck = (FirestoneDeck) p.getDeck();
		
		assertTrue(deck.getCards().pop().getName().equals(name));
	}

	@Test
	public void testSetMaxHealth() {
		FirestoneHero hero = mock(FirestoneHero.class);
		FirestoneHero hero2 = mock(FirestoneHero.class);
		List<Player> players = new ArrayList<Player>();
		Player p1 = mock(GamePlayer.class);
		Player p2 = mock(GamePlayer.class);
		players.add(p1);
		players.add(p2);
		fb.setPlayers(players);
		when(p1.getHero()).thenReturn(hero);
		when(p2.getHero()).thenReturn(hero2);
	
		int health = 2;
		fb.setMaxHealth(1, health);
		
		verify(hero).setMaxHealth(health);
		verify(hero).setHealth(health);
		
		fb.setMaxHealth(2, health);
		
		verify(hero2).setMaxHealth(health);
		verify(hero2).setHealth(health);
	}

	@Test
	public void testSetStartingMana() {
		FirestoneHero hero = mock(FirestoneHero.class);
		FirestoneHero hero2 = mock(FirestoneHero.class);
		List<Player> players = new ArrayList<Player>();
		players.add(mock(GamePlayer.class));
		players.add(mock(GamePlayer.class));
		fb.setPlayers(players);
		when(players.get(0).getHero()).thenReturn(hero);
		when(players.get(1).getHero()).thenReturn(hero2);
	
		int mana = 2;
		fb.setStartingMana(1, mana);
		
		verify(hero).setMaxMana(mana);
		verify(hero).setMana(mana);
		
		fb.setStartingMana(2, mana);
		
		verify(hero2).setMaxMana(mana);
		verify(hero2).setMana(mana);
	}

}
