package com.sanjay900.DoomPlugin.WAD.BSP;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DoomNode {
	short partLinex = -1;
	short partLiney = -1;
	short distanceX = -1;
	short distanceY = -1;
	short yUpBR = -1; //y upper bound for right bounding box
	short yLowBR = -1;
	short xUpBR = -1;
	short xLowBR = -1;
	short yUpBL = -1; //ditto for left
	short yLowBL = -1;
	short xUpBL = -1;
	short xLowBL = -1;
	short rChild = -1;
	short lChild = -1;
	boolean isrSector = false;
	boolean islSector = false;
	public DoomNode (byte[] data) {
		ByteBuffer bb = ByteBuffer.wrap(data);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		this.partLinex = bb.getShort(0);
		this.partLiney = bb.getShort(2);
		this.distanceX = bb.getShort(4);
		this.distanceY = bb.getShort(6);
		this.yUpBR = bb.getShort(8);
		this.yLowBR = bb.getShort(10);
		this.xUpBR = bb.getShort(12);
		this.xUpBR = bb.getShort(14);
		this.yUpBL = bb.getShort(16);
		this.yLowBL = bb.getShort(18);
		this.xUpBL = bb.getShort(20);
		this.xLowBL = bb.getShort(22);
		this.rChild = bb.getShort(24);
		this.lChild = bb.getShort(26);
		this.isrSector = (rChild > 0);  
		this.islSector = (lChild > 0);  
		if (!isrSector) {
			rChild = (short) (rChild & ~(1 << 15));
		}
		if (!islSector) {
			lChild = (short) (lChild & ~(1 << 15));
		}
	}
	public short getPartLinex() {
		return partLinex;
	}
	public short getPartLiney() {
		return partLiney;
	}
	public short getDistanceX() {
		return distanceX;
	}
	public short getDistanceY() {
		return distanceY;
	}
	public short getyUpBR() {
		return yUpBR;
	}
	public short getyLowBR() {
		return yLowBR;
	}
	public short getxUpBR() {
		return xUpBR;
	}
	public short getxLowBR() {
		return xLowBR;
	}
	public short getyUpBL() {
		return yUpBL;
	}
	public short getyLowBL() {
		return yLowBL;
	}
	public short getxUpBL() {
		return xUpBL;
	}
	public short getxLowBL() {
		return xLowBL;
	}
	public short getrChild() {
		return rChild;
	}
	public short getlChild() {
		return lChild;
	}
	public boolean isIsrSector() {
		return isrSector;
	}
	public boolean isIslSector() {
		return islSector;
	}
	
}
