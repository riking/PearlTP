package com.kdude63.pearltp;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

public class main extends JavaPlugin implements Listener
{
	Logger log = Logger.getLogger("Minecraft");
	FileConfiguration config;
	Integer cost;
	Integer maxdist;
	String noperm;
	Boolean itp;
	public static ItemStack pearls;
	
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		config = getConfig();
        if (!new File(this.getDataFolder().getPath() + File.separatorChar + "config.yml").exists())
            saveDefaultConfig();
        
        cost = config.getInt("cost");
        maxdist = config.getInt("max_distance");
        noperm = config.getString("noperm_message");
        itp = config.getBoolean("itp");
        pearls = new ItemStack(Material.ENDER_PEARL, cost);
	}
	
	//For checking if a string is a number
	public boolean isNumber(String n){
		if (n.matches("-?\\d+(\\.\\d+)?"))
			return true;
		else
			return false;
	}
	
	public void playerTeleport(String origin, String target){
		Player oPlayer = Bukkit.getServer().getPlayer(origin);
		Player tPlayer = Bukkit.getServer().getPlayer(target);
		
		//Get location of the target player
		Location targetCoords = tPlayer.getLocation();
		
		//Teleport the origin player to the target player
		oPlayer.teleport(targetCoords);
	}
	
	//Command function
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("ptp")){
			Player player = Bukkit.getServer().getPlayer(sender.getName());
			//If the command sender is a player
			if (sender instanceof Player){
				if (sender.hasPermission("pearltp.notp") && sender.isOp() == false){
					//Tell the command sender that they don't have permission to use the command
					sender.sendMessage(ChatColor.RED + noperm);
				//If the command sender has permission to use the command
				}else{
					if (args.length == 1){
						if (itp){
							if (Bukkit.getServer().getPlayer(args[0]) != null){
								//If sender is not trying to teleport to themself
								if (Bukkit.getServer().getPlayer(sender.getName()) != Bukkit.getServer().getPlayer(args[0])){
									if (player.getInventory().contains(Material.ENDER_PEARL, cost)){					
										player.getInventory().removeItem(pearls);
										Location target = Bukkit.getServer().getPlayer(args[0]).getLocation();
										
										//Initiate teleport
										player.teleport(target);
									}else{
										sender.sendMessage(ChatColor.RED + "You need " + cost.toString() + " ender pearl(s) to teleport.");
									}
								}else{
									sender.sendMessage("You can't teleport to yourself.");
								}
							}else{
								sender.sendMessage(ChatColor.RED + "Unable to find player " + args[0]);
							}
						}else{
							sender.sendMessage(ChatColor.RED + noperm);
						}
					}
					else if (args.length == 3){
						if (isNumber(args[0]) && isNumber(args[1]) && isNumber(args[2])){
							if (player.getInventory().contains(Material.ENDER_PEARL, cost)){
								player.getInventory().removeItem(pearls);
								Location target = player.getLocation();
								
								//Update target coordinates
								target.setX(Double.parseDouble(args[0]));
								target.setY(Double.parseDouble(args[1]));
								target.setZ(Double.parseDouble(args[2]));
								
								//Intiate teleport
								player.teleport(target);
							}else{
								sender.sendMessage(ChatColor.RED + "You need " + cost.toString() + " ender pearl(s) to teleport.");
							}
						}else{
							return false;
						}
					}else{
						return false;
					}
				}
			}else{
				//If the command sender is console
				sender.sendMessage("Console can't use this command!");
			}
			return true;
		}else{
			return false;
		}
	}
}
