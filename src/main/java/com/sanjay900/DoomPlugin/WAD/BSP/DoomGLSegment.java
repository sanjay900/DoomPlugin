package com.sanjay900.DoomPlugin.WAD.BSP;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import com.sanjay900.DoomPlugin.WAD.DoomLine;

import lombok.Getter;
@Getter
public class DoomGLSegment {
	short startVertex;
	short endVertex;
	boolean isStartVertGL;
	boolean isEndVertGL;
	DoomLine line;
	short direction;
	boolean hasLine;
	short partnerSeg;
	public DoomGLSegment(byte[] data, ArrayList<DoomLine> lines) {
		ByteBuffer bb = ByteBuffer.wrap(data);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		startVertex = bb.getShort(0);
		endVertex = bb.getShort(2);
		isStartVertGL = (startVertex & (1L << 15)) != 0;
		isEndVertGL = (endVertex & (1L << 15)) != 0;
		if (isStartVertGL) {
			startVertex -=-32768;
		}
		if (isEndVertGL) {
			endVertex -=-32768;
		}
		hasLine =  (bb.getShort(4) != -1);
		if (hasLine)
			line = (DoomLine) lines.get(bb.getShort(4));
		direction = bb.getShort(6);
		partnerSeg = bb.getShort(8);
	}

}

