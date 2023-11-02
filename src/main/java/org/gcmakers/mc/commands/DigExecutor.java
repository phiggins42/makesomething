package org.gcmakers.mc.commands;

import static org.gcmakers.mc.util.SimplexNoise.noise;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Location;

import java.time.Instant;
import java.util.*;
import org.bukkit.World;
import org.bukkit.Material;
import static org.gcmakers.mc.util.SphereUtil.*;

public class DigExecutor implements CommandExecutor {

    private final double w = 1000;
    private final double h = 1000;
    private final double mx = w / 2;
    private final double my = h / 2;
    private final double deltaAngle = (Math.PI * 2) / 400;
    private final double noiseFactor = (50 / w) * 50;
    private final double zoom = (my / h) * 2;
    private long now = Instant.now().getEpochSecond();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            if (args.length > 0) {
                double r = Double.parseDouble(args[0]);

                Player p = (Player) sender;
                Location l = p.getLocation();
                World world = p.getWorld();
                double startY = l.getY();
                double maxY = 72;
                Map coords = new HashMap();
                for (double y = startY; y < startY + 10; y++) {
                    Map thisCircle = drawCircle(Math.floor(++r * Math.PI * Math.random()), y);
                    coords.putAll(thisCircle);
                }

                for (Object pos : coords.keySet()) {
                    String[] parts = pos.toString().split(",");
                    Double x = Double.parseDouble(parts[0]) + l.getX();
                    Double y = Double.parseDouble(parts[1]) + l.getY();
                    Double z = Double.parseDouble(parts[2]) + l.getZ();
                    Location target = new Location(world, x, y, z);
                    emptyBetweenFromList(l, target, blacklist);
                }

                return true;

            }
        }
        return false;
    }

    // get coordinates for a circle
    private Map drawCircle(double r, double y) {
        Map coords = new HashMap();
        for (double angle = 0; angle < Math.PI * 2; angle += deltaAngle) {
            String point = calcPoint(angle, r, y);
            coords.putIfAbsent(point, true);
        }
        return coords;
    }

    private String calcPoint(double angle, double r, double y) {
        double x = Math.cos(angle) * r + w  / 2;
        double z = Math.sin(angle) * r + h / 2;
        double n = noise(x / zoom, z / zoom, now / 2000D) * noiseFactor;
        x = Math.ceil((Math.cos(angle) * (r + n) + w / 2) - mx);
        z = Math.ceil((Math.sin(angle) * (r + n) + h / 2) - my);
        return x + "," + y + "," + z;
    }

    private static final Material[] blacklist = {
            Material.STONE,
            Material.GRANITE,
            Material.DIORITE,
            Material.DEEPSLATE,
            Material.TUFF,
            Material.CALCITE,
            Material.WATER,
            Material.LAVA,
            Material.ANDESITE,
            Material.DIRT,
            Material.GRASS_BLOCK,
            Material.DRIPSTONE_BLOCK,
            Material.GOLD_ORE,
            Material.DEEPSLATE_COPPER_ORE,
            Material.DEEPSLATE_GOLD_ORE,
            Material.DEEPSLATE_REDSTONE_ORE,
            Material.DEEPSLATE_IRON_ORE,
            Material.DEEPSLATE_DIAMOND_ORE,
            Material.SMOOTH_BASALT,
            Material.MAGMA_BLOCK,
            Material.GRAVEL,
            Material.IRON_ORE,
            Material.DIAMOND_ORE,
            Material.LAPIS_ORE,
            Material.REDSTONE_ORE,
            Material.COPPER_ORE,
    };
}
