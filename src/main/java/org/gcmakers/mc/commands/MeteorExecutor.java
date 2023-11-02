package org.gcmakers.mc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Location;

import org.bukkit.World;
import static org.gcmakers.mc.util.SphereUtil.*;

public class MeteorExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            Player p = (Player) sender;
            Location l = p.getLocation();
            World world = p.getWorld();

            Location target = l.clone();
            target.add(12 * Math.random() * randomMod(), 0, 12 * Math.random() * randomMod());
            Location origin = l.add(128 * Math.random() * randomMod(), world.getMaxHeight(), 128 * Math.random() * randomMod());
            drawPath(origin, target);
            try {
                Thread.sleep(1000);
                world.createExplosion(target, Float.parseFloat(10 * Math.random() + ""));

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // clearPath(origin, target);

//            new BukkitRunnable() {
//
//                int time = 30;
//
//                @Override
//                public void run() {
//
//                    if (time == 3) {
//                        // CHANGE WOOL TO COLOR 1
//                    } else if (time == 0) {
//                        // CHANGE WOOL TO COLOR 2
//                    } else if (time == 0) {
//                        // CHANGE WOOL TO COLOR 3(I THINK YOU SHOULD REMOVE THE BLOCK TOO)
//                        cancel();
//                    }
//
//
//                    time--;
//                }
//            }.runTaskTimer(MakeSomething.getPlugin(MakeSomething.class), 0L, 20L);

            return true;
        }
        return false;
    }

    private int randomMod() {
        int r = 1;
        if (Math.random() > 0.5) {
            r = -1;
        }
        return r;
    }
}

