package com.seailz.chestlock.commands;

import com.seailz.chestlock.ChestLockingPlugin;
import com.seailz.chestlock.core.Locale;
import games.negative.framework.command.Command;
import games.negative.framework.command.annotation.CommandInfo;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

@CommandInfo(
        name = "unlock"
)
public class CommandUnlock extends Command {
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Block block = player.getTargetBlock(null, 5);
        if (block == null || block.getType() == Material.AIR) {
            Locale.ERROR_NO_BLOCK.send(player);
            return;
        }

        if (block.getType() != Material.CHEST && block.getType() != Material.BARREL) {
            Locale.ERROR_NOT_CHEST.send(player);
            return;
        }

        if (!ChestLockingPlugin.isLocationLocked(block.getLocation())) {
            Locale.ERROR_NOT_LOCKED.send(player);
            return;
        }

        if (!ChestLockingPlugin.getOwnerUUID(block.getLocation()).equals(player.getUniqueId().toString())) {
            Locale.ERROR_NOT_YOUR_BLOCK.send(player);
            return;
        }

        FileConfiguration config = ChestLockingPlugin.getInstance().getConfig();
        config.set(ChestLockingPlugin.getLocationUUID(block.getLocation()), "");
        ChestLockingPlugin.getInstance().saveConfig();
        Locale.SUCCESS_UNLOCKED.send(player);
    }
}
