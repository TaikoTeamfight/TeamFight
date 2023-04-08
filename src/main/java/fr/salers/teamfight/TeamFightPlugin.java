package fr.salers.teamfight;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class TeamFightPlugin extends JavaPlugin {

    @Getter
    public static TeamFightPlugin INSTANCE;

    @Override
    public void onLoad() {
        TFight.INSTANCE.load(this);
    }

    @Override
    public void onEnable() {
        TFight.INSTANCE.enable();
        INSTANCE = this;

        //Can be removed
        Bukkit.getWorld("world").setGameRuleValue("doMobSpawning", "false");
        Bukkit.getWorld("world").setGameRuleValue("doMobLoot", "false");
        Bukkit.getWorld("world").setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getWorld("world").setTime(1000);

    }

    @Override
    public void onDisable() {
        TFight.INSTANCE.disable();
    }
}
