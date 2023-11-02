package org.gcmakers.mc.items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    public static ItemStack wand;

    public static void init() {
        createWand();
    }

    private static void createWand() {
        ItemStack item = new ItemStack(Material.STICK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Stick Of Revenge");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("This stick hates you.");
        lore.add("Like, really, really hates you.");
        lore.add("");
        lore.add(ChatColor.ITALIC + "Do not underestimate how much.");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.LUCK, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        wand = item;

        // Shaped Recipe
        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("awesome-wand"), item);
        sr.shape(
                "B  ",
                " S ",
                "  S"
        );
        sr.setIngredient('B', Material.BLAZE_POWDER);
        sr.setIngredient('S', Material.STICK);
        Bukkit.getServer().addRecipe(sr);

        // Shapeless Recipe
        // ShapelessRecipe sir = new ShapelessRecipe(NamespacedKey.minecraft("awesome-wand-shapeless"), item);
        // sir.addIngredient(2, Material.BLAZE_POWDER);
        // sir.addIngredient(1, Material.DIAMOND);
        // Bukkit.getServer().addRecipe(sir);

        // Furnace Recipe
        // FurnaceRecipe smelt = new FurnaceRecipe(NamespacedKey.minecraft("smelted-awesome-wand"), item, Material.EMERALD, 9.0f, 200);
        // Bukkit.getServer().addRecipe(smelt);
    }

}
