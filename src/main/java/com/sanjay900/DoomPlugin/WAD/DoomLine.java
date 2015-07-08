package com.sanjay900.DoomPlugin.WAD;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.sanjay900.DoomPlugin.DoomPlugin;
//This class stores information about a line.
public class DoomLine {
	//Global variables, these are accessed by other objects that need information about a line.
	private short a;
	private short b;
	short left_side;
	short right_side;
	DoomLineParser.LineType type;
	private short sectorTag;
	//flags
	boolean blocksPl;
	boolean blocksMonsters;
	boolean twoSided;
	boolean upUnPeg;
	boolean lowUnPeg;
	boolean secret;
	boolean blocksSound;
	boolean neverShowOnMap;
	boolean alwaysShowOnMap;
	
	public DoomLine(byte[] data, DoomLineParser doomLineParser) {
		//This reads the data lump, and converts it into the variables above.
		ByteBuffer bb = ByteBuffer.wrap(data);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		//Get the coords of this line
		this.a = (bb.getShort(0));
		this.b = (bb.getShort(2));
		//Get the flags
		short flags = bb.getShort(4);
		//Use LineParser to get this lines type
		type = doomLineParser.LineTypes.get((Integer)(int)bb.getShort(6));
		//A sector tag tells us what sector this line controls when activated. for example, controlling a door when you cross a line.
		this.sectorTag = (bb.getShort(8));
		left_side = bb.getShort(10);
		right_side = bb.getShort(12);
		//Checks if specific bits are on or off.
		blocksPl = (flags & 0x0001) != 0;
		blocksMonsters = (flags & 0x0002) != 0;
		twoSided = (flags & 0x0004) != 0;
		upUnPeg = (flags & 0x0008) != 0;
		lowUnPeg = (flags & 0x0010) != 0;
		secret = (flags & 0x0020) != 0;
		blocksSound = (flags & 0x0040) != 0;
		neverShowOnMap = (flags & 0x0080) != 0;
		alwaysShowOnMap = (flags & 0x0100) != 0;
		
	}
	//Getters
	public DoomLineParser.LineType getType() {
		return type;
	}
	public boolean is_one_sided() {
		return !twoSided;
	}
	public short getSectorTag() {
		return sectorTag;
	}
	public short getB() {
		return b;
	}
	public short getA() {
		return a;
	}
}
