package kth.firestone.minion;

import java.util.List;

import kth.firestone.buff.BuffDescription;

/**
 * Describes a minion.
 */
public interface Minion {
    /**
     * @return an unique id in the context of a {@link kth.firestone.Game}.
     */
    public String getId();

    /**
     * @return the name of the minion.
     */
    public String getName();

    /**
     * @return the current health of the minion.
     */
    public int getHealth();

    /**
     * @return the current maximum health of the minion.
     */
    public int getMaxHealth();

    /**
     * Note that the original health and max health of a minion is the same.
     * 
     * @return the original health of the minion.
     */
    public int getOriginalHealth();

    /**
     * @return the original attack of the minion.
     */
    public int getOriginalAttack();

    /**
     * @return the attack of a the minion.
     */
    public int getAttack();

    /**
     * @return the race of the minion.
     */
    public MinionRace getRace();

    /**
     * @return the states of the minion.
     */
    public List<MinionState> getStates();

    /**
     * @return the descriptions of buffs of the minion.
     */
    public List<BuffDescription> getBuffDescriptions();

    /**
     * Tells if the minion is sleepy, i.e., it cannot attack. This usually
     * happens when a minion is played to the board for the first time.
     * 
     * @return if the minion is sleepy or not (if it can attack).
     */
    public boolean isSleepy();
}
