package com.orf9.securitycore;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class SecurityCore extends JavaPlugin implements Listener {

boolean authenticated = false;

    @Override
    public void onEnable() {
        System.out.println("[SecurityCore] Plugin initializing");
        registerEvents();
        registerCommands();
        System.out.println("[SecurityCore] Plugin initialization complete");
        System.out.println("[SecurityCore] Starting authentication");
        if (authenticate()){
            System.out.println("[SecurityCore] Authentication complete");
            authenticated = true;
        }else{
            System.out.println("[SecurityCore] Authentication failed");
            authenticated = false;
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        System.out.println("[SecurityCore] Plugin shutting down");
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e){
        System.out.println("[SecurityCore] Player logged in with IP:" + e.getRealAddress());
    }

    public void registerCommands(){
        getCommand("securitycore").setExecutor(new securitycoreCommand());
        getCommand("login").setExecutor(new loginCommand());
        getCommand("register").setExecutor(new registerCommand());
        getCommand("authenticate").setExecutor(new authenticateCommand());
    }
    public void registerEvents(){
        Bukkit.getPluginManager().registerEvents(this,this);
    }
    public boolean authenticate(){
        return true;
    }
}
