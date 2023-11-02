package org.gcmakers.mc;

import org.bukkit.plugin.java.JavaPlugin;
import org.gcmakers.mc.commands.ItemExecutor;
import org.gcmakers.mc.commands.MeteorExecutor;
import org.gcmakers.mc.commands.SphereExecutor;

import org.gcmakers.mc.events.WandEvents;
import org.gcmakers.mc.items.ItemManager;

public final class MakeSomething extends JavaPlugin {

    @Override
    public void onEnable() {
        ItemManager.init();

        getServer().getPluginManager().registerEvents(new WandEvents(), this);
        getServer().getPluginCommand("meteor").setExecutor(new MeteorExecutor());
        getServer().getPluginCommand("make").setExecutor(new SphereExecutor());

//        getServer().getPluginCommand("dig").setExecutor(new DigCommandExecutor());
//        // experimental/trolly:
        // getServer().getPluginManager().registerEvents(new PlayerMovementListener(), this);
        // getServer().getPluginManager().registerEvents(new MagicSword(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
