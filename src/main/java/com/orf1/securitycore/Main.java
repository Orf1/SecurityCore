package com.orf1.securitycore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public final class Main extends JavaPlugin implements Listener {

    boolean debug = false;

    private File testFile;
    private YamlConfiguration modifyTestFile;

    @Override
    public void onEnable() {
        System.out.println("[SecurityCore] Initializing");
        System.out.println("[SecurityCore] Server Version: " + Bukkit.getVersion());

        loadConfig();
        checkAuthentication();
        registerEvents();
        registerCommands();
        try {
            initiateFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("[SecurityCore] Initialization complete");
    }

    private void initiateFiles() throws IOException {
        testFile = new File(this.getDataFolder(), "playerdata.yml");
        if (!testFile.exists()) {
            testFile.createNewFile();
        }
        modifyTestFile = YamlConfiguration.loadConfiguration(testFile);
    }

    public YamlConfiguration getTestFile() {
        return modifyTestFile;
    }

    public File getFile(){
        return testFile;
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
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().sendMessage(ChatColor.GREEN + "[SecurityCore] " + ChatColor.WHITE + "Welcome!");
    }

    public void registerCommands() {
        getCommand("securitycore").setExecutor(new SecurityCoreCommand());
        getCommand("login").setExecutor(new LoginCommand());
        getCommand("register").setExecutor(new RegisterCommand());
        getCommand("setup").setExecutor(new SetupCommand());
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
}
