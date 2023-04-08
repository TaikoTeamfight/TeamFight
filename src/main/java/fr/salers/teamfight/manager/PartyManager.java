package fr.salers.teamfight.manager;

import com.alessiodp.parties.api.interfaces.Party;
import com.alessiodp.parties.api.interfaces.PartyPlayer;
import fr.salers.teamfight.TFight;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Salers
 * made on fr.salers.teamfight.party
 */
public enum PartyManager {

    INSTANCE;

    public Party getPartyFromPlayer(final Player player) {
        Party party = null;
        PartyPlayer partyPlayer = TFight.INSTANCE.getPartiesAPI().getPartyPlayer(player.getUniqueId());
        if(partyPlayer.isInParty()) {
            party = TFight.INSTANCE.getPartiesAPI().getParty(partyPlayer.getPartyName());
        }

        return party;
    }

    public boolean isInParty(final Player player) {
        return getPartyFromPlayer(player) != null;
    }

    public List<Player> getPlayersOnlineParty(Party party) {
        List<Player> players = new ArrayList<>();
        for(PartyPlayer partyPlayer : party.getOnlineMembers()) {
            players.add(Bukkit.getPlayer(partyPlayer.getPlayerUUID()));
        }
        return players;
    }
}
