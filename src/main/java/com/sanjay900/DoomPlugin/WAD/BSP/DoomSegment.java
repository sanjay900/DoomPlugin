package com.sanjay900.DoomPlugin.WAD.BSP;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedHashMap;

import com.sanjay900.DoomPlugin.WAD.DoomLine;
import com.sanjay900.DoomPlugin.WAD.DoomSide;

public class DoomSegment {
	short startVertex;
	short endVertex;
	short angle;
	DoomLine line;
	short direction;
	short offset;
	public DoomSegment(byte[] data, LinkedHashMap<DoomLine,DoomSide[]> lines) {
		ByteBuffer bb = ByteBuffer.wrap(data);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		startVertex = bb.getShort(0);
		endVertex = bb.getShort(2);
		angle = bb.getShort(4);
		line = (DoomLine) lines.keySet().toArray()[bb.getShort(6)];
		direction = bb.getShort(8);
		offset = bb.getShort(10);
	}
	public short getStartVertex() {
		return startVertex;
	}
	public short getEndVertex() {
		return endVertex;
	}
	public short getAngle() {
		return angle;
	}
	public DoomLine getLine() {
		return line;
	}
	public short getDirection() {
		return direction;
	}
	public short getOffset() {
		return offset;
	}
}
