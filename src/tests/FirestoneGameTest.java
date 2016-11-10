package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import kth.firestone.FirestoneBuilder;
import kth.firestone.FirestoneGame;
import kth.firestone.GameBuilder;
import kth.firestone.GameData;
import kth.firestone.deck.FirestoneDeck;
import kth.firestone.minion.Minion;
import kth.firestone.player.Player;

public class FirestoneGameTest {
	
	FirestoneGame fg;
	
	public FirestoneGameTest() {
		GameBuilder gb = new FirestoneBuilder(new GameData());
		// set starting state
		int startingMana = 0;
		for (int i = 1; i < 3; i++) {
			gb.setMaxHealth(i, 30)
			  .setDeck(i, "Imp", "War Golem", "Boulderfist Ogre", 
				"Ironforge Rifleman", "Blackwing Corruptor", "Twilight Drake")
			  .setStartingMana(i, startingMana++);
		}
		fg = (FirestoneGame) gb.build();
	}
	
	@Test
	public void testGetPlayerInTurn() {
		assertEquals(fg.getPlayerInTurn(), fg.getPlayers().get(0));
		fg.endTurn(fg.getPlayers().get(0));
		assertEquals(fg.getPlayerInTurn(), fg.getPlayers().get(1));
		fg.endTurn(fg.getPlayers().get(1));
		assertEquals(fg.getPlayerInTurn(), fg.getPlayers().get(0));
	}

	@Test
	public void testEndTurn() {
		// check that next player != current player
		Player player1 = fg.getPlayerInTurn();
		fg.endTurn(player1);
		Player player2 = fg.getPlayerInTurn();
		assertFalse(player2.equals(player1));
		
		// make deck size 0 and check that hero health is reduced by 1
		((FirestoneDeck) player2.getDeck()).getCards().clear();
		assertEquals(player2.getDeck().size(), 0);
		int healthBefore = player2.getHero().getHealth();
		fg.endTurn(player2);
		fg.endTurn(player1);
		int healthAfter = player2.getHero().getHealth();
		assertEquals(healthBefore, healthAfter+1);
		
		// check that player's hand and deck are updated
		int handBefore = player1.getHand().size();
		int deckBefore = player1.getDeck().size();
		fg.endTurn(player2);
		int handAfter = player1.getHand().size();
		int deckAfter = player1.getDeck().size();
		assertEquals(handBefore, handAfter-1);
		assertEquals(deckBefore, deckAfter+1);
		
		// check that no minions are sleepy
		fg.playMinionCard(player1, player1.getHand().get(0), 0);
		assertEquals(player1.getActiveMinions().size(), 1);
		Minion minion = player1.getActiveMinions().get(0);
		assertTrue(minion.isSleepy());
		fg.endTurn(player1);
		fg.endTurn(player2);
		assertFalse(minion.isSleepy());
		
		// check that max mana is increased and all mana is restored
		fg.endTurn(player1);
		int manaBefore = player1.getHero().getMana();
		int maxManaBefore = player1.getHero().getMaxMana();
		fg.playMinionCard(player1, player1.getHand().get(0), 0);
		fg.endTurn(player2);
		int manaAfter = player1.getHero().getMana();
		int maxManaAfter = player1.getHero().getMaxMana();
		assertEquals(maxManaBefore, maxManaAfter-1);
		assertEquals(manaBefore, manaAfter-1);
		assertEquals(manaAfter, maxManaAfter);
	}

	@Test
	public void testStartPlayer() {
		Player player1 = fg.getPlayers().get(0);
		Player player2 = fg.getPlayers().get(1);
		// check player1's deck decreased by 4 and hand contains 4 cards
		int deckBefore = player1.getDeck().size();
		fg.start(player1);
		int deckAfter = player1.getDeck().size();
		int handAfter = player1.getHand().size();
		assertEquals(deckBefore-deckAfter, 4);
		assertEquals(handAfter, 4);
		// check player2's deck decreased by 3 and hand contains 3 cards
		deckBefore = player2.getDeck().size();
		fg.start(player2);
		deckAfter = player2.getDeck().size();
		handAfter = player2.getHand().size();
		assertEquals(deckBefore-deckAfter, 3);
		assertEquals(handAfter, 3);
	}

}
