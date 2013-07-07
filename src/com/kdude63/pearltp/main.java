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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class main extends JavaPlugin implements Listener
{
	Logger log = Logger.getLogger("Minecraft");
	FileConfiguration config;
	Integer cost;
	Integer maxdist;
	String noperm;
	Boolean itp;
	
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
	}
	
	public boolean isNumber(String n){
		if (n.matches("-?\\d+(\\.\\d+)?"))
			return true;
		else
			return false;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("ptp")){
			if (sender instanceof Player){ //If the command sender is a player
				if (sender.hasPermission("pearltp.notp")){
					sender.sendMessage(noperm);	
				}
				else
				{
					if (args.length == 1 || args.length == 3){
						if (args.length == 1){ //If the command has one argument
							Player player = Bukkit.getServer().getPlayer(sender.getName());
							Location target = Bukkit.getServer().getPlayer(args[0]).getLocation();
							Inventory pinv = player.getInventory();
							ItemStack pearls = new ItemStack(Material.ENDER_PEARL, cost);
							if (itp){						
								if (pinv.contains(Material.ENDER_PEARL, cost)){
									pinv.remove(pearls);
									player.teleport(target);
								}else{
									sender.sendMessage(ChatColor.RED + "You need " + cost.toString() + " pearl(s) to teleport." + ChatColor.RESET);
								}
							}else{
								sender.sendMessage("You can't teleport to other players.");
							}
						}
						else if (args.length == 3){ //If the command has three arguments
							if (isNumber(args[0]) && isNumber(args[1]) && isNumber(args[2])){
								Player player = Bukkit.getServer().getPlayer(sender.getName());
								Location target = player.getLocation();
								Inventory pinv = player.getInventory();
								ItemStack pearls = new ItemStack(Material.ENDER_PEARL, cost);
								if (pinv.contains(Material.ENDER_PEARL, cost)){
									target.setX(Double.parseDouble(args[0]));
									target.setY(Double.parseDouble(args[1]));
									target.setZ(Double.parseDouble(args[2]));
									
									pinv.remove(pearls);
									player.teleport(target);
								}else{
									sender.sendMessage(ChatColor.RED + "You need " + cost.toString() + " pearl(s) to teleport." + ChatColor.RESET);
								}
							}else{
								return false;
							}
						}
					}
					else{
						return false;
					}
				}
			}
			else{ //If the command sender is console
				sender.sendMessage("Console can't use this command!");
			}
			return true;
		}
		else{
			return false;
		}
	}
}