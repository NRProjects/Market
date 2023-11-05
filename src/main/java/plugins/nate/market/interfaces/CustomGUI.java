package plugins.nate.market.interfaces;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public interface CustomGUI {
    Inventory createGUI();

    /**
     * Creates an ItemStack with the specified material, display name, and lore.
     * This method applies custom formatting to the display name and lore,
     * and only sets them if they are not null and not empty.
     *
     * @param material    The Material of the ItemStack to be created.
     * @param displayName The display name for the ItemStack; if it's null or empty, no display name is set.
     * @param lore        Varargs of String representing the lore for the ItemStack; if null or contains only empty strings, no lore is set.
     * @return The created ItemStack with the given properties, if any.
     */
    default ItemStack createItem(Material material, @Nullable String displayName, @Nullable String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return item;
        }

        Optional.ofNullable(displayName)
                .filter(name -> !name.isEmpty())
                .ifPresent(name -> meta.setDisplayName(coloredProperties("&r" + name)));

        Optional.ofNullable(lore)
                .map(Arrays::stream)
                .map(stream -> stream.filter(line -> line != null && !line.isEmpty()))
                .map(stream -> stream.map(this::coloredProperties).collect(Collectors.toList()))
                .ifPresent(meta::setLore);

        item.setItemMeta(meta);

        return item;
    }

    /**
     * Supports color codes in the config properties
     *
     * @param message The message with color.
     * @return The formatted message with translated color codes.
     */
    default String coloredProperties(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
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
    default ItemStack createCategoryItem(Material material, String title, String description, List<String> blockList) {
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
