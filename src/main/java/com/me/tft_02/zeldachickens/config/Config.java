package com.me.tft_02.zeldachickens.config;

import java.util.ArrayList;
import java.util.List;

public class Config extends AutoUpdateConfigLoader {
    private static Config instance;

    private Config() {
        super("config.yml");
        validate();
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }

        return instance;
    }

    @Override
    protected boolean validateKeys() {
        // Validate all the settings!
        List<String> reason = new ArrayList<String>();

        /* Chicken Settings */
        if (getChickenAmount() <= 0) {
            reason.add("Chickens.Amount should be greater than 0!");
        }

        if (getChickenHealth() <= 0 && getChickenHealth() > 20) {
            reason.add("Chickens.Health only accepts values from 1 to 20!");
        }

        if (getChickenDamage() <= 0 && getChickenDamage() > 20) {
            reason.add("Chickens.Damage only accepts values from 1 to 20!");
        }

        if (getChickenAttackChance() <= 0) {
            reason.add("Chickens.Attack_Chance should be greater than 0!");
        }

        if (getChickenAttackRange() <= 0) {
            reason.add("Chickens.Attack_Range should be greater than 0!");
        }

        if (getChickenSpawnHeight() <= 0) {
            reason.add("Chickens.Spawn_Height should be greater than 0!");
        }

        if (getChickenOutrunDistance() <= 0) {
            reason.add("Chickens.Max_Outrun_Distance should be greater than 0!");
        }

        return noErrorsInConfig(reason);
    }

    @Override
    protected void loadKeys() {}

    /* @formatter:off */

    /* GENERAL SETTINGS */
    public String getLocale() { return config.getString("General.Locale", "en_us"); }

    public boolean getUpdateCheckEnabled() { return config.getBoolean("General.Update_Check", true); }

    public boolean getPreferBeta() { return config.getBoolean("General.Prefer_Beta", false); }

    public boolean getVerboseLoggingEnabled() { return config.getBoolean("General.Verbose_Logging", false); }

    public boolean getConfigOverwriteEnabled() { return config.getBoolean("General.Config_Update_Overwrite", true); }

    /* CHICKEN SETTINGS */
    public int getChickenAmount() { return config.getInt("Chickens.Amount", 10); }

    public double getChickenHealth() { return config.getDouble("Chickens.Health", 4.0); }

    public double getChickenDamage() { return config.getDouble("Chickens.Damage", 2.0); }

    public int getChickenAttackChance() { return config.getInt("Chickens.Attack_Chance", 5); }

    public double getChickenAttackRange() { return config.getDouble("Chickens.Attack_Range", 1.0); }

    public double getChickenSpawnHeight() { return config.getDouble("Chickens.Spawn_Height", 2.0); }

    public boolean getChickenTeleportEnabled() { return config.getBoolean("Chickens.Teleport_Enabled", true); }

    public double getChickenOutrunDistance() { return config.getDouble("Chickens.Max_Outrun_Distance", 4.0); }

    public int getAttackDuration() { return config.getInt("Chickens.Attack_Duration", 120); }

    public boolean getChickenDespawnEnabled() { return config.getBoolean("Chickens.Despawn_Enabled", true); }

    /* @formatter:on */
}
