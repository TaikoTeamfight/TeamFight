package fr.salers.teamfight.arena;

import fr.salers.teamfight.manager.CustomConfigManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.io.IOException;

/**
 * @author Salers
 * made on fr.salers.teamfight.arena
 */

@Getter
public class Arena {

    private Location firstLocation, secondLocation;
    private final String name;
    @Setter
    private boolean occupied;

    public Arena(Location firstLocation, Location secondLocation, String name) {
        this.firstLocation = firstLocation;
        this.secondLocation = secondLocation;
        this.name = name;
        this.occupied = false;

        CustomConfigManager.INSTANCE.getArenaConfig().set("arenas." + name + ".world", firstLocation.getWorld().getName());

        setFirstLocation(firstLocation);
        setSecondLocation(secondLocation);


    }

    public void setFirstLocation(Location firstLocation) {
        this.firstLocation = firstLocation;
        CustomConfigManager.INSTANCE.getArenaConfig().set("arenas." + name + ".pos1." + ".x", firstLocation.getX());
        CustomConfigManager.INSTANCE.getArenaConfig().set("arenas." + name + ".pos1." + ".y", firstLocation.getY());
        CustomConfigManager.INSTANCE.getArenaConfig().set("arenas." + name + ".pos1." + ".z", firstLocation.getZ());
        CustomConfigManager.INSTANCE.getArenaConfig().set("arenas." + name + ".pos1." + ".yaw", firstLocation.getYaw());
        save();
    }

    public void setSecondLocation(Location secondLocation) {
        this.secondLocation = secondLocation;
        CustomConfigManager.INSTANCE.getArenaConfig().set("arenas." + name + ".pos2." + ".x", secondLocation.getX());
        CustomConfigManager.INSTANCE.getArenaConfig().set("arenas." + name + ".pos2." + ".y", secondLocation.getY());
        CustomConfigManager.INSTANCE.getArenaConfig().set("arenas." + name + ".pos2." + ".z", secondLocation.getZ());
        CustomConfigManager.INSTANCE.getArenaConfig().set("arenas." + name + ".pos2." + ".z", secondLocation.getYaw());

        save();

    }

    private void save() {
        try {
            CustomConfigManager.INSTANCE.getArenaConfig().save(CustomConfigManager.INSTANCE.getArenaFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}
