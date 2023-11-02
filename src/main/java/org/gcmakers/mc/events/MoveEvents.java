package org.gcmakers.mc.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class MoveEvents implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player p = event.getPlayer();
        // the block the player is standing on
        Block b = p.getLocation().getBlock().getRelative(BlockFace.DOWN);

        if (b.getType() == Material.GRASS_BLOCK) {
            World w = p.getWorld();
            w.createExplosion(p.getLocation(), 5);
        }
    }

}

