package fr.salers.teamfight.player.handler.impl;

import fr.salers.teamfight.TFight;
import fr.salers.teamfight.config.Config;
import fr.salers.teamfight.player.TFPlayer;
import fr.salers.teamfight.player.handler.AbstractHandler;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * @author Salers
 * made on fr.salers.teamfight.player.handler.impl
 */
public class BlockHandler extends AbstractHandler {

    public BlockHandler(TFPlayer tfPlayer) {
        super(tfPlayer);
    }

    @Override
    public void handle(Event e) {
        if (e instanceof BlockPlaceEvent) {
            final BlockPlaceEvent event = (BlockPlaceEvent) e;

            if (event.getBlockPlaced().getType() == Material.SANDSTONE) {
                Bukkit.getScheduler().runTaskLater(TFight.INSTANCE.getPlugin(), () ->
                        event.getBlock().getLocation().getBlock().setType(Material.AIR), 13 * 20);
            }

            ((BlockPlaceEvent) e).setCancelled(tfPlayer.getPlayer().getGameMode() != GameMode.CREATIVE);

        } else if (e instanceof BlockBreakEvent) {
            if (!tfPlayer.isFighting()) {
                final boolean cancel = tfPlayer.getPlayer().getGameMode() != GameMode.CREATIVE;
                ((BlockBreakEvent) e).setCancelled(cancel);
            } else {
                ((BlockBreakEvent) e).setCancelled(((BlockBreakEvent) e).getBlock().getType() != Material.SANDSTONE);

            }




        }

    }
}
