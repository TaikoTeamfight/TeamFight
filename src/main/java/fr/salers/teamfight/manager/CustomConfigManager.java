package fr.salers.teamfight.manager;

import fr.salers.teamfight.TFight;
import fr.salers.teamfight.TeamFightPlugin;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

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
        arenaFile = new File(plugin.getDataFolder(), "custom.yml");
        if (!arenaFile.exists()) {
            arenaFile.getParentFile().mkdirs();
            plugin.saveResource("arenas.yml", false);
        }

        arenaConfig = new YamlConfiguration();
        YamlConfiguration.loadConfiguration(arenaFile);

    }
}
