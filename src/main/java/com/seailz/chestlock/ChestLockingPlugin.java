package com.seailz.chestlock;

import com.seailz.chestlock.commands.CommandLockChest;
import com.seailz.chestlock.commands.CommandUnlock;
import com.seailz.chestlock.core.Locale;
import com.seailz.chestlock.core.Logger;
import com.seailz.chestlock.listeners.BreakLockChestListener;
import com.seailz.chestlock.listeners.OpenLockChestListener;
import games.negative.framework.BasePlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public final class ChestLockingPlugin extends BasePlugin {

    @Getter
    @Setter
    public static ChestLockingPlugin instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        super.onEnable();
        long start = System.currentTimeMillis();

        setInstance(this);
        Locale.init(this);

        saveDefaultConfig();

        register(RegisterType.COMMAND);
        register(RegisterType.LISTENER);

        long finish = System.currentTimeMillis() - start;
        Logger.log(Logger.LogLevel.SUCCESS, "Started in " + finish + "ms!");
    }

    public void register(RegisterType type) {
        switch (type) {
            case COMMAND:
                registerCommands(
                        // Insert commands
                        new CommandLockChest(),
                        new CommandUnlock()
                );
                break;
            case LISTENER:
                registerListeners(
                        // Register Listeners
                        new OpenLockChestListener(),
                        new BreakLockChestListener()
                );
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static boolean isLocationLocked(Location loc) {
        FileConfiguration config = getInstance().getConfig();
        for (String key : config.getKeys(false)) {
            if (config.getLocation(key + ".location") != null && config.getLocation(key + ".location").equals(loc)) {
                return true;
            }
        }
        return false;
    }

    public static String getLocationUUID(Location loc) {
        FileConfiguration config = getInstance().getConfig();
        for (String key : config.getKeys(false)) {
            if (config.getLocation(key + ".location") != null && config.getLocation(key + ".location").equals(loc)) {
                return key;
            }
        }
        return null;
    }

    /**
     * Returns the owner of a given location, or null if none can be found.
     * <br>Recommended to run {@link #isLocationLocked(Location)} first before running this method to avoid null errors.
     */
    public static String getOwnerUUID(Location loc) {
        FileConfiguration config = getInstance().getConfig();
        for (String key : config.getKeys(false)) {
            if (config.getLocation(key + ".location") != null && config.getLocation(key + ".location").equals(loc)) {
                return config.getString(key + ".owner");
            }
        }
        return null;
    }


    public enum RegisterType {COMMAND, LISTENER}
}
