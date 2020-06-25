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
                player.sendMessage("Invalid Args!");
            }else if (main.getPlayerData().get(player.getUniqueId().toString() + ".REGISTERED").equals(false)){
                String pin = args[0];
                main.getPlayerData().set(player.getUniqueId().toString() + ".PIN", pin);
                main.getPlayerData().set(player.getUniqueId().toString() + ".REGISTERED", true);
            }else{
                player.sendMessage("You are already registered! If you would like to change your pin, contact an admin.");
            }


        } else {
            System.out.println(ChatColor.GREEN + "[SecurityCore] " + ChatColor.WHITE + "This command can only be used in-game.");
        }
        return false;
    }

}
/*if (main.getPlayerData().get(player.getUniqueId().toString() + ".REGISTERED").equals(false) ){
  }else {

            }
 */

