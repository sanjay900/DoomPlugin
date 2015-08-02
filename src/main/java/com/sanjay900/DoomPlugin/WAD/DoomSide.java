package com.sanjay900.DoomPlugin.WAD;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
public class DoomSide {
		short xOffset;
	short yOffset;
		String upperTex = "Unknown";
	String lowerTex = "Unknown";
	String midTex = "Unknown";
		short sectorNum;
		public DoomSide(byte[] data) {
		ByteBuffer bb = ByteBuffer.wrap(data);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		xOffset = bb.getShort(0);
		yOffset = bb.getShort(2);
		sectorNum = bb.getShort(28);
		try {
			upperTex = new String(Arrays.copyOfRange(data, 4, 12), "UTF-8");
						upperTex = upperTex.replaceAll("\0", "");			
			lowerTex = new String(Arrays.copyOfRange(data, 12, 20), "UTF-8");
						lowerTex = lowerTex.replaceAll("\0", "");
			midTex = new String(Arrays.copyOfRange(data, 20, 28), "UTF-8");
						midTex = midTex.replaceAll("\0", "");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
	}
		public String toString() {
		return "["+upperTex+","+lowerTex+","+midTex+"]";
	}
}
