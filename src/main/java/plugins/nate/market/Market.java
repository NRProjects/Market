package plugins.nate.market;

import org.bukkit.plugin.java.JavaPlugin;
import plugins.nate.market.utils.CommandRegistration;
import plugins.nate.market.utils.ConfigUtil;
import plugins.nate.market.utils.EventRegistration;

public final class Market extends JavaPlugin {
    private static Market plugin;
    private ConfigUtil configUtil;


    @Override
    public void onEnable() {
        super.onEnable();

        plugin = this;
        configUtil = new ConfigUtil(this);

        EventRegistration.registerEvents(this);
        CommandRegistration.registerCommands(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Market getPlugin() {
        return plugin;
    }

    public ConfigUtil getConfigUtil() {
        return configUtil;
    }
}
