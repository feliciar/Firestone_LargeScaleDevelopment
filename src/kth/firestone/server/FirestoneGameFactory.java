package kth.firestone.server;

import kth.firestone.FirestoneBuilder;
import kth.firestone.Game;
import kth.firestone.GameBuilder;
import kth.firestone.GameData;

public class FirestoneGameFactory implements GameFactory {
	
	public static void main(String[] args) {
		GameFactory gf = new FirestoneGameFactory();
		ServerFactory sf = new ServerFactory(gf);
		sf.createServer().start();
	}

	@Override
	public Game createGame() {

		GameBuilder gb = new FirestoneBuilder(new GameData());
		// set starting state
		int startingMana = 0;
		for (int i = 1; i < 3; i++) {
			gb.setMaxHealth(i, 30)
			  .setDeck(i, "Imp", "War Golem", "Boulderfist Ogre", 
				"Ironforge Rifleman", "Blackwing Corruptor", "Twilight Drake",
				"Edwin VanCleef", "Midnight Drake", "Wild Pyromancer", "Frostbolt", "Shadowstep", 
				"Backstab", "Equality", "Shadowform",
				"Alexstrasza", "Acolyte of Pain", "Lorewalker Cho")
			  .setStartingMana(i, startingMana);
		}
		Game game = gb.build();
		return game;
		//game.start();
	}
	
}
