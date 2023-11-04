package plugins.nate.market.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import plugins.nate.market.interfaces.CustomGUI;
import java.util.stream.IntStream;


public class MarketGUI implements CustomGUI {
    @Override
    public Inventory createGUI() {
        int inventorySize = 27;

        Inventory marketGUI = Bukkit.createInventory(null, inventorySize, "Market");

        ItemStack buyItem = createItem(Material.EMERALD_BLOCK, "&a&lBUY");
        ItemStack sellItem = createItem(Material.REDSTONE_BLOCK, "&c&lSELL");

        ItemStack fillerItem = createItem(Material.GRAY_STAINED_GLASS_PANE, " ");

        IntStream.range(0, inventorySize).forEach(i -> marketGUI.setItem(i, fillerItem));

        marketGUI.setItem(11, buyItem);
        marketGUI.setItem(15, sellItem);


        return marketGUI;
    }
}
