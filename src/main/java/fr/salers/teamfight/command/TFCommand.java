package fr.salers.teamfight.command;


import fr.salers.teamfight.TFight;
import fr.salers.teamfight.command.sub.api.SubCommand;
import fr.salers.teamfight.command.sub.impl.ArenaCreateCommand;
import fr.salers.teamfight.command.sub.impl.ArenaSetPos;
import fr.salers.teamfight.utilities.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Salers
 * made on fr.salers.tflobby.command
 */
public class TFCommand implements CommandExecutor {

    private final List<SubCommand> subCommands = Arrays.asList(
            new ArenaSetPos(),
            new ArenaCreateCommand()

    );

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("Only players are able to use this command.");
            return false;
        }

        final Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(getHelpMessage());
            return false;
        }

        final String targetedSub = args[0];
        final Optional<SubCommand> optionalCommand = this.subCommands.stream().filter(sub -> sub.getName().equalsIgnoreCase(targetedSub)).findFirst();

        if (!optionalCommand.isPresent()) {
            player.sendMessage(getHelpMessage());
            return false;
        }

        final SubCommand subCommand = optionalCommand.get();

        if (args.length != subCommand.getArgsLength()) {
            player.sendMessage("§cIncorrect, essayez : " + subCommand.getCorrectUsage());
            return false;
        }

        if (!player.hasPermission(subCommand.getPermission())) {
            player.sendMessage("Vous n'avez pas accès à cette commande.");
            return false;
        }

        subCommand.processCommand(TFight.INSTANCE.getPlayerManager().get(player), args);

        return true;
    }

    private String getHelpMessage() {
        return CC.translate(
                "&aTeamFight - \n&r" +
                        CC.getLineBreak("&7") +
                        "&e/tf arenacreate <nom>\n" +
                        "&e/tf arenasetpos <position 1 ou 2> <arene>\n" +
                        "&e/tf editkit\n" +
                        CC.getLineBreak("&7")
        );
    }
}
