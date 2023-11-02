package org.gcmakers.mc;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChainFactory;
import co.aikar.taskchain.TaskChain;
import org.bukkit.plugin.java.JavaPlugin;

public final class MakeSomething extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginCommand("make").setExecutor(new SphereCommandExecutor());
        getServer().getPluginCommand("dig").setExecutor(new DigCommandExecutor());
        getServer().getPluginCommand("meteor").setExecutor(new MeteorCommandExecutor());
        // experimental/trolly:
        // getServer().getPluginManager().registerEvents(new PlayerMovementListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
