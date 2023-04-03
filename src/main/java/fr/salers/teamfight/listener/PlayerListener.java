package fr.salers.teamfight.listener;

import fr.salers.teamfight.TFight;
import fr.salers.teamfight.player.TFPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
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

}
