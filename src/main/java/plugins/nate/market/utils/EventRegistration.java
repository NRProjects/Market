package plugins.nate.market.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import plugins.nate.market.Market;
import plugins.nate.market.listeners.MarketListener;

public class EventRegistration {
    public static void registerEvents(Market plugin) {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new MarketListener(), plugin);

    }
}
