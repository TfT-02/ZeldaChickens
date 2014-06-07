package com.me.tft_02.zeldachickens.util;

import org.bukkit.permissions.Permissible;

public class Permissions {

    public static boolean bypass(Permissible permissible) {
        return permissible.hasPermission("zeldachickens.bypass");
    }

    public static boolean zeldaChickens(Permissible permissible) {
        return permissible.hasPermission("zeldachickens.commands.zeldachickens");
    }

    public static boolean chickenAttack(Permissible permissible) {
        return permissible.hasPermission("zeldachickens.commands.chickenattack");
    }

    public static boolean chickenAttackOthers(Permissible permissible) {
        return permissible.hasPermission("zeldachickens.commands.chickenattack.others");
    }
}
