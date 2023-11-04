package plugins.nate.market.listeners;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import plugins.nate.market.Market;
import plugins.nate.market.gui.BuyGUI;
import plugins.nate.market.gui.SellGUI;
import plugins.nate.market.managers.GUIManager;
import plugins.nate.market.utils.MarketUtils;

import java.util.ArrayList;
import java.util.List;

import static plugins.nate.market.utils.ChatUtils.coloredChat;

public class MarketListener implements Listener {
    private final List<String> restrictedTitles = new ArrayList<>();
    GUIManager guiManager = GUIManager.getInstance();



    public MarketListener() {
        restrictedTitles.add("Market");
        restrictedTitles.add("Buy");
        restrictedTitles.add("Sell");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) {
            return;
        }

        String titleComponent = LegacyComponentSerializer.legacySection().serialize(event.getView().title());

        if (restrictedTitles.contains(titleComponent)) {
            event.setCancelled(true);
        }

        Player player = (Player) event.getWhoClicked();
        Inventory gui = event.getClickedInventory();

        if (gui == null) {
            return;
        }

        if (event.getClickedInventory().getType() == InventoryType.CHEST) {
            ItemStack clickedItem = event.getCurrentItem();
            ItemMeta meta = clickedItem.getItemMeta();

            List<String> lore = meta.getLore();

            if (lore != null && lore.contains(coloredChat(MarketUtils.BACK_BUTTON_LORE))) {
                guiManager.goBack(player);
                return;
            }
        }

        switch (titleComponent) {
            case "Market" -> {
                switch (event.getCurrentItem().getType()) {
                    case EMERALD_BLOCK -> guiManager.openGUI(player, new BuyGUI(Market.getPlugin().getConfigUtil().getBuyConfig().getConfigurationSection("categories")));

                    case REDSTONE_BLOCK -> guiManager.openGUI(player, new SellGUI(Market.getPlugin().getConfigUtil().getSellConfig().getConfigurationSection("categories")));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();


        if (guiManager.getHistory(event.getPlayer()).size() > 1) {
            guiManager.clearHistory(player);
        }
    }
}
