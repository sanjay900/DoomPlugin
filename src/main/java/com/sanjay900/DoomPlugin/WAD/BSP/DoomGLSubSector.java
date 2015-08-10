package com.sanjay900.DoomPlugin.WAD.BSP;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class DoomGLSubSector {
	short SegmentCount = -1;
	DoomGLSegment[] Segments;
	public DoomGLSubSector (byte[] data, ArrayList<DoomGLSegment> segments) {
		ByteBuffer bb = ByteBuffer.wrap(data);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		SegmentCount = bb.getShort(0);
		short firstSegment = bb.getShort(2);
		Segments = new DoomGLSegment[SegmentCount];
		for (int i = 0; i < SegmentCount; i ++) {
			Segments[i] = segments.get(firstSegment+i);
		}
	}
	public int getSegmentCount() {
		return SegmentCount;
	}
	public DoomGLSegment[] getSegments() {
		return Segments;
	}
}
