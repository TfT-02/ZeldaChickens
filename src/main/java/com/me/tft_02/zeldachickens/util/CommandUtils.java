package com.me.tft_02.zeldachickens.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.me.tft_02.zeldachickens.ZeldaChickens;

public final class CommandUtils {

    public static boolean noConsoleUsage(CommandSender sender) {
        if (sender instanceof Player) {
            return false;
        }

        sender.sendMessage("This command does not support console usage.");
        return true;
    }

    public static boolean isOffline(CommandSender sender, OfflinePlayer player) {
        if (player != null && player.isOnline()) {
            return false;
        }

        sender.sendMessage(ChatColor.RED + "This command does not work for offline players.");
        return true;
    }

    public static List<String> getOnlinePlayerNames(CommandSender sender) {
        Player player = sender instanceof Player ? (Player) sender : null;
        List<String> onlinePlayerNames = new ArrayList<String>();

        for (Player onlinePlayer : ZeldaChickens.p.getServer().getOnlinePlayers()) {
            if (player != null && player.canSee(onlinePlayer)) {
                onlinePlayerNames.add(onlinePlayer.getName());
            }
        }

        return onlinePlayerNames;
    }

    /**
     * Get a matched player name if one was found in the database.
     *
     * @param partialName Name to match
     *
     * @return Matched name or {@code partialName} if no match was found
     */
    public static String getMatchedPlayerName(String partialName) {
        Player player = ZeldaChickens.p.getServer().getPlayer(partialName);

        if (player != null) {
            partialName = player.getName();
        }


        return partialName;
    }
}
