package plugins.nate.market.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import plugins.nate.market.interfaces.CustomGUI;
import plugins.nate.market.utils.MarketUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


public class BuyGUI implements CustomGUI {
    private final ConfigurationSection categoriesConfig;


    public BuyGUI(ConfigurationSection categoriesConfig) {
        this.categoriesConfig = categoriesConfig;
    }

    /**
     * Creates the inventory GUI with item categories.
     *
     * @return The constructed Inventory GUI object.
     */
    @Override
    public Inventory createGUI() {
        int inventorySize = 54;
        Inventory gui = Bukkit.createInventory(null, inventorySize, "Buy");

        // Fill the GUI with placeholder items for all the slots
        ItemStack fillerItem = createItem(Material.GRAY_STAINED_GLASS_PANE, " ");
        IntStream.range(0, inventorySize).forEach(i -> gui.setItem(i, fillerItem));

        // Places the back button in the GUI
        gui.setItem(49, createItem(Material.PAPER, "&f&lBack", MarketUtils.BACK_BUTTON_LORE));

        // Starting index where the first category will go
        int slot = 10;

        // Iterate over the categories in the buy_config.yml file and create GUI items for each of them.
        for (String categoryName : categoriesConfig.getKeys(false)) {

            // Makes sure the categories don't spill over into the bottom row
            if (slot >= inventorySize - 9) {
                MarketUtils.log("[Market] Too many categories have been added to the config!");
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
            if (slot % 9 == 8) {
                slot += 2;
            }
        }
        return gui;
    }

    /**
     * Creates an ItemStack representing a category in the GUI.
     *
     * @param material The material of the item to represent the category.
     * @param title The title of the category item.
     * @param description The description for the category item.
     * @param blockList The list of blocks that belong to the category.
     * @return The constructed ItemStack for the category.
     */
    private ItemStack createCategoryItem(Material material, String title, String description, List<String> blockList) {
        ItemStack categoryItem = createItem(material, title);
        ItemMeta itemMeta = categoryItem.getItemMeta();

        if (itemMeta != null) {
            List<String> lore = new ArrayList<>();
            lore.add(description);
            itemMeta.setLore(lore);
            categoryItem.setItemMeta(itemMeta);
        }

        return categoryItem;
    }
}
