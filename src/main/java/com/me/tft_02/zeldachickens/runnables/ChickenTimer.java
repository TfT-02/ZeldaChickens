package com.me.tft_02.zeldachickens.runnables;

import org.bukkit.Location;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.me.tft_02.zeldachickens.config.Config;
import com.me.tft_02.zeldachickens.datatypes.ZeldaChickenPlayer;

public class ChickenTimer extends BukkitRunnable {
    public Chicken chicken;
    public ZeldaChickenPlayer zeldaChickenPlayer;
    public Player player;
    boolean spawned = true;

    double outrunDistance;
    boolean teleportEnabled;
    double attackRange;
    double damage;
    double spawnHeight;

    public ChickenTimer(Player player, Chicken chicken, ZeldaChickenPlayer chickenPlayer) {
        this.player = player;
        this.chicken = chicken;
        this.zeldaChickenPlayer = chickenPlayer;

        this.outrunDistance = Config.getInstance().getChickenOutrunDistance();
        this.teleportEnabled = Config.getInstance().getChickenTeleportEnabled();
        this.attackRange = Config.getInstance().getChickenAttackRange();
        this.damage = Config.getInstance().getChickenDamage();
        this.spawnHeight = Config.getInstance().getChickenSpawnHeight();
    }

    public void run() {
        if (!chicken.isValid() || chicken.isDead()) {
            this.cancel();
            return;
        }

        if (player != null) {
            chicken.setTarget(player);



            double xDistance = Math.abs(player.getLocation().getX() - chicken.getLocation().getX());
            double yDistance = Math.abs(player.getLocation().getY() - chicken.getLocation().getY());
            double zDistance = Math.abs(player.getLocation().getZ() - chicken.getLocation().getZ());

            if ((xDistance > outrunDistance || zDistance > outrunDistance) && teleportEnabled) {
                teleportChicken();
            }

            if ((yDistance > outrunDistance) && (!spawned)) {
                teleportChicken();
            }

            if (spawned && (this.player.getLocation().getY() - chicken.getLocation().getY() > 0.0D)) {
                spawned = false;
            }

            if (spawned && (yDistance > spawnHeight + 0.5D)) {
                spawned = false;
            }

            if ((xDistance < attackRange) && (yDistance < attackRange) && (zDistance < attackRange)) {
                player.damage(damage, chicken);
            }
        }

        chicken.setRemainingAir(20);
    }

    @Override
    public void cancel() {
        chicken.damage(20);
        super.cancel();
    }

    private void teleportChicken() {
        // TODO this can be improved with a moderately slow ticking timer to check if a valid tp spot if available?
        Location location = player.getLocation();
        boolean check = true;
        location.setY(location.getY() + 1.0D);

//        while (check) {
//            if (location.getBlock().getType() == Material.WATER) {
//                check = false;
//                location.setY(location.getY());
//            }
//            else if ((location.getBlock().getType() == Material.AIR) && (location.getY() <= player.getLocation().getY() + spawnHeight + 1.0D)) {
//                location.setY(location.getY() + 1.0D);
//            }
//            else {
//                check = false;
//                location.setY(location.getY() - 1.0D);
//            }
//        }

        chicken.teleport(location);
        spawned = true;
    }
}