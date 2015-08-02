package com.sanjay900.DoomPlugin.attacks;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
public class FireballWeapon implements DoomAttack 
{
						@Override
	public void attack(NPC from, Location to) {
		from.getBukkitEntity().launchProjectile(Fireball.class, to.toVector().subtract(from.getEntity().getLocation().toVector()));
	}
			@Override
	public void attack(NPC from, Entity to) {
		attack(from,to.getLocation());
	}

}
