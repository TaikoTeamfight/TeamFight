package fr.salers.teamfight.command.sub.impl;

import fr.salers.teamfight.arena.Arena;
import fr.salers.teamfight.command.sub.api.SubCommand;
import fr.salers.teamfight.command.sub.api.annotation.CommandArgs;
import fr.salers.teamfight.command.sub.api.annotation.CommandLabel;
import fr.salers.teamfight.manager.ArenaManager;
import fr.salers.teamfight.player.TFPlayer;
import fr.salers.teamfight.utilities.CC;

/**
 * @author Salers
 * made on fr.salers.teamfight.command.sub.impl
 */

@CommandLabel(name = "arenasetpos", permission = "tf.arena.command")
@CommandArgs(argsLength = 3, correctUsage = "/tf arenasetpos <position 1 ou 2> <arena>")
public class ArenaSetPos extends SubCommand {

    @Override
    public void processCommand(TFPlayer tfPlayer, String[] args) {
        final String target = args[2];

        if (!ArenaManager.INSTANCE.getArenas().stream()
                .anyMatch(arena -> arena.getName().equalsIgnoreCase(target))) {
            tfPlayer.getPlayer().sendMessage(CC.formatPrefixTranslate("&cAucune arène de ce nom n'existe."));
            return;
        }

        if (!args[1].equalsIgnoreCase("1") && !args[1].equalsIgnoreCase("2")) {
            tfPlayer.getPlayer().sendMessage(CC.formatPrefixTranslate("&cLa position doit être 1 ou 2."));
            return;
        }

        final Arena arena = ArenaManager.INSTANCE.getArenas().stream()
                .filter(arena1 -> arena1.getName().equalsIgnoreCase(target))
                .findFirst().get();

        switch (args[1]) {
            case "1":
                arena.setFirstLocation(tfPlayer.getPlayer().getLocation());
                break;
            case "2":
                arena.setSecondLocation(tfPlayer.getPlayer().getLocation());
                break;
        }

        tfPlayer.getPlayer().sendMessage(CC.formatPrefixTranslate("&7Vous avez mis à jour la position &e" + args[1]));

    }
}
