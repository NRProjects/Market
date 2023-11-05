package plugins.nate.market.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import plugins.nate.market.interfaces.CustomGUI;


public class MarketGUI implements CustomGUI {
    @Override
    public Inventory createGUI() {
        // Standard single chest sized inventory
        int inventorySize = 27;

        // Creates the inventory with title "Market"
        Inventory marketGUI = Bukkit.createInventory(null, inventorySize, "Market");

        // Creates an item to access the purchase options
        ItemStack buyItem = createItem(Material.EMERALD_BLOCK, "&a&lBUY");
        // Creates an item to access the sell options
        ItemStack sellItem = createItem(Material.REDSTONE_BLOCK, "&c&lSELL");

        // Sets the buy and sell items
        marketGUI.setItem(11, buyItem);
        marketGUI.setItem(15, sellItem);

        return marketGUI;
    }
}
