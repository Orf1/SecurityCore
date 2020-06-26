package com.orf1.securitycore;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class LoginCommand implements CommandExecutor {
    private final Main main = JavaPlugin.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;
            if (player.hasPermission("securitycore.login")) {
                if (args.length != 1) {

                    player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.RED + "Invalid usage! /login [pin]");

                } else if (main.getPlayerData().get(player.getUniqueId().toString() + ".LoggedIn").equals(false)) {

                    String hash = main.getPlayerData().get(player.getUniqueId().toString() + ".Pin").toString();

                    String hashedInput = null;
                    try {
                        hashedInput = main.hashString(args[0]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (main.getPlayerData().get(player.getUniqueId().toString() + ".Registered").equals(true)) {
                        if (hashedInput.equals(hash)) {

                            player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.WHITE + "You have successfully logged in!");
                            main.getPlayerData().set(player.getUniqueId().toString() + ".LoggedIn", true);
                            main.saveFile(main.getPlayerData(), main.getPlayerDataFile());
                            System.out.println("[SecurityCore] Player " + player.getName() + "successfully logged in!");
                        } else {
                            player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.RED + "Incorrect Pin!");

                            main.getLogger().log(Level.WARNING, " Player " + player.getName() + "failed to log in!");
                        }
                    } else {
                        player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.RED + "You are not registered. Please register using /register before logging in!");
                    }

                } else {
                    player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.RED + "You are already logged in!");
                }
            }else {
                player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.RED + "You do not have permission to use this command!");
            }
        } else {
            System.out.println(ChatColor.GREEN + "[SecurityCore] " + ChatColor.RED + "This command can only be used in-game.");
        }
        return false;
    }
}
