package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import kth.firestone.AttackHandler;
import kth.firestone.FirestoneBuilder;
import kth.firestone.FirestoneGame;
import kth.firestone.Game;
import kth.firestone.GameBuilder;
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
import kth.firestone.player.Player;

public class FirestoneGameTest {
	
	FirestoneGame fg;
	List<Player> players;
	
	public FirestoneGameTest() {
		GameBuilder gb = new FirestoneBuilder(new GameData());
		// set starting state
		for (int i = 1; i < 3; i++) {
			gb.setMaxHealth(i, 30)
			  .setDeck(i, "Imp", "War Golem", "Boulderfist Ogre", 
				"Ironforge Rifleman", "Blackwing Corruptor", "Twilight Drake")
			  .setStartingMana(i, 10);
		}
		fg = (FirestoneGame) gb.build();
		
		
		players = new ArrayList<>();
		players.add(new GamePlayer("1", new FirestoneHero("1", 30)));
		players.add(new GamePlayer("2", new FirestoneHero("2", 30)));
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
		// TODO Implement test
	}

	@Test
	public void testStartPlayer() {
		// TODO Implement test
	}
	
	@Test
	public void testIsActive() {
		// TODO Implement test
	}

}
