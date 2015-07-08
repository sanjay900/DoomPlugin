package com.sanjay900.DoomPlugin.levelElements;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
//Doom Wiki Information on Damaging Floors:
//http://doom.wikia.com/wiki/Damaging_floor
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
	//a reference to the DoomPlugin class. 
	private DoomPlugin plugin;
	//Create an instance of this class, call with a reference to the DoomPlugin class.
	public DoomDamageFloor(DoomPlugin plugin) {
		//Set the instance of the plugin to the supplied variable
		this.plugin = plugin;
	}
	//This function gets called automatically as the class extends runnable.
	@Override
	public void run() {
		//Loop through all players in a game
		for (DoomPlayer p : plugin.doomPlayers) {
			//Get the block a player is standing on
			Block under = p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);
			//Nukage - Sector type 7
			//Is this block Lime Stained Glass (Minecraft uses the integers 0 -15 to represent different colours. 5 is lime.
			if (under.getType()==Material.STAINED_GLASS && under.getData() == (byte)5) {
				//calculate the damage to the player
				//As stated above, 5% of health is taken by Nukage - in this case, lime stained glass
				//Calculate 5% with respect to armour, then hurt the player
				p.damage(plugin.doomDamageCalculator.calculateDamage(5, p));
			}
		}
	}
}
