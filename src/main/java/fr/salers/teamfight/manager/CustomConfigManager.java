package fr.salers.teamfight.manager;

import fr.salers.teamfight.TFight;
import fr.salers.teamfight.TeamFightPlugin;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * @author Salers
 * made on fr.salers.teamfight.manager
 */

@Getter
public enum CustomConfigManager {

    INSTANCE;

    private File arenaFile;
    private YamlConfiguration arenaConfig;

    public void load() {
        createArenaConfig();
    }


    private void createArenaConfig() {
        final TeamFightPlugin plugin = TFight.INSTANCE.getPlugin();
        arenaFile = new File(plugin.getDataFolder(), "arenas.yml");
        if (!arenaFile.exists()) {
            arenaFile.getParentFile().mkdirs();
            plugin.saveResource("arenas.yml", false);
        }

        this.arenaConfig = new YamlConfiguration();

        try {
            arenaConfig.load(arenaFile);
        } catch (IOException | InvalidConfigurationException ignored) {

        }


    }
}
