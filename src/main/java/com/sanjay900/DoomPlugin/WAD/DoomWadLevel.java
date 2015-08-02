package com.sanjay900.DoomPlugin.WAD;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BlockIterator;
import org.inventivetalent.bossbar.BossBarAPI;

import com.google.common.primitives.Shorts;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.sanjay900.DoomPlugin.DoomPlugin;
import com.sanjay900.DoomPlugin.WAD.DoomLine.SideType;
import com.sanjay900.DoomPlugin.WAD.BSP.DoomNode;
import com.sanjay900.DoomPlugin.WAD.BSP.DoomSegment;
import com.sanjay900.DoomPlugin.WAD.BSP.DoomSubSector;
import com.sanjay900.DoomPlugin.entities.items.DoomItem;
import com.sanjay900.DoomPlugin.entities.mobs.DoomEntity;
import com.sanjay900.DoomPlugin.entities.mobs.Imp;
import com.sanjay900.DoomPlugin.levelElements.DoomLevel;
import com.sk89q.worldedit.EditSession;

public class DoomWadLevel {
	DoomPlugin plugin;
	World world;
	String name = null;
	LinkedHashMap<String,byte[]> lumps = new LinkedHashMap<>();
	ArrayList<short[]> vertices = new ArrayList<>();
	Short[] lower_left = new Short[2];
	Short[] upper_right = new Short[2];
	int width = 0;
	int height = 0;
	ArrayList<DoomThing> things = new ArrayList<>();
	ArrayList<DoomNode> nodes = new ArrayList<>();
	ArrayList<DoomSector> sectors = new ArrayList<>();
	ArrayList<DoomSubSector> subsectors = new ArrayList<>();
	ArrayList<DoomSegment> segments = new ArrayList<>();
	ArrayList<DoomLine> lines = new ArrayList<>();
	ArrayList<DoomSide> sides = new ArrayList<>();
	int[] sdhift = new int[2] ;
	DoomBlockParser blockParser;
	private UUID playerUUID;
	DoomLineParser doomLineParser;

