package fr.salers.teamfight.player;

import fr.salers.teamfight.TFight;
import fr.salers.teamfight.arena.Arena;
import fr.salers.teamfight.manager.ArenaManager;
import fr.salers.teamfight.player.handler.AbstractHandler;
import fr.salers.teamfight.player.handler.impl.BlockHandler;
import fr.salers.teamfight.player.handler.impl.InteractHandler;
import fr.salers.teamfight.player.handler.impl.InventoryHandler;
import fr.salers.teamfight.player.handler.impl.JoinHandler;
import lombok.Getter;
import org.bukkit.Bukkit;
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


    public TFPlayer(UUID uid) {
        this.uid = uid;
        this.player = Bukkit.getPlayer(uid);

        this.handlers = Arrays.asList(
                new InteractHandler(this),
                new JoinHandler(this),
                new InventoryHandler(this),
                new BlockHandler(this)
        );

       Bukkit.getScheduler().runTaskLater(TFight.INSTANCE.getPlugin(),() ->  handleEvent(new PlayerJoinEvent(player, "")), 30L);
    }

    public void handleEvent(final Event event) {
        this.handlers.forEach(abstractHandler -> abstractHandler.handle(event));
    }


}
