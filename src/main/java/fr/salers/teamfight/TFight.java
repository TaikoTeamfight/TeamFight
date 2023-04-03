package fr.salers.teamfight;

import com.alessiodp.parties.api.Parties;
import com.alessiodp.parties.api.interfaces.PartiesAPI;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

/**
 * @author Salers
 * made on fr.salers.teamfight
 */

@Getter
public enum TFight {
    INSTANCE;

    private PartiesAPI partiesAPI;
    private TeamFightPlugin plugin;


    public void load(final TeamFightPlugin plugin) {
        this.plugin = plugin;
    }

    public void enable() {
        final PluginManager pluginManager = Bukkit.getPluginManager();

        loadPartiesAPI(pluginManager);


    }

    public void disable() {
        this.partiesAPI = null;
        this.plugin = null;

    }

    private void loadPartiesAPI(final PluginManager pluginManager) {
        if (!pluginManager.getPlugin("Parties").isEnabled()) return;
        this.partiesAPI = Parties.getApi();


    }
}
