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
            if(args.length != 1){
                player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.RED + "Invalid usage! /register [pin]");
            }else if (main.getPlayerData().get(player.getUniqueId().toString() + ".Registered").equals(false)){
                String pin = args[0];
                main.getPlayerData().set(player.getUniqueId().toString() + ".Pin", pin);
                main.getPlayerData().set(player.getUniqueId().toString() + ".Registered", true);
                main.saveFile(main.getPlayerData(), main.getPlayerDataFile());

                player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.WHITE + "You have successfully registered with pin: " + pin);
            }else{
                player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.RED + "You are already registered! If you would like to change your pin, contact an admin.");
            }

        } else {
            System.out.println(ChatColor.GREEN + "[SecurityCore] " + ChatColor.RED + "This command can only be used in-game.");
        }
        return false;
    }

}

