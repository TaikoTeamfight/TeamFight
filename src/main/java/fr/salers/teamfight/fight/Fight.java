package fr.salers.teamfight.fight;

import com.alessiodp.parties.api.interfaces.Party;
import com.alessiodp.parties.api.interfaces.PartyPlayer;
import fr.salers.teamfight.TFight;
import fr.salers.teamfight.arena.Arena;
import fr.salers.teamfight.manager.ArenaManager;
import fr.salers.teamfight.manager.FightManager;
import fr.salers.teamfight.player.TFPlayer;
import fr.salers.teamfight.player.state.PlayerState;
import fr.salers.teamfight.utilities.CC;
import fr.salers.teamfight.utilities.nametags.NameTags;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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
    private int partyOneWins, partyTwoWins;

    @Getter
    private final List<TFPlayer> spectators = new ArrayList<>();

    public Fight(Party partyOne, Party partyTwo) {
        this.partyOne = partyOne;
        this.partyTwo = partyTwo;
        this.arena = ArenaManager.INSTANCE.peekNextArena();
    }

    public void startMatch() {
        arena.setOccupied(true);

        partyOne.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                player -> {
                    player.getInventory().clear();
                    player.teleport(arena.getFirstLocation());
                    player.sendMessage(CC.formatPrefixTranslate("§7Votre match contre l'équipe de §e"
                            + Bukkit.getPlayer(partyTwo.getLeader()).getDisplayName() + " §7a commencé!"));
                    final TFPlayer tfPlayer = TFight.INSTANCE.getPlayerManager().get(player);
                    tfPlayer.setActiveFight(this);
                    tfPlayer.getRushKit().giveToPlayer(tfPlayer.getPlayer());
                }
        );
        List<Player> allplayerPartyOne = new ArrayList<>();
        List<Player> allplayerPartyTwo = new ArrayList<>();

        for (PartyPlayer partyPlayer : partyOne.getOnlineMembers()) {
            TFight.INSTANCE.getPlayerManager().get(Bukkit.getPlayer(partyPlayer.getPlayerUUID())).setPlayerState(PlayerState.FIGHTING);
            allplayerPartyOne.add(Bukkit.getPlayer(partyPlayer.getPlayerUUID()));
        }

        for (PartyPlayer partyPlayer : partyTwo.getOnlineMembers()) {
            TFight.INSTANCE.getPlayerManager().get(Bukkit.getPlayer(partyPlayer.getPlayerUUID())).setPlayerState(PlayerState.FIGHTING);
            allplayerPartyTwo.add(Bukkit.getPlayer(partyPlayer.getPlayerUUID()));
        }

        //TODO Nametags couleur pour chaque team

        partyTwo.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                player -> {
                    player.getInventory().clear();
                    player.teleport(arena.getSecondLocation());
                    player.sendMessage(CC.formatPrefixTranslate("§7Votre match contre l'équipe de §e"
                            + Bukkit.getPlayer(partyOne.getLeader()).getDisplayName() + " §7a commencé!"));
                    final TFPlayer tfPlayer = TFight.INSTANCE.getPlayerManager().get(player);
                    tfPlayer.setActiveFight(this);
                    tfPlayer.getRushKit().giveToPlayer(tfPlayer.getPlayer());
                }
        );

    }

    public void endMatch() {

        Bukkit.getScheduler().runTaskLaterAsynchronously(TFight.INSTANCE.getPlugin(),
                () -> ArenaManager.INSTANCE.getArenas().stream().filter(arena1 -> arena1.equals(arena)).findAny().get().setOccupied(false)
                , 20L * 12L);

        if (partyTwoWins == 3) {

            partyTwo.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                    player -> {
                        player.sendMessage(CC.formatPrefixTranslate("§a§lVICTOIRE !§7 l'équipe de §e" + Bukkit.getPlayer(partyTwo.getLeader()).getDisplayName() + " §7a gagné!"));
                    }
            );

            partyOne.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                    player -> {
                        player.sendMessage(CC.formatPrefixTranslate("§c§lPERDU !§7 l'équipe de §e" + Bukkit.getPlayer(partyTwo.getLeader()).getDisplayName() + " §7a gagné!"));
                    }
            );
        } else if (partyOneWins == 3) {
            partyOne.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                    player -> {
                        player.sendMessage(CC.formatPrefixTranslate("§a§lVICTOIRE !§7 l'équipe de §e" + Bukkit.getPlayer(partyTwo.getLeader()).getDisplayName() + " §7a gagné!"));
                    }
            );

            partyTwo.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                    player -> {
                        player.sendMessage(CC.formatPrefixTranslate("§c§lPERDU !§7 l'équipe de §e" + Bukkit.getPlayer(partyTwo.getLeader()).getDisplayName() + " §7a gagné!"));
                    }
            );
        }

        partyOne.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                player -> {
                    final TFPlayer tfPlayer = TFight.INSTANCE.getPlayerManager().get(player);
                    tfPlayer.setActiveFight(null);
                    tfPlayer.setToLobby();
                }
        );

        partyTwo.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                player -> {
                    final TFPlayer tfPlayer = TFight.INSTANCE.getPlayerManager().get(player);
                    tfPlayer.setActiveFight(null);
                    tfPlayer.setToLobby();
                }
        );



        spectators.forEach(spectator -> {
            spectator.setPlayerState(PlayerState.SPAWN);
            spectator.setToLobby();
        });

        for (PartyPlayer partyPlayer : partyOne.getOnlineMembers()) {
            TFight.INSTANCE.getPlayerManager().get(Bukkit.getPlayer(partyPlayer.getPlayerUUID())).setPlayerState(PlayerState.SPAWN);
        }

        for (PartyPlayer partyPlayer : partyTwo.getOnlineMembers()) {
            TFight.INSTANCE.getPlayerManager().get(Bukkit.getPlayer(partyPlayer.getPlayerUUID())).setPlayerState(PlayerState.SPAWN);
        }

        FightManager.INSTANCE.deleteFight(this);

    }

    public void handleWin(final boolean partyOneWon) {
        if (partyOneWon) {

            if (partyTwo.getOnlineMembers().isEmpty()) {
                partyOneWins = 3;
                endMatch();
                return;
            }

            partyOne.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                    player -> {
                        player.sendMessage(CC.formatPrefixTranslate("§a§lVICTOIRE !§7 l'équipe de §e" +
                                Bukkit.getPlayer(partyOne.getLeader()).getDisplayName() + " §7a gagné ce round!"));
                    }
            );

            partyTwo.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                    player -> {
                        player.sendMessage(CC.formatPrefixTranslate("§c§lPERDU !§7 l'équipe de §e" +
                                Bukkit.getPlayer(partyOne.getLeader()).getDisplayName() + " §7a gagné ce round!"));
                    }
            );

        } else {

            if (partyOne.getOnlineMembers().isEmpty()) {
                partyTwoWins = 3;
                endMatch();
                return;
            }

            partyTwo.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                    player -> {
                        player.sendMessage(CC.formatPrefixTranslate("§a§lVICTOIRE !§7 l'équipe de §e" +
                                Bukkit.getPlayer(partyTwo.getLeader()).getDisplayName() + " §7a gagné ce round!"));
                    }
            );

            partyOne.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                    player -> {
                        player.sendMessage(CC.formatPrefixTranslate("§c§lPERDU !§7 l'équipe de §e" +
                                Bukkit.getPlayer(partyTwo.getLeader()).getDisplayName() + " §7a gagné ce round!"));
                    }
            );


        }

        if (partyOneWon)
            partyOneWins += 1;
        else partyTwoWins += 1;

        endRound();


    }

    private boolean handleFinalWin() {
        final boolean win = partyOneWins == 3 || partyTwoWins == 3;

        if (win) endMatch();

        return win;
    }


    private void endRound() {

        if (handleFinalWin()) return;

        partyOne.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                player -> {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.teleport(arena.getFirstLocation());
                    player.sendMessage(CC.formatPrefixTranslate("§7Le round§e " + getRounds() + " §7a commencé!"));
                    final TFPlayer tfPlayer = TFight.INSTANCE.getPlayerManager().get(player);
                    player.getInventory().clear();
                    player.setHealth(player.getMaxHealth());
                    player.getActivePotionEffects().clear();
                    tfPlayer.getRushKit().giveToPlayer(player);
                }
        );

        partyTwo.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                player -> {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.teleport(arena.getSecondLocation());
                    player.sendMessage(CC.formatPrefixTranslate("§7Le round§e " + getRounds() + " §7a commencé!"));
                    final TFPlayer tfPlayer = TFight.INSTANCE.getPlayerManager().get(player);
                    player.getInventory().clear();
                    player.setHealth(player.getMaxHealth());
                    player.getActivePotionEffects().clear();
                    tfPlayer.getRushKit().giveToPlayer(player);
                }
        );


    }

    public void handleKill(final Player player) {

        player.setGameMode(GameMode.SPECTATOR);
        player.getInventory().clear();
        player.setHealth(player.getMaxHealth());

        partyOne.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                players -> {
                    players.sendMessage(CC.formatPrefixTranslate("§e" + player.getDisplayName() + "§7 a été tué."));
                }
        );

        partyTwo.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                players -> {
                    players.sendMessage(CC.formatPrefixTranslate("§e" + player.getDisplayName() + "§7 a été tué."));
                }
        );


        if (partyOne.getOnlineMembers().stream().noneMatch(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID()).getGameMode() == GameMode.SURVIVAL)) {
            handleWin(false);
        } else if (partyTwo.getOnlineMembers().stream().noneMatch(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID()).getGameMode() == GameMode.SURVIVAL)) {
            handleWin(true);
        }

    }

    public String[] getScoreboardData(final Player player) {

        final boolean inPartyOne = partyOne.getOnlineMembers().stream().anyMatch(partyPlayer ->
                Bukkit.getPlayer(partyPlayer.getPlayerUUID()).getDisplayName().equalsIgnoreCase(player.getDisplayName()));

        final int allyRounds = !inPartyOne ? partyTwoWins : partyOneWins;
        final int ennemyRounds = inPartyOne ? partyTwoWins : partyOneWins;

        final long allies = inPartyOne ?
                partyOne.getOnlineMembers().stream().map(partyPlayer ->
                        Bukkit.getPlayer(partyPlayer.getPlayerUUID())).filter(player1 -> player1.getGameMode() == GameMode.SURVIVAL).count() :
                partyTwo.getOnlineMembers().stream().map(partyPlayer ->
                        Bukkit.getPlayer(partyPlayer.getPlayerUUID())).filter(player1 -> player1.getGameMode() == GameMode.SURVIVAL).count();

        final long ennemies = !inPartyOne ?
                partyTwo.getOnlineMembers().stream().map(partyPlayer ->
                        Bukkit.getPlayer(partyPlayer.getPlayerUUID())).filter(player1 -> player1.getGameMode() == GameMode.SURVIVAL).count() :
                partyOne.getOnlineMembers().stream().map(partyPlayer ->
                        Bukkit.getPlayer(partyPlayer.getPlayerUUID())).filter(player1 -> player1.getGameMode() == GameMode.SURVIVAL).count();


        //Space
        final String allyTeam = "§a▶§fTeam: §e" + (inPartyOne ? partyTwo.getName() : partyOne.getName());
        final String alliesAlive = "   §l§e➦ §r§eMembers: §f(" + allies + "/5)";
        String space = "";
        String alliesRound = "§1➤ §7⬤ ⬤ ⬤";
        if(allyRounds == 1) {
            alliesRound = "§1➤ §1⬤ §7⬤ ⬤";
        }

        if(allyRounds == 2) {
            alliesRound = "§1➤ §1⬤ ⬤ §7⬤";
        }

        if(allyRounds == 3) {
            alliesRound = "§1➤ §1⬤ ⬤ ⬤";
        }
        //Space
        final String ennemyTeam = "§a▶§fOpponent: §e" + (inPartyOne ? partyTwo.getName() : partyOne.getName());
        final String ennemiesAlive = "   §l§e➦ §r§eMembers: §f(" + ennemies + "/5)";
        //Space

        String ennemiesRound = "§c➤ §7⬤ ⬤ ⬤";

        if(ennemyRounds == 1) {
            ennemiesRound = "§c➤ §c⬤ §7⬤ ⬤";
        }

        if(ennemyRounds == 2) {
            ennemiesRound = "§c➤ §c⬤ ⬤ §7⬤";
        }

        if(ennemyRounds == 3) {
            ennemiesRound = "§c➤ §c⬤ ⬤ ⬤";
        }

        return new String[]{allyTeam, alliesAlive, alliesRound, space, ennemyTeam, ennemiesAlive, ennemiesRound};



    }

    public int getRounds() {
        return partyOneWins + partyTwoWins + 1;
    }

}