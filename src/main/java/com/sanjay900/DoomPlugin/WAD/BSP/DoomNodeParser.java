package com.sanjay900.DoomPlugin.WAD.BSP;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.BlockIterator;

import com.google.common.primitives.Shorts;
import com.sanjay900.DoomPlugin.DoomPlugin;
import com.sanjay900.DoomPlugin.WAD.DoomLine;
import com.sanjay900.DoomPlugin.WAD.DoomLineParser;
import com.sanjay900.DoomPlugin.WAD.DoomSector;
import com.sanjay900.DoomPlugin.WAD.DoomSide;
import com.sanjay900.DoomPlugin.WAD.BSP.DoomNode;
import com.sanjay900.DoomPlugin.WAD.BSP.DoomSegment;
import com.sanjay900.DoomPlugin.WAD.BSP.DoomSubSector;
import com.sanjay900.DoomPlugin.util.FaceUtil;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.blocks.BlockID;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class DoomNodeParser {
	DoomPlugin plugin;
	World world;
	String name = null;
	LinkedHashMap<String,byte[]> lumps = new LinkedHashMap<>();
	ArrayList<short[]> vertices = new ArrayList<>();
	Short[] lower_left = new Short[2];
	Short[] upper_right = new Short[2];
	int width = 0;
	int height = 0;
	ArrayList<DoomNode> nodes = new ArrayList<>();
	ArrayList<DoomSector> sectors = new ArrayList<>();
	ArrayList<DoomSubSector> subsectors = new ArrayList<>();
	ArrayList<DoomSegment> segments = new ArrayList<>();
	LinkedHashMap<DoomLine,DoomSide[]> lines = new LinkedHashMap<>();
	int[] shift = new int[2] ;
	private UUID playerUUID;

	public DoomNodeParser(String name, DoomPlugin plugin, UUID playerUUID, World world) {
		this.name = name;
		this.playerUUID = playerUUID;
		this.world = world;
		this.plugin = plugin;

	}
	public void convertToDoomLevel() {
		//TODO: In this function, convert from WadLevel to DoomLevel
	}
	public void drawSector(DoomSubSector s) {
		int blockData =new Random().nextInt(15); 
		for (DoomSegment seg : s.getSegments()) {
			int[] a = normalize(vertices.get(seg.getStartVertex()));
			int[] b = normalize(vertices.get(seg.getEndVertex()));
			drawLine(a[0],a[1],b[0],b[1],null,Material.WOOL,(byte)blockData);
		}




	}
	public void buildLevel() {

		byte data = (byte)new Random().nextInt(15);

		Bukkit.getPlayer(playerUUID).sendMessage(name);
		if (name.equals("E1M1")) {
			for (Entry<DoomLine, DoomSide[]> entry: lines.entrySet()) {
				DoomLine l = entry.getKey();
				int[] a = normalize(vertices.get(l.getA()));
				int[] b = normalize(vertices.get(l.getB()));
				//DoomSector s = sectors.get(l.getSector());

				//drawLine(a[0],a[1],b[0],b[1],s,Material.WOOL,data);	
			}
		}
		if (name.equals("E1M1")) {
			for (DoomSubSector s : subsectors) {
				fillSector(s);
			}
		}
	}



	private void traverseNode(short node) {
		final DoomNode n = nodes.get(node);
		int x = n.getPartLinex();
		int y = n.getPartLiney();
		int x2 = x+ n.getDistanceX();
		int y2 = y+ n.getDistanceY();
		System.out.print(String.valueOf(x)+","+String.valueOf(y)+","+String.valueOf(x2)+","+String.valueOf(y2));
		//drawLine(x/15,height-(y/15),x2/15,height-(y2/15),66,Material.WOOL,(byte)0);
		//draw line
		if (n.isIslSector()) {
			Bukkit.getScheduler().runTask(plugin, new Runnable(){

				@Override
				public void run() {
					traverseNode(n.getlChild());
				}});

		}

		else {
			drawSector(subsectors.get(n.getlChild()));
		}
		if (n.isIsrSector()) {
			
					traverseNode(n.getrChild());
				
		}
		else {
			drawSector(subsectors.get(n.getrChild()));
		}

	}



public void fillSector(DoomSubSector s) {
	//int se =s.getSegments()[0].getLine().getSector();
	//int blockData = se%15; 
	int[] a = normalize(vertices.get(s.getSegments()[0].getStartVertex()));
	int[] b = normalize(vertices.get(s.getSegments()[0].getEndVertex()));
	org.bukkit.util.Vector v = new org.bukkit.util.Vector(width-a[0],66,a[1]);
	org.bukkit.util.Vector v2 = new org.bukkit.util.Vector(width-b[0],66,b[1]);
	org.bukkit.util.Vector midPoint = v.getMidpoint(v2);
	org.bukkit.util.Vector movement = v2.subtract(v);
	int direction = (s.getSegments()[0].getDirection()==(short)1)?-2:2;
	Block fillPoint = midPoint.toLocation(world).getBlock().getRelative(FaceUtil.rotate(FaceUtil.getDirection(movement), direction));

	double radius = 50;
	WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
	Vector pos =new Vector(fillPoint.getX(),fillPoint.getY(),fillPoint.getZ());
	int affected = -1;
	try {
		affected = worldEdit.createEditSession(Bukkit.getPlayer(playerUUID)).fillXZ(pos,
				new BaseBlock(BlockID.CLOTH),
				radius, 1, false);
	} catch (MaxChangedBlocksException e) {
		e.printStackTrace();
	}

	Bukkit.getPlayer(playerUUID).sendMessage(affected + " block(s) have been created.");

}
public void drawLine(int x, int y, int x2, int y2, DoomSector sector, Material blockType, byte data) {

	Location location = new Location(world,width-x,66,y);
	Location newLocation = new Location(world,width-x2,66,y2);
	System.out.print(location.toString());
	System.out.print(newLocation.toString());
	if (Math.floor(location.distance(newLocation)) < 1) {
		location.getBlock().setTypeIdAndData(blockType.getId(), data, false);
	} else {
		BlockIterator blocksToAdd = new BlockIterator(location.getWorld(), location.toVector(), newLocation.toVector().subtract(location.toVector()), 0D, (int) Math.floor(location.distance(newLocation)));
		Block bl;
		while(blocksToAdd.hasNext()) {
			bl = blocksToAdd.next();
			bl.setTypeIdAndData(blockType.getId(), data, false);

		}
	}

}
public void load(String wad_type, DoomLineParser doomLineParser) {

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

	for (int i = 0; i < packets_of_size(4, lumps.get("VERTEXES")).length; i++) {
		ByteBuffer bb = ByteBuffer.wrap(packets_of_size(4, lumps.get("VERTEXES"))[i]);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		short x = bb.getShort();
		short y = bb.getShort();
		x = (short) (x/15);
		y = (short) (y/15);
		vertices.add(new short[]{x,y});
		keys[i]=x;
		values[i]=y;
	}
	lower_left[0] = Shorts.min(keys);
	lower_left[1]= Shorts.min(values);
	upper_right[0] = Shorts.max(keys);
	upper_right[1] = Shorts.max(values);
	shift[0] = 0-lower_left[0];
	shift[1] = 0-lower_left[1];
	width = upper_right[0] - lower_left[0];
	height = upper_right[1] - lower_left[1];
	for (int i = 0; i < packets_of_size(26, lumps.get("SECTORS")).length; i++) {
		DoomSector s = new DoomSector(packets_of_size(26, lumps.get("SECTORS"))[i]);
		sectors.add(s);	
	}

	for (int i = 0; i < packets_of_size(12, lumps.get("SEGS")).length; i++) {
		DoomSegment s = new DoomSegment(packets_of_size(12, lumps.get("SEGS"))[i], lines);
		segments.add(s);	
	}
	for (int i = 0; i < packets_of_size(28, lumps.get("NODES")).length; i++) {
		DoomNode n = new DoomNode(packets_of_size(28, lumps.get("NODES"))[i]);
		nodes.add(n);	
	}
	for (int i = 0; i < packets_of_size(4, lumps.get("SSECTORS")).length; i++) {
		DoomSubSector s = new DoomSubSector(packets_of_size(4, lumps.get("SSECTORS"))[i],segments);
		subsectors.add(s);	
	}
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

}
