package com.sanjay900.DoomPlugin;

import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import com.sanjay900.DoomPlugin.entities.items.DoomItem;
import com.sanjay900.DoomPlugin.entities.mobs.DoomEntity;
import com.sanjay900.DoomPlugin.entities.mobs.Imp;
import com.sanjay900.DoomPlugin.levelElements.DoomLevel;
import com.sanjay900.DoomPlugin.util.MathUtil;
import com.shampaggon.crackshot.CSUtility;
public class DoomConfig {
		private DoomPlugin plugin;
		private FileConfiguration config;
			public ArrayList<DoomLevel> levels = new ArrayList<>();
			public DoomConfig(DoomPlugin plugin) {
		
				this.plugin = plugin;
						plugin.saveDefaultConfig();
								config = plugin.getConfig();
								loadConfig();
	}
		@SuppressWarnings("deprecation")
	public void loadConfig() {
				levels.clear();
								Set<String> levelKeys = this.config.getKeys(false);
				for (String levelkey : levelKeys) {
						ArrayList<DoomEntity> entities = new ArrayList<>();
			ArrayList<DoomItem> items = new ArrayList<>();
			String worldName = getString(levelkey,"world");
			if (worldName == null || Bukkit.getWorld(worldName) == null) {
				System.out.println("[Doom] - The Level: "+levelkey+" appears to be invalid, as it has an invalid world name.");			
				continue;
			}
			if (getString(levelkey,"spawnLoc") == null) {
				System.out.println("[Doom] - The Level: "+levelkey+" appears to be invalid, as it is missing a spawn location.");			
				continue;
			}
						String[] spawnLocS = getString(levelkey,"spawnLoc").split(",");
			
			if (spawnLocS.length != 3) {
				System.out.println("[Doom] - The Level: "+levelkey+" appears to be invalid, as its spawn location is invalid");			
				continue;
			}
			Location spawnLoc = new Location(Bukkit.getWorld(worldName),Double.parseDouble(spawnLocS[0]),Double.parseDouble(spawnLocS[1]),Double.parseDouble(spawnLocS[2]));
						Set<String> entitykeys = this.config.getConfigurationSection(levelkey+".entities").getKeys(false);
						for (String entity : entitykeys) { 
								String node = levelkey+".entities."+entity;
				if (getString(node,"spawnLoc") == null) {
					System.out.println("[Doom] - The Entity: "+entity+" appears to be invalid, as it is missing a spawn location.");			
					continue;
				}
								
				String[] spawnLocES = getString(node,"spawnLoc").split(",");
				if (spawnLocES.length != 3) {
					System.out.println("[Doom] - The Entity: "+entity+" appears to be invalid, as its spawn location is invalid");			
					continue;
				}
				Location spawnLocE = new Location(Bukkit.getWorld(worldName),Double.parseDouble(spawnLocES[0]),Double.parseDouble(spawnLocES[1]),Double.parseDouble(spawnLocES[2]));
								String entityType = getString(node,"entityType");
												switch (entityType.toLowerCase()) {
				case "imp":
					entities.add(new Imp(plugin,spawnLocE));
										break;
									default:
					System.out.println("[Doom] - The Entity: "+entityType+" Does not appear to be a valid Doom Entity. Your choices are: Imp.");
					continue;
				}
			}
						Set<String> itemkeys = this.config.getConfigurationSection(levelkey+".items").getKeys(false);
						for (String item : itemkeys) { 
								String node = levelkey+".items."+item;
				if (getString(node,"spawnLoc") == null) {
					System.out.println("[Doom] - The item: "+item+" appears to be invalid, as it is missing a spawn location.");			
					continue;
				}
								String[] spawnLocES = getString(node,"spawnLoc").split(",");
				if (spawnLocES.length != 3) {
					System.out.println("[Doom] - The item: "+item+" appears to be invalid, as its spawn location is invalid");			
					continue;
				}
				
				Location spawnLocE = new Location(Bukkit.getWorld(worldName),Double.parseDouble(spawnLocES[0]),Double.parseDouble(spawnLocES[1]),Double.parseDouble(spawnLocES[2]));
																String entityTypeS = getString(node,"entityType");
				String entityType = entityTypeS;
																int amount = -1;
				int data = -1;
												if (StringUtils.countMatches(entityType,":")==1) {
																				if (MathUtil.isInteger(entityType.split(":")[1]))
						data = Integer.valueOf(entityType.split(":")[1]);
															entityType = entityTypeS.split(":")[0];
				}
												if (StringUtils.countMatches(entityType,":")==2) {
																				if (MathUtil.isInteger(entityType.split(":")[1]))
						amount = Integer.valueOf(entityType.split(":")[1]);
																				if (MathUtil.isInteger(entityType.split(":")[2]))
						data = Integer.valueOf(entityType.split(":")[2]);
															entityType = entityTypeS.split(":")[0];
				}
								ItemStack i = new CSUtility().generateWeapon(entityType);
				
												if (i == null) {
										Material mt = Material.matchMaterial(entityType);
															if (mt == null) {
												if (MathUtil.isInteger(entityType))
														mt = Material.getMaterial(Integer.parseInt(entityType));
						else {
																					System.out.println("[Doom] - The Item: "+entityTypeS+" Does not appear to be a valid item.");
							continue;
						}
					}
																									i = new ItemStack(mt,amount==-1?1:amount,(short)(data==-1?0:data));
				}
								items.add(new DoomItem(i,spawnLocE));
			}
						DoomLevel dlevel = new DoomLevel(levelkey,spawnLoc,entities,items,plugin);
						levels.add(dlevel);
			System.out.println("[Doom] - Loaded the level "+levelkey);
		}


	}
		public void addLevel(DoomLevel level) {
				config.set(level.getName(),null);
				config.set(getString(level.getName(),"spawnLoc"), locationToString(level.getSpawnLoc()));
		config.set(getString(level.getName(),"worldName"), level.getSpawnLoc().getWorld().getName());
				for (DoomEntity en: level.getEntities()) {
			String path = level+".entities."+String.valueOf(level.getEntities().indexOf(en));
			config.set(path+"."+"spawnLoc", locationToString(en.spawnLocation()));
			config.set(path+"."+"entityType", en.getEntityType());
		}
				for (DoomItem item: level.getItems()) {
			String path = level+".items."+String.valueOf(level.getItems().indexOf(item));
			config.set(path+"."+"spawnLoc", locationToString(level.getSpawnLoc()));
			config.set(path+"."+"entityType", item.itemName());
		}
				levels.add(level);

	}
	private String locationToString(Location loc) {
		return String.valueOf(loc.getX())+","+String.valueOf(loc.getY())+","+String.valueOf(loc.getZ());
	}
		private String getString(String path, String keyName) {
		return this.config.getString(path+"."+keyName);
	}
}
