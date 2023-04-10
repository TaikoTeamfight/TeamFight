package fr.salers.teamfight.scoreboard.adapter;

import com.alessiodp.parties.api.interfaces.Party;
import fr.salers.teamfight.TFight;
import fr.salers.teamfight.TeamFightPlugin;
import fr.salers.teamfight.fight.Fight;
import fr.salers.teamfight.manager.FightManager;
import fr.salers.teamfight.manager.PartyManager;
import fr.salers.teamfight.manager.PlayerManager;
import fr.salers.teamfight.manager.QueueManager;
import fr.salers.teamfight.player.TFPlayer;
import fr.salers.teamfight.scoreboard.Board;
import fr.salers.teamfight.scoreboard.BoardAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TeamfightBoard implements BoardAdapter {
    private static final String LINE;
    private final TeamFightPlugin plugin;

    public TeamfightBoard() {
        this.plugin = TeamFightPlugin.getINSTANCE();
    }

    @Override
    public List<String> getScoreboard(Player player, Board board) {
        final TFPlayer playerData = TFight.INSTANCE.getPlayerManager().get(player);
        if (playerData == null) {
            this.plugin.getLogger().warning(player.getName() + "'s player data is null");
            return null;
        }
        switch (playerData.getPlayerState()) {
            case SPAWN: {
                return this.getLobbyBoard(player, false);
            }
            case SPECTATING: {
                return this.getSpectatorBoard(player);
            }
            case QUEUE: {
                return this.getLobbyBoard(player, true);
            }
            case FIGHTING: {
                return this.getGameBoard(player);
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public String getTitle(final Player player) {
        return ChatColor.translateAlternateColorCodes('&', "&a&lTEAMFIGHT");
    }


    private List<String> getLobbyBoard(final Player player, final boolean queuing) {
        final List<String> strings = new LinkedList<String>();
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formattedDate = myDateObj.format(myFormatObj);
        strings.add("&7" + formattedDate);
        strings.add("");
        strings.add("&fPlayers: &e" + TeamfightCache.getPlayersOnline());
        strings.add("&fServer: &e" + "Taiko");
        strings.add("");
        strings.add("&fIn Fights: &e" + TeamfightCache.getPlayersInMatch());
        strings.add("&fIn Queues: &e" + TeamfightCache.getPlayersInQueue());
        strings.add("");

        final TFPlayer tfPlayer = new PlayerManager().get(player);
        if (PartyManager.INSTANCE.isInParty(player)) {
            Party party = PartyManager.INSTANCE.getPartyFromPlayer(player);
            strings.add("&fParty: &e" + party.getName());
            List<Player> players = PartyManager.INSTANCE.getPlayersOnlineParty(party);
            int size = players.size();
            strings.add("&fMembers: &e" + size);
        } else {
            strings.add("&fTeam: &eAucune");
        }
        if (queuing) {
            strings.add("");
            strings.add("&eTeamfight Kit");
            strings.add("&8&oDans la queue ...");
        }
        strings.add("");
        strings.add("&7taiko.eu");
        return strings;
    }

    @Override
    public void onScoreboardCreate(final Player player, final Scoreboard scoreboard) {
        Team red = scoreboard.getTeam("red");
        if (red == null) {
            red = scoreboard.registerNewTeam("red");
        }
        Team green = scoreboard.getTeam("green");
        if (green == null) {
            green = scoreboard.registerNewTeam("green");
        }

        red.setPrefix(ChatColor.RED.toString());
        green.setPrefix(ChatColor.GREEN.toString());

        final TFPlayer playerData = new PlayerManager().get(player);

        //Enlever ses teams si il est pas en fight
        /*
        if (playerData.getPlayerState() != PlayerState.FIGHTING) {
            for (final String entry : red.getEntries()) {
                red.removeEntry(entry);
            }
            for (final String entry : green.getEntries()) {
                green.removeEntry(entry);
            }


        }
        */
        //Créer fight manager (récuprer les deux parties, les membres et les teams)
        //Ajouter chaque partie a une team (red ou green)
        //red#addEntry(player)
        // final Fight match = TFight.INSTANCE.getFightManager().getFight(player.getUniqueId()); -> Exemple de méthode

    }

    @Override
    public void preLoop() {

    }

    private List<String> getGameBoard(final Player player) {
        final List<String> strings = new LinkedList<String>();
        Fight fight = TFight.INSTANCE.getPlayerManager().get(player).getActiveFight();
        final String[] data = fight.getScoreboardData(player);
        //Faire le scoreboard en fight
        strings.add("");
        strings.addAll(Arrays.asList(data));
        strings.add("");
        strings.add("&7taiko.eu");

        return strings;
    }

    private List<String> getSpectatorBoard(final Player spectator) {
        final List<String> strings = new LinkedList<String>();
        strings.add("&aSpectator Mode !");
        //Faire méthode pour récuperer les spéctateurs
        return strings;
    }


    @Override
    public long getInterval() {
        return 1L;
    }

    static {
        LINE = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "-------------------";
    }
}
