package com.sanjay900.DoomPlugin.attacks;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

//This class is just a basic interface that we can add onto to allow us to create doom attacks.
public interface DoomAttack {
	//These functions are defined here so that they will exist in all classes implementing this
	//interface
	//This will cause an implemented class to attack a location
	void attack(NPC from, Location to);
	//This will cause an implemented class to attack an entity.
	void attack(NPC from, Entity to);
}
