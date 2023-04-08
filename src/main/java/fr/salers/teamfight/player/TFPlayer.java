package fr.salers.teamfight.player;

import fr.salers.teamfight.TFight;
import fr.salers.teamfight.arena.Arena;
import fr.salers.teamfight.config.Config;
import fr.salers.teamfight.fight.Fight;
import fr.salers.teamfight.manager.ArenaManager;
import fr.salers.teamfight.manager.QueueManager;
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

    @Getter @Setter
    private PlayerState playerState;

    private final List<AbstractHandler> handlers;

    @Setter
    private Fight activeFight = null;


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

       Bukkit.getScheduler().runTaskLater(TFight.INSTANCE.getPlugin(),() ->  handleEvent(new PlayerJoinEvent(player, "")), 30L);
    }

    public void handleEvent(final Event event) {
        this.handlers.forEach(abstractHandler -> abstractHandler.handle(event));
    }

    public boolean isFighting() {
        return activeFight != null;
    }

    public void setToLobby() {
        player.getInventory().clear();
        player.setHealth(20.D);
        player.setGameMode(GameMode.SURVIVAL);

        player.sendMessage(CC.translate(
                        "&a&lTaiko &r&7- &eTeamFight v1.0\n" +
                                CC.getLineBreak("&7") +
                                "&a/party &7Créez votre groupe pour en affronter d'autres." +
                                CC.getLineBreak("&7")
                )

        );
        player.teleport(Config.getLobbyLocation());

        if(QueueManager.INSTANCE.isInQueue(player)) {
            QueueManager.INSTANCE.giveLeaveQueueItem(player);
        } else {
            QueueManager.INSTANCE.giveQueueItem(player);
        }
    }

}
