package fr.salers.teamfight.fight;

import com.alessiodp.parties.api.interfaces.Party;
import fr.salers.teamfight.arena.Arena;
import fr.salers.teamfight.manager.ArenaManager;
import fr.salers.teamfight.utilities.CC;
import org.bukkit.Bukkit;

/**
 * @author Salers
 * made on fr.salers.teamfight.fight
 */
public class Fight {

    private final Party partyOne;
    private final Party partyTwo;
    private final Arena arena;

    public Fight(Party partyOne, Party partyTwo) {
        this.partyOne = partyOne;
        this.partyTwo = partyTwo;
        this.arena = ArenaManager.INSTANCE.peekNextArena();
    }

    private void startMatch() {
        arena.setOccupied(true);

        partyOne.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                player -> player.teleport(arena.getFirstLocation())
        );

        partyTwo.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                player -> player.teleport(arena.getSecondLocation())
        );



    }
}
