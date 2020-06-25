package com.orf1.securitycore;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class LoginCommand implements CommandExecutor {
    private final Main main = JavaPlugin.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length != 1){
                player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.RED + "Invalid usage! /login [pin]");

            }else if (main.getPlayerData().get(player.getUniqueId().toString() + ".LoggedIn").equals(false)){
                String pin = main.getPlayerData().get(player.getUniqueId().toString() + ".Pin").toString();
                if(args[0].equals(pin)){
                    player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.WHITE + "You have successfully logged in!");
                    main.getPlayerData().set(player.getUniqueId().toString() + ".LoggedIn", true);
                    main.saveFile(main.getPlayerData(), main.getPlayerDataFile());
                }else {
                    player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.RED + "Incorrect Pin!");
                }

            }else{
                player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.RED + "You are already logged in!");
            }

        } else {
            System.out.println(ChatColor.GREEN + "[SecurityCore] " + ChatColor.RED + "This command can only be used in-game.");
        }
        return false;
    }
}