	public DoomWadLevel(String name, DoomPlugin plugin, UUID playerUUID, DoomLineParser doomLineParser) {
		this.blockParser = new DoomBlockParser(this);
		this.doomLineParser = doomLineParser;
		this.name = name;
		this.playerUUID = playerUUID;
		this.plugin = plugin;

	}
	public void load(String wad_type) {
		for (byte[] data : packets_of_size(26, lumps.get("SECTORS"))) {
			DoomSector s = new DoomSector(data);
			sectors.add(s);	
		}
		short[] keys = new short[packets_of_size(4, lumps.get("VERTEXES")).length];
		short[] values = new short[packets_of_size(4, lumps.get("VERTEXES")).length];
		int packet_size = wad_type=="HEXEN"?16:14;
		for (int i = 0; i < packets_of_size(30, lumps.get("SIDEDEFS")).length; i++) {
			DoomSide s = new DoomSide(packets_of_size(30, lumps.get("SIDEDEFS"))[i]);
			sides.add(s);
		} 
		for (int i = 0; i < packets_of_size(packet_size, lumps.get("LINEDEFS")).length; i++) {
			DoomLine l = new DoomLine(packets_of_size(packet_size, lumps.get("LINEDEFS"))[i],doomLineParser,sides);

			lines.add(l);	
		} 
		BossBarAPI.setHealth(Bukkit.getPlayer(playerUUID), 10);

		for (int i = 0; i < packets_of_size(4, lumps.get("VERTEXES")).length; i++) {
			ByteBuffer bb = ByteBuffer.wrap(packets_of_size(4, lumps.get("VERTEXES"))[i]);
			bb.order(ByteOrder.LITTLE_ENDIAN);
			short x = bb.getShort();
			short y = bb.getShort();
			x = (short) (x);
			y = (short) (y);
			vertices.add(new short[]{x,y});
			keys[i]=x;
			values[i]=y;
		}

		BossBarAPI.setHealth(Bukkit.getPlayer(playerUUID), 20);
		lower_left[0] = Shorts.min(keys);
		lower_left[1]= Shorts.min(values);
		upper_right[0] = Shorts.max(keys);
		upper_right[1] = Shorts.max(values);
		width = upper_right[0] - lower_left[0];
		height = upper_right[1] - lower_left[1];

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
	public void convertToDoomLevel() {
		DoomThing player = getThingFromType(DoomThing.DoomThingType.Player1);
		ArrayList<DoomEntity> entities = new ArrayList<>();
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
				break;


			}
		}
		ArrayList<DoomItem> items = new ArrayList<>();
		Location spawnLoc = new Location(world, player.x,getYFromCoords(player.x,player.y),player.y);
		DoomLevel l = new DoomLevel(name,spawnLoc, entities, items, plugin);
	}
	public DoomThing getThingFromType(DoomThing.DoomThingType type) {
		for (DoomThing thing: things) {
			if (thing.type.equals(type)) {
				return thing;
			}
		}
		return null;
	}
	public ArrayList<DoomThing> getThingsByCatagory(DoomThing.DoomThingCatagory type) {
		ArrayList<DoomThing> things = new ArrayList<DoomThing>();
		for (DoomThing thing: things) {
			if (thing.type.getDoomThingCatagory().equals(type)) {
				things.add(thing);
			}
		}
		return things;
	}
	public int getYFromCoords(short x, short z) {
		return height;

	}
	private void setBar(final String message, final float percent) {
		System.out.println(message);
		//BossBarAPI.setMessage(Bukkit.getPlayer(playerUUID), message, percent);
	}
	public void buildLevel() {
		setBar("[DOOM] Creating World", 1f);
		Bukkit.getOnlinePlayers().forEach(p -> p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation()));
		if (Bukkit.getWorld(name) != null) {

			//getMultiverseCore().regenWorld(name, false, true, "");
		} else {
			getMultiverseCore().getMVWorldManager().addWorld(name, Environment.NORMAL, "", WorldType.FLAT, false, "CleanroomGenerator:1,bedrock");
		}
		getMultiverseCore().getMVWorldManager().getMVWorld(name).setAllowMonsterSpawn(false);
		getMultiverseCore().getMVWorldManager().getMVWorld(name).setAllowAnimalSpawn(false);
		world = Bukkit.getWorld(name);
		Bukkit.getOnlinePlayers().forEach(p -> p.teleport(Bukkit.getWorlds().get(1).getSpawnLocation()));


		HashMap<DoomSector, ArrayList<Line>> coords = new HashMap<>();
		for (DoomLine l: lines) {
			short[] a = vertices.get(l.getA());
			short[] b = vertices.get(l.getB());

			if (l.twoSided) {
				DoomSide sil = l.getLeftSide();
				DoomSide sir = l.getRightSide();
				DoomSector sl = sectors.get(sil.sectorNum);
				DoomSector sr = sectors.get(sir.sectorNum);
				Line sll = new Line(new Point(a[0]/20,a[1]/20),new Point(b[0]/20,b[1]/20),new Line(new Point(a[0],a[1]),new Point(b[0],b[1]),null, l, sil), l, sil);
				Line slr = new Line(new Point(a[0]/20,a[1]/20),new Point(b[0]/20,b[1]/20),new Line(new Point(a[0],a[1]),new Point(b[0],b[1]),null, l, sir), l, sir);
				if (!coords.containsKey(sl)) 
					coords.put(sl, new ArrayList<Line>());
				coords.get(sl).add(sll);
				if (!coords.containsKey(sr)) 
					coords.put(sr, new ArrayList<Line>());
				coords.get(sr).add(slr);

				drawLine(slr,sr);
				drawLine(sll,sl);
			} else {
				DoomSide sil = l.getLeftSide();
				DoomSide sir = l.getRightSide();
				if (l.getDisabledSide() == SideType.LEFT) {
					Line slr = new Line(new Point(a[0]/20,a[1]/20),new Point(b[0]/20,b[1]/20),new Line(new Point(a[0],a[1]),new Point(b[0],b[1]),null, l, sir), l, sir);
					DoomSector sr = sectors.get(sir.sectorNum);
					drawLine(slr,sr);
					if (!coords.containsKey(sr)) 
						coords.put(sr, new ArrayList<Line>());
					coords.get(sr).add(slr);
				} else if (l.getDisabledSide() == SideType.RIGHT) {
					Line sll = new Line(new Point(a[0]/20,a[1]/20),new Point(b[0]/20,b[1]/20),new Line(new Point(a[0],a[1]),new Point(b[0],b[1]),null, l, sil), l, sil);
					DoomSector sl = sectors.get(sil.sectorNum);
					drawLine(sll,sl);
					if (!coords.containsKey(sl)) 
						coords.put(sl, new ArrayList<Line>());
					coords.get(sl).add(sll);
				}
			}
		}
		System.out.println("Ray fill.");
		for (DoomSector s : sectors) {
			rayFill(coords.get(s),s);
		}



	}
	public MultiverseCore getMultiverseCore() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");

		if (plugin instanceof MultiverseCore) {
			return (MultiverseCore) plugin;
		}

		throw new RuntimeException("Multiverse not found!");
	}
	static double[][] getPoints(Path2D path) {
	    List<double[]> pointList = new ArrayList<double[]>();
	    double[] coords = new double[6];
	    int numSubPaths = 0;
	    for (PathIterator pi = path.getPathIterator(null);
	         ! pi.isDone();
	         pi.next()) {
	        switch (pi.currentSegment(coords)) {
	        case PathIterator.SEG_MOVETO:
	            pointList.add(Arrays.copyOf(coords, 2));
	            ++ numSubPaths;
	            break;
	        case PathIterator.SEG_LINETO:
	            pointList.add(Arrays.copyOf(coords, 2));
	            break;
	        case PathIterator.SEG_CLOSE:
	            if (numSubPaths > 1) {
	                throw new IllegalArgumentException("Path contains multiple subpaths");
	            }
	            return pointList.toArray(new double[pointList.size()][]);
	        default:
	            throw new IllegalArgumentException("Path contains curves");
	        }
	    }
	    throw new IllegalArgumentException("Unclosed path");
	}
	byte d = 0;
	public void rayFill(ArrayList<Line> lines,DoomSector s) {
		if (d > 15) d = 0;
		GeneralPath polyline = 
				new GeneralPath(GeneralPath.WIND_EVEN_ODD, lines.size());
		polyline.moveTo(lines.get(0).orig.p1.getX(),lines.get(0).orig.p1.getY());
		for (Line l : lines) {
			polyline.moveTo(l.orig.p1.getX(),l.orig.p1.getY());
			polyline.lineTo(l.orig.p2.getX(),l.orig.p2.getY());	
		}
		polyline.closePath();
		System.out.println(getPoints(polyline));
		Rectangle bounds = polyline.getBounds();
		for (int x = bounds.x; x < bounds.getWidth()+bounds.x; x++) {
			for (int y = bounds.y; y < bounds.getHeight()+bounds.y; y++) {
				if (polyline.contains(x, y)) {
					world.getBlockAt((width-x)/20, 10+(s.floorHeight), y/20).setTypeIdAndData(Material.WOOL.getId(),d,false);
				}
			}
		}
		d++;
	}

	public void drawLine(Line line, DoomSector sector) {
		drawLine(line,sector,10+sector.ceilHeight);
	}
	public void drawLine(Line line, DoomSector sector,int height) {
		Location location = new Location(world,(width-line.p1.x),10+(sector.floorHeight),line.p1.y);
		Location newLocation = new Location(world,(width-line.p2.x),10+(sector.floorHeight),line.p2.y);
		int top = 10+height;
		if (Math.floor(location.distance(newLocation)) < 1) {
			for (int i = top > 0?0:top; i < (top > 0?top:0); i++) {
				DoomBlockParser.BlockData data = blockParser.getId(!line.line.twoSided, line.side, i, top, sector);
				location.getBlock().getRelative(0, i, 0).setTypeIdAndData(data.id,data.data, false);
			}
		} else {
			BlockIterator blocksToAdd = new BlockIterator(location.getWorld(), location.toVector(), newLocation.toVector().subtract(location.toVector()), 0D, (int) Math.floor(location.distance(newLocation)));
			List<Block> blocks = new ArrayList<>();
			while(blocksToAdd.hasNext()) {
				blocks.add(blocksToAdd.next());
			}
			for (int i2 = 0; i2 < blocks.size(); i2++) {
				Block bl = blocks.get(i2);
				for (int i = top > 0?0:top; i < (top > 0?top:0); i++) {
					DoomBlockParser.BlockData data = blockParser.getId(!line.line.twoSided, line.side, i, top, sector);
					//if (bl.getRelative(0, i, 0).getType() == Material.AIR)
					bl.getRelative(0, i, 0).setTypeIdAndData(data.id,data.data, false);
				}
			}
		}
	}
	EditSession es;
	@AllArgsConstructor
	@Getter
	private class Line {
		Point p1;
		Point p2;
		Line orig;
		DoomLine line;
		DoomSide side;
	}
}

