package com.orf1.securitycore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SecurityCoreCommand implements CommandExecutor {

    private final Main main = JavaPlugin.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("securitycore.admin")) {
                if (args.length == 0) {
                    player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.RED + "Invalid arguments! Usage: /securitycore reload");
                } else if (args[0].equalsIgnoreCase("reload")) {
                    main.reloadConfig();
                }
            }else{
                player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.RED + "You do not have permission to use this command!");
            }
        }else {
            CommandSender console = sender;
        }
        return false;
    }
}
