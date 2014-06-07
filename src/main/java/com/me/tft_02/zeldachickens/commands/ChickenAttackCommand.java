package com.me.tft_02.zeldachickens.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import com.me.tft_02.zeldachickens.ZeldaChickens;
import com.me.tft_02.zeldachickens.util.CommandUtils;
import com.me.tft_02.zeldachickens.util.Permissions;
import com.me.tft_02.zeldachickens.util.PlayerManager;

import com.google.common.collect.ImmutableList;

public class ChickenAttackCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length) {
            case 0:
                if (CommandUtils.noConsoleUsage(sender)) {
                    return true;
                }

                if (!Permissions.chickenAttack(sender)) {
                    sender.sendMessage(command.getPermissionMessage());
                    return true;
                }

                PlayerManager.getPlayer((Player) sender).summonChickens(null);
                return true;

            case 1:
                if (!Permissions.chickenAttackOthers(sender)) {
                    sender.sendMessage(command.getPermissionMessage());
                    return true;
                }

                String playerName = CommandUtils.getMatchedPlayerName(args[0]);
                Player player = ZeldaChickens.p.getServer().getPlayerExact(playerName);

                if (CommandUtils.isOffline(sender, player)) {
                    return true;
                }

                PlayerManager.getPlayer(player).summonChickens(null);
                return true;

            default:
                return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        switch (args.length) {
            case 1:
                List<String> playerNames = CommandUtils.getOnlinePlayerNames(sender);
                return StringUtil.copyPartialMatches(args[0], playerNames, new ArrayList<String>(playerNames.size()));
            default:
                return ImmutableList.of();
        }
    }
}
