package kth.firestone;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.UUID;

import kth.firestone.buff.BuffHandler;
import kth.firestone.buff.BuffMethods;
import kth.firestone.card.Card;
import kth.firestone.card.FirestoneCard;
import kth.firestone.card.PlayCardHandler;
import kth.firestone.deck.Deck;
import kth.firestone.deck.FirestoneDeck;
import kth.firestone.hero.FirestoneHero;
import kth.firestone.hero.FirestoneHeroPower;
import kth.firestone.hero.HeroPower;
import kth.firestone.hero.HeroPowerHandler;
import kth.firestone.player.GamePlayer;
import kth.firestone.player.Player;

public class FirestoneBuilder implements GameBuilder {
    private List<Player> players;
    private GameData gameData;
    private final int HERO_HEALTH = 30;
    private final String PLAYER_1_ID = "1";
    private final String PLAYER_2_ID = "2";

    public FirestoneBuilder(GameData gameData) {
        this.gameData = gameData;
        this.players = createPlayers();
    }

    @Override
    public GameBuilder setDeck(int playerIndex, List<String> cardNames) {
        if (playerIndex < 1) {
            System.err.println("Error: The given ID of the player must be 1 or higher.");
            System.exit(1);
        }
        Deque<Card> cards = new ArrayDeque<>();

        GamePlayer p = (GamePlayer) players.get(playerIndex - 1);
        HashMap<String, HashMap<String, String>> t = gameData.getCards();
        for (String s : cardNames) {
            HashMap<String, String> data = t.get(s);

            Card card = new FirestoneCard(UUID.randomUUID().toString(), data.get("name"), data.get("health"),
                    data.get("attack"), data.get("mana"), data.get("type"), data.get("buff"), data.get("race"));
            cards.add(card);
        }
        Deck deck = new FirestoneDeck(cards);
        p.setDeck(deck);

        return this;
    }

    @Override
    public GameBuilder setDeck(int playerIndex, int numberOfCards, String cardName) {
        if (playerIndex < 1) {
            System.err.println("Error: The given ID of the player must be 1 or higher.");
            System.exit(1);
        }
        Deque<Card> cards = new ArrayDeque<>();

        GamePlayer p = (GamePlayer) players.get(playerIndex - 1);
        HashMap<String, HashMap<String, String>> t = gameData.getCards();
        HashMap<String, String> data = t.get(cardName);

        for (int i = 0; i < numberOfCards; i++) {
            Card card = new FirestoneCard(UUID.randomUUID().toString(), data.get("name"), data.get("health"),
                    data.get("attack"), data.get("mana"), data.get("type"), data.get("buff"), data.get("race"));
            cards.add(card);
        }
        Deck deck = new FirestoneDeck(cards);
        p.setDeck(deck);

        return this;
    }

    @Override
    public GameBuilder setDeck(int playerIndex, String... cardNames) {
        if (playerIndex < 1) {
            System.err.println("Error: The given ID of the player must be 1 or higher.");
            System.exit(1);
        }

        Deque<Card> cards = new ArrayDeque<>();

        GamePlayer p = (GamePlayer) players.get(playerIndex - 1);
        HashMap<String, HashMap<String, String>> t = gameData.getCards();
        for (String s : cardNames) {
            HashMap<String, String> data = t.get(s);

            Card card = new FirestoneCard(UUID.randomUUID().toString(), data.get("name"), data.get("health"),
                    data.get("attack"), data.get("mana"), data.get("type"), data.get("buff"), data.get("race"));
            cards.add(card);
        }

        Deck deck = new FirestoneDeck(cards);
        p.setDeck(deck);

        return this;
    }

    @Override
    public GameBuilder setMaxHealth(int playerIndex, int maxHealth) {
        if (playerIndex < 1) {
            System.exit(1);
        }

        GamePlayer p = (GamePlayer) getPlayers().get(playerIndex - 1);
        ((FirestoneHero) p.getHero()).setMaxHealth(maxHealth);
        ((FirestoneHero) p.getHero()).setHealth(maxHealth);

        return this;
    }

    @Override
    public GameBuilder setActiveMinions(int playerIndex, List<String> activeMinionNames) {
        // TODO Implement when we want to start the game in the middle
        return null;
    }

    @Override
    public GameBuilder setActiveMinions(int playerIndex, int numberOfMinions, String cardName) {
        // TODO Implement when we want to start the game in the middle
        return null;
    }

    @Override
    public GameBuilder setActiveMinions(int playerIndex, String... activeMinionNames) {
        // TODO Implement when we want to start the game in the middle
        return null;
    }

    @Override
    public GameBuilder setStartingMana(int playerIndex, int startingMana) {
        if (playerIndex < 1) {
            System.exit(1);
        }

        GamePlayer p = (GamePlayer) players.get(playerIndex - 1);
        ((FirestoneHero) p.getHero()).setMaxMana(startingMana);
        ((FirestoneHero) p.getHero()).setMana(startingMana);

        return this;
    }

    public GameBuilder setHero(int playerIndex, String heroName, String heroPower, int manaCost) {
        if (playerIndex < 1) {
            System.exit(1);
        }

        GamePlayer p = (GamePlayer) getPlayers().get(playerIndex - 1);
        ((GamePlayer) p).setHero(
                new FirestoneHero(p.getId(), heroName, HERO_HEALTH, new FirestoneHeroPower(heroPower, "" + manaCost)));
        return this;
    }

    @Override
    public Game build() {
        Observable observable = new FirestoneObservable(players);
        DamageHandler damageHandler = new DamageHandler(observable);
        BuffMethods buffMethods = new BuffMethods(damageHandler);
        BuffHandler buffHandler = new BuffHandler(buffMethods);
        HeroPowerHandler heroPowerHandler = new HeroPowerHandler(buffHandler);
        return new FirestoneGame(players, new PlayCardHandler(gameData, buffHandler, observable),
                new AttackHandler(players, damageHandler), heroPowerHandler, observable);
    }

    private List<Player> createPlayers() {
        List<Player> players = new ArrayList<>();
        HeroPower heroPowerPlayer1 = new FirestoneHeroPower("Life Tap", "2");
        HeroPower heroPowerPlayer2 = new FirestoneHeroPower("Mind Spike", "2");
        Player player1 = new GamePlayer(PLAYER_1_ID,
                new FirestoneHero(PLAYER_1_ID, "Gul'dan", HERO_HEALTH, heroPowerPlayer1));
        Player player2 = new GamePlayer(PLAYER_2_ID,
                new FirestoneHero(PLAYER_2_ID, "Anduin Wrynn", HERO_HEALTH, heroPowerPlayer2));
        players.add(player1);
        players.add(player2);

        return players;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

}
