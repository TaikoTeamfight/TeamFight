package fr.salers.teamfight.player.handler.impl;

import fr.salers.teamfight.player.TFPlayer;
import fr.salers.teamfight.player.handler.AbstractHandler;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author Salers
 * made on fr.salers.teamfight.player.handler.impl
 */
public class InventoryHandler extends AbstractHandler {

    public InventoryHandler(TFPlayer tfPlayer) {
        super(tfPlayer);
    }

    @Override
    public void handle(Event e) {
        if (e instanceof InventoryClickEvent) {
            final InventoryClickEvent event = (InventoryClickEvent) e;


            if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;

            final ItemStack clicked = event.getCurrentItem();

            final boolean shouldCancel = clicked.getItemMeta().getDisplayName().contains("Queue") || clicked.getItemMeta().getDisplayName().contains("Teamfight - Party");

            if(shouldCancel)
                event.setCancelled(true);


        }
    }
}
