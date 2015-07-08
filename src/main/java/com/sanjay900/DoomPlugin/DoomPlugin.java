package com.sanjay900.DoomPlugin;

import java.util.ArrayList;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.sanjay900.DoomPlugin.WAD.DoomLineParser;
import com.sanjay900.DoomPlugin.entities.mobs.DoomEntity;
import com.sanjay900.DoomPlugin.levelElements.DoomDamageFloor;
import com.sanjay900.DoomPlugin.listeners.DoomCommandHandler;
import com.sanjay900.DoomPlugin.listeners.DoomEventListener;
import com.sanjay900.DoomPlugin.player.DoomPlayer;
import com.sanjay900.DoomPlugin.util.DamageCalculator;
//I created this class as it was required to make this plugin functional. Without it, bukkit has
//no way of running any of my code. I decided to use it as a storage for some objects as they
//are used by many functions, and passing a copy of this object is useful as the JavaPlugin
//object is needed by other functions to create timers anyway, so i would have ended up passing
//this class to many other constructors anyway.
public class DoomPlugin extends JavaPlugin{
	//I chose to define these specific classes here as they are accessed by many other classes
	
	//Create an array to hold all players in a game. this is global as lots of objects
	//End up referencing it. This is because it is the only way to get hold of a DoomPlayer object.
	public ArrayList<DoomPlayer> doomPlayers = new ArrayList<DoomPlayer>();
	//Create a damage calculation object. This is global as it is referenced by many other objects.
	public DamageCalculator doomDamageCalculator;
	//Create a doom line parser. this object is responsible for converting doom lines to
	//A line that we can use in code and that is human readable and saveable to a configuration.
	
	//Create a list of spawned entities. This is so that we can kill them on command if needed, and
	//Just so that we know what entities are spawned.
	public ArrayList<DoomEntity> spawnedEntities = new ArrayList<DoomEntity>();
	//Create a object that parses the configuration for the game. This is responsible
	//For telling the game where items and entities and players spawn and what they are.
	public DoomConfig doomConfig;
	//onEnable and onDisable are called by bukkit when a plugin is enabled or disabled.
	@Override
    public void onDisable() {
		//Make sure that any running task is killed, to free up valuable resources.
		getServer().getScheduler().cancelTasks(this);
	}
	@Override
    public void onEnable() {
		//Initilize the variables we defined above. We have to do this after the plugin is loaded,
		//to avoid problems.
		doomConfig = new DoomConfig(this);
		doomDamageCalculator = new DamageCalculator(this);
		//Create an object that handles running commands in game and via the console.
		new DoomCommandHandler(this);
		//Create an object that listenes to events such as a player hitting a mob.
		new DoomEventListener(this);
		//Run a task every 20 ticks (a measure of time ingame) that damages a player standing
		//On a damaging floor.
		getServer().getScheduler().runTaskTimer(this, new DoomDamageFloor(this), 1l, 20L);
		
	}

	//Try getting a doom player from the list by the player they are.
	//This function is global because it is designed to be used by other objects.
	//I decided to create this function as it was useful for grabbing an instance of
	//a player that is playing a level at a given time, for use with commands and events.
	public DoomPlayer getPlayer(Player player) {
		//Loop through all the doom players
		for (DoomPlayer pl : doomPlayers) {
			//Check if this doomplayer references the supplied player
			if (pl.getPlayer().equals(player)) {
				//They do, lets return this doom player
				return pl;
			}
		}
		//No doom players are referenced by this player. Return null since it represents nothing.
		return null;
	}
		//Try getting a doom entity from the list by the NPC it is.
		//This function is global because it is designed to be used by other objects.
	public DoomEntity getEntity(NPC npc) {
		//Loop through all spawned entities
		for (DoomEntity e: spawnedEntities){
			//Check if both npcs have the same Unique id. if they do, they are the same.
			if (e.getNPC().getUniqueId().equals(npc.getUniqueId())) {
				//They do. return this entity.
				return e;
			}
		}
		//This npc doesnt appear to be a doom entity.
 		return null;
	}
	//This function is global as it allows you to get the doomConfiguration object from other
	//objects, so that you can save a new level or reload the configuration from a command.
	public DoomConfig getDoomConfig() {
		return doomConfig;
		
	}
}
