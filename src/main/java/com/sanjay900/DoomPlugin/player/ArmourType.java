package com.sanjay900.DoomPlugin.player;

import org.bukkit.Material;
/**
 * The ArmourType enumeration represents the type of armour a player is wearing.
 * There are two types of Armour:
 *  <li>{@link #NORMAL} - Normal Armour</li>
 *  <li>{@link #MEGA} - Mega Armour</li>
 *  @see #getArmourType(int)
 *  @see #getArmourType(Material)
 *  @see #getMaterial()
 */
public enum ArmourType {
/* Doom game armours
	The armour bonus (glowing helmet) increases armour by 1%, up to 200%. - Iron Helmet
	The armour (green vest) sets armour to 100%, if it is less. It absorbs one-third of damage. - Iron chest plate
	The megaarmour (blue vest) maxes out armour to 200%, if it is less. It absorbs one-half of damage. Diamond Chest Plate
	The megasphere (gray sphere) acts like the megaarmour, in addition to maxing out health.
 */
	//Define the Normal and Mega armour types
	/** 
	 * Normal Armour
	 * @see ArmourType
	 */
	NORMAL,
	/** 
	 * Mega Armour
	 * @see ArmourType
	 */
	MEGA;

	/**
     * Convert from an {@link ArmourType} to a {@link Material}
     * @return A Material representing this enum's {@link ArmourType}
     * @see ArmourType
     */
	Material getMaterial() {
		//Is this normal armour?
		if (this.equals(ArmourType.NORMAL)) {
			//Normal armour is represented by an Iron chestplate, so return one.
			return Material.IRON_CHESTPLATE;
		}
		if (this.equals(ArmourType.MEGA)) {
			//Mega armour is represented by an diamond chestplate, so return one.
			return Material.DIAMOND_CHESTPLATE;
		}
		return null;
		
	}
	/**
     * Convert from an Armour Percentage to an {@link ArmourType}
     * This will return {@link null} if the percentage you supply is less
     * than 1 or greater than 200.
     * @param percentage - The percentage you want to get the {@link ArmourType} from
     * @return An {@link ArmourType} instance representing the percentage. 
     * {@link null} if the supplied percentage doesn't represent anything.
     * @see ArmourType
     */
	static ArmourType getArmourType(int percentage) {
		//You dont have armour if your armour percentage is less than 1
		if (percentage < 1) {
			return null;
		} else
			//1 - 100% is normal armour
		if (percentage < 100) {
			return ArmourType.NORMAL;
		}else if (percentage < 200){
			//100% + is mega armour
			return ArmourType.MEGA;
		}
		//Its impossible to go past 200%, but if you do, your armour is invalid.
		return null;
	}
	/**
     * Convert from a Bukkit Material to an ArmourType
     * This will return null if the material you call this with isn't
     * armour.
     * @param material - The material you want to get the ArmourType from
     * @return An {@link ArmourType} instance representing the material
     * @see ArmourType
     */
	static ArmourType getArmourType(Material material) {
		if (material.equals(Material.IRON_CHESTPLATE)) {
			return ArmourType.NORMAL;
		}
		if (material.equals(Material.DIAMOND_CHESTPLATE)) {
			return ArmourType.MEGA;
		}
		return null;
		
	}
}
