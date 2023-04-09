package fr.salers.teamfight.manager;

import com.alessiodp.parties.api.interfaces.Party;
import com.samjakob.spigui.SGMenu;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import fr.salers.teamfight.TFight;
import fr.salers.teamfight.fight.Fight;
import fr.salers.teamfight.player.TFPlayer;
import fr.salers.teamfight.player.state.PlayerState;
import fr.salers.teamfight.utilities.CC;
import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Salers
 * made on fr.salers.teamfight.manager
 */
public enum FightManager {

    INSTANCE;

    @Getter
    private final List<Fight> activeFights = new ArrayList<>();

    public void startFight(final Party pOne, Party pTwo) {
        final Fight fight = new Fight(pOne, pTwo);
        fight.startMatch();
        activeFights.add(fight);
    }

    public void deleteFight(final Fight fight) {
        activeFights.remove(fight);
    }

    public void spectate(final TFPlayer tfPlayer, final Fight target) {
        if(tfPlayer.isFighting()) {
            tfPlayer.getPlayer().sendMessage(CC.formatPrefixTranslate("&cVous ne pouvez pas faire ça en combat."));
            return;
        }

        final Player player = tfPlayer.getPlayer();

        player.setGameMode(GameMode.SPECTATOR);
        player.getInventory().clear();
        player.teleport(target.getArena().getFirstLocation());
        player.sendMessage(CC.formatPrefixTranslate("&7Vous êtes désormais spectateur."));

        final ItemStack leaveSpec = new ItemBuilder(Material.WOOD_DOOR).name("&cQuitter le mode spectateur.").build();

        player.getInventory().setItem(4, leaveSpec);

        tfPlayer.setPlayerState(PlayerState.SPECTATING);
        target.getSpectators().add(tfPlayer);

    }

    public void leaveSpectator(final TFPlayer player) {
       for(Fight fight : activeFights) {
           fight.getSpectators().remove(player);
       }

       player.getPlayer().sendMessage(CC.formatPrefixTranslate("&eVous avez quitté le mode spectateur."));
       player.setToLobby();
    }

    public void openSpecGUI(final TFPlayer player) {
        final SGMenu gui = TFight.INSTANCE.getSpiGUI().create("&eObserver un Match", 5);
        for(Fight fights : activeFights) {

            final SGButton button = new SGButton(
                    new ItemBuilder(Material.PAPER)
                            .name("&a" + fights.getPartyOne().getName() + " &7VS &c" + fights.getPartyTwo().getName())
                            .build()
            ).withListener((InventoryClickEvent event) -> {
                player.getPlayer().closeInventory();
                spectate(player, fights);
            });

            gui.addButton(button);

        }

        player.getPlayer().openInventory(gui.getInventory());

    }

    public void giveSpecItem(final Player player) {
        final ItemStack item = new ItemBuilder(Material.COMPASS).name("&cRegarder les Combats").build();
        player.getInventory().setItem(4, item);
    }
}
