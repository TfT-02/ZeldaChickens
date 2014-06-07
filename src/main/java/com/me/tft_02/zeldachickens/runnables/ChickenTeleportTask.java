package com.me.tft_02.zeldachickens.runnables;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.me.tft_02.zeldachickens.config.Config;

public class ChickenTeleportTask extends BukkitRunnable {
    private Player player;
    private Chicken chicken;

    public ChickenTeleportTask(Player player, Chicken chicken) {
        this.player = player;
        this.chicken = chicken;
    }

    @Override
    public void run() {
        double spawnHeight = Config.getInstance().getChickenSpawnHeight();
        Location location = player.getLocation();
        location.setY(location.getY() + 1.0D);

        boolean done = false;

        while (!done) {
            if (location.getBlock().getType() == Material.WATER) {
                done = true;
                location.setY(location.getY());
            }
            else if ((location.getBlock().getType() == Material.AIR) && (location.getY() <= player.getLocation().getY() + spawnHeight + 1.0D)) {
                location.setY(location.getY() + 1.0D);
            }
            else {
                done = true;
                location.setY(location.getY() - 1.0D);
            }
        }

        chicken.teleport(location);
//        spawned = true; Not sure what this is for
    }
}
