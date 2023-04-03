package fr.salers.teamfight.party;

import com.alessiodp.parties.api.interfaces.Party;
import com.alessiodp.parties.api.interfaces.PartyPlayer;
import fr.salers.teamfight.TFight;
import org.bukkit.entity.Player;

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
}
