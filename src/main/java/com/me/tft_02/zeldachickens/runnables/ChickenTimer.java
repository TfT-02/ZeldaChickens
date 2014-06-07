package com.me.tft_02.zeldachickens.runnables;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

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
        if (!chicken.isValid() || chicken.isDead() || player == null) {
            this.cancel();
            return;
        }

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

        if (xDistance > 1.0 || zDistance > 1.0) {
            Vector vector = player.getLocation().toVector().subtract(chicken.getLocation().toVector());
            vector.normalize().multiply(0.5);
            chicken.setVelocity(vector);
        }

        if (spawned && (this.player.getLocation().getY() - chicken.getLocation().getY() > 0.0D)) {
            spawned = false;
        }

        if (spawned && (yDistance > spawnHeight + 0.5D)) {
            spawned = false;
        }

        if ((xDistance < attackRange) && (yDistance < attackRange) && (zDistance < attackRange)) {
            if (!player.isDead()) {
                player.damage(damage, chicken);
                chicken.getLocation().getWorld().playSound(chicken.getLocation(), Sound.CHICKEN_HURT, 1F, 1F);
            }
        }

        chicken.setRemainingAir(20);
    }

    public void kill() {
        playSmokeEffect(chicken);
        chicken.remove();
        zeldaChickenPlayer.getChickenTimers().remove(this);
        this.cancel();
    }

    private void playSmokeEffect(Entity entity) {
        Location location = entity.getLocation();
        World world = entity.getWorld();

        world.playEffect(location, Effect.SMOKE, BlockFace.SOUTH_EAST);
        world.playEffect(location, Effect.SMOKE, BlockFace.SOUTH);
        world.playEffect(location, Effect.SMOKE, BlockFace.SOUTH_WEST);
        world.playEffect(location, Effect.SMOKE, BlockFace.EAST);
        world.playEffect(location, Effect.SMOKE, BlockFace.SELF);
        world.playEffect(location, Effect.SMOKE, BlockFace.WEST);
        world.playEffect(location, Effect.SMOKE, BlockFace.NORTH_EAST);
        world.playEffect(location, Effect.SMOKE, BlockFace.NORTH);
        world.playEffect(location, Effect.SMOKE, BlockFace.NORTH_WEST);
    }

    private void teleportChicken() {
        Location location = player.getLocation();
        Random random = new Random();
        location.setY(location.getY() + 1.0D);
        location.setX(location.getX() + random.nextDouble());
        location.setZ(location.getZ() + random.nextDouble());

        chicken.teleport(location);
        spawned = true;
    }
}