package fr.salers.teamfight;

import com.alessiodp.parties.api.Parties;
import com.alessiodp.parties.api.interfaces.PartiesAPI;
import com.samjakob.spigui.SpiGUI;
import fr.salers.teamfight.command.TFCommand;
import fr.salers.teamfight.listener.LogListener;
import fr.salers.teamfight.listener.PlayerListener;
import fr.salers.teamfight.listener.WorldListener;
import fr.salers.teamfight.manager.ArenaManager;
import fr.salers.teamfight.manager.CustomConfigManager;
import fr.salers.teamfight.manager.PlayerManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;

/**
 * @author Salers
 * made on fr.salers.teamfight
 */

@Getter
public enum TFight {
    INSTANCE;

    private PartiesAPI partiesAPI;
    private TeamFightPlugin plugin;
    private SpiGUI spiGUI;

    private final PlayerManager playerManager = new PlayerManager();


    public void load(final TeamFightPlugin plugin) {
        this.plugin = plugin;
    }

    public void enable() {

        final PluginManager pluginManager = Bukkit.getPluginManager();

        plugin.saveDefaultConfig();
        CustomConfigManager.INSTANCE.load();

        this.spiGUI = new SpiGUI(plugin);

        pluginManager.registerEvents(new LogListener(), this.plugin);
        pluginManager.registerEvents(new PlayerListener(), this.plugin);
        pluginManager.registerEvents(new WorldListener(), this.plugin);
        loadPartiesAPI(pluginManager);

        ArenaManager.INSTANCE.loadFromConfig();

        loadCommands();

    }

    public void disable() {
        this.partiesAPI = null;
        this.plugin = null;

    }

    private void loadPartiesAPI(final PluginManager pluginManager) {
        if (!pluginManager.getPlugin("Parties").isEnabled()) return;
        this.partiesAPI = Parties.getApi();

    }

    private void loadCommands() {
        plugin.getCommand("teamfight").setExecutor(new TFCommand());
        plugin.getCommand("teamfight").setAliases(Arrays.asList("tfight", "tf"));


    }
}
