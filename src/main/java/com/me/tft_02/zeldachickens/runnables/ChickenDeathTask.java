package com.me.tft_02.zeldachickens.runnables;

import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import com.me.tft_02.zeldachickens.datatypes.ZeldaChickenPlayer;

public class ChickenDeathTask extends BukkitRunnable {
    public ZeldaChickenPlayer zeldaChickenPlayer;

    public ChickenDeathTask(ZeldaChickenPlayer zeldaChickenPlayer) {
        this.zeldaChickenPlayer = zeldaChickenPlayer;
    }

    public void run() {
        List<ChickenTimer> chickenTimers = zeldaChickenPlayer.getChickenTimers();

        if (chickenTimers.isEmpty()) {
            this.cancel();
            return;
        }

        for (ChickenTimer chickenTimer : chickenTimers) {
            if (chickenTimer == null) {
                continue;
            }

            chickenTimer.kill();
        }

        zeldaChickenPlayer.clearTimers();
    }
}