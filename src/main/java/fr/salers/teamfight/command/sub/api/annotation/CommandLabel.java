package fr.salers.teamfight.command.sub.api.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Salers
 * made on fr.salers.ffalobby.command.sub.api.annotation
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandLabel {

    String name();
    String permission();

}
