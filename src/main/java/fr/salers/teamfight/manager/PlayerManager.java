package fr.salers.teamfight.manager;

import fr.salers.teamfight.player.TFPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Salers
 * made on fr.salers.teamfight.party
 */
public class PlayerManager {

    private final Map<UUID, TFPlayer> playerMap = new HashMap<>();

    public void add(final Player player) {
        final UUID uuid = player.getUniqueId();
        playerMap.put(uuid, new TFPlayer(uuid));

    }

    public void remove(final Player player) {
        final UUID uuid = player.getUniqueId();
        playerMap.get(uuid).handleEvent(new PlayerQuitEvent(player, ""));
        playerMap.remove(uuid);
    }

    public TFPlayer get(final Player player) {
        return playerMap.get(player.getUniqueId());
    }
}
