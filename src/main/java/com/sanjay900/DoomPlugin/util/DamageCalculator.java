package com.sanjay900.DoomPlugin.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.sanjay900.DoomPlugin.DoomPlugin;
import com.sanjay900.DoomPlugin.player.ArmourType;
import com.sanjay900.DoomPlugin.player.DoomPlayer;

//This class handles calculating the damage to a player with respect to how doom handles armour and damage
public class DamageCalculator {
	//An instance of the doom plugin
	private DoomPlugin plugin;
	//Initialize this class, called with an instance of the doom plugin
	public DamageCalculator(DoomPlugin plugin) {
		//store this instance for later usage
		this.plugin = plugin;
	}
	/**
	 * Check if a player is wearing armour 
	 * @param player - the {@link Player}.
	 * @return check if the player is wearing armour.
	 */
	public boolean hasArmour(Player player) {
		//Loop through all the armour slots
	for (ItemStack stack : player.getInventory().getArmorContents()) {
		//If a slot isn't air, the player has some sort of armour on
	    if (stack.getType() != Material.AIR) {
	    	//This slot isn't air, the player is wearing armour
	    return true;
	    }
	}
	//Since we haven't returned yet, the player can't be wearing armour.
	return false;
	}
	/**
	 * Calculate the damage caused to a player with respect to doom calculations
	 * @param damage - the raw damage to the player, without doom calculations. 
	 * @param player - the {@link Player}.
	 * @return the damage to the player with respect to doom calculations.
	 */
	public double calculateDamage (double damage, DoomPlayer player) {
		//Calculate the damage with respect for armour. This function may do more things in the future.
		double calcDamage = calculateArmour(damage, player);
		//return the damage calculated
		return calcDamage;

	}
	/**
	 * Calculate the damage caused to a player with respect to doom armour. Also wear our the armour on the player
	 * @param damage - the raw damage to the player, without armour. 
	 * @param player - the {@link Player}.
	 * @return the damage to the player with respect to armour.
	 */
	//Calculate the damage removed by Armour, and wear out the Armour too.
	private double calculateArmour(double damage, DoomPlayer player) {
		//Does the player have armour?
		if (player.hasArmour()) {
			//How much damage does the armour save?
			double damageSaved = damage;
			//With normal armour, we save 1 third of the total damage
			if (player.getArmourType() == ArmourType.NORMAL) {
				damageSaved = damage / 3;
			}
			//With mega armour, we save 1 half of the total damage
			if (player.getArmourType() == ArmourType.MEGA) {
				damageSaved = damage / 2;
			}
			//If the armour is used up, clear it
			if (player.getArmourPercentage() < damageSaved) {
				player.setArmourPercentage(0);
			} else {
				//set the armour to the calculated value
				player.setArmourPercentage(player.getArmourPercentage()-(int)damageSaved);
			}
			//return the real damage
			return damage - damageSaved;
		}
		//We have no armour, this function just returns how much damage it was called with
		return damage;
	}
}
//The version of this from the doom source code
/* C doom source code [p_inter.c] line 854:
 * https://github.com/id-Software/DOOM/blob/77735c3ff0772609e9c8d29e3ce2ab42ff54d20b/linuxdoom-1.10/p_inter.c
	  if (player->armortype)
	{
	    if (player->armortype == 1)
		saved = damage/3;
	    else
		saved = damage/2;

	    if (player->armorpoints <= saved)
	    {
		// armor is used up
		saved = player->armorpoints;
		player->armortype = 0;
	    }
	    player->armorpoints -= saved;
	    damage -= saved;
	}
	player->health -= damage;
	if (player->health < 0)
	    player->health = 0;
 */
