package plugins.nate.market.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import plugins.nate.market.Market;

import java.io.File;
import java.io.IOException;

public class ConfigUtil {
    private final Market plugin;
    private FileConfiguration buyConfig;
    private FileConfiguration sellConfig;


    public ConfigUtil(Market plugin) {
        this.plugin = plugin;
        loadBuyConfig();
        loadSellConfig();
    }

    private void loadBuyConfig() {
        File buyConfigFile = new File(plugin.getDataFolder(), "buy_config.yml");
        if (!buyConfigFile.exists()) {
            plugin.saveResource("buy_config.yml", false);
        }

        buyConfig = YamlConfiguration.loadConfiguration(buyConfigFile);
    }

    private void loadSellConfig() {
        File sellConfigFile = new File(plugin.getDataFolder(), "sell_config.yml");
        if (!sellConfigFile.exists()) {
            plugin.saveResource("sell_config.yml", false);
        }

        sellConfig = YamlConfiguration.loadConfiguration(sellConfigFile);
    }

    public void saveBuyConfig() {
        File buyConfigFile = new File(plugin.getDataFolder(), "buy_config.yml");
        try {
            buyConfig.save(buyConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSellConfig() {
        File sellConfigFile = new File(plugin.getDataFolder(), "sell_categories.yml");
        try {
            sellConfig.save(sellConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getBuyConfig() {
        return buyConfig;
    }

    public FileConfiguration getSellConfig() {
        return sellConfig;
    }
}
