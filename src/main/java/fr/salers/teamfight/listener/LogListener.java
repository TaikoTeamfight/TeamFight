package fr.salers.teamfight.listener;

import com.alessiodp.parties.api.interfaces.Party;
import fr.salers.teamfight.TFight;
import fr.salers.teamfight.config.Config;
import fr.salers.teamfight.manager.FightManager;
import fr.salers.teamfight.manager.PartyManager;
import fr.salers.teamfight.manager.PlayerManager;
import fr.salers.teamfight.manager.QueueManager;
import fr.salers.teamfight.scoreboard.Board;
import org.bukkit.Bukkit;
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
        if(PartyManager.INSTANCE.isInParty(player)) {
            Party party = PartyManager.INSTANCE.getPartyFromPlayer(player);
            int size = party.getOnlineMembers().size();
            if(size > 1) {
                TFight.INSTANCE.getSplitPartyManager().add(party);
                player.sendMessage("Vous pouvez Split Party");
                Player leader = Bukkit.getPlayer(party.getLeader());
                TFight.INSTANCE.getSplitPartyManager().giveSplitPartyItems(leader);
            }
        }
        FightManager.INSTANCE.giveSpecItem(player);
        if (TFight.INSTANCE.getBoardManager() != null) {
            TFight.INSTANCE.getBoardManager().getPlayerBoards().put(player.getUniqueId(), new Board(player, TFight.INSTANCE.getBoardManager().getAdapter()));
        }
    }

    @EventHandler
    public void onLeave(final PlayerQuitEvent event) {
        Player player = event.getPlayer();
        TFight.INSTANCE.getPlayerManager().remove(player);

        if(PartyManager.INSTANCE.isInParty(player)) {
            Party party = PartyManager.INSTANCE.getPartyFromPlayer(player);
            int size = party.getOnlineMembers().size();
            if(size < 2) {
                TFight.INSTANCE.getSplitPartyManager().remove(party);
                Player leader = Bukkit.getPlayer(party.getLeader());
                if(QueueManager.INSTANCE.isInQueue(leader)) {
                    QueueManager.INSTANCE.remove(party);
                    leader.getInventory().clear();
                    QueueManager.INSTANCE.giveQueueItem(leader);
                    FightManager.INSTANCE.giveSpecItem(leader);
                }
                leader.sendMessage("Vous pouvez plus Split Party");
                leader.getInventory().clear();
                QueueManager.INSTANCE.giveQueueItem(leader);
                FightManager.INSTANCE.giveSpecItem(leader);
            }
        }

        if(QueueManager.INSTANCE.isInQueue(player)) {
            QueueManager.INSTANCE.remove(PartyManager.INSTANCE.getPartyFromPlayer(player));
        }
        if (TFight.INSTANCE.getBoardManager() != null) {
            TFight.INSTANCE.getBoardManager().getPlayerBoards().remove(player.getUniqueId());
        }
    }
}
