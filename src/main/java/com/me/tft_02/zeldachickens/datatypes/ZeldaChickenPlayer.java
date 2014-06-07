package com.me.tft_02.zeldachickens.datatypes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.me.tft_02.zeldachickens.ZeldaChickens;
import com.me.tft_02.zeldachickens.config.Config;
import com.me.tft_02.zeldachickens.runnables.ChickenDeathTask;
import com.me.tft_02.zeldachickens.runnables.ChickenTimer;

public class ZeldaChickenPlayer {
    public Player player;
    public List<BukkitTask> chickenTimers = new ArrayList<BukkitTask>();
    public int chickenAttacks = 0;

    public ZeldaChickenPlayer(Player player) {
        this.player = player;
    }

    public void summonChickens(Chicken firstChicken) {
        for (Player players : ZeldaChickens.p.getServer().getOnlinePlayers()) {
            if (players != player) {
                players.sendMessage(player.getDisplayName() + "has incurred the wrath of the chicken goddess!");
            }
            else {
                players.sendMessage(ChatColor.RED + "Warning... Incoming swarm!");
            }
        }

        Location location = player.getLocation();
        location.setY(location.getY() + Config.getInstance().getChickenSpawnHeight());
        int chickenAmount = Config.getInstance().getChickenAmount();

        if (firstChicken != null) {
            BukkitTask chickenTimer = new ChickenTimer(player, firstChicken, this).runTaskTimer(ZeldaChickens.p, 10, 10);
            chickenTimers.add(chickenTimer);
        }

        for (int i = 0; i < chickenAmount; i++) {
            Chicken chicken = (Chicken) location.getWorld().spawnEntity(location, EntityType.CHICKEN);
            chicken.setHealth(Config.getInstance().getChickenHealth());

            BukkitTask chickenTimer = new ChickenTimer(player, chicken, this).runTaskTimer(ZeldaChickens.p, 10, 10);
            chickenTimers.add(chickenTimer);
            i++;
        }

        new ChickenDeathTask(this).runTaskLater(ZeldaChickens.p, 2 * 60 * 20);
        chickenAttacks = 0;
    }

    public void checkAttack(Chicken chicken) {
        if (chickenTimers.isEmpty()) {
            chickenAttacks += 1;
        }

        if (chickenAttacks >= Config.getInstance().getChickenHitTrigger()) {
            summonChickens(chicken);
        }
    }

    public List<BukkitTask> getChickenTimers() {
        return chickenTimers;
    }
}