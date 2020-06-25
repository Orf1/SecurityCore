package com.orf1.securitycore;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class SetupCommand implements CommandExecutor {

    private Main main = JavaPlugin.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        try {
            main.getTestFile().save(main.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;


    }
}
