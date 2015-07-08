package com.sanjay900.DoomPlugin.WAD;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;
import org.bukkit.plugin.Plugin;
import org.inventivetalent.bossbar.BossBarAPI;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.sanjay900.DoomPlugin.DoomPlugin;
/**
 * 
 * Parse a Doom WAD file a DOOM Wad file, and convert all its levels to a format recognized by {@link DoomPlugin}.
 *
 *
 */
public class DoomLevelParser {
	//Global variables, as they are accessed by multiple functions and even other objects.
	//Each Wad contains several levels, this arraylist will eventually store all wad levels.
	ArrayList<DoomWadLevel> levels = new ArrayList<DoomWadLevel>();
	//This contains the type of wad file we have initialized with
	private String wad_type = "";
	//The UUID of the player who created this LevelParser
	private UUID playerUUID;
	//Initialize an instance of this class
	private DoomPlugin plugin;
	
	
	/**
	 * Parse a Doom WAD file a DOOM WAD file, and convert all its levels to a format recognized by {@link DoomPlugin}.
	 * @param plugin - an instance of {@link DoomPlugin}
	 * @param wadName - the name of the WAD file to load
	 */
	@SuppressWarnings("deprecation")
	public DoomLevelParser(final DoomPlugin plugin , final String wadName, final UUID playerUUID) {
		this.playerUUID = playerUUID;
		this.plugin = plugin;
		setBar("[DOOM] Creating World", 1f);
		//Generate a world to dump everything into - This can't be done Async, so thats why its done early
		if (Bukkit.getWorld(wadName) != null) {
			//This world already exists, so just wipe it
			//Multiverse is a bukkit plugin that takes care of having multiple worlds.
			getMultiverseCore().regenWorld(wadName, false, true, "");
			getMultiverseCore().getMVWorldManager().getMVWorld(wadName).setAllowMonsterSpawn(false);
			getMultiverseCore().getMVWorldManager().getMVWorld(wadName).setAllowAnimalSpawn(false);
		} else {
			//Generate a world from scratch
			getMultiverseCore().getMVWorldManager().addWorld(wadName, Environment.NORMAL, "", WorldType.FLAT, false, "CleanroomGenerator:1,bedrock");
			getMultiverseCore().getMVWorldManager().getMVWorld(wadName).setAllowMonsterSpawn(false);
			getMultiverseCore().getMVWorldManager().getMVWorld(wadName).setAllowAnimalSpawn(false);
		}
		//We have the run the loading Asynchronously, otherwise it hangs the main thread as it takes a while, and crashes
		//the server.
		Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable(){

			@Override
			public void run() {
				setBar("[DOOM] Parsing Wad file", 1f);
				//Get an instance of the WAD file as a file object.
				File file = new File(plugin.getDataFolder().getAbsolutePath()+"/wads/"+wadName);

				//Create a FileInputStream to allow us to read data from the file in bytes
				FileInputStream f = null;
				//Create a DataInputStream that adds utility functions to let us read data in more ways
				//from a FileInputStream
				DataInputStream ds = null;
				try {
					//Initialize f so that we can use it, this lets use read byte by byte from the file object, like a stream of bytes
					f = new FileInputStream(file);
					//Initialize ds so that we can use it, this lets us read byte by byte from the file input stream, but as different formats like an integer
					ds = new DataInputStream(f);
					//The amount of bytes that contain the header of the WAD file
					int header_size = 12;
					//Create an instance of a WadLevel. This stores Level data from the doom WAD in a way
					//That allows us to manipulate it.
					//The first level is there because the doom wad has a few lumps before real levels take place. This is why everything is
					//null.
					//This lets us convert doom line types from an index to something that is human readable.
					DoomLineParser doomLineParser = new DoomLineParser();
					DoomWadLevel current_level = new DoomWadLevel(null, plugin, playerUUID, null, doomLineParser);
					//Get the type of WAD file by converting the first 4 bytes of the file into text.
					for (int i = 0; i < 4; i++) {
						wad_type+=(char)ds.read();
					}
					//Check the first four bytes contain WAD - Doom wad files should have either
					//IWAD or PWAD as their first four bytes.
					if (!wad_type.contains("WAD")) {
						sendMessage("The WAD file specified is invalid");
						return;
					}
					//Debug the wad type.
					sendMessage("WAD file type: "+wad_type);
					//The following is just reading a whole lot of bytes, converting them to different
					//data formats via ByteBuffer and turning them into WadLevels...
					/**
					 * A lump is just a collection of bytes.
					 */
					//Ignore this integer, just read it so that our buffer is at something we want to read
					ds.readInt();
					//This bytebuffer allows us to get the size of the data lump
					ByteBuffer 	bb2 = ByteBuffer.wrap(new byte[]{ds.readByte(),ds.readByte(),ds.readByte(),ds.readByte()});
					//Doom has everything in the Little Endian format, so thats how we have to read it.
					bb2.order(ByteOrder.LITTLE_ENDIAN);
					//Create a byte array that holds all of the data lump. bb2.getInt() converts a buffer of bytes to an Integer.
					byte[] data = new byte[bb2.getInt()-header_size];
					//Read in bytes to the data array.
					ds.read(data, 0, data.length);
					//Read the first lump of data. it has a size of 16 bytes.
					byte[] lump = new byte[16];
					//Read in bytes to the lump array.
					ds.read(lump, 0, lump.length);
					setBar("[DOOM] Converting Lumps", 1f);
					while (ds.available() > 16) {
						//Lets parse a lump
						//This allows us to get the file position
						ByteBuffer bb = ByteBuffer.wrap(new byte[]{lump[0],lump[1],lump[2],lump[3]});
						bb.order(ByteOrder.LITTLE_ENDIAN);
						//This variable stores the position in the data array this lump stores its data in.
						int filepos = bb.getInt()-header_size;
						//This variable stores the size of this lump.
						bb = ByteBuffer.wrap(new byte[]{lump[4],lump[5],lump[6],lump[7]});
						bb.order(ByteOrder.LITTLE_ENDIAN);
						int size = bb.getInt();
						//Get the name of the lump, from the next collection of bytes
						String name = new String(new byte[]{lump[8],lump[9],lump[10],lump[11],lump[12],lump[13],lump[14],lump[15]}, "UTF-8");
						//Remove null characters, otherwise we end up with problems.
						name = name.replaceAll("\0", "");
						//Tell the player what level we are parsing - and how much of it has been parsed.
						setBar("[DOOM] Parsing Wad file - Parsing "+current_level.name != null?current_level.name:"Initial Data"+" Part: "+name, (float)ds.available()/f.getChannel().size()*100);

						//names matching the following regex are a special case: they are levels
						if (name.matches("E\\dM\\d|MAP\\d\\d")) {
							//if the current level is valid, add it to the array of levels
							if(current_level.is_valid()) {
								levels.add(current_level);
							} 
							//Create a new WadLevel, we are at a new level lump
							current_level = new DoomWadLevel(name, plugin, playerUUID, Bukkit.getWorld(wadName),doomLineParser);
							//Check this lump actually contains data...
						} else if (size != 0){
							//Add this lump to the level, so we can use it later to build up the level
							current_level.lumps.put(name, Arrays.copyOfRange(data, filepos, filepos+size));
						}
						//is there more data to read?
						if (ds.available() > 16) {
							//Yes, lets create a new lump then go back to the top
							ds.read(lump, 0, lump.length);
						}
					}
					//Theres no more data. Add the last level to the array of levels.
					if(current_level.is_valid()) {
						levels.add(current_level);
					}
					setBar("[DOOM] Parsed the wad file "+file.getName(),100f);

					//Debug how many levels were loaded.
					sendMessage("Loaded levels. Level amount: "+String.valueOf(levels.size()));
					sendMessage("We are now parsing the levels. This may take a while.");
					for (DoomWadLevel l: levels) {
						setBar("[DOOM] Parsing level "+l.name+". Level "+levels.indexOf(l)+" out of "+levels.size(),1f);	
						l.load(wad_type);

					}
					//Catch errors so they don't crash the plugin
				} catch (IOException e) {
					//The file could not be read.
					try {
						if (ds != null) {
							ds.close();
						}
						if (f != null)
							f.close();
					} catch (IOException ex) {
						//We found an error... Print it to console and move on.
						ex.printStackTrace();
					}
					sendMessage("The wad file that was entered could not be found.");
					return;
				} finally {
					//Close our Streams so that they can be garbage collected and don't cause memory leaks.
					try {
						if (ds != null) {
							ds.close();
						}
						if (f != null)
							f.close();
					} catch (IOException ex) {
						//We found an error... Print it to console and move on.
						ex.printStackTrace();
					}
				}
				//The next part of things doesn't need to be async.
				Bukkit.getScheduler().runTask(plugin, new Runnable(){

					@Override
					public void run() {
						sendMessage("Levels parsed. We are now converting them to Levels recognised by the plugin.");
						sendMessage("Due to our hooks on worldedit, the next bit may fill your chat.");
						setBar("[DOOM] Parsed Levels. Converting levels to Minecraft.",100);

						//Convert to DoomLevels
						convertLevels();
					}});
			}

		});
	}
	/**
	 * Convert all the stored levels from this WAD to DoomLevels.
	 */
	public void convertLevels() {
		//Loop through all our levels
		for (DoomWadLevel l: levels) {
			setBar("[DOOM] Building level "+l.name+". Level "+levels.indexOf(l)+" out of "+levels.size(),1f);
			//Build WAD levels as blocks
			l.buildLevel();
			setBar("[DOOM] Converting level "+l.name+". Level "+levels.indexOf(l)+" out of "+levels.size(),1f);
			//Convert WAD levels to doom levels
			l.convertToDoomLevel();
		}
	}
	//This code gets an instance of the MultiverseCore plugin, which is responsible for
	//Managing multiple worlds on minecraft servers. This function is global as it
	//Is used by another function to modify worlds.
	public MultiverseCore getMultiverseCore() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
 
        if (plugin instanceof MultiverseCore) {
            return (MultiverseCore) plugin;
        }
 
        throw new RuntimeException("Multiverse not found!");
    }
	/**
	 * Utility method for sending a message to the initiating player
	 * @param string - Message to send
	 */
	private void sendMessage(String string) {
		//Get the player from the stored UUID and send them a message. If for some reason a player isn't online
		//when we attempt to send the message, the null check will make sure the plugin doesn't crash.
		if (Bukkit.getPlayer(playerUUID) != null)
			Bukkit.getPlayer(playerUUID).sendMessage(string);

	}
	private void setBar(final String message, final float percent) {
		System.out.println(message);
		Bukkit.getScheduler().runTask(plugin, new Runnable(){

			@Override
			public void run() {
				BossBarAPI.setMessage(Bukkit.getPlayer(playerUUID), message, percent);
			}});
		

	}


}
