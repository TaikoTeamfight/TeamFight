package fr.salers.teamfight.listener;

import com.alessiodp.parties.api.interfaces.Party;
import fr.salers.teamfight.TFight;
import fr.salers.teamfight.manager.PartyManager;
import fr.salers.teamfight.player.TFPlayer;
import fr.salers.teamfight.player.state.PlayerState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * @author Salers
 * made on fr.salers.teamfight.listener
 */
public class PlayerListener implements Listener {

    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {
        final TFPlayer tfPlayer = TFight.INSTANCE.getPlayerManager().get(event.getPlayer());
        tfPlayer.handleEvent(event);
    }

    @EventHandler
    public void onInteractAtEntity(final PlayerInteractAtEntityEvent event) {
        final TFPlayer tfPlayer = TFight.INSTANCE.getPlayerManager().get(event.getPlayer());
        tfPlayer.handleEvent(event);
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        final TFPlayer tfPlayer = TFight.INSTANCE.getPlayerManager().get((Player) event.getWhoClicked());
        tfPlayer.handleEvent(event);
    }

    @EventHandler
    public void onItemDrop(final PlayerDropItemEvent event) {
        final TFPlayer tfPlayer = TFight.INSTANCE.getPlayerManager().get((Player) event.getPlayer());
        tfPlayer.handleEvent(event);
    }

    @EventHandler
    public void onBlockBreak(final BlockBreakEvent event) {
        final TFPlayer tfPlayer = TFight.INSTANCE.getPlayerManager().get((Player) event.getPlayer());
        tfPlayer.handleEvent(event);
    }

    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent event) {
        final TFPlayer tfPlayer = TFight.INSTANCE.getPlayerManager().get((Player) event.getPlayer());
        tfPlayer.handleEvent(event);
    }

    @EventHandler
    public void onAttack(final EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) return;
        final TFPlayer tfPlayer = TFight.INSTANCE.getPlayerManager().get((Player) event.getDamager());
        final Player target = (Player) event.getEntity();
        tfPlayer.handleEvent(event);


        if (PartyManager.INSTANCE.isInParty(tfPlayer.getPlayer()) && PartyManager.INSTANCE.isInParty(target)) {
            final Party party = PartyManager.INSTANCE.getPartyFromPlayer(tfPlayer.getPlayer());
            final boolean sameParty = PartyManager.INSTANCE.getPartyFromPlayer(target).equals(party);
            if (sameParty)
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(final EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        final TFPlayer tfPlayer = TFight.INSTANCE.getPlayerManager().get((Player) event.getEntity());
        tfPlayer.handleEvent(event);


    }

    @EventHandler
    public void onHungerLoose(FoodLevelChangeEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) e.getEntity();
        final TFPlayer tfPlayer = TFight.INSTANCE.getPlayerManager().get((Player) player);
        if (tfPlayer.getPlayerState() != PlayerState.SPAWN) {
            return;
        } else {
            e.setFoodLevel(20);
        }

    }

}
