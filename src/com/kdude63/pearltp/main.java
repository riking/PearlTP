package com.kdude63.pearltp;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class main extends JavaPlugin implements Listener
{
	Logger log = Logger.getLogger("Minecraft");
	FileConfiguration config;
	
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("tp")){
			if (!(sender instanceof Player)) { //If the command sender is console
				if (args.length <= 1 || args.length > 4 || args.length == 3) {; //If the command does not have either two or four arguments
					return false; //Print proper command usage
				}else{
					if (args.length == 2){ //If command only has two arguments
						if (Bukkit.getServer().getPlayer(args[0]) != null){ //If first argument in command is an existing player
							if (Bukkit.getServer().getPlayer(args[1]) != null){ //If second argument in command is an existing player
								Player player = Bukkit.getServer().getPlayer(args[0]); //Store origin player in variable
								Location target = Bukkit.getServer().getPlayer(args[1]).getLocation(); //Store target player location in variable
								player.teleport(target); //Teleport origin player to target player's location			
							}else{
								System.out.println("Cannot find player " + args[1]); //Tell the console that the target player is nonexistent
							}
						}else{
							System.out.println("Cannot find player " + args[0]); //Tell the console that the origin player is nonexistent
							return true;
						}
					} else if (args.length == 4){ //If the command has four arguments
						if (Bukkit.getServer().getPlayer(args[0]) != null){
							if (args[1].matches("-?\\d+(\\.\\d+)?") && args[2].matches("-?\\d+(\\.\\d+)?") && args[3].matches("-?\\d+(\\.\\d+)?")){					
								Player player = Bukkit.getServer().getPlayer(args[0]); //Store origin player in variable
								Location target = player.getLocation();
								
								//Set the target coordinates
								target.setX(Double.parseDouble(args[1]));
								target.setY(Double.parseDouble(args[2]));
								target.setZ(Double.parseDouble(args[3]));
								
								player.teleport(target); //Teleport the origin player to the target coordinates
							}else{
								return false; //Print proper command usage
							}
						}else{
							System.out.println("Could not find player " + args[0]); //Tell the console that the origin player is nonexistent
						}
					}
				}
						
			}else{ //If the command sender is a player
				
			}
			return true;
		}else{
			return false;
		}
	}
}