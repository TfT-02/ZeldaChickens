package com.me.tft_02.zeldachickens.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.me.tft_02.zeldachickens.datatypes.ZeldaChickenPlayer;

public class PlayerManager {
    public static List<ZeldaChickenPlayer> players = new ArrayList<ZeldaChickenPlayer>();

    public static void addPlayer(ZeldaChickenPlayer zeldaChickenPlayer) {
        players.add(zeldaChickenPlayer);
    }

    public static ZeldaChickenPlayer getPlayer(Player player) {
        ZeldaChickenPlayer zeldaChickenPlayer = null;

        for (ZeldaChickenPlayer chickenPlayer : players) {
            if (chickenPlayer.player.getUniqueId() != player.getUniqueId()) {
                continue;
            }

            zeldaChickenPlayer = chickenPlayer;
            break;
        }

        return zeldaChickenPlayer;
    }

    public static void removePlayer(Player player) {
        players.remove(getPlayer(player));
    }
}
