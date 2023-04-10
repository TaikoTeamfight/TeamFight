package fr.salers.teamfight.player;

import com.alessiodp.parties.api.interfaces.Party;
import fr.salers.teamfight.TFight;
import fr.salers.teamfight.config.Config;
import fr.salers.teamfight.fight.Fight;
import fr.salers.teamfight.kit.Kit;
import fr.salers.teamfight.kit.impl.RushKit;
import fr.salers.teamfight.manager.FightManager;
import fr.salers.teamfight.manager.PartyManager;
import fr.salers.teamfight.manager.QueueManager;
import fr.salers.teamfight.manager.SplitPartyManager;
import fr.salers.teamfight.player.handler.AbstractHandler;
import fr.salers.teamfight.player.handler.impl.*;
import fr.salers.teamfight.player.state.PlayerState;
import fr.salers.teamfight.utilities.CC;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author Salers
 * made on fr.salers.teamfight.player
 */

@Getter
public class TFPlayer {

    private final UUID uid;
    private final Player player;
    private final List<AbstractHandler> handlers;
    @Getter
    @Setter
    private PlayerState playerState;
    @Setter
    private Fight activeFight = null;

    private Kit rushKit;


    public TFPlayer(UUID uid) {
        this.uid = uid;
        this.player = Bukkit.getPlayer(uid);
        this.playerState = PlayerState.SPAWN;

        this.handlers = Arrays.asList(
                new InteractHandler(this),
                new JoinHandler(this),
                new InventoryHandler(this),
                new BlockHandler(this),
                new QuitHandler(this)
        );

        loadKit();
        Bukkit.getScheduler().runTaskLater(TFight.INSTANCE.getPlugin(), () -> handleEvent(new PlayerJoinEvent(player, "")), 5L);
    }

    public void handleEvent(final Event event) {
        this.handlers.forEach(abstractHandler -> abstractHandler.handle(event));
    }

    public boolean isFighting() {
        return activeFight != null;
    }

    private void loadKit() {
        // TODO MAYBE KIT EDITOR SO LOAD FROM DB ???
        this.rushKit = new RushKit();
    }

    public void setToLobby() {
        player.getInventory().clear();
        activeFight = null;
        this.setPlayerState(PlayerState.SPAWN);
        player.setHealth(20.D);
        player.getInventory().setArmorContents(null);
        player.setGameMode(GameMode.SURVIVAL);
        player.getActivePotionEffects().clear();

        player.sendMessage(CC.translate(
                        "&a&lTaiko &r&7- &eTeamFight v1.0\n" +
                                CC.getLineBreak("&7") +
                                "&a/party &7Créez votre groupe pour en affronter d'autres." +
                                CC.getLineBreak("&7")
                )

        );
        player.teleport(Config.getLobbyLocation());


        FightManager.INSTANCE.giveSpecItem(player);
        if (PartyManager.INSTANCE.isInParty(player)) {
            Party party = PartyManager.INSTANCE.getPartyFromPlayer(player);
            if (Bukkit.getPlayer(party.getLeader()) == player) {
                QueueManager.INSTANCE.giveQueueItem(player);
                FightManager.INSTANCE.giveSpecItem(player);
                if (party.getOnlineMembers().size() > 1) {
                    TFight.INSTANCE.getSplitPartyManager().giveSplitPartyItems(player);
                }
            } else {
                QueueManager.INSTANCE.giveQueueItem(player);
                FightManager.INSTANCE.giveSpecItem(player);
            }

        } else {
            QueueManager.INSTANCE.giveQueueItem(player);
            FightManager.INSTANCE.giveSpecItem(player);
        }
    }
}
