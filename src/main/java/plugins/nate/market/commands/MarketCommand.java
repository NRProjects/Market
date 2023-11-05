package plugins.nate.market.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plugins.nate.market.Market;
import plugins.nate.market.events.MarketNavigateEvent;
import plugins.nate.market.gui.MarketGUI;
import plugins.nate.market.managers.GUIManager;

import java.util.List;

import static plugins.nate.market.utils.ChatUtils.coloredChat;

public class MarketCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // Checks if command sender is a player since only players will be able to access the GUI
        if (!(sender instanceof Player player)) {
            sender.sendMessage(coloredChat("&cOnly players can use this command!"));
            return true;
        }

        // Gets the GUI instance
        GUIManager guiManager = GUIManager.getInstance();

        // TODO: Expand command

        if (args.length == 0) {
            guiManager.navigateTo(player, new MarketGUI());
            return true;
        } else {
            switch(args[0].toLowerCase()) {

            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
