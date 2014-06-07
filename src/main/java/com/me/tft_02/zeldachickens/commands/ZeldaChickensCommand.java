package com.me.tft_02.zeldachickens.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.me.tft_02.zeldachickens.ZeldaChickens;
import com.me.tft_02.zeldachickens.util.Permissions;

public class ZeldaChickensCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length) {
            case 0:
                sender.sendMessage("ZeldaChickens version " + ZeldaChickens.p.getDescription().getVersion());
                return printUsage(sender);
            case 1:
                if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
                    return helpPages(sender, args);
                }
            default:
                return false;
        }
    }

    private boolean helpPages(CommandSender sender, String[] args) {
        if (args.length >= 2 && Integer.parseInt(args[1]) > 1) {
            getHelpPage(Integer.parseInt(args[1]), sender);
            return true;
        }

        getHelpPage(1, sender);
        return true;
    }

    private void getHelpPage(int page, CommandSender sender) {
        int maxPages = 1;
        int nextPage = page + 1;
        if (page > maxPages) {
            sender.sendMessage(ChatColor.RED + "This page does not exist." + ChatColor.GOLD + " /help [0-" + maxPages + "]");
            return;
        }

        String dot = ChatColor.DARK_RED + "* ";
        sender.sendMessage(ChatColor.GRAY + "-----[ " + ChatColor.GOLD + "ZeldaChickens Help" + ChatColor.GRAY + " ]----- Page " + page + "/" + maxPages);
        if (page == 1) {
            sender.sendMessage(ChatColor.GOLD + "Commands:");
            if (Permissions.zeldaChickens(sender)) {
                sender.sendMessage(dot + ChatColor.GREEN + "/zeldachickens" + ChatColor.GRAY + " Check the status of the plugin.");
            }
            if (Permissions.chickenAttack(sender)) {
                sender.sendMessage(dot + ChatColor.GREEN + "/chickenattack" + ChatColor.GRAY + " Summon a chicken swarm attack on yourself.");
            }
            if (Permissions.chickenAttackOthers(sender)) {
                sender.sendMessage(dot + ChatColor.GREEN + "/chickenattack <player>" + ChatColor.GRAY + " Summon a chicken swarm attack at the target player.");
            }
        }
        if (nextPage <= maxPages) {
            sender.sendMessage(ChatColor.GOLD + "Type /zeldachickens help " + nextPage + " for more");
        }
    }

    private boolean printUsage(CommandSender sender) {
        sender.sendMessage("Usage: /zeldachickens [help | ?]");
        return false;
    }
}