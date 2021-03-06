package com.orf1.securitycore;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class RegisterCommand implements CommandExecutor {

    private final Main main = JavaPlugin.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            Player player = (Player) sender;
            if (player.hasPermission("securitycore.register")) {
                if (args.length != 1 || args[0].length() < 4 || args[0].length() > 16) {

                    player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.RED + "Invalid usage! /register [pin] (4-16 character limit!)");

                } else if (!main.getPlayerData().get(player.getUniqueId().toString() + ".Registered").equals(true)) {

                    String hash = null;
                    try {
                        hash = main.hashString(args[0]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    System.out.println("Hash of pin is:" + hash);

                    main.getPlayerData().set(player.getUniqueId().toString() + ".Pin", hash);
                    main.getPlayerData().set(player.getUniqueId().toString() + ".Registered", true);

                    main.saveFile(main.getPlayerData(), main.getPlayerDataFile());

                    player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.WHITE + "You have successfully registered!");
                    System.out.println("[SecurityCore] Player " + player.getName() + "successfully registered!");
                } else {

                    player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.RED + "You are already registered! If you would like to change your pin, contact an admin.");

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

