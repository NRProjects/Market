package plugins.nate.market.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import plugins.nate.market.interfaces.CustomGUI;
import plugins.nate.market.utils.MarketUtils;

import java.util.List;

public class BlockCatalogGUI implements CustomGUI {
    private final List<String> blocks;


    public BlockCatalogGUI(List<String> blocks) {
        this.blocks = blocks;
    }

    @Override
    public Inventory createGUI() {
        // Standard double chest sized inventory
        int inventorySize = 54;

        // Reserves the bottom row for utility buttons (back, next, previous, etc.)
        int usableSlots = inventorySize - 9;

        // Starting index where the first block will go
        int slot = 0;

        // Creates the inventory with title "Select Block"
        Inventory gui = Bukkit.createInventory(null, inventorySize, "Select Block");

        // Places the back button in the GUI
        gui.setItem(49, createItem(Material.PAPER, "&f&lBack", MarketUtils.BACK_BUTTON_LORE));

        // Iterates through the blocks included in the category
        for (String blockName : blocks) {
            // Checks if the amount of blocks will overflow out of the inventory
            if (slot >= usableSlots) {
                MarketUtils.warn("Too many blocks have been added to this category!");
                break;
            }

            // Matches the string in the config to a material
            Material material = Material.matchMaterial(blockName);

            if (material != null) {
                // Creates the item
                ItemStack blockItem = createItem(material, null, "Click to buy quantities");

                // Sets the item the inventory
                gui.setItem(slot, blockItem);

                // Moves onto the next slot
                slot++;
            }
        }

        return gui;
    }
}
