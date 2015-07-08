package com.sanjay900.DoomPlugin.WAD.BSP;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class DoomSubSector {
	short SegmentCount = -1;
	DoomSegment[] Segments;
	public DoomSubSector (byte[] data, ArrayList<DoomSegment> segments) {
		ByteBuffer bb = ByteBuffer.wrap(data);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		SegmentCount = bb.getShort(0);
		short firstSegment = bb.getShort(2);
		Segments = new DoomSegment[SegmentCount];
		for (int i = 0; i < SegmentCount; i ++) {
			Segments[i] = segments.get(firstSegment+i);
		}
	}
	public short getSegmentCount() {
		return SegmentCount;
	}
	public DoomSegment[] getSegments() {
		return Segments;
	}
}
