package org.gcmakers.mc;

import java.util.*;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class SphereUtil {
    /**
     * Empty the blocks between two Locations
     *
     * @param origin Location the origin location
     * @param dest Location the destination location
     */
    public static void emptyBetween(Location origin, Location dest) throws InterruptedException {
        double interval = 1 / 3D;
        double distance = origin.distance(dest);
        Vector difference = dest.toVector().subtract(origin.toVector());
        double points = Math.ceil(distance / interval);
        difference.multiply(1D / points);
        Location location = origin.clone();
        for (int i = 0; i < points; i++) {
            location.add(difference);
            location.getBlock().setType(Material.AIR);
            Thread.sleep(1);
        }
    }

    public static void emptyBetweenFromList(Location origin, Location dest, Material[] blacklist) {
        double interval = 1 / 2D;
        double distance = origin.distance(dest);
        Vector difference = dest.toVector().subtract(origin.toVector());
        double points = Math.ceil(distance / interval);
        difference.multiply(1D / points);
        Location location = origin.clone();
        for (int i = 0; i < points; i++) {
            location.add(difference);
            Block block = location.getBlock();
            Material blockMat = block.getType();
            boolean contains = Arrays.asList(blacklist).contains(blockMat);
            if (contains) {
                block.setType(Material.AIR);
            }
        }
    }

    public static void drawPath(Location origin, Location dest) {
        double interval = 1 / 2D;
        double distance = origin.distance(dest);
        Vector difference = dest.toVector().subtract(origin.toVector());
        double points = Math.ceil(distance / interval);
        difference.multiply(1D / points);
        Location location = origin.clone();
        for (int i = 0; i < points; i++) {
            location.add(difference);
            location.getBlock().setType(Material.MAGMA_BLOCK);
        }
    }

    public static void clearPath(Location origin, Location dest) {
        double interval = 1 / 2D;
        double distance = origin.distance(dest);
        Vector difference = dest.toVector().subtract(origin.toVector());
        double points = Math.ceil(distance / interval);
        difference.multiply(1D / points);
        Location location = origin.clone();
        for (int i = 0; i < points; i++) {
            location.add(difference);
            location.getBlock().setType(Material.AIR);
        }
    }

    /**
     * Get the points for a geometric shape (spheres in this case)
     *
     * @param r the radius of the shape
     * @param dome true to include the top of the shape
     * @param bowl true to include the bottom of the shape
     * @return Map of coordinates that make up the shape (0 based, so needs offset by player position)
     */
    public static Map getPoints(int r, boolean dome, boolean bowl) {
        Map coords = new Hashtable();
        double longsteps = r * 2;
        double latsteps = r * 2;
        for (double ss = 0; ss < longsteps; ss += 0.1) {
            for (double tt = 0; tt <= latsteps; tt += 0.1) {
                double s = 2 * Math.PI * ss / longsteps;
                double t = Math.PI * tt / latsteps;
                double x = Math.floor(r * Math.cos(s) * Math.sin(t));
                double y = Math.floor(r * Math.sin(s) * Math.sin(t));
                double z = Math.floor(r * Math.cos(t));

                // a dome would be y >= 0
                // a bowl would be y <= 0
                // a sphere is all coordinates
                if ((dome && bowl) || (dome && y >= 0) || (bowl && y <= 0)) {
                    String xyz = x + "," + y + "," + z;
                    coords.putIfAbsent(xyz, true);
                }

            }
        }
        return coords;

    }

    public static Map getCircle(int r, int height) {
        int w = (50 + r) * 2;
        int h = (50 + r) * 2;
        double cx = w / 2d;
        double cy = h / 2d;

        Map coords = new Hashtable();

        for (int yy = 0; yy <= height; yy++) {
            for (int i = 0; i < 3610; i++) {
                double radians = (i / 10d) * (Math.PI / 180d);
                double x = cx + r * Math.sin(radians);
                double y = cy + r * Math.cos(radians);
                String xyz = Math.floor(x - cx) + "," + yy + "," +Math.floor(y - cy);
                coords.putIfAbsent(xyz, true);
            }
        }
        return coords;
    }

    /**
     * Get a full sphere of a particular size
     * @param r the radius
     * @return Map of the coordinates
     */
    public static Map getSphere(int r) {
        return getPoints(r, true, true);
    }

    /**
     * Get a dome (top half of a sphere) of a particular size
     *
     * @param r the radius
     * @return Map of the coordinates
     */
    public static Map getDome(int r) {
        return getPoints(r, true, false);
    }

    /**
     * Get a bowl (lower half of a sphere) of a particular size
     *
     * @param r the radius
     * @return Map of the coordinates
     */
    public static Map getBowl(int r) {
        return getPoints(r, false, true);
    }

}