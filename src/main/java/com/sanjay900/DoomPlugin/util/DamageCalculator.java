package com.sanjay900.DoomPlugin.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.sanjay900.DoomPlugin.DoomPlugin;
import com.sanjay900.DoomPlugin.player.ArmourType;
import com.sanjay900.DoomPlugin.player.DoomPlayer;

public class DamageCalculator {
		private DoomPlugin plugin;
		public DamageCalculator(DoomPlugin plugin) {
				this.plugin = plugin;
	}
	/**
	 * Check if a player is wearing armour 
	 * @param player - the {@link Player}.
	 * @return check if the player is wearing armour.
	 */
	public boolean hasArmour(Player player) {
			for (ItemStack stack : player.getInventory().getArmorContents()) {
			    if (stack.getType() != Material.AIR) {
	    		    return true;
	    }
	}
		return false;
	}
	/**
	 * Calculate the damage caused to a player with respect to doom calculations
	 * @param damage - the raw damage to the player, without doom calculations. 
	 * @param player - the {@link Player}.
	 * @return the damage to the player with respect to doom calculations.
	 */
	public double calculateDamage (double damage, DoomPlayer player) {
				double calcDamage = calculateArmour(damage, player);
				return calcDamage;

	}
	/**
	 * Calculate the damage caused to a player with respect to doom armour. Also wear our the armour on the player
	 * @param damage - the raw damage to the player, without armour. 
	 * @param player - the {@link Player}.
	 * @return the damage to the player with respect to armour.
	 */
		private double calculateArmour(double damage, DoomPlayer player) {
				if (player.hasArmour()) {
						double damageSaved = damage;
						if (player.getArmourType() == ArmourType.NORMAL) {
				damageSaved = damage / 3;
			}
						if (player.getArmourType() == ArmourType.MEGA) {
				damageSaved = damage / 2;
			}
						if (player.getArmourPercentage() < damageSaved) {
				player.setArmourPercentage(0);
			} else {
								player.setArmourPercentage(player.getArmourPercentage()-(int)damageSaved);
			}
						return damage - damageSaved;
		}
				return damage;
	}
}
/* C doom source code [p_inter.c] line 854:
 * https:	  if (player->armortype)
	{
	    if (player->armortype == 1)
		saved = damage/3;
	    else
		saved = damage/2;

	    if (player->armorpoints <= saved)
	    {
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
