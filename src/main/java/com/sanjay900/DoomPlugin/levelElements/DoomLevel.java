package com.sanjay900.DoomPlugin.levelElements;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.sanjay900.DoomPlugin.DoomPlugin;
import com.sanjay900.DoomPlugin.entities.items.DoomItem;
import com.sanjay900.DoomPlugin.entities.mobs.DoomEntity;
import com.sanjay900.DoomPlugin.player.DoomPlayer;

public class DoomLevel {
	//These variables are global as they are accessed by multiple objects and functions
	//Has this game started?
	private boolean started = false;
	//Where do you spawn when the game starts
	private Location spawnLoc;
	//What entities and player and items does this game have
	private ArrayList<DoomEntity> entities = new ArrayList<DoomEntity>();
	private ArrayList<DoomItem> items = new ArrayList<DoomItem>();
	private ArrayList<DoomPlayer> players = new ArrayList<DoomPlayer>();
	//An instance of the plugin
	private DoomPlugin plugin;
	//What is this level called?
	private String levelName;

	//Initilize this object - global as it is only called by other objects
	public DoomLevel (String level, Location spawnLoc, ArrayList<DoomEntity> entities, ArrayList<DoomItem> items, DoomPlugin plugin) {
		//Set some global variables to the passed variables
		this.spawnLoc = spawnLoc;
		this.entities = entities;
		this.items = items;
		this.plugin = plugin;
		this.levelName = level;
	}
	//Get the name of this level - global as it is called by other objects
	public String getName() {
		return levelName;
	}
	//Start a game with a player - global as it is called by other objects
	public void startGame(Player player) {
		//This game has started. Add him to a running game instead.
		if (started) {
			addToGame(player);
			return;
		}
		for (DoomItem i : getItems()) {
			i.spawnItem();
		}
		for (DoomEntity e : getEntities()) {
			e.spawnEntity();
		}
		//teleport the player to the spawn location
		player.teleport(spawnLoc);
		//add a new DoomPlayer to the game
		DoomPlayer dp = new DoomPlayer(player, plugin,this);
		players.add(dp);
		plugin.doomPlayers.add(dp);
		//Set this level as started.
		this.started = true;

	}
	//Add someone to a game. - global as it is called by other objects
	private void addToGame(Player player) {
		//TODO: check if the game supports more players
		//teleport the player to the spawn location
		player.teleport(spawnLoc);
		//add a new DoomPlayer to the game
		DoomPlayer dp = new DoomPlayer(player, plugin,this);
		players.add(dp);
		plugin.doomPlayers.add(dp);

	}
	//End this game. - global as it is called by other objects
	public void endGame() {
		//tell the doom player this game has stopped.
		for (DoomPlayer p: players) {
			p.stopGame();
		}
		//Despawn all items and entities
		for (DoomItem i : getItems()) {
			i.despawnItem();
		}
		for (DoomEntity e : getEntities()) {
			e.despawnEntity();
		}
	}
	//Get the spawn location - for configuration saving so it is global
	public Location getSpawnLoc() {
		return spawnLoc;
	}
	//get a list of all entities - for configuration saving so it is global
	public ArrayList<DoomEntity> getEntities() {
		return entities;
	}
	//get a list of all items - for configuration saving so it is global
	public ArrayList<DoomItem> getItems() {
		return items;
	}

}
