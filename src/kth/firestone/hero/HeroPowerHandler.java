package kth.firestone.hero;

import java.util.List;

import kth.firestone.Action;
import kth.firestone.Action.Type;
import kth.firestone.Event;
import kth.firestone.buff.BuffHandler;
import kth.firestone.player.Player;

public class HeroPowerHandler {

    private BuffHandler buffHandler;

    public HeroPowerHandler(BuffHandler buffHandler) {
        this.buffHandler = buffHandler;
    }

    /**
     * Determines if the hero power can be used without a target.
     */
    public boolean isUseOfHeroPowerValid(List<Player> players, Player player) {
        return isUseOfHeroPowerValid(players, player, null);
    }

    /**
     * Determines if the hero power can be used with a target.
     */
    public boolean isUseOfHeroPowerValid(List<Player> players, Player player, String targetId) {
        HeroPower power = player.getHero().getHeroPower();
        if (power.getManaCost() > player.getHero().getMana()) {
            return false;
        }
        if (((FirestoneHero) player.getHero()).hasUsedHeroPower()) {
            return false;
        }
        Action action = new Action(players, player.getId(), null, null, -1, targetId, null, Type.HERO_POWER);
        return buffHandler.isPerformHeroPowerValid(action);
    }

    /**
     * Use the hero power without a target.
     */
    public List<Event> useHeroPower(List<Player> players, Player player) {
        return useHeroPower(players, player, null);
    }

    /**
     * Use the hero power with a target.
     */
    public List<Event> useHeroPower(List<Player> players, Player player, String targetId) {
        ((FirestoneHero) player.getHero()).setHasUsedHeroPower(true);
        int mana = player.getHero().getMana() - player.getHero().getHeroPower().getManaCost();
        ((FirestoneHero) player.getHero()).setMana(mana);
        Action action = new Action(players, player.getId(), null, null, -1, targetId, null, Type.HERO_POWER);
        buffHandler.performHeroPower(action);
        return null;
    }

}
