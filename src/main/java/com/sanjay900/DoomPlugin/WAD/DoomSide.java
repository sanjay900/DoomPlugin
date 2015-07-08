package com.sanjay900.DoomPlugin.WAD;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
//This stores the information about how a line is textured. There is one of these mapped to a line's side
public class DoomSide {
	//Texture offsets
	short xOffset;
	short yOffset;
	//Textures
	String upperTex = "Unknown";
	String lowerTex = "Unknown";
	String midTex = "Unknown";
	//Sector this faces
	short sectorNum;
	//Construct this object from a lump of data
	public DoomSide(byte[] data) {
		ByteBuffer bb = ByteBuffer.wrap(data);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		xOffset = bb.getShort(0);
		yOffset = bb.getShort(2);
		sectorNum = bb.getShort(28);
		try {
			upperTex = new String(Arrays.copyOfRange(data, 4, 12), "UTF-8");
			//Replace null characters, otherwise we run into problems with comparing strings
			upperTex = upperTex.replaceAll("\0", "");			
			lowerTex = new String(Arrays.copyOfRange(data, 12, 20), "UTF-8");
			//Replace null characters, otherwise we run into problems with comparing strings
			lowerTex = lowerTex.replaceAll("\0", "");
			midTex = new String(Arrays.copyOfRange(data, 20, 28), "UTF-8");
			//Replace null characters, otherwise we run into problems with comparing strings
			midTex = midTex.replaceAll("\0", "");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
	}
	//Useful for debugging, just prints all the textures of this object
	public String toString() {
		return "["+upperTex+","+lowerTex+","+midTex+"]";
	}
}
