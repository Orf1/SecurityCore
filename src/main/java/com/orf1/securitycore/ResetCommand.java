package com.orf1.securitycore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ResetCommand implements CommandExecutor {
    private final Main main = JavaPlugin.getPlugin(Main.class);
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("securitycore.reset")) {
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null) {
                    main.getPlayerData().set(target.getUniqueId().toString() + ".Registered", false);
                    main.getPlayerData().set(target.getUniqueId().toString() + ".LoggedIn", false);
                    main.getPlayerData().set(target.getUniqueId().toString() + ".Pin", "NA");
                    sender.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.GREEN + "Successfully reset user: " + target.getName());
                }else {
                    sender.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.RED + "You must specify a valid player!");
                }
            } else {
                sender.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.RED + "Invalid usage! /reset [player]");
            }
        }else {
            sender.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.RED + "You do not have permission to use this command!");
        }
        return false;
    }
}
