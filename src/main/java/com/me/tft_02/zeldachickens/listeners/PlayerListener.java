package com.me.tft_02.zeldachickens.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.me.tft_02.zeldachickens.config.Config;
import com.me.tft_02.zeldachickens.datatypes.ZeldaChickenPlayer;
import com.me.tft_02.zeldachickens.runnables.ChickenTimer;
import com.me.tft_02.zeldachickens.util.PlayerManager;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        PlayerManager.addPlayer(new ZeldaChickenPlayer(event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ZeldaChickenPlayer zeldaChickenPlayer = PlayerManager.getPlayer(player);

        if (!zeldaChickenPlayer.chickenTimers.isEmpty()) {
            for (ChickenTimer chickenTimer : zeldaChickenPlayer.chickenTimers) {
                if (chickenTimer == null) {
                    continue;
                }

                chickenTimer.kill();
            }
        }

        PlayerManager.removePlayer(player);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        ZeldaChickenPlayer zeldaChickenPlayer = PlayerManager.getPlayer(player);

        if (zeldaChickenPlayer.chickenTimers.isEmpty()) {
            return;
        }

        for (ChickenTimer chickenTimer : zeldaChickenPlayer.chickenTimers) {
            if (chickenTimer == null) {
                continue;
            }

            if (Config.getInstance().getChickenDespawnEnabled()) {
                chickenTimer.kill();
            }
        }
    }
}
