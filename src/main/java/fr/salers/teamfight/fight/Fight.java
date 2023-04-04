package fr.salers.teamfight.fight;

import com.alessiodp.parties.api.interfaces.Party;
import fr.salers.teamfight.arena.Arena;
import fr.salers.teamfight.config.Config;
import fr.salers.teamfight.manager.ArenaManager;
import fr.salers.teamfight.utilities.CC;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

/**
 * @author Salers
 * made on fr.salers.teamfight.fight
 */

@Getter
@Setter
public class Fight {

    private final Party partyOne;
    private final Party partyTwo;
    private final Arena arena;

    public Fight(Party partyOne, Party partyTwo) {
        this.partyOne = partyOne;
        this.partyTwo = partyTwo;
        this.arena = ArenaManager.INSTANCE.peekNextArena();
    }

    public void startMatch() {
        arena.setOccupied(true);

        partyOne.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                player -> {
                    player.teleport(arena.getFirstLocation());
                    player.sendMessage(CC.formatPrefixTranslate("&7Votre match contre l'équipe de &e"
                            + Bukkit.getPlayer(partyTwo.getLeader()).getDisplayName() + " &7a commencé!"));
                }
        );

        partyTwo.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                player -> {
                    player.teleport(arena.getSecondLocation());
                    player.sendMessage(CC.formatPrefixTranslate("&7Votre match contre l'équipe de &e"
                            + Bukkit.getPlayer(partyOne.getLeader()).getDisplayName() + " &7a commencé!"));
                }
        );

    }

    public void endMatch() {
        ArenaManager.INSTANCE.getArenas().stream().filter(arena1 -> arena1.equals(arena)).findAny().get().setOccupied(false);

        partyOne.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                player -> player.teleport(Config.getLobbyLocation())
        );

        partyTwo.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                player -> player.teleport(Config.getLobbyLocation())
        );
    }
}
