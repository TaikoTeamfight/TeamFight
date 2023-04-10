package fr.salers.teamfight.listener.parties;

import com.alessiodp.parties.api.events.bukkit.party.BukkitPartiesPartyPreDeleteEvent;
import com.alessiodp.parties.api.events.bukkit.player.BukkitPartiesPlayerPostLeaveEvent;
import com.alessiodp.parties.api.events.bukkit.player.BukkitPartiesPlayerPreJoinEvent;
import com.alessiodp.parties.api.events.bukkit.player.BukkitPartiesPlayerPreLeaveEvent;
import com.alessiodp.parties.api.interfaces.Party;
import com.alessiodp.parties.api.interfaces.PartyPlayer;
import fr.salers.teamfight.TFight;
import fr.salers.teamfight.TeamFightPlugin;
import fr.salers.teamfight.fight.sub.SplitParty;
import fr.salers.teamfight.manager.ArenaManager;
import fr.salers.teamfight.manager.FightManager;
import fr.salers.teamfight.manager.QueueManager;
import fr.salers.teamfight.player.TFPlayer;
import fr.salers.teamfight.player.state.PlayerState;
import fr.salers.teamfight.utilities.nametags.NameTags;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PartieListener implements Listener {

    @EventHandler
    public void onPlayerPartyJoin(BukkitPartiesPlayerPreJoinEvent event) {
        PartyPlayer partyPlayer = event.getPartyPlayer();
        Party party = event.getParty();
        Player player = Bukkit.getPlayer(partyPlayer.getPlayerUUID());
        if(TFight.INSTANCE.getPlayerManager().get(player).getPlayerState() == PlayerState.FIGHTING) {
            return;
        }
        TFPlayer tfPlayer = TFight.INSTANCE.getPlayerManager().get(player);
        List<Player> partiesPlayer = new ArrayList<>();
        List<TFPlayer> tfPlayerList = new ArrayList<>();

        //Récupere tous les PartyPlayer de la party
        for(PartyPlayer partyPlayer1 : party.getOnlineMembers()) {
            partiesPlayer.add(Bukkit.getPlayer(partyPlayer1.getPlayerUUID()));
            tfPlayerList.add(TFight.INSTANCE.getPlayerManager().get(Bukkit.getPlayer(partyPlayer.getPlayerUUID())));
        }
        //Récupere tous les TFPlayer de la party
        //Si pas au spawn, alors return
        for(TFPlayer tfPlayer1 : tfPlayerList) {
            if(tfPlayer1.getPlayerState() != PlayerState.SPAWN) {
                return;
            }
        }


        Player leader = Bukkit.getPlayer(party.getLeader());

        NameTags.color(player, leader, ChatColor.DARK_PURPLE, false);
        NameTags.color(leader, player, ChatColor.DARK_PURPLE, false);

        leader.sendMessage("Vous pouvez split party maintenant");
        SplitParty splitParty = new SplitParty(party);
        TFight.INSTANCE.getSplitPartyManager().add(party);
        TFight.INSTANCE.getSplitPartyManager().giveSplitPartyItems(leader);


        for(Player player1 : partiesPlayer) {
            player1.sendMessage("Vous êtes désormais éligible pour un split party !");
        }
        System.out.println("Parti join");
    }

    @EventHandler
    public void onPartyLeave(BukkitPartiesPlayerPostLeaveEvent event) {
        PartyPlayer partyPlayer = event.getPartyPlayer();
        Party party = event.getParty();
        Player player = Bukkit.getPlayer(partyPlayer.getPlayerUUID());
        TFPlayer tfPlayer = TFight.INSTANCE.getPlayerManager().get(player);
        Player leader = Bukkit.getPlayer(party.getLeader());

        NameTags.reset(player , leader);
        NameTags.reset(leader , player);

        if(TFight.INSTANCE.getPlayerManager().get(player).getPlayerState() == PlayerState.FIGHTING) {
            return;
        }

        System.out.println("Partis size :" + party.getOnlineMembers().size());
        if(party.getOnlineMembers().size() < 2) {
            TFight.INSTANCE.getSplitPartyManager().remove(party);
            Bukkit.getPlayer(party.getLeader()).sendMessage("Vous pouvez plus Split Party");
            player.sendMessage("Vous pouvez plus Split Party");
            leader.getInventory().clear();
            if(QueueManager.INSTANCE.isInQueue(leader)) {
                QueueManager.INSTANCE.remove(party);
                leader.getInventory().clear();
                QueueManager.INSTANCE.giveQueueItem(leader);
                FightManager.INSTANCE.giveSpecItem(leader);
            } else {
                QueueManager.INSTANCE.giveQueueItem(leader);
                FightManager.INSTANCE.giveSpecItem(leader);
            }
        }

    }

    @EventHandler
    public void onPartyDelete(BukkitPartiesPartyPreDeleteEvent event) {
        Player leader = Bukkit.getPlayer(event.getParty().getLeader());
        Party party = event.getParty();
        List<Player> allplayer = new ArrayList<>();
        if(QueueManager.INSTANCE.isInQueue(leader)) {
            QueueManager.INSTANCE.remove(party);
        }
        leader.getInventory().clear();
        QueueManager.INSTANCE.giveQueueItem(leader);
        FightManager.INSTANCE.giveSpecItem(leader);

        for(UUID uuid : party.getMembers()) {
            allplayer.add(Bukkit.getPlayer(uuid));
        }
        for(Player player : allplayer) {
            NameTags.reset(player , leader);
            NameTags.reset(leader , player);
        }

    }
}
