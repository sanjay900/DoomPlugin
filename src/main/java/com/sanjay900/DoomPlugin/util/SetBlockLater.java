package com.sanjay900.DoomPlugin.util;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
public class SetBlockLater extends BukkitRunnable {
	private int x;
	private int y;
	private int z;
	private int id;
	private byte data;
	private String name;
		public SetBlockLater(String name, int x, int i, int j, int id, byte data) {
		this.name = name;
		this.x = x;
		this.y = i;
		this.z = j;
		this.id = id;
		this.data = data;
	}

	public void run() {
				Bukkit.getWorld(name).getBlockAt(x, y, z).setTypeIdAndData(id, data, false);

	}
}
