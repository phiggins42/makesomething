package org.gcmakers.mc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.gcmakers.mc.items.ItemManager;

public class ItemExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players plz.");
            return true;
        }

        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("givewand")) {
            player.getInventory().addItem(ItemManager.wand);
        }

        return true;

    }
}
