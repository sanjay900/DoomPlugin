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
public class DoomPlugin extends JavaPlugin{
		
			public ArrayList<DoomPlayer> doomPlayers = new ArrayList<DoomPlayer>();
		public DamageCalculator doomDamageCalculator;
			
			public ArrayList<DoomEntity> spawnedEntities = new ArrayList<DoomEntity>();
			public DoomConfig doomConfig;
		@Override
    public void onDisable() {
				getServer().getScheduler().cancelTasks(this);
	}
	@Override
    public void onEnable() {
						doomConfig = new DoomConfig(this);
		doomDamageCalculator = new DamageCalculator(this);
				new DoomCommandHandler(this);
				new DoomEventListener(this);
						getServer().getScheduler().runTaskTimer(this, new DoomDamageFloor(this), 1l, 20L);
		
	}

					public DoomPlayer getPlayer(Player player) {
				for (DoomPlayer pl : doomPlayers) {
						if (pl.getPlayer().equals(player)) {
								return pl;
			}
		}
				return null;
	}
					public DoomEntity getEntity(NPC npc) {
				for (DoomEntity e: spawnedEntities){
						if (e.getNPC().getUniqueId().equals(npc.getUniqueId())) {
								return e;
			}
		}
		 		return null;
	}
			public DoomConfig getDoomConfig() {
		return doomConfig;
		
	}
}
