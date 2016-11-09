package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import kth.firestone.GameData;
import kth.firestone.card.Card;
import kth.firestone.card.FirestoneCard;
import kth.firestone.card.PlayCardHandler;
import kth.firestone.hero.FirestoneHero;
import kth.firestone.hero.Hero;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.minion.Minion;
import kth.firestone.minion.MinionState;
import kth.firestone.player.GamePlayer;

public class PlayCardHandlerTest {
	

	
	public PlayCardHandlerTest(){
	}

	@Test
	public void testPlaySpellCardPlayerCard() {
		// TODO Implement test
	}

	@Test
	public void testPlaySpellCardPlayerCardString() {
		// TODO Implement test
	}

	@Test
	public void testPlayMinionCardPlayerCardInt() {
		//Create PlayCardHandler
		GameData gameData = new GameData();
		gameData.populate();
		PlayCardHandler pch = new PlayCardHandler(gameData);
		
		//Mock player.getHand()
		GamePlayer player = mock(GamePlayer.class);
		int manaCost = 5;
		Card card = new FirestoneCard("Imp", "1", "1", ""+manaCost, "MINION", "");
		List<Card> hand = new ArrayList<Card>();
		hand.add(card);
		when(player.getHand()).thenReturn(hand);
		
		//Mock hero
		int startHealth = 30;
		FirestoneHero hero = new FirestoneHero(startHealth);
		int startMana = 10;
		hero.setMana(startMana);
		when(player.getHero()).thenReturn(hero);
		
		
		//Mock player.getActiveMinions()
		List<Minion> minions = new ArrayList<Minion>();
		minions.add(mock(Minion.class));
		minions.add(mock(Minion.class));
		minions.add(mock(Minion.class));
		when(player.getActiveMinions()).thenReturn(minions);
		
		//Stimulate the function
		int index1 = 0;
		pch.playMinionCard(player, card, index1);
		
		//Assert
		hand.remove(card);
		minions.add(index1, new FirestoneMinion(card,gameData));
		assertEquals(hand,player.getHand());
		assertEquals(minions, player.getActiveMinions());
		
		//Stimulate the function
		int index2 = 4;
		pch.playMinionCard(player, card, index2);
		
		
		//Assert
		hand.remove(card);
		minions.add(index2, new FirestoneMinion(card,gameData));
		assertEquals(hand,player.getHand());
		assertEquals(minions, player.getActiveMinions());
		assertEquals(player.getHero().getMana(), startMana-manaCost*2);
		
		
		
	}

	@Test
	public void testPlayMinionCardPlayerCardIntString() {
		// TODO Implement test
	}

	@Test
	public void testPlayWeaponCardPlayerCard() {
		// TODO Implement test
	}

	@Test
	public void testPlayWeaponCardPlayerCardString() {
		// TODO Implement test
	}

}
