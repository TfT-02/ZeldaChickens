package com.me.tft_02.zeldachickens.runnables;

import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.me.tft_02.zeldachickens.datatypes.ZeldaChickenPlayer;

public class ChickenDeathTask extends BukkitRunnable {
    public ZeldaChickenPlayer zeldaChickenPlayer;

    public ChickenDeathTask(ZeldaChickenPlayer zeldaChickenPlayer) {
        this.zeldaChickenPlayer = zeldaChickenPlayer;
    }

    public void run() {
        List<BukkitTask> chickenTimers = zeldaChickenPlayer.getChickenTimers();

        if (chickenTimers.isEmpty()) {
            this.cancel();
            return;
        }

        for (BukkitTask chickenTimer : chickenTimers) {
            if (chickenTimer == null) {
                continue;
            }

            chickenTimer.cancel();
        }

        chickenTimers.clear();
    }
}