package fr.salers.teamfight.command.sub.api.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandArgs {

    int argsLength() default 1;
    String correctUsage();
}
