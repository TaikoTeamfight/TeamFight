package fr.salers.teamfight.fight.sub;

import com.alessiodp.parties.api.interfaces.Party;
import fr.salers.teamfight.manager.PartyManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SplitParty {

    private Party party;
    private Player leader;

    List<Player> allPlayers;

    List<Player> firstHalf;
    List<Player> secondHalf;

    public SplitParty(Party party) {
        this.party = party;
        this.leader = Bukkit.getPlayer(party.getLeader());
        this.allPlayers = PartyManager.INSTANCE.getPlayersOnlineParty(party);
        int halfSize = this.allPlayers.size() / 2;
        List<Player> firstHalf =this.allPlayers.subList(0, halfSize);
        List<Player> secondHalf = this.allPlayers.subList(halfSize, this.allPlayers.size());
        this.firstHalf = firstHalf;
        this.secondHalf = secondHalf;
    }
}
