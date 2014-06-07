package com.me.tft_02.zeldachickens;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.me.tft_02.zeldachickens.commands.ChickenAttackCommand;
import com.me.tft_02.zeldachickens.commands.ZeldaChickensCommand;
import com.me.tft_02.zeldachickens.config.Config;
import com.me.tft_02.zeldachickens.listeners.EntityListener;
import com.me.tft_02.zeldachickens.listeners.PlayerListener;
import com.me.tft_02.zeldachickens.util.LogFilter;

public class ZeldaChickens extends JavaPlugin {
    public static ZeldaChickens p;

    // Config Validation Check
    public boolean noErrorsInConfigFiles = true;

    public void onEnable() {
        p = this;
        getLogger().setFilter(new LogFilter(this));

        Config.getInstance();

        if (!noErrorsInConfigFiles) {
            return;
        }

        registerEvents();

        getCommand("zeldachickens").setExecutor(new ZeldaChickensCommand());
        getCommand("chickenattack").setExecutor(new ChickenAttackCommand());
    }

    /**
     * Registers all event listeners
     */
    private void registerEvents() {
        PluginManager pluginManager = getServer().getPluginManager();

        // Register events
        pluginManager.registerEvents(new PlayerListener(), this);
        pluginManager.registerEvents(new EntityListener(), this);
    }

    public void onDisable() {
        this.getServer().getScheduler().cancelTasks(this);
    }

    public void debug(String message) {
        getLogger().info("[Debug] " + message);
    }
}