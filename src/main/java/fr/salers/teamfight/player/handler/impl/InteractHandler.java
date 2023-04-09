package fr.salers.teamfight.player.handler.impl;

import fr.salers.teamfight.manager.FightManager;
import fr.salers.teamfight.manager.PartyManager;
import fr.salers.teamfight.manager.QueueManager;
import fr.salers.teamfight.player.TFPlayer;
import fr.salers.teamfight.player.handler.AbstractHandler;
import fr.salers.teamfight.player.state.PlayerState;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
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

            if(inHand == null || !inHand.hasItemMeta() || inHand.getItemMeta().getDisplayName() == null) return;


            if(!QueueManager.INSTANCE.isInQueue(tfPlayer.getPlayer())) {
                if(inHand.getItemMeta().getDisplayName().contains("Teamfight - Party")){
                    QueueManager.INSTANCE.openQueueGUI(tfPlayer.getPlayer());
                }
            } else {
                if(inHand.getItemMeta().getDisplayName().contains("Leave Queue")) {
                    QueueManager.INSTANCE.remove(PartyManager.INSTANCE.getPartyFromPlayer(tfPlayer.getPlayer()));
                }
            }

            if(tfPlayer.getPlayerState() == PlayerState.SPECTATING) {
                if(inHand.getItemMeta().getDisplayName().contains("Quitter") && inHand.getType() == Material.WOODEN_DOOR) {
                    FightManager.INSTANCE.leaveSpectator(tfPlayer);
                }
            }

            if(tfPlayer.getPlayerState() == PlayerState.SPAWN) {
                if(inHand.getItemMeta().getDisplayName().contains("Regarder"))
                    FightManager.INSTANCE.openSpecGUI(tfPlayer);
            }


        } else if(e instanceof EntityDamageEvent) {
            final ItemStack inHand = tfPlayer.getPlayer().getItemInHand();

            if (!tfPlayer.isFighting()) {
                ((EntityDamageEvent) e).setCancelled(true);
                return;
            }

            if(((EntityDamageEvent) e).getFinalDamage() - tfPlayer.getPlayer().getHealth() >= 0)
                tfPlayer.getActiveFight().handleKill(tfPlayer.getPlayer());



        } else if(e instanceof PlayerDropItemEvent) {
            final PlayerDropItemEvent event = (PlayerDropItemEvent) e;

            if(!event.getItemDrop().getItemStack().hasItemMeta()) return;

            event.setCancelled(true);
        }

    }




}
