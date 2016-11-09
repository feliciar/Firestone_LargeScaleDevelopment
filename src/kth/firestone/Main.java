package kth.firestone;

import kth.firestone.card.Card;
import kth.firestone.player.Player;

public class Main {

	public static void main(String[] args) {
		GameBuilder gb = new FirestoneBuilder(new GameData());
		// set starting state
		for (int i = 1; i < 3; i++) {
			gb.setMaxHealth(i, 30)
			  .setDeck(i, "Imp", "War Golem", "Boulderfist Ogre", 
				"Ironforge Rifleman", "Blackwing Corruptor", "Twilight Drake")
			  .setStartingMana(i, 10);
		}
		Game game = gb.build();
		game.start(); //TODO do so start deals cards to the players, and sets the starting player
		
		//TODO replace this with a real game loop
		Player playerInTurn = game.getPlayerInTurn();
		Card cardToPlay1 = playerInTurn.getHand().get(0);
		Card cardToPlay3 = playerInTurn.getHand().get(1);
		game.isPlayCardValid(playerInTurn, cardToPlay1);
		game.playMinionCard(playerInTurn, cardToPlay1, 0);
		game.isPlayCardValid(playerInTurn, cardToPlay3);
		game.playMinionCard(playerInTurn, cardToPlay3, 1);
		game.endTurn(playerInTurn);
		
		playerInTurn = game.getPlayerInTurn();
		Card cardToPlay2 = playerInTurn.getHand().get(2);
		game.isPlayCardValid(playerInTurn, cardToPlay2);
		game.playMinionCard(playerInTurn, cardToPlay2, 0);
		game.endTurn(playerInTurn);
		
		//Attack another minion
		game.attack(playerInTurn, cardToPlay1.getId(), cardToPlay2.getId());
		game.endTurn(playerInTurn);
		
		//Attack another player

		
		
	}

}
