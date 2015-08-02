package com.sanjay900.DoomPlugin.listeners;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sanjay900.DoomPlugin.DoomPlugin;
import com.sanjay900.DoomPlugin.WAD.DoomLevelParser;
import com.sanjay900.DoomPlugin.levelElements.DoomLevel;
import com.sanjay900.DoomPlugin.player.DoomPlayer;

public class DoomCommandHandler implements CommandExecutor{
		private DoomPlugin plugin;
		public DoomCommandHandler (DoomPlugin plugin) {
				this.plugin = plugin;
				plugin.getCommand("doom").setExecutor(this);
	}
		@Override
	public boolean onCommand(CommandSender sender, Command command, String commandName,
			String[] args) {
				if (sender instanceof Player) {
			switch (args.length) {
						case 1:
				DoomPlayer p = plugin.getPlayer((Player)sender);
				switch (args[0]) {
				case "play":
					
					if (p != null) {
						sender.sendMessage("You have already started a game");
					} else {
						DoomLevel l = plugin.doomConfig.levels.get(0);
						l.startGame(((Player)sender));
						sender.sendMessage("You started a game of Doom. You are playing the level "+l.getName());
					}
					
					break;
				case "stop":
					if (p == null) {
						sender.sendMessage("You can only stop a game that has already been started.");
					} else {
						p.stopGame();
						sender.sendMessage("Your game was stopped.");
					}
				}
				return false;
							case 2:
				switch (args[0]) {
				case "import":

										if (!sender.isOp()) return false;
										sender.sendMessage("Loading Doom WAD: "+args[1]);
										DoomLevelParser d = new DoomLevelParser(plugin,args[1],((Player)sender).getUniqueId());
				}
				return false;

							case 3:
				switch(args[0]){
				case "door":
					if(!sender.isOp()) return false;
					sender.sendMessage("Right click on the bottom left corner of the door");
					
				}
			default:
				sender.sendMessage("The arguments passed were not recognised.");
								return false;

			}
		}
		return false;
	}

}
