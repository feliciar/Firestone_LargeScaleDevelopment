package kth.firestone.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        String[] deckStrings = { "Imp", "Imp", "Imp", "Imp", "Imp", "War Golem", "War Golem", "Boulderfist Ogre",
                "Boulderfist Ogre", "Ironforge Rifleman", "Ironforge Rifleman", "Ironforge Rifleman",
                "Ironforge Rifleman", "Blackwing Corruptor", "Blackwing Corruptor", "Twilight Drake", "Twilight Drake",
                "Edwin VanCleef", "Edwin VanCleef", "Midnight Drake", "Wild Pyromancer", "Frostbolt", "Frostbolt",
                "Shadowstep", "Backstab", "Equality", "Shadowform", "Alexstrasza", "Acolyte of Pain",
                "Lorewalker Cho" };
        List<List<String>> decks = new ArrayList<>();
        decks.add(new ArrayList<>(Arrays.asList(deckStrings)));
        decks.add(new ArrayList<>(Arrays.asList(deckStrings)));
        Collections.shuffle(decks.get(0));
        Collections.shuffle(decks.get(1));

        for (int i = 1; i < 3; i++) {
            gb.setMaxHealth(i, 30).setDeck(i, decks.get(i - 1)).setStartingMana(i, startingMana);
        }

        ((FirestoneBuilder) gb).setHero(1, "Gul'dan", "Life Tap", 2);
        ((FirestoneBuilder) gb).setHero(2, "Anduin Wrynn", "Lesser Heal", 2);
        Game game = gb.build();
        return game;
    }

}
