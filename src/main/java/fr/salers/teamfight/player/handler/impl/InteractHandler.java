package fr.salers.teamfight.player.handler.impl;

import fr.salers.teamfight.manager.QueueManager;
import fr.salers.teamfight.player.TFPlayer;
import fr.salers.teamfight.player.handler.AbstractHandler;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author Salers
 * made on fr.salers.teamfight.player.handler.impl
 */
public class InteractHandler extends AbstractHandler {

    public InteractHandler(TFPlayer tfPlayer) {
        super(tfPlayer);
    }

    @Override
    public void handle(Event e) {
        if(e instanceof PlayerInteractEvent) {
            if(((PlayerInteractEvent) e).getAction() == Action.PHYSICAL) return;
            final ItemStack inHand = tfPlayer.getPlayer().getItemInHand();

            if(inHand == null || !inHand.hasItemMeta()) return;

            if(inHand.getItemMeta().getDisplayName().contains("Queue"))
                QueueManager.INSTANCE.openQueueGUI(tfPlayer.getPlayer());

        } else if(e instanceof PlayerInteractAtEntityEvent) {

        } else if(e instanceof PlayerDropItemEvent) {
            final PlayerDropItemEvent event = (PlayerDropItemEvent) e;

            if(!event.getItemDrop().getItemStack().hasItemMeta()) return;

            event.setCancelled(true);
        }

    }




}
