package com.orf1.securitycore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class SecurityCore extends JavaPlugin implements Listener {

boolean authenticated = false;
private File playerData;
private YamlConfiguration modifyPlayerData;
    @Override
    public void onEnable() {
        System.out.println("[SecurityCore] Plugin initializing");
        System.out.println("[SecurityCore] Server Version: " + Bukkit.getVersion());
        registerEvents();
        registerCommands();
        loadConfig();
        try {
            initiateFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        e.getPlayer().sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.WHITE + "Welcome!");
    }

    public void registerCommands(){
        getCommand("securitycore").setExecutor(new SecurityCoreCommand());
        getCommand("login").setExecutor(new LoginCommand());
        getCommand("register").setExecutor(new RegisterCommand());
        getCommand("authenticate").setExecutor(new AuthenticateCommand());
        getCommand("moose").setExecutor(new MooseCommand());
    }
    public void registerEvents(){
        Bukkit.getPluginManager().registerEvents(this,this);
    }
    public File getFile(){
        return playerData;
    }
    public boolean authenticate(){

        return this.getConfig().getBoolean("auth");
    }
    public void loadConfig() {
        this.getConfig().options().copyDefaults();
        saveDefaultConfig();
    }
    public void initiateFiles() throws IOException {
        playerData = new File(Bukkit.getServer().getPluginManager().getPlugin("SecurityCore").getDataFolder(), "playerdata.yml");
        if (!playerData.exists()){
            playerData.createNewFile();
        }
        modifyPlayerData = YamlConfiguration.loadConfiguration(playerData);
    }
    public YamlConfiguration getPlayerData(){
        return modifyPlayerData;
    }

}
