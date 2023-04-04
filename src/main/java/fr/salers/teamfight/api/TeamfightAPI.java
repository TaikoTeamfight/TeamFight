package fr.salers.teamfight.api;

import com.alessiodp.parties.api.interfaces.Party;
import com.alessiodp.parties.api.interfaces.PartyPlayer;
import fr.salers.teamfight.fight.Fight;
import fr.salers.teamfight.player.TFPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamfightAPI {

    public static Player getPlayer(TFPlayer tfPlayer) {
        return Bukkit.getPlayer(tfPlayer.getUid());
    }

    public static List<Player> playersPartyOnline(Party party) {
        List<Player> data = new ArrayList<>();
        for(PartyPlayer partyPlayer : party.getOnlineMembers()) {
            data.add(Bukkit.getPlayer(partyPlayer.getPlayerUUID()));
        }
        return data;
    }

    public static List<Player> playersParty(Party party) {
        List<Player> data = new ArrayList<>();
        for(UUID partyPlayer : party.getMembers()) {
            data.add(Bukkit.getPlayer(partyPlayer));
        }
        return data;
    }
}
