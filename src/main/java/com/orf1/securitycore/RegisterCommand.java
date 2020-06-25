package com.orf1.securitycore;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.UUID;

public class RegisterCommand implements CommandExecutor {

    private Main main = JavaPlugin.getPlugin(Main.class);
    private HashMap<Player, String> userPins = new HashMap<>();
    private String pin;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (args[0].isEmpty()) {
                player.sendMessage("Empty");
            }
            else if (args[0].length() > 16 || args[0].length() < 4){
                player.sendMessage("Too Short/long");

            }else {
                pin = args[0];
            }
            userPins.put(player, pin);
            writeFile(userPins);

            player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.WHITE + "You have been registered.");
        }else{
            System.out.println("This command can only be used in-game.");
        }
        return false;
    }
    public void writeFile(HashMap data){
        main.getTestFile().createSection("Users");
        main.getTestFile().set("Users", data);
        try {
            main.getTestFile().save(main.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
