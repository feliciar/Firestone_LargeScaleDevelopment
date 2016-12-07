package kth.firestone.minion;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import kth.firestone.Action;
import kth.firestone.buff.BuffDescription;
import kth.firestone.buff.BuffHandler;

public class FirestoneMinion implements Minion, Observer {
    private String id;
    private String name;
    private int health;
    private int maxHealth;
    private int originalHealth;
    private int originalAttack;
    private int attack;
    private MinionRace race;
    private List<MinionState> states;
    private String buff;
    private boolean sleepy = true;
    private boolean usedThisTurn = false;
    private BuffHandler buffHandler;

    public FirestoneMinion(String id, String name, int health, int originalHealth, int originalAttack, int attack,
            MinionRace race, List<MinionState> states, String buff, BuffHandler buffHandler) {
        this.id = id;
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.originalHealth = originalHealth;
        this.originalAttack = originalAttack;
        this.attack = attack;
        this.race = race;
        this.states = states;
        this.buff = buff;
        this.buffHandler = buffHandler;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public int getOriginalHealth() {
        return originalHealth;
    }

    @Override
    public int getOriginalAttack() {
        return originalAttack;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public MinionRace getRace() {
        return race;
    }

    @Override
    public List<MinionState> getStates() {
        return states;
    }

    @Override
    public boolean isSleepy() {
        return sleepy;
    }

    public boolean wasUsedThisTurn() {
        return usedThisTurn;
    }

    public String getBuff() {
        return buff;
    }

    public void setUsedThisTurn(boolean usedThisTurn) {
        this.usedThisTurn = usedThisTurn;
    }

    public void setSleepy(boolean sleepy) {
        this.sleepy = sleepy;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public String toString() {
        String raceString = (race == MinionRace.NONE) ? "" : race.toString();
        String sleepString = isSleepy() ? "Zzz" : "";
        return name + " (" + health + "/" + maxHealth + ", " + attack + "/" + originalAttack + ") " + raceString + " "
                + sleepString;
    }

    public void reduceHealth(int val) {
        health -= val;
    }

    @Override
    public void update(Observable observable, Object arg) {
        buffHandler.performBuff((Action) arg, this);
    }

    @Override
    public List<BuffDescription> getBuffDescriptions() {
        return new ArrayList<>();
    }

}
