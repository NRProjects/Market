package plugins.nate.market.managers;

import org.bukkit.entity.Player;
import plugins.nate.market.interfaces.CustomGUI;

import java.util.*;

public class GUIManager {
    private Map<Player, List<CustomGUI>> guiMap = new HashMap<>();
    private static final GUIManager instance = new GUIManager();

    private GUIManager() {}


    public void openGUI(Player player, CustomGUI gui) {
        List<CustomGUI> guiList = guiMap.computeIfAbsent(player, l -> new ArrayList<>());
        guiList.add(gui);
        player.openInventory(gui.createGUI());
    }

    public void goBack(Player player) {
        List<CustomGUI> guiList = guiMap.get(player);
        if (guiList != null && !guiList.isEmpty()) {
            guiList.remove(guiList.size() - 1);
            if (!guiList.isEmpty()) {
                player.openInventory(guiList.get(guiList.size() - 1).createGUI());
            } else {
                player.closeInventory();
            }
        }
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
}
