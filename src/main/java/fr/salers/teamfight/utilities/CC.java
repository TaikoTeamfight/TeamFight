package fr.salers.teamfight.utilities;

import fr.salers.teamfight.config.Config;
import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

/**
 * @author Salers
 * made on fr.salers.teamfight.utilities
 */

@UtilityClass
public class CC {

    public String translate(final String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public String formatPrefixTranslate(final String str) {
        return Config.PREFIX.translate() + translate(str);
    }

    public String getLineBreak(String color) {
        return translate(color + "&m----------&r\n");
    }
}
