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
	//A plugin instance. This is the only global variable, as it is accessed by many functions
	private DoomPlugin plugin;
	//Initilize this command handler 
	public DoomCommandHandler (DoomPlugin plugin) {
		//set some variables based on what was provided
		this.plugin = plugin;
		//Tell bukkit that this handles the "doom" command
		plugin.getCommand("doom").setExecutor(this);
	}
	//Called when a command is handled
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandName,
			String[] args) {
		//These commands are only runnable by players.
		if (sender instanceof Player) {
			switch (args.length) {
			//The command has 1 argument
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
				//The command has 2 arguments
			case 2:
				switch (args[0]) {
				case "import":

					//If the player isn't an operator, they shouldn't be allowed to import levels.
					if (!sender.isOp()) return false;
					//Tell the player that they are importing a WAD File
					sender.sendMessage("Loading Doom WAD: "+args[1]);
					//Create a DoomLevelParser object that is responsible for importing the Wad File.
					DoomLevelParser d = new DoomLevelParser(plugin,args[1],((Player)sender).getUniqueId());
				}
				return false;

				//Unrecognized amount of arguments
			case 3:
				switch(args[0]){
				case "door":
					if(!sender.isOp()) return false;
					sender.sendMessage("Right click on the bottom left corner of the door");
					
				}
			default:
				sender.sendMessage("The arguments passed were not recognised.");
				//Send the default message for the command
				return false;

			}
		}
		return false;
	}

}
