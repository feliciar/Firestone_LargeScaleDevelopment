package kth.firestone.hero;

import java.util.List;

/**
 * Describes a hero that a player can play with.
 */
public interface Hero {
    /**
     * @return an unique id in the context of a {@link kth.firestone.Game}.
     */
    public String getId();

    /**
     * @return the name of the hero.
     */
    public String getName();

    /**
     * @return the armor of the hero.
     */
    public int getArmor();

    /**
     * @return the attack of the hero.
     */
    public int getAttack();

    /**
     * @return the maximum health that this hero can have.
     */
    public int getMaxHealth();

    /**
     * @return the current health of the hero.
     */
    public int getHealth();

    /**
     * @return the maximum mana that this hero can have.
     */
    public int getMaxMana();

    /**
     * @return the current mana of the hero.
     */
    public int getMana();
    
    /**
     * @return the states that the hero currently have.
     */
    public List<HeroState> getStates();

    /**
     * @return the hero power of the hero.
     */
    public HeroPower getHeroPower();
    
    /**
     * @return the weapon of the hero.
     */
    public Weapon getWeapon();

}

