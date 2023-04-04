package fr.salers.teamfight.command.sub.impl;

import fr.salers.teamfight.arena.Arena;
import fr.salers.teamfight.command.sub.api.SubCommand;
import fr.salers.teamfight.command.sub.api.annotation.CommandArgs;
import fr.salers.teamfight.command.sub.api.annotation.CommandLabel;
import fr.salers.teamfight.manager.ArenaManager;
import fr.salers.teamfight.player.TFPlayer;
import fr.salers.teamfight.utilities.CC;

import java.util.Locale;

/**
 * @author Salers
 * made on fr.salers.teamfight.command.sub.impl
 */

@CommandLabel(name = "arenacreate", permission = "tf.arena.command")
@CommandArgs(argsLength = 2, correctUsage = "/tf arenacreate <name>")
public class ArenaCreateCommand extends SubCommand {

    @Override
    public void processCommand(TFPlayer tfPlayer, String[] args) {
        final String name = args[1].toLowerCase(Locale.ROOT);

        if(ArenaManager.INSTANCE.getArenas().stream().anyMatch(arena -> arena.getName().equalsIgnoreCase(name))) {
            tfPlayer.getPlayer().sendMessage(CC.formatPrefixTranslate("&cUne arène avec ce nom existe déjà."));
            return;
        }

        ArenaManager.INSTANCE.createArena(new Arena(tfPlayer.getPlayer().getLocation(), tfPlayer.getPlayer().getLocation(), name));
        tfPlayer.getPlayer().sendMessage(CC.formatPrefixTranslate("&7Vous avez créé l'arène &e" + name));





    }
}
