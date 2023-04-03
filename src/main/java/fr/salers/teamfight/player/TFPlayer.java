package fr.salers.teamfight.player;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Salers
 * made on fr.salers.teamfight.player
 */

@Getter
public class TFPlayer {

    private final UUID uid;
    private final Player player;

    public TFPlayer(UUID uid) {
        this.uid = uid;
        this.player = Bukkit.getPlayer(uid);
    }


}
