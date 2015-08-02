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
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
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
	ArrayList<DoomWadLevel> levels = new ArrayList<DoomWadLevel>();
	private String wad_type = "";
	private UUID playerUUID;
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

		Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable(){

			@Override
			public void run() {
				setBar("[DOOM] Parsing Wad file", 1f);
				File file = new File(plugin.getDataFolder().getAbsolutePath()+"/wads/"+wadName);

				FileInputStream f = null;
				DataInputStream ds = null;
				try {
					f = new FileInputStream(file);
					ds = new DataInputStream(f);
					int header_size = 12;
					DoomLineParser doomLineParser = new DoomLineParser();
					DoomWadLevel current_level = new DoomWadLevel(null, plugin, playerUUID, doomLineParser);
					for (int i = 0; i < 4; i++) {
						wad_type+=(char)ds.read();
					}
					if (!wad_type.contains("WAD")) {
						sendMessage("The WAD file specified is invalid");
						return;
					}
					sendMessage("WAD file type: "+wad_type);
					/**
					 * A lump is just a collection of bytes.
					 */
					ds.readInt();
					ByteBuffer 	bb2 = ByteBuffer.wrap(new byte[]{ds.readByte(),ds.readByte(),ds.readByte(),ds.readByte()});
					bb2.order(ByteOrder.LITTLE_ENDIAN);
					byte[] data = new byte[bb2.getInt()-header_size];
					ds.read(data, 0, data.length);
					byte[] lump = new byte[16];
					ds.read(lump, 0, lump.length);
					setBar("[DOOM] Converting Lumps", 1f);
					while (ds.available() > 16) {
						ByteBuffer bb = ByteBuffer.wrap(new byte[]{lump[0],lump[1],lump[2],lump[3]});
						bb.order(ByteOrder.LITTLE_ENDIAN);
						int filepos = bb.getInt()-header_size;
						bb = ByteBuffer.wrap(new byte[]{lump[4],lump[5],lump[6],lump[7]});
						bb.order(ByteOrder.LITTLE_ENDIAN);
						int size = bb.getInt();
						String name = new String(new byte[]{lump[8],lump[9],lump[10],lump[11],lump[12],lump[13],lump[14],lump[15]}, "UTF-8");
						name = name.replaceAll("\0", "");
						setBar("[DOOM] Parsing Wad file - Parsing "+current_level.name != null?current_level.name:"Initial Data"+" Part: "+name, (float)ds.available()/f.getChannel().size()*100);

						if (name.matches("E\\dM\\d|MAP\\d\\d")) {
							if(current_level.is_valid()) {
								levels.add(current_level);
							} 
							current_level = new DoomWadLevel(name, plugin, playerUUID,doomLineParser);
						} else if (size != 0){
							current_level.lumps.put(name, Arrays.copyOfRange(data, filepos, filepos+size));
						}
						if (ds.available() > 16) {
							ds.read(lump, 0, lump.length);
						}
					}
					if(current_level.is_valid()) {
						levels.add(current_level);
					}
					setBar("[DOOM] Parsed the wad file "+file.getName(),100f);

					sendMessage("Loaded levels. Level amount: "+String.valueOf(levels.size()));
					sendMessage("We are now parsing the levels. This may take a while.");
					for (DoomWadLevel l: levels) {
						setBar("[DOOM] Parsing level "+l.name+". Level "+levels.indexOf(l)+" out of "+levels.size(),1f);	
						l.load(wad_type);

					}
				} catch (IOException e) {
					try {
						if (ds != null) {
							ds.close();
						}
						if (f != null)
							f.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					sendMessage("The wad file that was entered could not be found.");
					return;
				} finally {
					try {
						if (ds != null) {
							ds.close();
						}
						if (f != null)
							f.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				sendMessage("Levels parsed. We are now converting them to Levels recognised by the plugin.");
				setBar("[DOOM] Parsed Levels. Converting levels to Minecraft.",100);

				convertLevels();

			}

		});
	}
	/**
	 * Convert all the stored levels from this WAD to DoomLevels.
	 */
	public void convertLevels() {
		//TODO: uncomment when the first level converts correctly.
		/*
		for (DoomWadLevel l: levels) {
			Bukkit.getScheduler().runTaskLater(plugin, () -> {
				setBar("[DOOM] Building level "+l.name+". Level "+levels.indexOf(l)+" out of "+levels.size(),1f);
				l.buildLevel();
				setBar("[DOOM] Converting level "+l.name+". Level "+levels.indexOf(l)+" out of "+levels.size(),1f);
				l.convertToDoomLevel();
				setBar("[DOOM] Built level "+l.name+". Waiting 10 seconds.",1f);
			},levels.indexOf(l)*50);
		}
		 */
		Bukkit.getScheduler().runTask(plugin, () -> {
			levels.get(0).buildLevel();
		});

		levels.get(0).convertToDoomLevel();;
		
	}
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
		if (Bukkit.getPlayer(playerUUID) != null)
			Bukkit.getPlayer(playerUUID).sendMessage(string);

	}
	private void setBar(final String message, final float percent) {
		System.out.println(message);
		/*Bukkit.getScheduler().runTask(plugin, new Runnable(){

			@Override
			public void run() {
				//BossBarAPI.setMessage(Bukkit.getPlayer(playerUUID), message, percent);
			}});
		 */

	}


}
