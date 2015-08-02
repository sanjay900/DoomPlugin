package com.sanjay900.DoomPlugin.levelElements;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.sanjay900.DoomPlugin.DoomPlugin;
import com.sanjay900.DoomPlugin.entities.items.DoomItem;
import com.sanjay900.DoomPlugin.entities.mobs.DoomEntity;
import com.sanjay900.DoomPlugin.player.DoomPlayer;

public class DoomLevel {
			private boolean started = false;
		private Location spawnLoc;
		private ArrayList<DoomEntity> entities = new ArrayList<DoomEntity>();
	private ArrayList<DoomItem> items = new ArrayList<DoomItem>();
	private ArrayList<DoomPlayer> players = new ArrayList<DoomPlayer>();
		private DoomPlugin plugin;
		private String levelName;

		public DoomLevel (String level, Location spawnLoc, ArrayList<DoomEntity> entities, ArrayList<DoomItem> items, DoomPlugin plugin) {
				this.spawnLoc = spawnLoc;
		this.entities = entities;
		this.items = items;
		this.plugin = plugin;
		this.levelName = level;
	}
		public String getName() {
		return levelName;
	}
		public void startGame(Player player) {
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
				player.teleport(spawnLoc);
				DoomPlayer dp = new DoomPlayer(player, plugin,this);
		players.add(dp);
		plugin.doomPlayers.add(dp);
				this.started = true;

	}
		private void addToGame(Player player) {
						player.teleport(spawnLoc);
				DoomPlayer dp = new DoomPlayer(player, plugin,this);
		players.add(dp);
		plugin.doomPlayers.add(dp);

	}
		public void endGame() {
				for (DoomPlayer p: players) {
			p.stopGame();
		}
				for (DoomItem i : getItems()) {
			i.despawnItem();
		}
		for (DoomEntity e : getEntities()) {
			e.despawnEntity();
		}
	}
		public Location getSpawnLoc() {
		return spawnLoc;
	}
		public ArrayList<DoomEntity> getEntities() {
		return entities;
	}
		public ArrayList<DoomItem> getItems() {
		return items;
	}

}
