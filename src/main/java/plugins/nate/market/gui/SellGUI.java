package plugins.nate.market.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import plugins.nate.market.interfaces.CustomGUI;
import plugins.nate.market.utils.MarketUtils;

import java.util.List;


public class SellGUI implements CustomGUI {
    private final ConfigurationSection categoriesConfig;


    public SellGUI(ConfigurationSection categoriesConfig) {
        this.categoriesConfig = categoriesConfig;
    }

    @Override
    public Inventory createGUI() {
        // Standard double chest sized inventory
        int inventorySize = 54;

        // Reserves the bottom row for utility buttons (back, next, previous, etc.)
        int usableSlots = inventorySize - 9;

        // Starting index where the first category will go
        int slot = 0;

        // Creates the inventory with title "Sell"
        Inventory gui = Bukkit.createInventory(null, inventorySize, "Sell");

        // Places the back button in the GUI
        gui.setItem(49, createItem(Material.PAPER, "&f&lBack", MarketUtils.BACK_BUTTON_LORE));

        // Iterate over the categories in the sell_config.yml file and create GUI items for each of them.
        for (String categoryName : categoriesConfig.getKeys(false)) {

            // Makes sure the categories don't spill over into the bottom row
            if (slot >= usableSlots) {
                MarketUtils.warn("Too many categories have been added to the config!");
                break;
            }

            // Retrieve category information from the config
            ConfigurationSection categorySection = categoriesConfig.getConfigurationSection(categoryName);
            Material material = Material.matchMaterial(categorySection.getString("item", "BARRIER"));
            String title = coloredProperties(categorySection.getString("title", "DEFAULT_TITLE"));
            String description = coloredProperties(categorySection.getString("description", "DEFAULT_DESCRIPTION"));
            List<String> blockList = categorySection.getStringList("blocks");

            // Create the category item and place it in the GUI
            ItemStack categoryItem = createCategoryItem(material, title, description, blockList);
            gui.setItem(slot, categoryItem);

            // Advance to the next slot making sure to leave a margin on the left and right of the inventory of one slot
            slot++;
        }
        return gui;
    }
}
