package com.seailz.chestlock.listeners;

import com.seailz.chestlock.ChestLockingPlugin;
import com.seailz.chestlock.core.Locale;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class OpenLockChestListener implements Listener {

    @EventHandler
    public void onOpenChest(PlayerInteractEvent ev) {
        Block block = ev.getClickedBlock();
        if (block == null) return;
        if (ev.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (block.getType() != Material.CHEST && block.getType() != Material.BARREL) return;

        if (!ChestLockingPlugin.isLocationLocked(block.getLocation())) return;
        String ownerId = ChestLockingPlugin.getOwnerUUID(block.getLocation());
        if (ownerId == null) return;

        if (!ownerId.equals(ev.getPlayer().getUniqueId().toString())) {
            ev.setCancelled(true);
            Locale.BLOCK_IS_LOCKED.send(ev.getPlayer());
        }
    }

}
