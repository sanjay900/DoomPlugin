package com.sanjay900.DoomPlugin.attacks;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
//This implements DoomAttack, so you can treat it as a normal attack instead of having to
//directly cast to it. This allows you to store multiple attacks in one array.
//This specific attack gets the enemy to hit the player or the air at the location
public class Melee implements DoomAttack 
{
	//This tells the npc to attack the location
	@Override
	public void attack(NPC from, Location to) {
		from.getNavigator().setTarget(to);
	}
	//this tells the npc to attack the entity, and the trus means to be aggressive
	@Override
	public void attack(NPC from, Entity to) {
		from.getNavigator().setTarget(to, true);
	}

}
