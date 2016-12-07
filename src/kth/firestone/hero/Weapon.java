package kth.firestone.hero;

public interface Weapon {

    /**
     * @return the attack of the weapon.
     */
    public int getAttack();

    /**
     * @return the durability of the weapon.
     */
    public int getDurability();

    /**
     * @return the max durability of the weapon.
     */
    public int getMaxDurability();

    /**
     * @return the name of the weapon.
     */
    public String getName();

    /**
     * @return the original attack of the weapon.
     */
    public int getOriginalAttack();

    /**
     * @return the original durability of the weapon.
     */
    public int getOriginalDurability();

}
