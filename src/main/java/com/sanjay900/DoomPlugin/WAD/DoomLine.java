package com.sanjay900.DoomPlugin.WAD;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import lombok.Getter;

@Getter
public class DoomLine {
	private short a;
	private short b;
	short leftSideId;
	short rightSideId;
	DoomLineParser.LineType type;
	private short sectorTag;
	boolean blocksPl;
	boolean blocksMonsters;
	boolean twoSided;
	boolean upUnPeg;
	boolean lowUnPeg;
	boolean secret;
	boolean blocksSound;
	boolean neverShowOnMap;
	boolean alwaysShowOnMap;
	DoomSide leftSide;
	DoomSide rightSide;
	SideType disabledSide = SideType.NONE;

	public DoomLine(byte[] data, DoomLineParser doomLineParser, ArrayList<DoomSide> sides) {
		ByteBuffer bb = ByteBuffer.wrap(data);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		this.a = (bb.getShort(0));
		this.b = (bb.getShort(2));
		short flags = bb.getShort(4);
		type = doomLineParser.LineTypes.get((Integer)(int)bb.getShort(6));
		this.sectorTag = (bb.getShort(8));
		leftSideId = bb.getShort(10);
		rightSideId = bb.getShort(12);
		blocksPl = (flags & 0x0001) != 0;
		blocksMonsters = (flags & 0x0002) != 0;
		twoSided = (flags & 0x0004) != 0;
		upUnPeg = (flags & 0x0008) != 0;
		lowUnPeg = (flags & 0x0010) != 0;
		secret = (flags & 0x0020) != 0;
		blocksSound = (flags & 0x0040) != 0;
		neverShowOnMap = (flags & 0x0080) != 0;
		alwaysShowOnMap = (flags & 0x0100) != 0;
		if (leftSideId != -1) {
			leftSide = sides.get(leftSideId);
		}
		if (rightSideId != -1) {
			rightSide = sides.get(rightSideId);
		}
		if (rightSideId == -1) {
			disabledSide = SideType.RIGHT;
		} else if (leftSideId == -1){
			disabledSide = SideType.LEFT;
		}

	}
	public static enum SideType {
		LEFT,RIGHT,NONE;
	}
}
