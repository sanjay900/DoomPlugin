package com.sanjay900.DoomPlugin.WAD;

import java.awt.Point;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.BlockIterator;
import org.inventivetalent.bossbar.BossBarAPI;

import com.google.common.primitives.Shorts;
import com.sanjay900.DoomPlugin.DoomPlugin;
import com.sanjay900.DoomPlugin.WAD.BSP.DoomNode;
import com.sanjay900.DoomPlugin.WAD.BSP.DoomSegment;
import com.sanjay900.DoomPlugin.WAD.BSP.DoomSubSector;
import com.sanjay900.DoomPlugin.entities.items.DoomItem;
import com.sanjay900.DoomPlugin.entities.mobs.DoomEntity;
import com.sanjay900.DoomPlugin.entities.mobs.Imp;
import com.sanjay900.DoomPlugin.levelElements.DoomLevel;
import com.sanjay900.DoomPlugin.util.FaceUtil;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.blocks.BlockID;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class DoomWadLevel {
	//Global varialbes as they are accessed by multiple functions
	DoomPlugin plugin;
	World world;
	String name = null;
	LinkedHashMap<String,byte[]> lumps = new LinkedHashMap<>();
	ArrayList<short[]> vertices = new ArrayList<>();
	Short[] lower_left = new Short[2];
	Short[] upper_right = new Short[2];
	int width = 0;
	int height = 0;
	//Decoded lumps
	ArrayList<DoomThing> things = new ArrayList<>();
	ArrayList<DoomNode> nodes = new ArrayList<>();
	ArrayList<DoomSector> sectors = new ArrayList<>();
	ArrayList<DoomSubSector> subsectors = new ArrayList<>();
	ArrayList<DoomSegment> segments = new ArrayList<>();
	LinkedHashMap<DoomLine,DoomSide[]> lines = new LinkedHashMap<>();
	int[] shift = new int[2] ;
	DoomBlockParser blockParser;
	private UUID playerUUID;
	DoomLineParser doomLineParser;

	public DoomWadLevel(String name, DoomPlugin plugin, UUID playerUUID, World world, DoomLineParser doomLineParser) {
		this.blockParser = new DoomBlockParser(this);
		this.doomLineParser = doomLineParser;
		this.name = name;
		this.playerUUID = playerUUID;
		this.world = world;
		this.plugin = plugin;

	}
	//Parse a level from a collection of lumps
	public void load(String wad_type) {
		//The code below converts lumps to usable data in the form of objects
		
		short[] keys = new short[packets_of_size(4, lumps.get("VERTEXES")).length];
		short[] values = new short[packets_of_size(4, lumps.get("VERTEXES")).length];
		int packet_size = wad_type=="HEXEN"?16:14;
		int i2 = 0;
		for (int i = 0; i < packets_of_size(packet_size, lumps.get("LINEDEFS")).length; i++) {
			DoomLine l = new DoomLine(packets_of_size(packet_size, lumps.get("LINEDEFS"))[i],doomLineParser);
			DoomSide s = new DoomSide(packets_of_size(30, lumps.get("SIDEDEFS"))[i2]);
			DoomSide[] sides = new DoomSide[l.is_one_sided()?1:2];
			sides[0] = s;
			i2++;
			if (!l.is_one_sided()) {	
				DoomSide s2 = new DoomSide(packets_of_size(30, lumps.get("SIDEDEFS"))[i2]);
				sides[1] = s2;
				i2++;	
			}
			lines.put(l,sides);	
		} 
		//This just shows the player how much of the level has loaded on a percentage bar
		//BarAPI lets us manipluate the boss bar.
		BossBarAPI.setHealth(Bukkit.getPlayer(playerUUID), 10);

		for (int i = 0; i < packets_of_size(4, lumps.get("VERTEXES")).length; i++) {
			ByteBuffer bb = ByteBuffer.wrap(packets_of_size(4, lumps.get("VERTEXES"))[i]);
			bb.order(ByteOrder.LITTLE_ENDIAN);
			short x = bb.getShort();
			short y = bb.getShort();
			//the divide by 20 fixes issuse between the scale of doom and minecraft coordinates
			x = (short) (x/20);
			y = (short) (y/20);
			vertices.add(new short[]{x,y});
			keys[i]=x;
			values[i]=y;
		}
		
		BossBarAPI.setHealth(Bukkit.getPlayer(playerUUID), 20);
		lower_left[0] = Shorts.min(keys);
		lower_left[1]= Shorts.min(values);
		upper_right[0] = Shorts.max(keys);
		upper_right[1] = Shorts.max(values);
		shift[0] = 0-lower_left[0];
		shift[1] = 0-lower_left[1];
		width = upper_right[0] - lower_left[0];
		height = upper_right[1] - lower_left[1];
		for (byte[] data : packets_of_size(26, lumps.get("SECTORS"))) {
			DoomSector s = new DoomSector(data);
			sectors.add(s);	
		}
		BossBarAPI.setHealth(Bukkit.getPlayer(playerUUID), 30);
		for (byte[] data : packets_of_size(12, lumps.get("SEGS"))) {
			DoomSegment s = new DoomSegment(data, lines);
			segments.add(s);	
		}
		BossBarAPI.setHealth(Bukkit.getPlayer(playerUUID), 40);
		for (byte[] data :  packets_of_size(28, lumps.get("NODES"))) {
			DoomNode n = new DoomNode(data);
			nodes.add(n);	
		}
		BossBarAPI.setHealth(Bukkit.getPlayer(playerUUID), 60);
		for (byte[] data :packets_of_size(4, lumps.get("SSECTORS"))) {
			DoomSubSector s = new DoomSubSector(data,segments);
			subsectors.add(s);	
		}
		BossBarAPI.setHealth(Bukkit.getPlayer(playerUUID),80);
		for (byte[] data :packets_of_size(10, lumps.get("THINGS"))) {
			DoomThing t = new DoomThing(data);
			things.add(t);	
		}
		BossBarAPI.setHealth(Bukkit.getPlayer(playerUUID), 100);
	}
	public boolean is_valid() {
		return name != null && lumps.containsKey("VERTEXES") && lumps.containsKey("LINEDEFS");
	}
	public int[] normalize(short[] point) {
		return normalize(point, 5);
	}
	public int[] normalize(short[] point, int padding) {
		return (new int[]{shift[0]+point[0]+padding,shift[1]+point[1]+padding});
	}
	private byte[][] packets_of_size(int i, byte[] bs) {
		byte[][] bytes = new byte[bs.length/i][i];
		int index2 = 0;
		for (int index = 0; index < bs.length; index++) {
			bytes[index<i?0:index/i][index2] = bs[index];
			index2++;
			if (index2 == i) {
				index2 = 0;
			}
		}
		return bytes;
	}
	//Converts from a WAD Based Level to a Configuration based level
	public void convertToDoomLevel() {
		//TODO: In this function, convert from WadLevel to MinecraftLevel
		//TODO: Create an Entity class per mob, then store info about said mob and its abilities, what it throws, its health and the mob
		//it is based on
		//Get the player thing
		DoomThing player = getThingFromType(DoomThing.DoomThingType.Player1);
		//Temporarily store a list of entities
		ArrayList<DoomEntity> entities = new ArrayList<>();
		//Loop through all monsters, and convert them to Entity objects
		for (DoomThing th: getThingsByCatagory(DoomThing.DoomThingCatagory.Monster)) {
			switch (th.type) {
			case Arachnotron: 
				
				break;
			case ArchVile: 
				
				break;
			case Baron_of_Hell: 
				
				break;
			case Cacodemon: 
				
				break;
			case Chaingunner: 
				
				break;
			case Commander_Keen: 
				
				break;
			case Cyberdemon:
				
				break;
			case Demon: 
				
				break;
			case Trooper: 
				
				break;
			case Former_Human_Sergeant: 
				
				break;
			case Hell_Knight: 
				
				break;
			case Imp: 
				Location spawnLoc = new Location(world, th.x,getYFromCoords(th.x,th.y),th.y);
				entities.add(new Imp(plugin,spawnLoc)); 
				break;
			case Mancubus: 
				
				break;
			case Pain_Elemental: 
				
				break;
			case Revenant: 
				
				break;
			case Spectre: 
				
				break;
			case Spider_Mastermind: 
				
				break;
			case Wolfenstein_SS: 
				
				break;
			default:
				//This wont get called, since we are only looping through monsters
				break;


			}
		}
		//Create a temporary list of items
		ArrayList<DoomItem> items = new ArrayList<>();
		//TODO: loop through items and convert them.
		//Gets the location from the player thing
		Location spawnLoc = new Location(world, player.x,getYFromCoords(player.x,player.y),player.y);
		//Create a Configuration based level and add it to the configuration
		DoomLevel l = new DoomLevel(name,spawnLoc, entities, items, plugin);
		//plugin.getDoomConfig().addLevel(l);
	}
	//Get the first occurance of a Doomthing type
	public DoomThing getThingFromType(DoomThing.DoomThingType type) {
		for (DoomThing thing: things) {
			if (thing.type.equals(type)) {
				return thing;
			}
		}
		return null;
	}
	//Get all Doom Things from a specified Catagory
	public ArrayList<DoomThing> getThingsByCatagory(DoomThing.DoomThingCatagory type) {
		ArrayList<DoomThing> things = new ArrayList<DoomThing>();
		for (DoomThing thing: things) {
			if (thing.type.getDoomThingCatagory().equals(type)) {
				things.add(thing);
			}
		}
		return things;
	}
	//Get the y value from the specified x and z coordinates
	public int getYFromCoords(short x, short z) {
		//TODO: return the height of a sector, not the max level height
		return height;

	}
	//Build a doom level
	public void buildLevel() {
		//For the moment, only build the level E1M1
		if (name.equals("E1M1")) {
			//An array of points, so i can create a Java polygon. This could later be used to fill the shape, or to get
			//The height from a point
			HashMap<DoomSector, ArrayList<Point>> coords = new HashMap<>();
			//Loop through and build all lines.
			for (Entry<DoomLine, DoomSide[]> entry: lines.entrySet()) {
				DoomLine l = entry.getKey();
				DoomSide[] s = entry.getValue();
				int[] a = normalize(vertices.get(l.getA()));
				int[] b = normalize(vertices.get(l.getB()));
				for (DoomSide si : s) {
					DoomSector se = sectors.get(si.sectorNum);
					if (!coords.containsKey(si)) 
						coords.put(se, new ArrayList<Point>());
					
					coords.get(se).add(new Point(a[0],a[1]));
					coords.get(se).add(new Point(b[0],b[1]));
					drawLine(a[0],a[1],b[0],b[1],se,si,l);	
				}
				
				
				

			}
			//Fill sub sectors to create a floor and ceiling
			for (DoomSubSector s : subsectors) {
				//Commented out because it doesn't work
				fillSector(s);
			}
		}
	}
	//Attempt one at filling the floor and ceiling - it works, but it tends to overshoot and fill other sectors.
	public void fillSector(DoomSubSector s) {
		
		//Get the sides belonging to the first line - so that we can get a sector number.
		DoomSide[] sides =lines.get(s.getSegments()[0].getLine());
		//Assign a colour of wool to each sector, for testing
		DoomSector sector = sectors.get(sides[0].sectorNum);
		int blockData = sides[0].sectorNum%16; 
		//Get the start and end point of the first side
		int[] a = normalize(vertices.get(s.getSegments()[0].getStartVertex()));
		int[] b = normalize(vertices.get(s.getSegments()[0].getEndVertex()));
		//Get the midpoint of this line
		org.bukkit.util.Vector v = new org.bukkit.util.Vector(width-a[0],80+sector.floorHeight,a[1]);
		org.bukkit.util.Vector v2 = new org.bukkit.util.Vector(width-b[0],80+sector.floorHeight,b[1]);
		org.bukkit.util.Vector midPoint = v.getMidpoint(v2);
		org.bukkit.util.Vector movement = v2.subtract(v);
		if (sides.length == 1) {
			
			//Use worldedit to fill the subsector from the midpoint
			int direction = -2;
			Block fillPoint = midPoint.toLocation(world).getBlock().getRelative(FaceUtil.rotate(FaceUtil.getDirection(movement), direction));

			double radius = 50;
			WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
			Vector pos =new Vector(fillPoint.getX(),fillPoint.getY(),fillPoint.getZ());
			int affected = -1;
			try {
				affected = worldEdit.createEditSession(Bukkit.getPlayer(playerUUID)).fillXZ(pos,
						new BaseBlock(BlockID.CLOTH,blockData),
						radius, 1, false);
			} catch (MaxChangedBlocksException e) {
				e.printStackTrace();
			}
		}
		if (sides.length == 2) {
			//Use worldedit to fill the subsector from the midpoint
			int direction = -2;
			Block fillPoint = midPoint.toLocation(world).getBlock().getRelative(FaceUtil.rotate(FaceUtil.getDirection(movement), direction));

			WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
			Vector pos =new Vector(fillPoint.getX(),fillPoint.getY(),fillPoint.getZ());
			int affected = -1;
			try {
				affected = worldEdit.createEditSession(Bukkit.getPlayer(playerUUID)).fillXZ(pos,
						new BaseBlock(BlockID.CLOTH,blockData),
						50, 1, false);
			} catch (MaxChangedBlocksException e) {
				e.printStackTrace();
			}
			//Use worldedit to fill the subsector from the midpoint - on the opposite side of this line
			sector = sectors.get(sides[1].sectorNum);
			blockData = sides[1].sectorNum%16; 
			direction = 2;
			fillPoint = midPoint.toLocation(world).getBlock().getRelative(FaceUtil.rotate(FaceUtil.getDirection(movement, false), direction));
			pos =new Vector(fillPoint.getX(),fillPoint.getY(),fillPoint.getZ());
			try {
				affected = worldEdit.createEditSession(Bukkit.getPlayer(playerUUID)).fillXZ(pos,
						new BaseBlock(BlockID.CLOTH,blockData),
						50, 1, false);
			} catch (MaxChangedBlocksException e) {
				e.printStackTrace();
			}
		}

	}
	//Draw a line between two points
	public void drawLine(int x, int y, int x2, int y2, DoomSector sector, DoomSide si, DoomLine l) {
		//Start and end of this line
		Location location = new Location(world,width-x,80+sector.floorHeight,y);
		Location newLocation = new Location(world,width-x2,80+sector.floorHeight,y2);
		
		int top = sector.ceilHeight;
		if (l.is_one_sided()) top = height/15;
		//The start and end are the same coordinate
		if (Math.floor(location.distance(newLocation)) < 1) {
			//Loop through and build this wall - based on its material
			for (int i = top > 0?0:top; i < (top > 0?top:0); i++) {
				DoomBlockParser.BlockData data = blockParser.getId(l.is_one_sided(),si,i,top > 0?top:-top);
				if (location.getBlock().getRelative(0, i, 0).getType() == Material.AIR)
				location.getBlock().getRelative(0, i, 0).setTypeIdAndData(data.id,data.data, false);
			}
		} else {
			//Create a block iterator - this will loop through every block in this line
			BlockIterator blocksToAdd = new BlockIterator(location.getWorld(), location.toVector(), newLocation.toVector().subtract(location.toVector()), 0D, (int) Math.floor(location.distance(newLocation)));
			List<Block> blocks = new ArrayList<>();
			//Create an array from the iterator
			while(blocksToAdd.hasNext()) {
				blocks.add(blocksToAdd.next());
			}
			//Loop through the array, and place the block based on its location and material
			for (int i2 = 0; i2 < blocks.size(); i2++) {
				Block bl = blocks.get(i2);
				for (int i = top > 0?0:top; i < (top > 0?top:0); i++) {
					DoomBlockParser.BlockData data = blockParser.getId(l.is_one_sided(),si,i,i2,top > 0?top:-top);
					if (bl.getRelative(0, i, 0).getType() == Material.AIR)
					bl.getRelative(0, i, 0).setTypeIdAndData(data.id,data.data, false);
				}
			}
		}

	}


}
