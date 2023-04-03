package fr.salers.teamfight.player.handler.impl;

import fr.salers.teamfight.manager.QueueManager;
import fr.salers.teamfight.player.TFPlayer;
import fr.salers.teamfight.player.handler.AbstractHandler;
import fr.salers.teamfight.utilities.CC;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author Salers
 * made on fr.salers.teamfight.player.handler.impl
 */
public class JoinHandler extends AbstractHandler {

    public JoinHandler(TFPlayer tfPlayer) {
        super(tfPlayer);
    }

    @Override
    public void handle(Event e) {
        if (!(e instanceof PlayerJoinEvent)) return;

        final Player player = ((PlayerJoinEvent) e).getPlayer();

        player.playSound(player.getLocation(), Sound.LEVEL_UP, 3F, 0.5F);

        player.sendMessage(CC.translate(
                        "&a&lTaiko &r&7- &eTeamFight v1.0\n" +
                                CC.getLineBreak("&7") +
                                "&a/party &7Créez votre groupe pour en affronter d'autres." +
                                CC.getLineBreak("&7")
                )

        );

        QueueManager.INSTANCE.giveQueueItem(player);
    }
}
