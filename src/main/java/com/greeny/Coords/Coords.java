package com.greeny.Coords;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.World.Environment;
import org.bukkit.plugin.java.JavaPlugin;

public class Coords extends JavaPlugin {
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		// If command is from a non-player (i.e. console)
		if (!(sender instanceof Player)) {
			if (cmd.getName().equalsIgnoreCase("coords") && args.length == 1) {
				final Player player = Bukkit.getPlayer(args[0]);
				if (Bukkit.getOnlinePlayers().contains(player)) {
					sender.sendMessage(constructCoordsMessageFor(player));
					return true;
				} else {
					sender.sendMessage("No player with username is on the server.");
					return true;
				}
			} else {
				sender.sendMessage("Running this command from console requires 1 argument (player name).");
				return true;
			}
		} else {
			if (cmd.getName().equalsIgnoreCase("coords")) {
				// Player didn't specify a username, broadcast their own coordinates.
				if (args.length == 0) {
					final Player player = (Player) sender;
					Bukkit.broadcastMessage(constructCoordsMessageFor(player));
					return true;

				} else if (args.length == 1) {
					final Player player = Bukkit.getPlayer(args[0]);
					if (Bukkit.getOnlinePlayers().contains(player)) {
						sender.sendMessage(constructCoordsMessageFor(player));
						return true;
					} else {
						sender.sendMessage("No player with username is on the server.");
						return true;
					}
				}
			}
		}
		return false; 
	}

	private String getEnvironmentNameFor(Player player) {
		Environment environment = player.getWorld().getEnvironment();

		if (environment == World.Environment.NORMAL) {
			return "the " + ChatColor.GREEN + "Overworld";
		}

		if (environment == World.Environment.NETHER) {
			return "the " + ChatColor.RED + "Nether";
		}

		if (environment == World.Environment.THE_END) {
			return ChatColor.DARK_PURPLE + "The End";
		}

		return "Unknown Dimension";
	}

	private String constructCoordsMessageFor(Player player) {
		final Location location = player.getLocation();
		final String message = ChatColor.GOLD + player.getName() + ChatColor.RESET + "'s coords, X: " + location.getBlockX() + ", Y: "
		+ location.getBlockY() + ", Z: " + location.getBlockZ() + " in " + getEnvironmentNameFor(player);

		return message;
	}
}
