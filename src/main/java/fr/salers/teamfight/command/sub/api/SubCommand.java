package fr.salers.teamfight.command.sub.api;

import fr.salers.teamfight.command.sub.api.annotation.CommandArgs;
import fr.salers.teamfight.command.sub.api.annotation.CommandLabel;
import fr.salers.teamfight.player.TFPlayer;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.logging.Level;

/**
 * @author Salers
 * made on fr.salers.ffalobby.command.sub.api
 */

@Getter
public abstract class SubCommand {

    private String name, permission, correctUsage;
    private int argsLength;

    public SubCommand() {
        if (this.getClass().isAnnotationPresent(CommandLabel.class) &&
                this.getClass().isAnnotationPresent(CommandArgs.class)) {

            this.name = getClass().getAnnotation(CommandLabel.class).name();
            this.permission = getClass().getAnnotation(CommandLabel.class).permission();
            this.correctUsage = getClass().getAnnotation(CommandArgs.class).correctUsage();
            this.argsLength = getClass().getAnnotation(CommandArgs.class).argsLength();

        } else {
            Bukkit.getLogger().log(Level.WARNING,
                    "Annotations CommandLabel or CommandArgs missing in " + this.getClass().getSimpleName());
        }
    }

    public abstract void processCommand(final TFPlayer tfPlayer, String[] args);
}
