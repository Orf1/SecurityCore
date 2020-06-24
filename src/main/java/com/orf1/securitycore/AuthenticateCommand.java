package com.orf1.securitycore;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class AuthenticateCommand implements CommandExecutor {
    public SecurityCore main;
    public void TestCommand(SecurityCore main){
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (player.isOp()){
                main.getPlayerData().createSection("12345");
                main.getPlayerData().createSection("123");
                try {
                    main.getPlayerData().save(main.getFile());
                    player.sendMessage(ChatColor.GREEN + "[SecurityCore] "+ ChatColor.WHITE + "Created playerdata File");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            player.sendMessage(ChatColor.RED + "You are not worthy enough to summon the moose!");
        }else {
            System.out.println("This command can only be used in-game");
        }
        return false;
    }
}
