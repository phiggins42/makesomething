package org.gcmakers.mc.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.gcmakers.mc.items.ItemManager;

public class WandEvents implements Listener {
    @EventHandler
    public static void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
           if (event.getItem() != null) {
               if (event.getItem().getItemMeta().equals(ItemManager.wand.getItemMeta())) {
                   Player player = event.getPlayer();
                   player.getWorld().createExplosion(player.getLocation(), 2.0f);
                   player.sendMessage("You used it?");
               }
           }
        }
    }
}
