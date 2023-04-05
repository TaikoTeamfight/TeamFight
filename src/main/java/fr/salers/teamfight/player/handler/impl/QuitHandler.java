package fr.salers.teamfight.player.handler.impl;

import fr.salers.teamfight.player.TFPlayer;
import fr.salers.teamfight.player.handler.AbstractHandler;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Salers
 * made on fr.salers.teamfight.player.handler.impl
 */
public class QuitHandler extends AbstractHandler {

    public QuitHandler(TFPlayer tfPlayer) {
        super(tfPlayer);
    }

    @Override
    public void handle(Event e) {
        if(!(e instanceof PlayerQuitEvent)) return;

        if(tfPlayer.isFighting()) {
            tfPlayer.getActiveFight().handleKill(tfPlayer.getPlayer());
        }
    }
}
