package com.orf1.securitycore;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class RegisterCommand implements CommandExecutor {

    private Main main = JavaPlugin.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            main.getTestFile().createSection("Users."+ player.getUniqueId());
            try {
                main.getTestFile().save(main.getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.WHITE + "You have been registered.");
        }else{
            System.out.println("This command can only be used in-game.");
        }
        return false;
    }
}
