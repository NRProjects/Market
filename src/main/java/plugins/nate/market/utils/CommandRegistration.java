package plugins.nate.market.utils;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import plugins.nate.market.Market;
import plugins.nate.market.commands.MarketCommand;

public class CommandRegistration {
    public static void registerCommands(Market plugin) {
        setupCommand("market", new MarketCommand(), plugin);
    }

    private static void setupCommand(String commandLabel, CommandExecutor executor, Market plugin) {
        PluginCommand command = plugin.getCommand(commandLabel);
        if(command != null) {
            command.setExecutor(executor);
        }
    }
}
