package org.gcmakers.mc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.gcmakers.mc.SphereUtil;
import java.util.*;
import org.bukkit.block.Block;
import org.bukkit.World;
import org.bukkit.Material;
import static org.gcmakers.mc.SphereUtil.*;

public class SphereCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // args:
        //   0 enum sphere|bowl|dome
        //   1 r    radius
        //   2 empty ?
        //   3?  named material set (?)
        //

        if (sender instanceof Player) {

            Player p = (Player) sender;
            Location l = p.getLocation();
            World world = p.getWorld();

            if (args.length > 1) {
                int r = Integer.parseInt(args[1]);

                // clear out all the blocks first
                if (args.length > 2 && Objects.equals(args[2], "empty")) {
                    Map emptyPoints = pickPoints(r - 1, args[0]);
                    for (Object pos : emptyPoints.keySet()) {
                        String[] parts = pos.toString().split(",");
                        Double x = Double.parseDouble(parts[0])  + l.getX();
                        Double y = Double.parseDouble(parts[1]) + l.getY();
                        Double z = Double.parseDouble(parts[2])  + l.getZ();
                        emptyBetween(l, new Location(world, x, y, z));
                    }
                }

                // check if we passed a material type to use?
                Material mat = null;
                if (args.length > 3) {
                    try {
                        mat = Material.valueOf(args[3]);
                    } catch (IllegalArgumentException e) {
                        p.sendMessage("Unknown Material type: " + args[3]);
                        // instead, check if args[3] is one of our hardcoded "named sets"?
                        return false;
                    }
                }

                // fill in the blocks on the borders
                Map points = pickPoints(r, args[0]);
                for (Object pos : points.keySet()) {
                    String[] parts = pos.toString().split(",");
                    Double x = Double.parseDouble(parts[0]) + l.getX();
                    Double y = Double.parseDouble(parts[1]) + l.getY();
                    Double z = Double.parseDouble(parts[2]) + l.getZ();

                    Block block = world.getBlockAt(x.intValue(), y.intValue(), z.intValue());
                    if (mat != null) {
                        block.setType(mat);
                    } else {
                        block.setType(getRandomMaterial());
                    }

                }
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }
    public Map pickPoints(int r, String cmd) {
        Map points;
        if (Objects.equals(cmd, "dome")) {
            points = getDome(r);
        } else if (Objects.equals(cmd, "bowl")) {
            points = getBowl(r);
        } else if (Objects.equals(cmd, "sphere")) {
            points = getSphere(r);
        } else {
            points = getSphere(r);
        }
        return points;
    }
    public Material[] materials = {
            Material.STONE_BRICKS,
            Material.STONE_BRICKS,
            Material.STONE_BRICKS,
            Material.STONE_BRICKS,
            Material.STONE_BRICKS,
            Material.STONE_BRICKS,
            Material.STONE_BRICKS,
            Material.CRACKED_STONE_BRICKS,
            Material.MOSSY_STONE_BRICKS,
            Material.CRACKED_STONE_BRICKS,
            Material.MOSSY_STONE_BRICKS,
            Material.CRACKED_STONE_BRICKS,
            Material.MOSSY_STONE_BRICKS,
            Material.CRACKED_STONE_BRICKS,
            Material.MOSSY_STONE_BRICKS,
            Material.CRACKED_STONE_BRICKS,
            Material.MOSSY_STONE_BRICKS,
            Material.GLASS,
            Material.STONE,
            Material.STONE,
            Material.STONE,
            Material.STONE,
            Material.STONE,
            Material.STONE,
            Material.STONE,
            Material.STONE,
            Material.COBBLESTONE,
            Material.COBBLESTONE,
            Material.COBBLESTONE,
            Material.COBBLESTONE,
            Material.MOSSY_COBBLESTONE,
            Material.MOSSY_COBBLESTONE,
            Material.MOSSY_COBBLESTONE,
            Material.SMOOTH_STONE,
            Material.CHISELED_STONE_BRICKS,
            Material.SHROOMLIGHT,
            Material.GLOWSTONE,
    };

    public Material getRandomMaterial() {
        Random generator = new Random();
        int randIdx = generator.nextInt(materials.length);
        return materials[randIdx];
    }
}
