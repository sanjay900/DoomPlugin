package com.sanjay900.DoomPlugin.levelElements;

import org.bukkit.Location;
/**
 * A door object that represents a door, a set of blocks that can raise and lower to give access to areas.
 * @author Jacob
 *
 */
public class DoomDoor{

	private boolean doorOpen = false;	
	private int width;
	private int height;
	private Location bottomCorner;
	public DoomDoor(int width, int height, Location bottomCorner) {
		this.width = width;
		this.height =height;
		this.bottomCorner = bottomCorner;
	}
	/**
	 * Toggle a {@link #DoomDoor}
	 * @return if the door was opened or closed
	 */
	public boolean toggleDoor() {
		if (doorOpen){
			
		} else {
			
		}
		doorOpen = !doorOpen;
		return doorOpen;
	}

	
}
