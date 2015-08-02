package com.sanjay900.DoomPlugin.player;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.sanjay900.DoomPlugin.DoomPlugin;
import com.sanjay900.DoomPlugin.levelElements.DoomLevel;

public class DoomPlayer {
		private UUID playerUUID;
	private Location respawnLocation;
		private int armourPercentage = 0;
		private double health = 100;
		private DoomPlugin plugin;
	private BukkitTask task;
		public DoomPlayer(final Player player, DoomPlugin plugin, DoomLevel level) {
		this.plugin = plugin;
		this.playerUUID = player.getUniqueId();
		this.respawnLocation = level.getSpawnLoc();
				this.task = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable(){

			@Override
			public void run() {
				PacketContainer chat = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.CHAT);
												chat.getChatComponents().write(0, WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', getMesssage())));
				chat.getBytes().write(0, (byte)2);
				try {
					ProtocolLibrary.getProtocolManager().sendServerPacket(player, chat);
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}}, 1l, 1l);
	}
		public Location getRespawnLocation() {
		return respawnLocation;
	}
	public void setRespawnLocation(Location respawnLocation) {
		this.respawnLocation = respawnLocation;
	}
		public Player getPlayer() {
		return Bukkit.getPlayer(playerUUID);
	}
		public int getArmourPercentage() {
		return armourPercentage;
	}
	public void setArmourPercentage(int armourPercentage) {
		this.armourPercentage = armourPercentage;
	}
		public boolean hasArmour() {
		return armourPercentage > 0;
	}
		public ArmourType getArmourType() {
		return ArmourType.getArmourType(armourPercentage);
	}
		public void damage(double damage) {
		this.health -= damage;
		getPlayer().setHealth(health/5);
		getPlayer().playEffect(EntityEffect.HURT);
	}
		public String getMesssage() {
		return "&4Health: &c"+String.valueOf(health)+"% &6Armour: &e"+String.valueOf(armourPercentage)+"%";
	}
		public void stopGame() {
				plugin.doomPlayers.remove(this);
						task.cancel();
	}

}
