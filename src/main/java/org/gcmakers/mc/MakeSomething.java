package org.gcmakers.mc;

import org.bukkit.plugin.java.JavaPlugin;

public final class MakeSomething extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginCommand("geometry").setExecutor(new SphereCommandExecutor());
        // experimental/trolly:
        // getServer().getPluginManager().registerEvents(new PlayerMovementListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
