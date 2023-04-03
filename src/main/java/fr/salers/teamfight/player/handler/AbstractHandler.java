package fr.salers.teamfight.player.handler;

import fr.salers.teamfight.player.TFPlayer;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;


/**
 * @author Salers
 * made on fr.salers.teamfight.player.handler
 */


public abstract class AbstractHandler {

    protected final TFPlayer tfPlayer;

    public AbstractHandler(TFPlayer tfPlayer) {
        this.tfPlayer = tfPlayer;
    }

    public abstract void handle(Event e);
}
