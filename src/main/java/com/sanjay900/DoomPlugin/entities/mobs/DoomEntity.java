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
						private NPC npc;
		private DoomAttack[] attacks;
		private DoomPlugin plugin;
		private Location location;
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
				this.plugin = plugin;
		this.location = location;
		attacks = attack;
				NPCRegistry registry = CitizensAPI.getNPCRegistry();
		npc = registry.createNPC(entityType, dEntityType);
		this.dEntityType = dEntityType;
				npc.data().setPersistent(NPC.DEFAULT_PROTECTED_METADATA, false);
		 npc.data().setPersistent(NPC.TARGETABLE_METADATA, true);
		 npc.data().setPersistent(NPC.DAMAGE_OTHERS_METADATA, true);
		new EntityLogic();

	}
		public String getEntityType() {
		return dEntityType;
	}
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
		public class EntityLogic implements Runnable {
		private BukkitTask task;
		public EntityLogic() {
			task = Bukkit.getScheduler().runTaskTimer(plugin, this, 1l, 1l);
		}
		@Override
		public void run() {
			if (npc.isSpawned()) {
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
