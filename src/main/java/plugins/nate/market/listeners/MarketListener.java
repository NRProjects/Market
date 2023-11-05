package plugins.nate.market.listeners;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import plugins.nate.market.Market;
import plugins.nate.market.events.MarketNavigateEvent;
import plugins.nate.market.gui.BlockCatalogGUI;
import plugins.nate.market.gui.BuyGUI;
import plugins.nate.market.gui.SellGUI;
import plugins.nate.market.managers.GUIManager;
import plugins.nate.market.utils.ChatUtils;
import plugins.nate.market.utils.ConfigUtil;
import plugins.nate.market.utils.MarketUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MarketListener implements Listener {
    private final List<String> restrictedTitles = new ArrayList<>();
    GUIManager guiManager = GUIManager.getInstance();


    public MarketListener() {
        restrictedTitles.add("Market");
        restrictedTitles.add("Buy");
        restrictedTitles.add("Sell");
        restrictedTitles.add("Select Block");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        ItemStack clickedItem = event.getCurrentItem();
        ItemMeta meta = clickedItem.getItemMeta();
        Material clickedMaterial = event.getCurrentItem().getType();
        List<String> lore = meta.getLore();
        String titleComponent = LegacyComponentSerializer.legacySection().serialize(event.getView().title());

        if (restrictedTitles.contains(titleComponent)) {
            event.setCancelled(true);
            // Now we check if it's a back button and handle accordingly
            if (meta.hasLore() && lore.contains(ChatUtils.coloredChat(MarketUtils.BACK_BUTTON_LORE))) {
                guiManager.navigateBack(player);
                return;
            }
        }

        switch (titleComponent) {
            case "Market" -> {
                switch (clickedMaterial) {
                    case EMERALD_BLOCK -> {
                        ConfigurationSection buyConfig = Market.getPlugin().getConfigUtil().getConfig("buy_config.yml", "categories");

                        if (buyConfig == null) {
                            MarketUtils.severe("'categories' section is missing in buy_config.yml");
                            return;
                        }
                        guiManager.navigateTo(player, new BuyGUI(buyConfig));
                    }

                    case REDSTONE_BLOCK -> {
                        ConfigurationSection sellConfig = Market.getPlugin().getConfigUtil().getConfig("sell_config.yml", "categories");

                        if (sellConfig == null) {
                            MarketUtils.severe("'categories' section is missing in sell_config.yml");
                            return;
                        }

                        guiManager.navigateTo(player, new SellGUI(sellConfig));
                    }
                }
            }

            case "Buy", "Sell" -> {
//                if (clickedItem.getType() == Material.PAPER && lore != null && lore.contains(MarketUtils.BACK_BUTTON_LORE)) {
//                    guiManager.navigateBack(player);
//                    return;
//                }

                if (clickedItem.hasItemMeta()) {
                    if (meta.hasDisplayName()) {
                        String categoryName = ChatColor.stripColor(meta.getDisplayName());
                        List<String> blocks = getCategoryBlocks(categoryName);

                        if (blocks != null) {
                            guiManager.navigateTo(player, new BlockCatalogGUI(blocks));
                            return;
                        }
                    }
                }
            }

        }
    }




    // TODO: Replace this with a timer that clears history every 5-10 minutes
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (guiManager.getHistory(event.getPlayer()).size() > 1) {
            guiManager.clearHistory(player);
        }
    }

    public List<String> getCategoryBlocks(String displayName) {
        ConfigurationSection categories = Market.getPlugin().getConfigUtil().getBuyConfig().getConfigurationSection("categories");

        if (categories == null) {
            MarketUtils.severe("The 'categories' section is missing in the config.");
            return Collections.emptyList();
        }

        for (String key : categories.getKeys(false)) {
            String title = ChatColor.stripColor(categories.getConfigurationSection(key).getString("title"));
            if (title.equals(displayName)) {
                return categories.getConfigurationSection(key).getStringList("blocks");
            }
        }

        MarketUtils.warn("The category with title '" + displayName + "' does not exist in the config.");
        return Collections.emptyList(); // Return an empty list if the category is not found.
    }

    @EventHandler
    public void onMarketNavigation(MarketNavigateEvent event) {
        MarketUtils.severe(event.getPlayer().getDisplayName());
    }
}
