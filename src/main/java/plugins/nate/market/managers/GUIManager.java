package plugins.nate.market.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import plugins.nate.market.events.MarketNavigateEvent;
import plugins.nate.market.interfaces.CustomGUI;

import java.util.*;

public class GUIManager {
    private Map<Player, List<CustomGUI>> guiMap = new HashMap<>();
    private static final GUIManager instance = new GUIManager();


    private GUIManager() {}

    public void navigateTo(Player player, CustomGUI gui) {
        Inventory fromInventory = null;
        Inventory newInventory = gui.createGUI();

        List<CustomGUI> guiList = guiMap.computeIfAbsent(player, k -> new ArrayList<>());

        if (!guiList.isEmpty()) {
            CustomGUI currentGUI = guiList.get(guiList.size() - 1);
            fromInventory = currentGUI.createGUI();
        }

        guiList.add(gui);

        MarketNavigateEvent event = new MarketNavigateEvent(player, fromInventory, newInventory);
        Bukkit.getServer().getPluginManager().callEvent(event);

        player.openInventory(newInventory);
    }

    public void navigateBack(Player player) {
        List<CustomGUI> guiList = guiMap.get(player);

        if (guiList == null || guiList.size() <= 1) {
            player.closeInventory();
            guiMap.remove(player);
            return;
        }

        Inventory currentInventory = guiList.remove(guiList.size() - 1).createGUI();

        CustomGUI previousGUI = guiList.get(guiList.size() - 1);
        Inventory previousInventory = previousGUI.createGUI();

        MarketNavigateEvent event = new MarketNavigateEvent(player, currentInventory, previousInventory);
        Bukkit.getServer().getPluginManager().callEvent(event);

        player.openInventory(previousInventory);
    }

    public static GUIManager getInstance() {
        return instance;
    }

    public List<CustomGUI> getHistory(Player player) {
        return guiMap.getOrDefault(player, Collections.emptyList());
    }

    public void clearHistory(Player player) {
        List<CustomGUI> guiList = guiMap.get(player);
        if (guiList != null) {
            guiMap.remove(player);
        }
    }
//
//    private Inventory getCurrentInventory(Player player) {
//        return player.getOpenInventory().getTopInventory();
//    }
//
//    public CustomGUI getCurrentGUI(Player player) {
//        List<CustomGUI> guis = guiMap.get(player);
//        if (guis != null && !guis.isEmpty()) {
//            return guis.get(guis.size() - 1);
//        }
//        return null;
//    }
}
