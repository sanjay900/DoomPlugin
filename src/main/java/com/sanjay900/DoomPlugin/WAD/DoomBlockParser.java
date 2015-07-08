package com.sanjay900.DoomPlugin.WAD;

/**
 * 
 * This class is responsible for getting the material of a block
 *
 */
//TODO: Add all the doom materials and their block types.
public class DoomBlockParser {

	//Initilize this class
	DoomWadLevel level;
	public DoomBlockParser(DoomWadLevel doomWadLevel) {
		this.level = doomWadLevel;
	}
	//Get the type of a block, without an x value. This is for diagonal walls.
	public BlockData getId(boolean oneSided, DoomSide si, int h, int maxH) {
		return getId(oneSided, si,h,0, maxH);
	}
	//Get the middle texture
	private BlockData getMid(DoomSide si, int h, int x) {
		//Loop through all doom texture names
		switch (si.midTex) {

		case "-":
			return new BlockData(0,0);
		case "DOOR3":

		}
		return new BlockData(4,0);
	}
	//Get the upper texture
	private BlockData getUpper(DoomSide si, int h, int x) {
		switch (si.upperTex) {

		case "-":
			return new BlockData(0,0);
		case "DOOR3":

		}
		return new BlockData(4,0);
	}
	//Get the lower texture
	private BlockData getLower(DoomSide si, int h, int x) {
		switch (si.lowerTex) {

		case "-":
			return new BlockData(0,0);
		case "DOOR3":

		}
		return new BlockData(4,0);
	}
	//Get the texture this coord of h and x represents. then return its material
	public BlockData getId(boolean oneSided, DoomSide si, int h, int x, int maxH) {
		if (oneSided) 
			return getMid(si,h,x); 
		//This is a lower texture because its the first h coord
		if (h == 0) {
			return getLower(si,h,x);
		}
		//This is a mid texture because it isnt the last h coord
		if (h < maxH)
			return getMid(si,h,x);
		//Neither of the functions above have returned, so this is the upper texture.
		return getUpper(si,h,x);
	}
	//Utility object that stores a blocks id and data.
	public class BlockData {
		int id;
		byte data;
		public BlockData(int id, int data) {
			this.id = id;
			this.data = (byte)data;
		}
	}

}

