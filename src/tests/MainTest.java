package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import kth.firestone.FirestoneBuilder;
import kth.firestone.Game;
import kth.firestone.GameBuilder;
import kth.firestone.GameData;
import kth.firestone.card.Card;
import kth.firestone.minion.Minion;

public class MainTest {
	private final int STARTING_MANA = 1;
	
	@Test
	public void integrationTest() {
		GameBuilder gb = new FirestoneBuilder(new GameData());
		// set starting state
		for (int i = 1; i < 3; i++) {
			gb.setMaxHealth(i, 30)
			  .setDeck(i, "Imp", "Imp", "Boulderfist Ogre", 
				"Ironforge Rifleman", "Blackwing Corruptor", "Twilight Drake",
				"Twilight Drake", "Twilight Drake", "Twilight Drake", "Wild Pyromancer", "Frostbolt")
			  .setStartingMana(i, STARTING_MANA);
		}
		Game game = gb.build();
		game.start(game.getPlayers().get(0));	// start with player 1
		
		assertEquals(game.getPlayerInTurn().getId(), "1");
		
		String m1 = "Imp";
		//String m2 = "Boulderfist Ogre";
		
		//Play card "Imp"
		Card cardToPlay1 = game.getPlayerInTurn().getHand().get(0);
		assertTrue(game.isPlayCardValid(game.getPlayerInTurn(), cardToPlay1));
		game.playMinionCard(game.getPlayerInTurn(), cardToPlay1, 0);
		assertEquals(game.getPlayerInTurn().getActiveMinions().get(0).getName(),"Imp");
		assertEquals(game.getPlayerInTurn().getHand().size(), 3);
		assertEquals(30, game.getPlayerInTurn().getHero().getHealth());
		//Try and fail to play card "Boulderfist Ogre"
		Card cardToPlay2 = game.getPlayerInTurn().getHand().get(1);
		assertFalse(game.isPlayCardValid(game.getPlayerInTurn(), cardToPlay2));
		assertEquals(game.getPlayerInTurn().getHand().size(), 3); 
		
		//End turn
		game.endTurn(game.getPlayerInTurn());
		assertEquals(game.getPlayerInTurn().getId(), "2");
		assertEquals(game.getPlayerInTurn().getHero().getMana(), 2);
		assertEquals(4, game.getPlayerInTurn().getHand().size());
		
		//Play card "Imp"
		Card cardToPlay3 = game.getPlayerInTurn().getHand().get(0);
		assertTrue(game.isPlayCardValid(game.getPlayerInTurn(), cardToPlay3));
		game.playMinionCard(game.getPlayerInTurn(), cardToPlay3, 0);
		assertEquals(game.getPlayerInTurn().getActiveMinions().get(0).getName(),"Imp");
		assertEquals(game.getPlayerInTurn().getHand().size(), 3);
	
		//End turn
		game.endTurn(game.getPlayerInTurn());
		assertEquals(game.getPlayerInTurn().getId(), "1");
		assertEquals(game.getPlayerInTurn().getActiveMinions().get(0).getName(),m1);
		assertEquals(game.getPlayerInTurn().getHand().size(), 4); 
		assertEquals(game.getPlayerInTurn().getHero().getMana(), 3);

		//Attack "Imp" with "Imp"
		Minion minion1 = game.getPlayerInTurn().getActiveMinions().get(0);
		Minion minion2 = game.getPlayers().get(1).getActiveMinions().get(0);
		assertTrue(game.isAttackValid(game.getPlayerInTurn(), minion1.getId(), minion2.getId()));
		game.attack(game.getPlayerInTurn(), minion1.getId(), minion2.getId());
		
		assertEquals(game.getPlayers().get(1).getActiveMinions().size(), 0);
		assertEquals(game.getPlayers().get(0).getActiveMinions().size(), 0);
		
		//End turn
		game.endTurn(game.getPlayerInTurn());
		game.endTurn(game.getPlayerInTurn());
		assertEquals(game.getPlayerInTurn().getId(), "1");
		assertEquals(game.getPlayerInTurn().getHero().getMana(), 4);
		
		//Play a card "Imp"
		game.playMinionCard(game.getPlayerInTurn(), game.getPlayerInTurn().getHand().get(0), 0);
		Minion minionImp = game.getPlayerInTurn().getActiveMinions().get(0);
		assertEquals(minionImp.getName(), "Imp");
		
		
		//End turn
		game.endTurn(game.getPlayerInTurn());
		assertEquals(game.getPlayerInTurn().getId(), "2");
		assertEquals(game.getPlayerInTurn().getHero().getMana(), 4);
		
		//Play a card with Battlecry: Deal 1 damage and kill "Imp"
		assertEquals(game.getPlayerInTurn().getHand().get(2).getName(), "Ironforge Rifleman");
		game.playMinionCard(game.getPlayerInTurn(), game.getPlayerInTurn().getHand().get(2), 0,minionImp.getId());
		assertEquals(game.getPlayerInTurn().getActiveMinions().get(0).getName(), "Ironforge Rifleman");
		assertEquals(game.getPlayers().get(0).getActiveMinions().size(), 0);
		
		game.endTurn(game.getPlayerInTurn());
		assertEquals(game.getPlayerInTurn().getId(), "1");
		assertEquals(30, game.getPlayerInTurn().getHero().getHealth());
		
		//End turn to max out mana
		for(int i=0; i<14; ++i){
			game.endTurn(game.getPlayerInTurn());
		}
		assertEquals(game.getPlayerInTurn().getId(), "1");
		assertEquals(10, game.getPlayerInTurn().getHero().getMana());
		assertEquals(27, game.getPlayerInTurn().getHero().getHealth());
		assertEquals(28, game.getPlayers().get(1).getHero().getHealth());
		assertEquals(9, game.getPlayerInTurn().getHand().size());

		//Attack the hero
		Card cardAttackHero = game.getPlayerInTurn().getHand().get(0);
		assertEquals("Boulderfist Ogre", cardAttackHero.getName());
		game.playMinionCard(game.getPlayerInTurn(), cardAttackHero, 0);
		game.endTurn(game.getPlayerInTurn());
		game.endTurn(game.getPlayerInTurn());
		assertTrue(game.canAttack(game.getPlayerInTurn(), game.getPlayerInTurn().getActiveMinions().get(0)));
		game.attack(game.getPlayerInTurn(), game.getPlayerInTurn().getActiveMinions().get(0).getId(), "2");
		assertEquals(27-6, game.getPlayers().get(1).getHero().getHealth());
		assertEquals(26, game.getPlayers().get(0).getHero().getHealth());
		
		//Test play spell card and activate buff "After you cast a spell, deal 1 damage to ALL minions."
		//Play Wild Pyromancer
		game.playMinionCard(game.getPlayerInTurn(), game.getPlayerInTurn().getHand().get(6), 0);
		assertEquals("Wild Pyromancer", game.getPlayerInTurn().getActiveMinions().get(0).getName());
		
		//Play Ironforge Rifleman
		game.playMinionCard(game.getPlayerInTurn(), game.getPlayerInTurn().getHand().get(0), 0);
		assertEquals("Ironforge Rifleman", game.getPlayerInTurn().getActiveMinions().get(0).getName());
		
		//End turn
		game.endTurn(game.getPlayerInTurn());
		//Play Ironforge Rifleman
		game.playMinionCard(game.getPlayerInTurn(), game.getPlayerInTurn().getHand().get(0), 0);
		assertEquals("Ironforge Rifleman", game.getPlayerInTurn().getActiveMinions().get(1).getName());
		//End turn
		game.endTurn(game.getPlayerInTurn());
		assertEquals(25, game.getPlayers().get(0).getHero().getHealth());
		assertEquals(20, game.getPlayers().get(1).getHero().getHealth());
		
		//Play spell card and activate the buff
		assertEquals(2, game.getPlayers().get(0).getActiveMinions().get(0).getHealth());
		assertEquals("Ironforge Rifleman", game.getPlayers().get(1).getActiveMinions().get(1).getName());
		assertEquals(2, game.getPlayers().get(1).getActiveMinions().get(1).getHealth());
		game.playSpellCard(game.getPlayerInTurn(), game.getPlayerInTurn().getHand().get(5));
		assertEquals(1, game.getPlayers().get(0).getActiveMinions().get(0).getHealth());
		assertEquals(1, game.getPlayers().get(1).getActiveMinions().get(0).getHealth());
		assertEquals("Ironforge Rifleman", game.getPlayers().get(1).getActiveMinions().get(0).getName());	
		assertEquals(25, game.getPlayers().get(0).getHero().getHealth());
		assertEquals(20, game.getPlayers().get(1).getHero().getHealth());
	}
	
	


}
