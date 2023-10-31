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
            p.sendMessage(l.getYaw() + "");
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
        Map points = null;
        if (Objects.equals(cmd, "dome")) {
            points = getDome(r);
        } else if (Objects.equals(cmd, "bowl")) {
            points = getBowl(r);
        } else if (Objects.equals(cmd, "sphere")) {
            points = getSphere(r);
        } else if (Objects.equals(cmd, "circle")) {
            points = getCircle(r, 0);
        } else if (Objects.equals(cmd, "cylinder")) {
            // FIXME: we want tags or something for args, ala **kwargs
            // /make cylinder[r=10,h=20]
            points = getCircle(r, 10);
        } else if (Objects.equals(cmd, "ring")) {
            points = getCircle(r, 0);
            for (int i = r + 1; i <= r + 5; i++) {
                Map tmp = getCircle(i, 0);
                points.putAll(tmp);
            }
        } else if (Objects.equals(cmd, "arch")) {
            // getCirlce(r), but then flop y with either x or z for each point
            // FIXME: allow variable length of tunnel/arch
            // FIXME: determine if to switch x or z based on player orientation
            Map tmp = getCircle(r, 20);
            points = new Hashtable();

            for (Object pos : tmp.keySet()) {
                String[] parts = pos.toString().split(",");
                Double x = Double.parseDouble(parts[0]);
                Double y = Double.parseDouble(parts[1]);
                Double z = Double.parseDouble(parts[2]);
                // an arch is only above y0
                if (x >= -3) {
                    String xyz = y.intValue() + "," + x.intValue() + "," + z.intValue();
                    points.putIfAbsent(xyz, true);
                }

            }
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
