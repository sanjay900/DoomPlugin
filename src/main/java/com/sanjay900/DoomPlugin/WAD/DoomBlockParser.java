package com.sanjay900.DoomPlugin.WAD;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

/**
 * 
 * This class is responsible for getting the material of a block
 *
 */
public class DoomBlockParser {

	DoomWadLevel level;
	public DoomBlockParser(DoomWadLevel doomWadLevel) {
		this.level = doomWadLevel;
	}


	public BlockData getFloor(DoomSector s, int dec) {
		return getFlat(s.floor, dec);
	}
	public BlockData getCeil(DoomSector s, int dec) {
		return getFlat(s.ceil, dec);
	}
	public BlockData getFlat(String name, int dec) {
		switch (name) {
		case "-":
			return new BlockData(0,0);
		case "NUKAGE1":
		case "NUKAGE2":
		case "NUKAGE3":
			return new BlockData(Material.SPONGE,0);
		case "FLOOR7_1":
			return new BlockData(5,5);
		case "FLOOR4_8":
		case "FLOOR5_1":
			return new BlockData(98,1);
		case "FLOOR5_2":
		case "FLOOR5_3":
			return new BlockData(4,0);
		case "FLOOR7_2":
			return new BlockData(3,0);
		case "TLITE6_1":
			return new BlockData(95,14);
		case "FLAT14":
			return new BlockData(Material.LAPIS_BLOCK,0);
		case "FLAT20":
			return new BlockData(Material.QUARTZ_BLOCK,0);
		}
		System.out.println(name);
		return new BlockData(4,0);

	}
	public class BlockData {
		int id;
		byte data;
		public BlockData(int id, int data) {
			this.id = id;
			this.data = (byte)data;
		}
		public BlockData(Material mt, int data) {
			this.id = mt.getId();
			this.data = (byte)data;
		}
	}
	/**
	 * Get Texture for middle of LineDef
	 * @param name - The texture name
	 * @param x - X position on wall
	 * @param y - Y position on wall
	 * @param b - Direction of LineDef
	 * @return BlockData representing the data
	 */
	public BlockData getSide(String name, int x, int y, int top, BlockFace b) {
		switch (name) {
		case "-":
			return new BlockData(0,0);
		case "BIGDOOR2":

			System.out.println(b);
			System.out.println(top);
			System.out.println(x);
			System.out.println(y);

			switch (b) {
			case NORTH:
			case SOUTH:
				switch (x+1) {
				case 1:
					switch (y+1) {
					case 1:
						return new BlockData(Material.WOOL,5);
					case 3:
						return new BlockData(Material.WOOL,6);

					case 2:
					case 4:
						return new BlockData(Material.BARRIER,0);
					}
				case 2:
					return new BlockData(Material.BARRIER,0);
				case 3:
					switch (y+1) {
					case 1:
						return new BlockData(Material.WOOL,7);
					case 3:
						return new BlockData(Material.WOOL,9);

					case 2:
					case 4:
						return new BlockData(Material.BARRIER,0);
					}
				case 4:
					return new BlockData(Material.BARRIER,0);
				}
			case EAST:
			case WEST:
				switch (x+1) {
				case 1:
					switch (y+1) {
					case 1:
						return new BlockData(Material.WOOL,2);
					case 3:
						return new BlockData(Material.WOOL,1);

					case 2:
					case 4:
						return new BlockData(Material.BARRIER,0);
					}
				case 2:
					return new BlockData(Material.BARRIER,0);
				case 3:
					switch (y) {
					case 1:
						return new BlockData(Material.WOOL,4);
					case 3:
						return new BlockData(Material.WOOL,3);

					case 2:
					case 4:
						return new BlockData(Material.BARRIER,0);
					}
				case 4:
					return new BlockData(Material.BARRIER,0);
				}
			default:
				break;
			}
			break;
		case "BRNBIGC":
		case "BRNBIGL":
		case "BRNBIGR":
			return new BlockData(101,0);
		case "STARGR1":
			if (y%2==0) {
				return new BlockData(24,2);
			}
			return new BlockData(24,1);

		case "BROWNGRN":
			if (y==top-1) {
				return new BlockData(12,1);
			}
			return new BlockData(12,0);
		}
		//System.out.println(name);
		return new BlockData(4,0);
	}
	/**
	 * Get Texture for middle of LineDef - Taking semi blocks into account
	 * @param name - The texture name
	 * @param x - X position on wall
	 * @param y - Y position on wall
	 * @param dec - The decimal representation of the slab
	 * @param b - Direction of LineDef
	 * @return BlockData representing the data
	 */
	public BlockData getSide(String name, int x, int y, int dec, int top, BlockFace b) {
		if (dec == 0) {
			return getSide(name,x,y,top,b);
		}
		//TODO: add a custom parser just for slabs.
		return getSide(name,x,y,top,b);
	}

}

