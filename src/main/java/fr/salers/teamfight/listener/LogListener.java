package fr.salers.teamfight.listener;

import fr.salers.teamfight.TFight;
import fr.salers.teamfight.manager.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Salers
 * made on fr.salers.teamfight.party
 */
public class LogListener implements Listener {

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        TFight.INSTANCE.getPlayerManager().add(event.getPlayer());
    }

    @EventHandler
    public void onLeave(final PlayerQuitEvent event) {
        TFight.INSTANCE.getPlayerManager().remove(event.getPlayer());
    }
}
