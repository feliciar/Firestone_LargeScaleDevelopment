package tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import kth.firestone.FirestoneBuilder;
import kth.firestone.GameData;
import kth.firestone.deck.FirestoneDeck;
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
	}

	@Test
	public void testSetStartingMana() {
		
	}

}
