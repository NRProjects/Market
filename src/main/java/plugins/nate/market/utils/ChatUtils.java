package plugins.nate.market.utils;

import net.md_5.bungee.api.ChatColor;

public class ChatUtils {
    public static final String PREFIX = "&8[&a&lMARKET&8] &r";
    public static final String DEV_PREFIX = "&8[&3&lDEV&8] &r";
    public static final String DENIED_COMMAND = "&cYou do not have access to this command";
    public static String coloredChat(String message) { return ChatColor.translateAlternateColorCodes('&', message); }
}
