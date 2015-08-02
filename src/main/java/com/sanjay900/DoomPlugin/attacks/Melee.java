package com.sanjay900.DoomPlugin.attacks;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
public class Melee implements DoomAttack 
{
		@Override
	public void attack(NPC from, Location to) {
		from.getNavigator().setTarget(to);
	}
		@Override
	public void attack(NPC from, Entity to) {
		from.getNavigator().setTarget(to, true);
	}

}
