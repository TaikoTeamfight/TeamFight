package fr.salers.teamfight.listener;

import fr.salers.teamfight.TFight;
import fr.salers.teamfight.config.Config;
import fr.salers.teamfight.manager.PartyManager;
import fr.salers.teamfight.manager.PlayerManager;
import fr.salers.teamfight.manager.QueueManager;
import fr.salers.teamfight.scoreboard.Board;
import org.bukkit.entity.Player;
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
        Player player = event.getPlayer();
        TFight.INSTANCE.getPlayerManager().add(player);
        player.teleport(Config.getLobbyLocation());
        if (TFight.INSTANCE.getBoardManager() != null) {
            TFight.INSTANCE.getBoardManager().getPlayerBoards().put(player.getUniqueId(), new Board(player, TFight.INSTANCE.getBoardManager().getAdapter()));
        }
    }

    @EventHandler
    public void onLeave(final PlayerQuitEvent event) {
        Player player = event.getPlayer();
        TFight.INSTANCE.getPlayerManager().remove(player);
        if(QueueManager.INSTANCE.isInQueue(player)) {
            QueueManager.INSTANCE.remove(PartyManager.INSTANCE.getPartyFromPlayer(player));
        }
        if (TFight.INSTANCE.getBoardManager() != null) {
            TFight.INSTANCE.getBoardManager().getPlayerBoards().remove(player.getUniqueId());
        }
    }
}
