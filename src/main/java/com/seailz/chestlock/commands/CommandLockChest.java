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

import java.util.HashSet;
import java.util.UUID;

@CommandInfo(
        name = "lockchest",
        description = "Locks a chest",
        playerOnly = true
)
public class CommandLockChest extends Command {
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

        FileConfiguration config = ChestLockingPlugin.getInstance().getConfig();
        UUID randomUid = UUID.randomUUID();

        if (ChestLockingPlugin.isLocationLocked(block.getLocation())) {
            Locale.ALREADY_LOCKED.send(player);
            return;
        }

        config.set(randomUid + ".location", block.getLocation());
        config.set(randomUid + ".owner", player.getUniqueId().toString());
        ChestLockingPlugin.getInstance().saveConfig();
        System.out.println("locked");
        Locale.LOCK_SUCCESS.send(player);
    }
}
