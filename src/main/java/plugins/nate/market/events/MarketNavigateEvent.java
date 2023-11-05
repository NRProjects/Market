package plugins.nate.market.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class MarketNavigateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final Inventory from;
    private final Inventory to;


    public MarketNavigateEvent(@NotNull Player player, Inventory fromGUI, Inventory toGUI) {
        this.player = player;
        this.from = fromGUI;
        this.to = toGUI;
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory fromGUI() {
        return from;
    }

    public Inventory toGUI() {
        return to;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlers;
    }
}
