package plugins.nate.market.interfaces;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.stream.Collectors;


public interface CustomGUI {
    Inventory createGUI();

    default ItemStack createItem(Material material, String displayName) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(coloredProperties(displayName));
        item.setItemMeta(meta);
        return item;
    }

    default ItemStack createItem(Material material, String displayName, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return item;
        }

        meta.setDisplayName(coloredProperties(displayName));

        if (lore.length > 0) {
            meta.setLore(Arrays.stream(lore).map(this::coloredProperties).collect(Collectors.toList()));
        }

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
}
