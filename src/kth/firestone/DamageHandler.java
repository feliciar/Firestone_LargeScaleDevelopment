package kth.firestone;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import kth.firestone.Action.Type;
import kth.firestone.hero.FirestoneHero;
import kth.firestone.hero.Hero;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.minion.Minion;

public class DamageHandler {
    private Observable observable;

    public DamageHandler(Observable observable) {
        this.observable = observable;
    }

    /**
     * Deals damage when a minion and hero battles.
     */
    public void dealDamageToMinionAndHero(Minion attacker, Hero target) {
        ((FirestoneHero) target).reduceHealth(attacker.getAttack());
        ((FirestoneMinion) attacker).reduceHealth(target.getAttack());

        Action action1 = new Action(((FirestoneObservable) observable).getPlayers(),
                ((FirestoneObservable) observable).getCurrentPlayerId(), null, null, -1, null, attacker.getId(),
                Type.DAMAGE);
        Action action2 = new Action(((FirestoneObservable) observable).getPlayers(),
                ((FirestoneObservable) observable).getCurrentPlayerId(), null, null, -1, null, target.getId(),
                Type.DAMAGE);
        observable.notifyObservers(action1);
        observable.notifyObservers(action2);

        // Remove the attacker if it has died
        removeDeadMinion(attacker);
    }

    /**
     * Deals damage to minions when two minions battle.
     */
    public void dealDamageToTwoMinions(Minion attacker, Minion target) {
        ((FirestoneMinion) target).reduceHealth(attacker.getAttack());
        ((FirestoneMinion) attacker).reduceHealth(target.getAttack());

        Action action1 = new Action(((FirestoneObservable) observable).getPlayers(),
                ((FirestoneObservable) observable).getCurrentPlayerId(), null, null, -1, null, attacker.getId(),
                Type.DAMAGE);
        Action action2 = new Action(((FirestoneObservable) observable).getPlayers(),
                ((FirestoneObservable) observable).getCurrentPlayerId(), null, null, -1, null, target.getId(),
                Type.DAMAGE);
        observable.notifyObservers(action1);
        observable.notifyObservers(action2);

        // Remove the two minions if they died
        removeDeadMinions(((FirestoneObservable) observable).getPlayers().get(0).getActiveMinions());
        removeDeadMinions(((FirestoneObservable) observable).getPlayers().get(1).getActiveMinions());
    }

    /**
     * Deals specified amount of damage to a minion.
     */
    public void dealDamageToOneMinion(Minion minion, int damage) {
        ((FirestoneMinion) minion).reduceHealth(damage);

        Action action = new Action(((FirestoneObservable) observable).getPlayers(),
                ((FirestoneObservable) observable).getCurrentPlayerId(), null, null, -1, null, minion.getId(),
                Type.DAMAGE);
        observable.notifyObservers(action);

        // Remove the minion if it died
        removeDeadMinion(minion);
    }

    /**
     * Deals specified amount of damage to a hero.
     */
    public void dealDamageToHero(Hero hero, int damage) {
        ((FirestoneHero) hero).reduceHealth(damage);

        Action action = new Action(((FirestoneObservable) observable).getPlayers(),
                ((FirestoneObservable) observable).getCurrentPlayerId(), null, null, -1, null, hero.getId(),
                Type.DAMAGE);
        observable.notifyObservers(action);
    }

    /**
     * Deals one damage to several minions.
     */
    public void dealOneDamageToSeveralMinions(List<Minion> minions) {
        for (Minion m : minions) {
            ((FirestoneMinion) m).reduceHealth(1);
        }

        for (Minion m : minions) {
            Action action = new Action(((FirestoneObservable) observable).getPlayers(),
                    ((FirestoneObservable) observable).getCurrentPlayerId(), null, null, -1, null, m.getId(),
                    Type.DAMAGE);
            observable.notifyObservers(action);
        }

        // Remove the minions that may have died
        removeDeadMinions(((FirestoneObservable) observable).getPlayers().get(0).getActiveMinions());
        removeDeadMinions(((FirestoneObservable) observable).getPlayers().get(1).getActiveMinions());
    }

    /**
     * Help method to find all dead minions in a list.
     */
    public List<Minion> findDeadMinions(List<Minion> minions) {
        List<Minion> deadMinions = new ArrayList<Minion>();
        for (Minion m : minions) {
            if (m.getHealth() <= 0) {
                deadMinions.add(m);
            }
        }
        return deadMinions;
    }

    /**
     * Removes all dead minions in a list of minions.
     */
    public void removeDeadMinions(List<Minion> minions) {
        List<Minion> deadMinions = findDeadMinions(minions);
        for (Minion dead : deadMinions) {
            minions.remove(dead);
            removeMinionAsObserver(dead);
        }

    }

    /**
     * Checks if a minion is dead.
     */
    public boolean isDead(Minion minion) {
        if (minion.getHealth() <= 0) {
            return true;
        }
        return false;
    }

    /**
     * Removes a dead minion.
     */
    public void removeDeadMinion(Minion minion) {
        if (isDead(minion)) {
            List<Minion> minions = ((FirestoneObservable) observable).getPlayers().get(0).getActiveMinions();
            if (minions.contains(minion)) {
                minions.remove(minion);
            } else {
                ((FirestoneObservable) observable).getPlayers().get(1).getActiveMinions().remove(minion);
            }
            removeMinionAsObserver(minion);
        }
    }

    /**
     * Removes a minion as an observer.
     */
    public void removeMinionAsObserver(Minion minion) {
        ((FirestoneObservable) observable).deleteObserver((FirestoneMinion) minion);
    }
}
