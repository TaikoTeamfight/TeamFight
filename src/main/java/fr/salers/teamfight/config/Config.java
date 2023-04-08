package fr.salers.teamfight.config;

import fr.salers.teamfight.TFight;
import fr.salers.teamfight.utilities.CC;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public enum Config {
    PREFIX("teamfight.prefix", "&a&lTAIKO &r&7> "),;

    private final String path;
    private final Object value;

    Config(String path, Object value) {
        this.path = path;

        if (TFight.INSTANCE.getPlugin().getConfig().contains(path)) {
            this.value = TFight.INSTANCE.getPlugin().getConfig().get(path);
        } else {
            TFight.INSTANCE.getPlugin().getConfig().set(path, value);
            TFight.INSTANCE.getPlugin().saveConfig();

            this.value = value;
        }
    }

    public String translate() {
        return CC.translate((String) value);
    }

    public static Location getLobbyLocation() {
        final FileConfiguration config = TFight.INSTANCE.getPlugin().getConfig();

        return new Location(
                Bukkit.getWorld(config.getString("teamfight.lobby.world")),
                config.getDouble("teamfight.lobby.x"),
                config.getDouble("teamfight.lobby.y"),
                config.getDouble("teamfight.lobby.z"),
                (float) config.getDouble("teamfight.lobby.yaw"),
                (float) config.getDouble("teamfight.lobby.pitch")

        );

    }
}
