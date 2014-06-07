package com.me.tft_02.zeldachickens.listeners;

import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

import com.me.tft_02.zeldachickens.ZeldaChickens;
import com.me.tft_02.zeldachickens.datatypes.ZeldaChickenPlayer;
import com.me.tft_02.zeldachickens.util.Permissions;
import com.me.tft_02.zeldachickens.util.PlayerManager;

public class EntityListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamage() <= 0) {
            return;
        }

        Entity attacker = event.getDamager();
        Entity defender = event.getEntity();

//        if (Misc.isNPCEntity(attacker) || Misc.isNPCEntity(defender)) {
//            return;
//        }

        if (attacker instanceof Projectile) {
            ProjectileSource projectileSource = ((Projectile) attacker).getShooter();

            if (projectileSource instanceof LivingEntity) {
                attacker = (LivingEntity) projectileSource;
            }
        }
        else if (attacker instanceof Tameable) {
            AnimalTamer animalTamer = ((Tameable) attacker).getOwner();

            if (animalTamer instanceof Entity) {
                attacker = (Entity) animalTamer;
            }
        }

        if (attacker instanceof Player && defender instanceof Chicken) {
            Player attackingPlayer = (Player) attacker;

            if (Permissions.bypass(attackingPlayer)) {
                return;
            }

            ZeldaChickenPlayer zeldaChickenPlayer = PlayerManager.getPlayer(attackingPlayer);

            if (zeldaChickenPlayer == null) {
                ZeldaChickens.p.getLogger().warning("Issue retrieving player from somewhere, please advise author");
                return;
            }

            zeldaChickenPlayer.checkAttack((Chicken) defender);
        }
    }
}
