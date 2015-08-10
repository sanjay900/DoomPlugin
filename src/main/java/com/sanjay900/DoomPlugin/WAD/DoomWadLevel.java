package com.sanjay900.DoomPlugin.WAD;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.GeneralPath;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BlockIterator;
import org.inventivetalent.bossbar.BossBarAPI;

import com.google.common.primitives.Ints;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.sanjay900.DoomPlugin.DoomPlugin;
import com.sanjay900.DoomPlugin.WAD.DoomBlockParser.BlockData;
import com.sanjay900.DoomPlugin.WAD.DoomLine.SideType;
import com.sanjay900.DoomPlugin.WAD.BSP.DoomGLSegment;
import com.sanjay900.DoomPlugin.WAD.BSP.DoomGLSubSector;
import com.sanjay900.DoomPlugin.WAD.BSP.DoomNode;
import com.sanjay900.DoomPlugin.WAD.BSP.DoomSegment;
import com.sanjay900.DoomPlugin.WAD.BSP.DoomSubSector;
import com.sanjay900.DoomPlugin.entities.items.DoomItem;
import com.sanjay900.DoomPlugin.entities.mobs.DoomEntity;
import com.sanjay900.DoomPlugin.entities.mobs.Imp;
import com.sanjay900.DoomPlugin.levelElements.DoomLevel;
import com.sanjay900.DoomPlugin.util.FaceUtil;
import com.sk89q.worldedit.EditSession;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class DoomWadLevel {
	DoomPlugin plugin;
	World world;
	String name = null;
	LinkedHashMap<String,byte[]> lumps = new LinkedHashMap<>();
	ArrayList<Point.Float> vertices = new ArrayList<>();
	ArrayList<Point.Float> glvertices = new ArrayList<>();
	int[] lower_left = new int[2];
	int[] upper_right = new int[2];
	int width = 0;
	int height = 0;
	ArrayList<DoomThing> things = new ArrayList<>();
	ArrayList<DoomNode> nodes = new ArrayList<>();
	ArrayList<DoomSector> sectors = new ArrayList<>();
	ArrayList<DoomSubSector> subsectors = new ArrayList<>();
	ArrayList<DoomSegment> segments = new ArrayList<>();
	ArrayList<DoomGLSegment> glsegments = new ArrayList<>();
	ArrayList<DoomLine> lines = new ArrayList<>();
	ArrayList<DoomSide> sides = new ArrayList<>();
	int[] sdhift = new int[2] ;
	DoomBlockParser blockParser;
	private UUID playerUUID;
	DoomLineParser doomLineParser;
	private ArrayList<DoomGLSubSector> glsubsectors = new ArrayList<>();;

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
		byte[] glverts= Arrays.copyOfRange(lumps.get("GL_VERT"), 4, lumps.get("GL_VERT").length);
		int[] keys = new int[packets_of_size(4, lumps.get("VERTEXES")).length+packets_of_size(8, glverts).length];
		int[] values = new int[packets_of_size(4, lumps.get("VERTEXES")).length+packets_of_size(8, glverts).length];
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
			vertices.add(new Point.Float(x,y));
			keys[i]=x;
			values[i]=y;
		}
		int i2 = packets_of_size(4, lumps.get("VERTEXES")).length-1;
		String name = "";
		try {
			name = new String(new byte[]{lumps.get("GL_VERT")[0],lumps.get("GL_VERT")[1],lumps.get("GL_VERT")[2],lumps.get("GL_VERT")[3]}, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(name);
		System.out.println(packets_of_size(8, glverts).length);

		for (int i = 0; i < packets_of_size(8, glverts).length; i++) {
			ByteBuffer bb = ByteBuffer.wrap(packets_of_size(8, glverts)[i]);
			bb.order(ByteOrder.LITTLE_ENDIAN);
			int x = bb.getInt();
			int y = bb.getInt();
			glvertices.add(new Point.Float(x/65536f,y/65536f));
			keys[i2+i]=x/65536;
			values[i2+i]=y/65536;
		}

		BossBarAPI.setHealth(Bukkit.getPlayer(playerUUID), 20);
		lower_left[0] = Ints.min(keys);
		lower_left[1]= Ints.min(values);
		upper_right[0] = Ints.max(keys);
		upper_right[1] = Ints.max(values);
		width = upper_right[0] - lower_left[0];
		height = upper_right[1] - lower_left[1];

		BossBarAPI.setHealth(Bukkit.getPlayer(playerUUID), 30);
		for (byte[] data : packets_of_size(12, lumps.get("SEGS"))) {
			DoomSegment s = new DoomSegment(data, lines);
			segments.add(s);	
		}
		for (byte[] data : packets_of_size(10, lumps.get("GL_SEGS"))) {
			DoomGLSegment s = new DoomGLSegment(data, lines);
			glsegments.add(s);	
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
		for (byte[] data :packets_of_size(4, lumps.get("GL_SSECT"))) {
			DoomGLSubSector s = new DoomGLSubSector(data,glsegments);
			glsubsectors.add(s);
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
		//Bukkit.getOnlinePlayers().forEach(p -> p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation()));
		if (Bukkit.getWorld(name) != null) {
			//getMultiverseCore().regenWorld(name, false, false, "");
		} else {
			getMultiverseCore().getMVWorldManager().addWorld(name, Environment.NORMAL, "", WorldType.FLAT, false, "CleanroomGenerator:1,bedrock");
		}
		getMultiverseCore().getMVWorldManager().getMVWorld(name).setAllowMonsterSpawn(false);
		getMultiverseCore().getMVWorldManager().getMVWorld(name).setAllowAnimalSpawn(false);
		world = Bukkit.getWorld(name);
		//Bukkit.getOnlinePlayers().forEach(p -> p.teleport(Bukkit.getWorlds().get(1).getSpawnLocation()));


		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
			for (DoomGLSubSector s : glsubsectors) {
				rayFill(s);
			}
		});
		HashMap<DoomSector, ArrayList<Line>> coords = new HashMap<>();
		for (DoomLine l: lines) {
			Point.Float a = vertices.get(l.getA());
			Point.Float b = vertices.get(l.getB());

			if (l.twoSided) {
				DoomSide sil = l.getLeftSide();
				DoomSide sir = l.getRightSide();
				DoomSector sl = sectors.get(sil.sectorNum);
				DoomSector sr = sectors.get(sir.sectorNum);
				Line sll = new Line(new Point.Float(a.x/32,a.y/32),new Point.Float(b.x/32,b.y/32), l, sil);
				Line slr = new Line(new Point.Float(a.x/32,a.y/32),new Point.Float(b.x/32,b.y/32), l, sir);
				if (!coords.containsKey(sl)) 
					coords.put(sl, new ArrayList<Line>());
				coords.get(sl).add(sll);
				if (!coords.containsKey(sr)) 
					coords.put(sr, new ArrayList<Line>());
				coords.get(sr).add(slr);

				drawTwoSidedLine(sll,slr,sl,sr);
			} else {
				DoomSide sil = l.getLeftSide();
				DoomSide sir = l.getRightSide();
				if (l.getDisabledSide() == SideType.LEFT) {
					Line slr = new Line(new Point.Float(a.x/32,a.y/32),new Point.Float(b.x/32,b.y/32), l, sir);
					DoomSector sr = sectors.get(sir.sectorNum);
					drawOneSidedLine(slr,sr);
					if (!coords.containsKey(sr)) 
						coords.put(sr, new ArrayList<Line>());
					coords.get(sr).add(slr);
				} else if (l.getDisabledSide() == SideType.RIGHT) {
					Line sll = new Line(new Point.Float(a.x/32,a.y/32),new Point.Float(b.x/32,b.y/32), l, sil);
					DoomSector sl = sectors.get(sil.sectorNum);
					drawOneSidedLine(sll,sl);
					if (!coords.containsKey(sl)) 
						coords.put(sl, new ArrayList<Line>());
					coords.get(sl).add(sll);
				}
			}
		}



	}
	public MultiverseCore getMultiverseCore() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");

		if (plugin instanceof MultiverseCore) {
			return (MultiverseCore) plugin;
		}

		throw new RuntimeException("Multiverse not found!");
	}
	public HashMap<DoomSector,ArrayList<GeneralPath>> glssectors = new HashMap<>();
	public void rayFill(DoomGLSubSector s) {
		ArrayList<Line> lines = new ArrayList<>();
		DoomSector sec = null;
		for (DoomGLSegment seg : s.getSegments()) {
			Point.Float p1;
			if (seg.isStartVertGL()) {
				p1=glvertices.get(seg.getStartVertex());
			} else {
				p1=vertices.get(seg.getStartVertex());
			}
			Point.Float p2;
			if (seg.isEndVertGL()) {
				p2=glvertices.get(seg.getEndVertex());
			} else {
				p2=vertices.get(seg.getEndVertex());
			}
			lines.add(new Line(p1, p2, null, null));
			if (seg.isHasLine()) {
				if (seg.getDirection() == 0)
					sec = sectors.get(seg.getLine().leftSide.sectorNum);
				else {
					sec = sectors.get(seg.getLine().rightSide.sectorNum);	
				}
			}
		}
		final DoomSector sec2 = sec;
		GeneralPath polyline = 
				new GeneralPath(GeneralPath.WIND_EVEN_ODD, lines.size());

		polyline.moveTo(lines.get(0).p1.getX(),lines.get(0).p1.getY());
		for (Line l : lines) {
			polyline.lineTo(l.p1.getX(),l.p1.getY());
			polyline.lineTo(l.p2.getX(),l.p2.getY());	
		}
		polyline.closePath();
		if (!glssectors.containsKey(sec2)) {
			glssectors.put(sec2, new ArrayList<>());
		}
		ArrayList<GeneralPath> gp = glssectors.get(sec2);
		gp.add(polyline);
		glssectors.put(sec2, gp);
		float h = 10+(sec2.floorHeight);
		int dec = (int)((h-((int)h))*100);
		BlockData b = blockParser.getFloor(sec2,dec);
		h = 10+(sec2.ceilHeight);
		int dec2 = (int)((h-((int)h))*100);
		BlockData b2 = blockParser.getCeil(sec2,dec2);
		Bukkit.getScheduler().runTask(plugin, () -> {
			Rectangle bounds = polyline.getBounds();
			for (int x = bounds.x; x < bounds.getWidth()+bounds.x; x++) {
				for (int y = bounds.y; y < bounds.getHeight()+bounds.y; y++) {
					if (polyline.contains(x, y)) {
						world.getBlockAt(x/32, (int) (10+(sec2.floorHeight)), y/32).setTypeIdAndData(b.id,b.data,false);
						world.getBlockAt(x/32, (int) (10+(sec2.ceilHeight)), y/32).setTypeIdAndData(b2.id,b2.data,false);
					}
				}
			}
		});
	}
	public void drawLine(Location start, Location end, int fdec, int cdec, int top, String texture) {
		BlockFace b = FaceUtil.getDirection(end.toVector().subtract(start.toVector()));
		if (Math.floor(start.distance(end)) < 1) {
			for (int y = 0;y<top; y++) {
				DoomBlockParser.BlockData data = blockParser.getSide(texture, 0,y,top,b);
				if (y == 0) {
					data = blockParser.getSide(texture, 0,y, fdec, top,b);
				}
				if (y == top-1) {
					data = blockParser.getSide(texture, 0,y, cdec, top,b);
				}
				start.getBlock().getRelative(0, y, 0).setTypeIdAndData(data.id,data.data, false);
			}
		} else {
			BlockIterator blocksToAdd = new BlockIterator(start.getWorld(), start.toVector(), end.toVector().subtract(start.toVector()), 0D, (int) Math.floor(start.distance(end)));
			List<Block> blocks = new ArrayList<>();
			while(blocksToAdd.hasNext()) {
				blocks.add(blocksToAdd.next());
			}
			for (int x = 0; x < blocks.size(); x++) {
				Block bl = blocks.get(x);
				for (int y = 0; y<top; y++) {
					DoomBlockParser.BlockData data = blockParser.getSide(texture, x,y,top,b);
					if (y == 0) {
						data = blockParser.getSide(texture, x,y, fdec, top,b);
					}
					if (y == top-1) {
						data = blockParser.getSide(texture, x,y, cdec, top,b);
					}
					bl.getRelative(0, y, 0).setTypeIdAndData(data.id,data.data, false);
				}
			}
		}
	}
	public void drawOneSidedLine(Line line, DoomSector sector) {
		Location location = new Location(world,line.p1.x,10+(sector.floorHeight),line.p1.y);
		Location newLocation = new Location(world,line.p2.x,10+(sector.floorHeight),line.p2.y);
		int top = (int) (10+sector.ceilHeight);
		float h = 10+sector.ceilHeight;
		int cdec = (int)((h-((int)h))*100);
		float h2 = 10+sector.floorHeight;
		int fdec = (int)((h2-((int)h2))*100);
		drawLine(location, newLocation, fdec,cdec,top,line.side.midTex);
		
	}
	public void drawTwoSidedLine(Line left,Line right, DoomSector sl,DoomSector sr) {
		Location leftfloor = new Location(world,left.p1.x,10+(sl.floorHeight),left.p1.y);
		Location leftfloordest = new Location(world,left.p2.x,10+(sl.floorHeight),left.p2.y);
		Location rightfloor = new Location(world,right.p1.x,10+(sr.floorHeight),right.p1.y);
		Location rightfloordest = new Location(world,right.p2.x,10+(sr.floorHeight),right.p2.y);
		Location leftceil = new Location(world,left.p1.x,10+(sl.ceilHeight),left.p1.y);
		Location leftceildest = new Location(world,left.p2.x,10+(sl.ceilHeight),left.p2.y);
		Location rightceil = new Location(world,right.p1.x,10+(sr.ceilHeight),right.p1.y);
		Location rightceildest = new Location(world,right.p2.x,10+(sr.ceilHeight),right.p2.y);
		float h = 10+sl.floorHeight;
		int lfdec = (int)((h-((int)h))*100);
		h = 10+sl.ceilHeight;
		int lcdec = (int)((h-((int)h))*100);
		h = 10+sr.floorHeight;
		int rfdec = (int)((h-((int)h))*100);
		h = 10+sr.ceilHeight;
		int rcdec = (int)((h-((int)h))*100);
		if (sl.floorHeight < sr.floorHeight) {
			//Lower texture between Left floor and right floor
			drawLine(leftfloor,leftfloordest,lfdec,rfdec,(int) (sr.floorHeight-sl.floorHeight),left.side.lowerTex);
			if (sl.ceilHeight < sr.ceilHeight) {
				//Mid texture between Right floor and left ceil
				//Upper texture between Left ceil and right ceil
				drawLine(rightfloor,rightfloordest,rfdec,lcdec,(int) (sl.ceilHeight-sr.floorHeight),left.side.midTex);
				drawLine(leftceil,leftceildest,lcdec,rcdec,(int) (sr.ceilHeight-sl.ceilHeight),left.side.upperTex);
				
			} else {
				//Mid texture between Right floor and Right ceil
				//Upper texture between Right ceil and Left ceil
				drawLine(rightfloor,rightfloordest,rfdec,rcdec,(int) (sr.ceilHeight-sr.floorHeight),left.side.midTex);
				drawLine(rightceil,rightceildest,rcdec,lcdec,(int) (sl.ceilHeight-sr.ceilHeight),left.side.upperTex);
			}
		} else {
			//Lower texture between right floor and left floor
			drawLine(rightfloor,rightfloordest,rfdec,lfdec,(int) (sl.floorHeight-sr.floorHeight),left.side.lowerTex);
			if (sl.ceilHeight < sr.ceilHeight) {
				//Mid texture between left floor and left ceil
				//Upper texture between Left ceil and right ceil
				drawLine(leftfloor,leftfloordest,lfdec,lcdec,(int) (sl.ceilHeight-sl.floorHeight),left.side.midTex);
				drawLine(leftceil,leftceildest,lcdec,rcdec,(int) (sr.ceilHeight-sl.ceilHeight),left.side.upperTex);
			} else {
				//Mid texture between left floor and Right ceil
				//Upper texture between Right ceil and Left ceil
				drawLine(leftfloor,leftfloordest,lfdec,rcdec,(int) (sr.ceilHeight-sl.floorHeight),left.side.midTex);
				drawLine(rightceil,rightceildest,rcdec,lcdec,(int) (sl.ceilHeight-sr.ceilHeight),left.side.upperTex);
			}
		}
	}
	EditSession es;
	@AllArgsConstructor
	@Getter
	private class Line {
		Point.Float p1;
		Point.Float p2;
		DoomLine line;
		DoomSide side;
	}
}

