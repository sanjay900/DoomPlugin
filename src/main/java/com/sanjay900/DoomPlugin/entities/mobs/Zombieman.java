package com.sanjay900.DoomPlugin.entities.mobs;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import com.sanjay900.DoomPlugin.DoomPlugin;
import com.sanjay900.DoomPlugin.attacks.Melee;
import com.sanjay900.DoomPlugin.attacks.RifleWeapon;
import com.sanjay900.DoomPlugin.entities.mobs.DoomEntity;

public class Zombieman extends DoomEntity{
		public Zombieman(DoomPlugin plugin, Location location) {
				super(plugin, location, EntityType.ZOMBIE, "Former Human", 20, 8, new RifleWeapon(), new Melee());

	}
	@Override
	public void entityLogic() {
		
		if (canEntitySeePlayer());
		
	}
	

}
