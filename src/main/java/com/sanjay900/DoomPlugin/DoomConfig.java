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
//I created this class because i needed some way to store a configured level, otherwise i would
//lose every level when i restarted the server.
public class DoomConfig {
	//Plugin Instance - Global as it is accessed through the class in many functions
	private DoomPlugin plugin;
	//Configuration instance - Global as it is accessed through the class in many functions
	private FileConfiguration config;
	//An array of levels - Global as it is accessed not only by this class, but from others
	//that let you play through the created levels.
	public ArrayList<DoomLevel> levels = new ArrayList<>();
	//Class creation function - this is public as it is only used to create the object from
	//another object.
	public DoomConfig(DoomPlugin plugin) {
		
		//assign the plugin instance with the supplied instance of a plugin
		this.plugin = plugin;
		//Save the default config. This will fail silently if a config already exists.
				plugin.saveDefaultConfig();
				//assign the config instance with the plugins configuration
				config = plugin.getConfig();
				//Parse the configuration file4
				loadConfig();
	}
	//parse the configuration file - this is public as it can be accessed by other objects to reload the configuration.
	@SuppressWarnings("deprecation")
	public void loadConfig() {
		//Clear the list of levels so that we only have the stored levels from the configuration
		levels.clear();
		//Everything here is a local variable as it is temporary. However, the final product of a DoomLevel object
		//is added to the global list of levels.
		//Get a list of all the keys from the root of the configuration (the level names)
		Set<String> levelKeys = this.config.getKeys(false);
		//Loop through all the level keys
		for (String levelkey : levelKeys) {
			//Create some local variables to store a temp list of entities and items
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
			//Get and convert the spawnLocation of the player as a local variable
			String[] spawnLocS = getString(levelkey,"spawnLoc").split(",");
			
			if (spawnLocS.length != 3) {
				System.out.println("[Doom] - The Level: "+levelkey+" appears to be invalid, as its spawn location is invalid");			
				continue;
			}
			Location spawnLoc = new Location(Bukkit.getWorld(worldName),Double.parseDouble(spawnLocS[0]),Double.parseDouble(spawnLocS[1]),Double.parseDouble(spawnLocS[2]));
			//Get a list of all the keys in the entity section of this level as a local variable
			Set<String> entitykeys = this.config.getConfigurationSection(levelkey+".entities").getKeys(false);
			//Loop through all entity keys
			for (String entity : entitykeys) { 
				//Temporarily store the name of the entity key into a local variable
				String node = levelkey+".entities."+entity;
				if (getString(node,"spawnLoc") == null) {
					System.out.println("[Doom] - The Entity: "+entity+" appears to be invalid, as it is missing a spawn location.");			
					continue;
				}
				//Get and convert the spawnLocation of this entity
				
				String[] spawnLocES = getString(node,"spawnLoc").split(",");
				if (spawnLocES.length != 3) {
					System.out.println("[Doom] - The Entity: "+entity+" appears to be invalid, as its spawn location is invalid");			
					continue;
				}
				Location spawnLocE = new Location(Bukkit.getWorld(worldName),Double.parseDouble(spawnLocES[0]),Double.parseDouble(spawnLocES[1]),Double.parseDouble(spawnLocES[2]));
				//Get the type of entity this is
				String entityType = getString(node,"entityType");
				//Create an entity object based on what this entity is called.
				//For an example, an imp in the configuration creates an Imp object.
				switch (entityType.toLowerCase()) {
				case "imp":
					entities.add(new Imp(plugin,spawnLocE));
					//The entity saved in the configuration doesnt match one that we have an object for
					break;
					//Print an error to the console
				default:
					System.out.println("[Doom] - The Entity: "+entityType+" Does not appear to be a valid Doom Entity. Your choices are: Imp.");
					continue;
				}
			}
			//Get a list of all the keys in the items section of this level as a local variable
			Set<String> itemkeys = this.config.getConfigurationSection(levelkey+".items").getKeys(false);
			//Loop through all the item keys
			for (String item : itemkeys) { 
				//Temporarily store the full name of this key
				String node = levelkey+".items."+item;
				if (getString(node,"spawnLoc") == null) {
					System.out.println("[Doom] - The item: "+item+" appears to be invalid, as it is missing a spawn location.");			
					continue;
				}
				//Get and convert the location of this item from the config
				String[] spawnLocES = getString(node,"spawnLoc").split(",");
				if (spawnLocES.length != 3) {
					System.out.println("[Doom] - The item: "+item+" appears to be invalid, as its spawn location is invalid");			
					continue;
				}
				
				Location spawnLocE = new Location(Bukkit.getWorld(worldName),Double.parseDouble(spawnLocES[0]),Double.parseDouble(spawnLocES[1]),Double.parseDouble(spawnLocES[2]));
				//Get the type of item this is
				//Either this is a weapon from CrackShot (A weapon plugin) 
				//Or this is a minecraft item expressed by name or id
				String entityTypeS = getString(node,"entityType");
				String entityType = entityTypeS;
				//Temporarily store the amount of this item, and its metadata.
				//Since these are never negative, this is a good way to check if
				//they have been changed.
				int amount = -1;
				int data = -1;
				//Check if the supplied item contains a single colon.
				//if it does, it is probably in the syntax itemid:data
				if (StringUtils.countMatches(entityType,":")==1) {
					//The text after the : should be an integer since its the items metadata
					//Convert it to an integer. if for some reason it isnt one, then we will ignore
					//it and the code below won't factor it in.
					if (MathUtil.isInteger(entityType.split(":")[1]))
						data = Integer.valueOf(entityType.split(":")[1]);
					//The rest of the string (Before the :) is either an item id or a material.
					//Remove the parsed data from the string so that the rest of the data can be handled later.
					entityType = entityTypeS.split(":")[0];
				}
				//Check if the supplied item contains a two colons.
				//if it does, it is probably in the syntax itemid:data:amount
				if (StringUtils.countMatches(entityType,":")==2) {
					//The text after the : should be an integer since its the items amount
					//Convert it to an integer. if for some reason it isnt one, then we will ignore
					//it and the code below won't factor it in.
					if (MathUtil.isInteger(entityType.split(":")[1]))
						amount = Integer.valueOf(entityType.split(":")[1]);
					//The text after the second : should be an integer since its the items metadata
					//Convert it to an integer. if for some reason it isnt one, then we will ignore
					//it and the code below won't factor it in.
					if (MathUtil.isInteger(entityType.split(":")[2]))
						data = Integer.valueOf(entityType.split(":")[2]);
					//The rest of the string (Before the :) is either an item id or a material.
					//Remove the parsed data from the string so that the rest of the data can be handled later.				
					entityType = entityTypeS.split(":")[0];
				}
				//Try an attempt to create a CrackShot weapon from the supplied item id.
				ItemStack i = new CSUtility().generateWeapon(entityType);
				
				//Our attempt failed - this doesn't appear to be a CrackShot weapon. 
				//We know this because generateWeapon returns null if a weapon isnt found.
				if (i == null) {
					//Try converting the supplied name to a minecraft material by name
					Material mt = Material.matchMaterial(entityType);
					//This failed because the suplied name wasnt a minecraft material.
					//So matchMaterial returned null.
					if (mt == null) {
						//Mabye this is an item id? Try converting it to an integer.
						if (MathUtil.isInteger(entityType))
							//It is. get the material by item id.
							mt = Material.getMaterial(Integer.parseInt(entityType));
						else {
							//This item doesnt appear to be entered correctly
							//Write that to console, and go to the next item
							System.out.println("[Doom] - The Item: "+entityTypeS+" Does not appear to be a valid item.");
							continue;
						}
					}
					//We got a valid material, amount and item id;
					//amount==-1?1:amount this is the ternary syntax. It pretty much says
					//If the amount is -1 than set the amount to 1.
					//For data, it is set to 0 if you give it a data of -1.
					i = new ItemStack(mt,amount==-1?1:amount,(short)(data==-1?0:data));
				}
				//We have a valid ItemStack. Create a DoomItem from it and add it to the array of items.
				items.add(new DoomItem(i,spawnLocE));
			}
			//We have all the entities and items parsed. Create a DoomLevel from this.
			DoomLevel dlevel = new DoomLevel(levelkey,spawnLoc,entities,items,plugin);
			//Add this level to the list of levels.
			levels.add(dlevel);
			System.out.println("[Doom] - Loaded the level "+levelkey);
		}


	}
	//This gets called by other objects to add a level after we have loaded the configuration.
	public void addLevel(DoomLevel level) {
		//Remove any instance of this level if it exists
		config.set(level.getName(),null);
		//Save the spawn location and world name
		config.set(getString(level.getName(),"spawnLoc"), locationToString(level.getSpawnLoc()));
		config.set(getString(level.getName(),"worldName"), level.getSpawnLoc().getWorld().getName());
		//Save all the entities
		for (DoomEntity en: level.getEntities()) {
			String path = level+".entities."+String.valueOf(level.getEntities().indexOf(en));
			config.set(path+"."+"spawnLoc", locationToString(en.spawnLocation()));
			config.set(path+"."+"entityType", en.getEntityType());
		}
		//Save all the items
		for (DoomItem item: level.getItems()) {
			String path = level+".items."+String.valueOf(level.getItems().indexOf(item));
			config.set(path+"."+"spawnLoc", locationToString(level.getSpawnLoc()));
			config.set(path+"."+"entityType", item.itemName());
		}
		//add this level to the list of levels.
		levels.add(level);

	}
	private String locationToString(Location loc) {
		return String.valueOf(loc.getX())+","+String.valueOf(loc.getY())+","+String.valueOf(loc.getZ());
	}
	//A convinence function for getting a value from a key and a path
	private String getString(String path, String keyName) {
		return this.config.getString(path+"."+keyName);
	}
}
