package com.sanjay900.DoomPlugin.entities.mobs;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import com.sanjay900.DoomPlugin.DoomPlugin;
import com.sanjay900.DoomPlugin.attacks.DoomAttack;
/** DoomEntity
 *  A DoomEntity is a mob that consists of an NPC and logic classes that allow it
 *  to act like an entity from doom.
 */
public class DoomEntity {
	//TODO: npc is passive on and targetable
	//https://github.com/CitizensDev/Citizens2/blob/master/src/main/java/net/citizensnpcs/commands/NPCCommands.java
	//These variables are global as they are accessed by multuple functions and objects
	//A citizens NPC, from the citizens plugin. Baiscally, its a mob thats controllable by code
	//Completly.
	private NPC npc;
	//An array of attacks
	private DoomAttack[] attacks;
	//An instance of the plugin
	private DoomPlugin plugin;
	//The location this mob is stored in
	private Location location;
	//The type of mob this is
	private String dEntityType;
	/** Doom Entity - spawns an NPC and makes it act like a doom entity
	 * 
	 * @param location - Spawn location
	 * @param entityType - Bukkit Entity Type
	 * @param name - NPC name
	 * @param health - max health
	 * @param speed - speed
	 * @param attack - the attacks of this mob
	 */
	public DoomEntity(DoomPlugin plugin, Location location, EntityType entityType, String dEntityType, double health, double speed, DoomAttack... attack) {
		//Store a few variables based on supplied variables
		this.plugin = plugin;
		this.location = location;
		attacks = attack;
		//Spawn a NPC through the citizens plugin.
		NPCRegistry registry = CitizensAPI.getNPCRegistry();
		npc = registry.createNPC(entityType, dEntityType);
		this.dEntityType = dEntityType;
		//Make NPC's killable, because if the were invincible, we would have problems...
		npc.data().setPersistent(NPC.DEFAULT_PROTECTED_METADATA, false);
		 npc.data().setPersistent(NPC.TARGETABLE_METADATA, true);
		 npc.data().setPersistent(NPC.DAMAGE_OTHERS_METADATA, true);
		new EntityLogic();

	}
	//Return this entities entityType
	public String getEntityType() {
		return dEntityType;
	}
	//Return if this entity is dead
	public boolean isDead() {
		return plugin.spawnedEntities.contains(this);
	}
	/** 
	 * Get the NPC this DoomEntity uses.
	 * @return The citizens NPC this DoomEntity uses
	 */
	public NPC getNPC() {
		return npc;
	}
	/**
	 * Get the attacks this mob has
	 * @return the {@link DoomAttack}s this mob has.
	 */
	public DoomAttack[] getAttacks() {
		return attacks;
	}
	/** Teleport a {@link DoomEntity}
	 * 	@param loc - The location to teleport to
	 */
	public void teleport(Location loc) {
		npc.teleport(loc, TeleportCause.PLUGIN);
	}
	/** Called when a DoomEntity has died. 
	 *  There is nothing in this method as it is designed to be overridden later on.
	 */
	public void death() {}
	/** Called by a timer to initiate entity logic
	 *  There is nothing in this method as it is designed to be overridden later on.
	 */
	public void entityLogic() {}
	//This is a runnable (timer) that handles the entities logic - if it moves and things.
	public class EntityLogic implements Runnable {
		private BukkitTask task;
		public EntityLogic() {
			task = Bukkit.getScheduler().runTaskTimer(plugin, this, 1l, 1l);
		}
		@Override
		public void run() {
			if (npc.isSpawned()) {
				//run the custom entityLogic code from a subclassed entity
				entityLogic();
			}
			else {
				death();
				entityCleanup();
				task.cancel();

			}
		}


	}
	public void entityCleanup() {
		npc.destroy();
		plugin.spawnedEntities.remove(this);
	}
	public void despawnEntity() {
		npc.despawn();

	}
	public void spawnEntity() {
		npc.spawn(location);

	}
	public Location spawnLocation() {
		return location;
	}
	public boolean canEntitySeePlayer() {
		return false;
		
	}
	
}
