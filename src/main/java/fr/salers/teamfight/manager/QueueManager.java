package fr.salers.teamfight.manager;

import com.alessiodp.parties.api.interfaces.Party;
import com.alessiodp.parties.api.interfaces.PartyPlayer;
import com.samjakob.spigui.SGMenu;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import fr.salers.teamfight.TFight;
import fr.salers.teamfight.fight.Fight;
import fr.salers.teamfight.player.TFPlayer;
import fr.salers.teamfight.player.state.PlayerState;
import fr.salers.teamfight.utilities.CC;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Salers
 * made on fr.salers.teamfight.party
 */

@Getter
public enum QueueManager {

    INSTANCE;

    private final List<Party> queuedParties = new ArrayList<>();

    public void add(final Party party) {
        if (!queuedParties.contains(party)) queuedParties.add(party);

        party.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                player -> player.sendMessage(CC.formatPrefixTranslate("&7Votre équipe a été &bajoutée à la queue."))
        );


        for(PartyPlayer partyPlayer : party.getOnlineMembers()) {
            giveLeaveQueueItem(Bukkit.getPlayer(partyPlayer.getPlayerUUID()));
            TFight.INSTANCE.getPlayerManager().get(Bukkit.getPlayer(partyPlayer.getPlayerUUID())).setPlayerState(PlayerState.QUEUE);
        }

        if (queuedParties.size() % 2 == 0 && queuedParties.size() > 1)
            handlePossibleMatch();
    }

    public void remove(final Party party) {
        queuedParties.remove(party);

        party.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                player -> player.sendMessage(CC.formatPrefixTranslate("&7Votre équipe a été &cretirée de la queue.."))
        );


        for(PartyPlayer partyPlayer : party.getOnlineMembers()) {
            giveQueueItem(Bukkit.getPlayer(partyPlayer.getPlayerUUID()));
            TFight.INSTANCE.getPlayerManager().get(Bukkit.getPlayer(partyPlayer.getPlayerUUID())).setPlayerState(PlayerState.SPAWN);
        }

    }

    public boolean isInQueue(final Player player) {
        if (!PartyManager.INSTANCE.isInParty(player)) return false;
        return queuedParties.contains(PartyManager.INSTANCE.getPartyFromPlayer(player));
    }

    public void handlePossibleMatch() {
        final Party partyOne = queuedParties.get(0);
        final Party partyTwo = queuedParties.get(1);

        if(ArenaManager.INSTANCE.peekNextArena() != null) {
            Fight fight = new Fight(partyOne, partyTwo);
            fight.startMatch();
        } else {
            partyOne.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                    player -> player.sendMessage(CC.formatPrefixTranslate("&7Votre équipe a été &cretirée de la queue.. Aucune arène disponible.")));
            partyTwo.getOnlineMembers().stream().map(partyPlayer -> Bukkit.getPlayer(partyPlayer.getPlayerUUID())).forEach(
                    player -> player.sendMessage(CC.formatPrefixTranslate("&7Votre équipe a été &cretirée de la queue.. Aucune arène disponible.")) );
        }

        queuedParties.remove(0);
        queuedParties.remove(0);
    }

    public void giveQueueItem(final Player player) {
        player.getInventory().clear();
        player.updateInventory();
        final ItemBuilder item = new ItemBuilder(Material.GOLD_SWORD)
                .name("&eTeamfight - Party")
                .enchant(Enchantment.DAMAGE_ALL, 1)
                .flag(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);


        player.getInventory().setItem(0, item.build());

    }

    public void giveLeaveQueueItem(final Player player) {
        player.getInventory().clear();
        final ItemBuilder item = new ItemBuilder(Material.REDSTONE)
                .name("&cLeave Queue")
                .flag(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);

        player.getInventory().setItem(8, item.build());
    }

    public void openQueueGUI(final Player player) {

        if(!PartyManager.INSTANCE.isInParty(player)) {
            player.sendMessage(CC.formatPrefixTranslate("&cVous devez être dans un groupe pour fare ceci."));
            return;
        }


        if(!(PartyManager.INSTANCE.getPartyFromPlayer(player).getLeader().equals(player.getUniqueId()))) {
            player.sendMessage(CC.translate("&cSeulement le leader peut lancer une partie !"));
            return;
        }
        final SGMenu gui = TFight.INSTANCE.getSpiGUI().create("&eQueue", 1);
        final SGButton kit = new SGButton(new ItemBuilder(Material.SANDSTONE).data((short) 2).name("&6Rush").build()).withListener(
                (InventoryClickEvent event) -> {
                    player.closeInventory();
                    add(PartyManager.INSTANCE.getPartyFromPlayer(player));
                }
        );

        gui.addButton(kit);
        gui.setAutomaticPaginationEnabled(false);

        player.openInventory(gui.getInventory());
    }

}
