package kth.firestone.hero;

public interface HeroPower {

	/**
	 * @return the name of the hero power.
	 */
	public String getName();
	
	/**
	 * @return the mana cost of the hero power.
	 */
	public int getManaCost();
	
	/**
	 * @return the original mana cost of the hero power.
	 */
	public int getOriginalManaCost();
	
}
