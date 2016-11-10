package kth.firestone;

import java.util.List;

import kth.firestone.card.Card;
import kth.firestone.minion.Minion;
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
		
		Tester.test(game);
		

		
		
	}
	
	static class Tester {
		
		public static void test(Game game){
			//TODO replace this with a real game loop
			Player playerInTurn = game.getPlayerInTurn();
			System.out.println("Started game. ");
			printAllInfoOfPlayer(playerInTurn);
			Card cardToPlay1 = playerInTurn.getHand().get(0);
			Card cardToPlay3 = playerInTurn.getHand().get(1);
			System.out.println("Will play card 0: " + cardToPlay1);
			System.out.println("Will play card 1: " + cardToPlay3);
			if(game.isPlayCardValid(playerInTurn, cardToPlay1))
				game.playMinionCard(playerInTurn, cardToPlay1, 0);
			else
				System.out.println("Could not play card");
			if(game.isPlayCardValid(playerInTurn, cardToPlay3))
				game.playMinionCard(playerInTurn, cardToPlay3, 1);
			else
				System.out.println("Could not play card");
			printAllInfoOfPlayer(playerInTurn);
			game.endTurn(playerInTurn);
			System.out.println("End turn"+"\n");
			
			playerInTurn = game.getPlayerInTurn();
			printAllInfoOfPlayer(playerInTurn);
			Card cardToPlay2 = playerInTurn.getHand().get(2);
			System.out.println("Will play card 2: "+cardToPlay2);
			if(game.isPlayCardValid(playerInTurn, cardToPlay2))
				game.playMinionCard(playerInTurn, cardToPlay2, 0);
			else
				System.out.println("Could not play card");
			printAllInfoOfPlayer(playerInTurn);
			game.endTurn(playerInTurn);
			System.out.println("End turn"+"\n");

			
			//Attack another minion
			printAllInfoOfPlayer(game.getPlayerInTurn());
			Minion m1 = game.getPlayers().get(0).getActiveMinions().get(0);
			Minion m2 = game.getPlayers().get(1).getActiveMinions().get(0);
			System.out.println("Will attack "+m2.getName()+ " with "+ m1.getName());
			if(game.isAttackValid(game.getPlayerInTurn(), m1.getId(), m2.getId()))
				game.attack(game.getPlayerInTurn(), m1.getId(), m2.getId());
			else
				System.out.println("Could not attack");
			printAllInfoOfPlayer(game.getPlayerInTurn());
			game.endTurn(game.getPlayerInTurn());
			System.out.println("End turn"+"\n");
			
			//Attack the hero
			printAllInfoOfPlayer(game.getPlayerInTurn());
			game.endTurn(game.getPlayerInTurn());
			System.out.println("End turn"+"\n");
			
			printAllInfoOfPlayer(game.getPlayerInTurn());
			Minion m3 = game.getPlayers().get(0).getActiveMinions().get(0);
			System.out.println("Will attack the other player's hero with "+ m3);
			assert(game.getPlayerInTurn().getId().equals("1"));
			if(game.isAttackValid(game.getPlayerInTurn(), m3.getId(), "2"))
				game.attack(game.getPlayerInTurn(), m3.getId(), "2");
			else
				System.out.println("Could not attack");
			printAllInfoOfPlayer(game.getPlayerInTurn());
			
			System.out.println();
			printAllInfoOfPlayer(game.getPlayers().get(1));
			

			
			
			
		}

		public static void printAllInfoOfPlayer(Player playerInTurn){
			System.out.println("Player in turn: "+playerInTurn.getId());
			System.out.println(playerInTurn.getHero());
			List<Card> hand = playerInTurn.getHand();
			System.out.print("Player's hand: ");
			for(Card c : hand)
				System.out.print(c+", ");
			System.out.println("");
			
			System.out.print("Cards on the board: ");
			for(Minion m : playerInTurn.getActiveMinions())
				System.out.print(m+", ");
			System.out.println("");

		}
        
    }
	
	

}
