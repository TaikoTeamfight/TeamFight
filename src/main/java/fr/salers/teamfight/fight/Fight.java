package fr.salers.teamfight.fight;

import com.alessiodp.parties.api.interfaces.Party;
import fr.salers.teamfight.TFight;
import fr.salers.teamfight.arena.Arena;
import fr.salers.teamfight.config.Config;
import fr.salers.teamfight.manager.ArenaManager;
import fr.salers.teamfight.utilities.CC;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

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
                    TFight.INSTANCE.getPlayerManager().get(player).setActiveFight(this);
                    player.getInventory().clear();
                }
        );

        partyTwo.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                player -> {
                    player.teleport(arena.getSecondLocation());
                    player.sendMessage(CC.formatPrefixTranslate("&7Votre match contre l'équipe de &e"
                            + Bukkit.getPlayer(partyOne.getLeader()).getDisplayName() + " &7a commencé!"));
                    TFight.INSTANCE.getPlayerManager().get(player).setActiveFight(this);
                    player.getInventory().clear();
                }
        );

    }

    public void endMatch() {
        ArenaManager.INSTANCE.getArenas().stream().filter(arena1 -> arena1.equals(arena)).findAny().get().setOccupied(false);

        partyOne.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                player -> {
                    TFight.INSTANCE.getPlayerManager().get(player).setActiveFight(null);
                    TFight.INSTANCE.getPlayerManager().get(player).setToLobby();
                }
        );

        partyTwo.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                player -> {
                    TFight.INSTANCE.getPlayerManager().get(player).setActiveFight(null);
                    TFight.INSTANCE.getPlayerManager().get(player).setToLobby();
                }
        );
    }

    public void handleWin(final boolean partyOneWon) {
        if(partyOneWon) {
            partyOne.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                    player -> {
                        player.sendMessage(CC.formatPrefixTranslate("&a&lVICTOIRE !&7 l'équipe de &e" + Bukkit.getPlayer(partyTwo.getLeader()).getDisplayName() + " &7a gagné!"));
                    }
            );

            partyTwo.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                    player -> {
                        player.sendMessage(CC.formatPrefixTranslate("&c&lPERDU !&7 l'équipe de &e" + Bukkit.getPlayer(partyTwo.getLeader()).getDisplayName() + " &7a gagné!"));
                    }
            );
        } else {
            partyTwo.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                    player -> {
                        player.sendMessage(CC.formatPrefixTranslate("&a&lVICTOIRE !&7 l'équipe de &e" + Bukkit.getPlayer(partyTwo.getLeader()).getDisplayName() + " &7a gagné!"));
                    }
            );

            partyOne.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                    player -> {
                        player.sendMessage(CC.formatPrefixTranslate("&c&lPERDU !&7 l'équipe de &e" + Bukkit.getPlayer(partyTwo.getLeader()).getDisplayName() + " &7a gagné!"));
                    }
            );
        }
        endMatch();

    }

    public void handleKill(final Player player) {

        player.setGameMode(GameMode.SPECTATOR);
        player.getInventory().clear();
        player.setHealth(player.getMaxHealth());

        partyOne.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                players -> {
                    players.sendMessage(CC.formatPrefixTranslate("&e" + player.getDisplayName() + "&7 a été tué."));
                }
        );

        partyTwo.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                players -> {
                    players.sendMessage(CC.formatPrefixTranslate("&e" + player.getDisplayName() + "&7 a été tué."));
                }
        );

        if (partyOne.getOnlineMembers().stream().noneMatch(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID()).getGameMode() == GameMode.SURVIVAL)) {
            handleWin(false);
        } else if (partyTwo.getOnlineMembers().stream().noneMatch(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID()).getGameMode() == GameMode.SURVIVAL)) {
            handleWin(true);
        }

    }

}