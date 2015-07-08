package com.sanjay900.DoomPlugin.attacks;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
//This implements DoomAttack, so you can treat it as a normal attack instead of having to
//directly cast to it. This allows you to store multiple attacks in one array.
//This specific attack shoots a fireball at the enemy.
public class RifleWeapon implements DoomAttack 
{
	//Override the attack function.
	//This will shoot an arrow at a specified location from the entity.
	//the npc.getBukkitEntity() method gets an entity that is capable of shooting fireballs.
	//launch projectile require a type of projectile, and a vector to travel in.
	//The vector is calculated by subtracting the npcs location from the destination.
	@Override
	public void attack(NPC from, Location to) {
		from.getBukkitEntity().launchProjectile(Arrow.class, to.toVector().subtract(from.getEntity().getLocation().toVector()));
	}
	//Helpful method to allow us to attack an entity. Right now, it just calls the above method,
	//but tells it to attack the location of the entity.
	@Override
	public void attack(NPC from, Entity to) {
		attack(from,to.getLocation());
	}
}
