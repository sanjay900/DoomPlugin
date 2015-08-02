package com.sanjay900.DoomPlugin.attacks;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public interface DoomAttack {
				void attack(NPC from, Location to);
		void attack(NPC from, Entity to);
}
