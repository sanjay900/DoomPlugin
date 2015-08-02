package com.sanjay900.DoomPlugin.levelElements;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
/*
 Damaging Floor Types
	The following damaging floor types are found in Doom:
	Sector type	Damage per second	Doom source code comment	Other effects
	7	5%	Nukage damage
	5	10%	Hellslime damage
	16	20%	Super hellslime damage
	4	20%	Strobe hurt	Light blinks each 0.5 second
	11	drops steadily until below 11%; Same as Type 16		
	Level ends when health is below 11%. God mode cheat, if player has activated it, is nullified.
 */


import com.sanjay900.DoomPlugin.DoomPlugin;
import com.sanjay900.DoomPlugin.player.DoomPlayer;

/**
 * This class handles Damaging players standing on Damaging Floors 
 */
public class DoomDamageFloor implements Runnable
{	
		private DoomPlugin plugin;
		public DoomDamageFloor(DoomPlugin plugin) {
				this.plugin = plugin;
	}
		@Override
	public void run() {
				for (DoomPlayer p : plugin.doomPlayers) {
						Block under = p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);
									if (under.getType()==Material.STAINED_GLASS && under.getData() == (byte)5) {
																p.damage(plugin.doomDamageCalculator.calculateDamage(5, p));
			}
		}
	}
}
