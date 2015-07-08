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
	//These are global variables as they are accessed by different functions and objects
	private UUID playerUUID;
	private Location respawnLocation;
	//Armour percentage, defaults to 0, max 200%
	private int armourPercentage = 0;
	//Health percentage. Defaults to 100%, max 100%.
	private double health = 100;
	//plugin instance
	private DoomPlugin plugin;
	private BukkitTask task;
	//Init this doom player 
	public DoomPlayer(final Player player, DoomPlugin plugin, DoomLevel level) {
		this.plugin = plugin;
		this.playerUUID = player.getUniqueId();
		this.respawnLocation = level.getSpawnLoc();
		//Draw the gui every second
		this.task = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable(){

			@Override
			public void run() {
				PacketContainer chat = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.CHAT);
				//NMS (Net minecraft server, Code that changes every minecraft version that allows us to do things 
				//that bukkit can't on its own) code, basically, this sends a packet to display something above the health bar.
				chat.getChatComponents().write(0, WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', getMesssage())));
				chat.getBytes().write(0, (byte)2);
				try {
					ProtocolLibrary.getProtocolManager().sendServerPacket(player, chat);
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}}, 1l, 1l);
	}
	//Get and set the respawn location - global as called by other objects
	public Location getRespawnLocation() {
		return respawnLocation;
	}
	public void setRespawnLocation(Location respawnLocation) {
		this.respawnLocation = respawnLocation;
	}
	//Get the player by converting his uuid to a player
	public Player getPlayer() {
		return Bukkit.getPlayer(playerUUID);
	}
	//get and set the armour percentage
	public int getArmourPercentage() {
		return armourPercentage;
	}
	public void setArmourPercentage(int armourPercentage) {
		this.armourPercentage = armourPercentage;
	}
	//Does this player even have armour?
	public boolean hasArmour() {
		return armourPercentage > 0;
	}
	//Get the type of armour this player has
	public ArmourType getArmourType() {
		return ArmourType.getArmourType(armourPercentage);
	}
	//Damage the player
	public void damage(double damage) {
		this.health -= damage;
		getPlayer().setHealth(health/5);
		getPlayer().playEffect(EntityEffect.HURT);
	}
	//Get the message to display above the hud
	public String getMesssage() {
		return "&4Health: &c"+String.valueOf(health)+"% &6Armour: &e"+String.valueOf(armourPercentage)+"%";
	}
	//Stop the current game
	public void stopGame() {
		//Remove this player from the list of players who are playing
		plugin.doomPlayers.remove(this);
		//TODO: lilypad send to hub
		//Stop the repeating task that draws the hud to the player, as they are no longer playing
		task.cancel();
	}

}
