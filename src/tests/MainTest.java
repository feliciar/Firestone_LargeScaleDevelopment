package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import kth.firestone.FirestoneBuilder;
import kth.firestone.Game;
import kth.firestone.GameBuilder;
import kth.firestone.GameData;
import kth.firestone.card.Card;
import kth.firestone.minion.Minion;
import kth.firestone.player.Player;

public class MainTest {
	private final int STARTING_MANA = 10;
	
	@Test
	public void integrationTest() {
		GameBuilder gb = new FirestoneBuilder(new GameData());
		// set starting state
		for (int i = 1; i < 3; i++) {
			gb.setMaxHealth(i, 30)
			  .setDeck(i, "Imp", "War Golem", "Boulderfist Ogre", 
				"Ironforge Rifleman", "Blackwing Corruptor", "Twilight Drake")
			  .setStartingMana(i, STARTING_MANA);
		}
		Game game = gb.build();
		game.start(); 
		
		assertEquals(game.getPlayerInTurn().getId(), "1");
		
		String m1 = game.getPlayerInTurn().getHand().get(0).getName();
		String m2 = game.getPlayerInTurn().getHand().get(2).getName();
		
		//Play card
		testPlayCard(game, game.getPlayerInTurn(), 0, 0);
		assertEquals(game.getPlayerInTurn().getActiveMinions().get(0).getName(),m1);
		assertEquals(game.getPlayerInTurn().getHand().size(), 3);
		
		//Play another card
		testPlayCard(game, game.getPlayerInTurn(), 1, 0);
		assertEquals(game.getPlayerInTurn().getActiveMinions().get(1).getName(),m1);
		assertEquals(game.getPlayerInTurn().getActiveMinions().get(0).getName(),m2);
		assertEquals(game.getPlayerInTurn().getHand().size(), 2); 
		
		//End turn
		game.endTurn(game.getPlayerInTurn());
		assertEquals(game.getPlayerInTurn().getId(), "2");
		
		//Play card
		String m3 = game.getPlayerInTurn().getHand().get(3).getName();
		testPlayCard(game, game.getPlayerInTurn(), 3, 0);
		assertEquals(game.getPlayerInTurn().getActiveMinions().get(0).getName(),m3);
		assertEquals(game.getPlayerInTurn().getHand().size(), 3);
	
		//End turn
		game.endTurn(game.getPlayerInTurn());
		assertEquals(game.getPlayerInTurn().getId(), "1");
		assertEquals(game.getPlayerInTurn().getActiveMinions().get(1).getName(),m1);
		assertEquals(game.getPlayerInTurn().getActiveMinions().get(0).getName(),m2);
		assertEquals(game.getPlayerInTurn().getHand().size(), 3); 

		//Attack another minion
		Minion minion1 = game.getPlayerInTurn().getActiveMinions().get(0);
		Minion minion2 = game.getPlayers().get(1).getActiveMinions().get(0);
		assertTrue(game.isAttackValid(game.getPlayerInTurn(), minion1.getId(), minion2.getId()));
		game.attack(game.getPlayerInTurn(), minion1.getId(), minion2.getId());
		if(minion1.getAttack()>minion2.getHealth()){
			assertEquals(game.getPlayers().get(1).getActiveMinions().size(), 0);
		}else{
			assertEquals(game.getPlayers().get(1).getActiveMinions().size(), 1);
		}
		if(minion2.getAttack()>minion1.getHealth()){
			assertEquals(game.getPlayers().get(0).getActiveMinions().size(), 1);
		}else{
			assertEquals(game.getPlayers().get(0).getActiveMinions().size(), 2);
		}
		
		//End turn
		game.endTurn(game.getPlayerInTurn());
		game.endTurn(game.getPlayerInTurn());
		assertEquals(game.getPlayerInTurn().getId(), "1");
		
		//Attack the hero
		Minion minion3 = game.getPlayers().get(0).getActiveMinions().get(0);
		int minionHealth = minion3.getHealth();
		assertTrue(game.isAttackValid(game.getPlayerInTurn(), minion3.getId(), "2"));
		int heroHealth = game.getPlayers().get(1).getHero().getHealth();
		game.attack(game.getPlayerInTurn(), minion3.getId(), "2");
		assertEquals(game.getPlayers().get(1).getHero().getHealth(), heroHealth - minion3.getAttack());		
		assertEquals(minion3.getHealth(), minionHealth - game.getPlayers().get(1).getHero().getAttack());		
	}
	
	public void testPlayCard(Game game, Player player, int cardIndex, int position){
		Card cardToPlay1 = player.getHand().get(cardIndex);
		assertTrue(game.isPlayCardValid(player, cardToPlay1));
		game.playMinionCard(player, cardToPlay1, position);
	}

}
