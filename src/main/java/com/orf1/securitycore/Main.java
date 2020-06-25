package com.orf1.securitycore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public final class Main extends JavaPlugin implements Listener {

    private File playerDataFile;
    private YamlConfiguration modifyPlayerData;

    @Override
    public void onEnable() {
        System.out.println("[SecurityCore] Initializing");

        loadConfig();
        checkAuthentication();
        registerEvents();
        registerCommands();
        initiateFiles();

        System.out.println("[SecurityCore] Initialization complete");
    }

    private void initiateFiles() {
        playerDataFile = new File(this.getDataFolder(), "playerdata.yml");
        if (!playerDataFile.exists()) {
            try {
                playerDataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        modifyPlayerData = YamlConfiguration.loadConfiguration(playerDataFile);
    }

    public YamlConfiguration getPlayerData() {
        return modifyPlayerData;
    }

    public File getPlayerDataFile(){
        return playerDataFile;
    }

    @Override
    public void onDisable() {
        System.out.println("[SecurityCore] Plugin shutting down");
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        System.out.println("[SecurityCore] Player logged in with IP:" + e.getRealAddress());
    }

    @EventHandler
    public void onJoin(PlayerLoginEvent e) {
        Player player = e.getPlayer();
        e.getPlayer().sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.WHITE + "Welcome!");

        modifyPlayerData.createSection(player.getUniqueId().toString());
        modifyPlayerData.createSection(player.getUniqueId().toString() + ".NAME");
        modifyPlayerData.createSection(player.getUniqueId().toString() + ".IP");
        modifyPlayerData.createSection(player.getUniqueId().toString() + ".REGISTERED");
        modifyPlayerData.createSection(player.getUniqueId().toString() + ".PIN");

        saveFile(modifyPlayerData, playerDataFile);

        modifyPlayerData.set(player.getUniqueId().toString() + ".NAME", player.getName());
        modifyPlayerData.set(player.getUniqueId().toString() + ".IP", e.getRealAddress().toString());
        if (!modifyPlayerData.get(player.getUniqueId().toString() + ".REGISTERED").equals(true)){
            modifyPlayerData.set(player.getUniqueId().toString() + ".REGISTERED", false);
            modifyPlayerData.set(player.getUniqueId().toString() + ".PIN", "NA");
        }

        saveFile(modifyPlayerData, playerDataFile);
    }


    public void registerCommands() {
        getCommand("securitycore").setExecutor(new SecurityCoreCommand());
        getCommand("login").setExecutor(new LoginCommand());
        getCommand("register").setExecutor(new RegisterCommand());
    }

    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    private void checkAuthentication() {
        System.out.println("[SecurityCore] Starting authentication");

        if (this.getConfig().getBoolean("auth")) {
            System.out.println("[SecurityCore] Authentication complete");
        } else {
            System.out.println("[SecurityCore] Authentication failed");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    public void loadConfig() {
        this.getConfig().options().copyDefaults();
        saveDefaultConfig();
    }
    public void saveFile(YamlConfiguration yml, File file){
        try {
            yml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
