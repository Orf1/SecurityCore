package com.orf1.securitycore;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class mooseCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player ){
            Player player = (Player) sender;
            if (player.isOp()){
                player.sendMessage(ChatColor.RED + "Whomst Has Summoned The Moose!");

            }
            player.sendMessage(ChatColor.RED + "You are not worthy enough to summon the moose!");
        }else {
            System.out.println("This command can only be used in-game");
        }
        return false;
    }
}
