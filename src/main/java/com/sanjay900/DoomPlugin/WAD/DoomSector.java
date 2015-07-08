package com.sanjay900.DoomPlugin.WAD;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
//this class store information about a sector
public class DoomSector {
	//Global variables, these are accessed by other objects that need information about a sector.
	//Sector textures
	String ceil = "Unknown";
	String floor = "Unknown";
	//Sector information
	short ceilHeight = -1;
	short floorHeight = -1;
	short lightLevel = -1;
	short type = -1;
	short sectorTag = -1;
	//Create a DoomSector object from a lump of data
	public DoomSector(byte[] data) {
		ByteBuffer bb = ByteBuffer.wrap(data);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		//The divide by 16 is because doom and minecraft have differing scales. Minecraft works by blocks, doom works by pixels.
		floorHeight = (short) (bb.getShort(0)/16);
		ceilHeight = (short) (bb.getShort(2)/16);
		lightLevel = bb.getShort(20);
		type = bb.getShort(22);
		sectorTag = bb.getShort(24);
		try {
			ceil = new String(Arrays.copyOfRange(data, 4, 12), "UTF-8");
			ceil = ceil.replaceAll("\0", "");			
			floor = new String(Arrays.copyOfRange(data, 12, 20), "UTF-8");
			floor = floor.replaceAll("\0", "");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
	}
	public String toString() {
		return String.valueOf(ceilHeight)+","+String.valueOf(floorHeight)+","+ceil+","+floor+","+String.valueOf(lightLevel)+","+String.valueOf(type)+","+String.valueOf(sectorTag);
	}
}
