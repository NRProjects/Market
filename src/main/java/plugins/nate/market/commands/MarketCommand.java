package plugins.nate.market.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plugins.nate.market.gui.MarketGUI;
import plugins.nate.market.managers.GUIManager;

import java.util.List;

import static plugins.nate.market.utils.ChatUtils.coloredChat;

public class MarketCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(coloredChat("&cOnly players can use this command!"));
            return true;
        }

        GUIManager guiManager = GUIManager.getInstance();

        if (args.length == 0) {
            guiManager.openGUI(player, new MarketGUI());
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
