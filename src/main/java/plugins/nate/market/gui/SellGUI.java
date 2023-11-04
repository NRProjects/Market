package plugins.nate.market.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import plugins.nate.market.interfaces.CustomGUI;
import plugins.nate.market.utils.MarketUtils;

import java.util.stream.IntStream;


public class SellGUI implements CustomGUI {
    private final ConfigurationSection categoriesConfig;

    public SellGUI(ConfigurationSection categoriesConfig) {
        this.categoriesConfig = categoriesConfig;
    }

    @Override
    public Inventory createGUI() {
        int inventorySize = 54;

        Inventory marketGUI = Bukkit.createInventory(null, inventorySize, "Sell");

        ItemStack buyItem = createItem(Material.EMERALD_BLOCK, "&a&lTEST");
        ItemStack sellItem = createItem(Material.REDSTONE_BLOCK, "&c&lSELL");
        ItemStack backItem = createItem(Material.PAPER, "&f&lBack", MarketUtils.BACK_BUTTON_LORE);
        ItemStack fillerItem = createItem(Material.GRAY_STAINED_GLASS_PANE, " ");

        IntStream.range(0, inventorySize).forEach(i -> marketGUI.setItem(i, fillerItem));

        marketGUI.setItem(10, buyItem);
        marketGUI.setItem(9, sellItem);
        marketGUI.setItem(49, backItem);

        return marketGUI;
    }
}
