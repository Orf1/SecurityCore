package com.orf1.securitycore;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class RegisterCommand implements CommandExecutor {

    private Main main = JavaPlugin.getPlugin(Main.class);
    private HashMap<Player, String> userPins = new HashMap<>();
    private List registeredUsers;
    private String pin;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            registeredUsers = (List) main.getTestFile().get("RegisteredUsers");
            if (registeredUsers.contains(player))
            {
                player.sendMessage(ChatColor.GREEN + "[SecurityCore] "+ChatColor.WHITE +"You are already registered!");


            }else{
                if (args.length < 1) {
                    player.sendMessage(ChatColor.GREEN + "[SecurityCore] "+ChatColor.WHITE +"Correct usage: /register [Password/Pin.");
                } else if (args[0].length() > 16 || args[0].length() < 4) {
                    player.sendMessage(ChatColor.GREEN + "[SecurityCore] "+ChatColor.WHITE +"Password/pin must be 4-16 characters long.");

                } else {
                    pin = args[0];
                    userPins.put(player, pin);
                    writePin(userPins);
                    registeredUsers.add(player);
                    writeRegistered(registeredUsers);
                    player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.WHITE + "You have been registered.");
                }
            }


        }else{
            System.out.println(ChatColor.GREEN + "[SecurityCore] "+ChatColor.WHITE +"This command can only be used in-game.");
        }
        return false;
    }
    public void writePin(HashMap data){
        main.getTestFile().createSection("UserPins");
        main.getTestFile().set("UserPins", data);
        try {
            main.getTestFile().save(main.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeRegistered(List data){
        main.getTestFile().createSection("RegisteredUsers");
        main.getTestFile().set("RegisteredUsers", data);
        try {
            main.getTestFile().save(main.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
