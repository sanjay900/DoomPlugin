package com.sanjay900.DoomPlugin.WAD;

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
	public BlockData getId(boolean oneSided, DoomSide si, int h, int maxH, DoomSector sector) {
		return getId(oneSided, si,h,0, maxH, sector);
	}
	public BlockData getMid(DoomSide si, int h, int x) {
		switch (si.midTex) {

		case "-":
			return new BlockData(1,0);
		case "DOOR3":

		}
		return new BlockData(4,0);
	}
	public BlockData getUpper(DoomSide si, int h, int x) {
		switch (si.upperTex) {

		case "-":
			return new BlockData(0,0);
		case "DOOR3":

		}
		return new BlockData(4,0);
	}
	public BlockData getLower(DoomSide si, int h, int x, DoomSector sector) {
		switch (si.lowerTex) {

		case "-":
			//TODO: Parse ground blocks and set this to the sector's ground. should solve flood fill errors.
			return new BlockData(0,0);
		case "DOOR3":

		}
		return new BlockData(4,0);
	}
	public BlockData getId(boolean oneSided, DoomSide si, int h, int x, int maxH, DoomSector sector) {
		if (oneSided) 
			return getMid(si,h,x); 
		if (h == 0) {
			return getLower(si,h,x,sector);
		}
		if (h < maxH)
			return getMid(si,h,x);
		return getUpper(si,h,x);
	}
	public class BlockData {
		int id;
		byte data;
		public BlockData(int id, int data) {
			this.id = id;
			this.data = (byte)data;
		}
	}

}

