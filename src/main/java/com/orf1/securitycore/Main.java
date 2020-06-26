package com.orf1.securitycore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public final class Main extends JavaPlugin implements Listener {


    private File playerDataFile;
    private YamlConfiguration modifyPlayerData;

    @Override
    public void onEnable() {
        System.out.println("[SecurityCore] Initializing");

        loadConfig();
        registerEvents();
        registerCommands();
        initiateFiles();

        System.out.println("[SecurityCore] Initialization complete");
    }

    @Override
    public void onDisable() {
        System.out.println("[SecurityCore] Plugin shutting down");
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



    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        System.out.println("[SecurityCore] Player logged in with IP:" + e.getRealAddress());
        Player player = e.getPlayer();
        if (!modifyPlayerData.contains(player.getUniqueId().toString())) {
            System.out.println("Contains Player");
            modifyPlayerData.createSection(player.getUniqueId().toString());
            modifyPlayerData.createSection(player.getUniqueId().toString() + ".Name");
            modifyPlayerData.createSection(player.getUniqueId().toString() + ".IPAddress");
            modifyPlayerData.createSection(player.getUniqueId().toString() + ".Registered");
            modifyPlayerData.createSection(player.getUniqueId().toString() + ".Pin");
            modifyPlayerData.createSection(player.getUniqueId().toString() + ".LoggedIn");
            saveFile(modifyPlayerData, playerDataFile);
        }

        modifyPlayerData.set(player.getUniqueId().toString() + ".Name", player.getName());
        modifyPlayerData.set(player.getUniqueId().toString() + ".IP", e.getRealAddress().toString());
        modifyPlayerData.set(player.getUniqueId().toString() + ".LoggedIn", false);

        if (!modifyPlayerData.get(player.getUniqueId().toString() + ".Registered").equals(true)){
            System.out.println("Player is not registered");
            modifyPlayerData.set(player.getUniqueId().toString() + ".Registered", false);
            modifyPlayerData.set(player.getUniqueId().toString() + ".Pin", "NA");
        }

        saveFile(modifyPlayerData, playerDataFile);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (player.hasPermission("securitycore.staff")){

            if (modifyPlayerData.get(player.getUniqueId().toString() + ".Registered").equals(true)){
                player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.WHITE + "Welcome! Make sure to login using /login");
            }else {
                player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.WHITE + "Welcome! Make sure to set a pin using /register");
            }
        }else {
            player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.WHITE + "Welcome to "+ getConfig().get("serverName") + "!");
        }
    }


    public void registerCommands() {
        getCommand("securitycore").setExecutor(new SecurityCoreCommand());
        getCommand("login").setExecutor(new LoginCommand());
        getCommand("register").setExecutor(new RegisterCommand());
    }

    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(this, this);
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
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {

        Player player = e.getPlayer();
        boolean loggedIn = (boolean) modifyPlayerData.get(player.getUniqueId().toString() + ".LoggedIn");

        if (player.hasPermission("securitycore.staff")){
            if (!loggedIn) {
                if (!e.getMessage().toLowerCase().contains("help") && !e.getMessage().toLowerCase().contains("login") && !e.getMessage().toLowerCase().contains("register")) {
                    e.setCancelled(true);
                    player.sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.RED + "You must be logged in to do that! /login");
                }
            }
        }
    }
    public static String hashString(String input) throws Exception{
        MessageDigest messageDigest=MessageDigest.getInstance("MD5");
        messageDigest.update(input.getBytes(),0,input.length());
        return new BigInteger(1,messageDigest.digest()).toString(16);
    }

}
