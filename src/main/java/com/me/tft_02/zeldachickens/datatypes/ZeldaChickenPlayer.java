package com.me.tft_02.zeldachickens.datatypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.me.tft_02.zeldachickens.ZeldaChickens;
import com.me.tft_02.zeldachickens.config.Config;
import com.me.tft_02.zeldachickens.runnables.ChickenDeathTask;
import com.me.tft_02.zeldachickens.runnables.ChickenTimer;
import com.me.tft_02.zeldachickens.util.Misc;

public class ZeldaChickenPlayer {
    public Player player;
    public List<ChickenTimer> chickenTimers = new ArrayList<ChickenTimer>();
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

        int chickenAmount = Config.getInstance().getChickenAmount();

        if (firstChicken != null) {
            ChickenTimer chickenTimer = new ChickenTimer(player, firstChicken, this);
            chickenTimer.runTaskTimer(ZeldaChickens.p, 8, 8);
            chickenTimers.add(chickenTimer);
        }

        for (int i = 0; i < chickenAmount; i++) {
            Location location = player.getLocation();
            Random random = new Random();
            location.setY(location.getY() + Config.getInstance().getChickenSpawnHeight() + random.nextDouble());
            location.setX(location.getX() + random.nextDouble());
            location.setZ(location.getZ() + random.nextDouble());

            Chicken chicken = (Chicken) location.getWorld().spawnEntity(location, EntityType.CHICKEN);
            chicken.setHealth(Config.getInstance().getChickenHealth());

            ChickenTimer chickenTimer = new ChickenTimer(player, chicken, this);
            chickenTimer.runTaskTimer(ZeldaChickens.p, 8, 8);
            chickenTimers.add(chickenTimer);
        }

        int attackDuration = Config.getInstance().getAttackDuration();
        new ChickenDeathTask(this).runTaskLater(ZeldaChickens.p, attackDuration * 20);
        chickenAttacks = 0;
    }

    public void checkAttack(Chicken chicken) {
        if (!chickenTimers.isEmpty()) {
            return;
        }

        if (Misc.activationSuccessful(Config.getInstance().getChickenAttackChance())) {
            summonChickens(chicken);
        }
    }

    public List<ChickenTimer> getChickenTimers() {
        return chickenTimers;
    }

    public void clearTimers() {
        chickenTimers.clear();
    }
}