package fr.salers.teamfight;

import org.bukkit.plugin.java.JavaPlugin;

public final class TeamFightPlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        TFight.INSTANCE.load(this);
    }

    @Override
    public void onEnable() {
        TFight.INSTANCE.enable();

    }

    @Override
    public void onDisable() {
        TFight.INSTANCE.disable();
    }
}
