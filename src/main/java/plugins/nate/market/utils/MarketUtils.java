package plugins.nate.market.utils;

import plugins.nate.market.Market;

public class MarketUtils {
    public static final String BACK_BUTTON_LORE = "&7Click to go back";
    public static void log(String log) {
        Market.getPlugin().getLogger().info(log);
    }

}
