package fr.salers.teamfight.manager;

import fr.salers.teamfight.arena.Arena;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Salers
 * made on fr.salers.teamfight.party
 */
public enum ArenaManager {

    INSTANCE;

    @Getter
    private final List<Arena> arenas = new ArrayList<>();

    public Arena peekNextArena() {
        Arena arena = null;

        for (Arena ars : arenas) {
            if (ars.isOccupied()) continue;

            arena = ars;
        }

        return arena;
    }

    public void loadFromConfig() {
        if (!this.arenas.isEmpty()) arenas.clear();

        final FileConfiguration config = CustomConfigManager.INSTANCE.getArenaConfig();

        for (String keys : config.getConfigurationSection("arenas").getKeys(false)) {

            final double x1 = config.getDouble("arenas." + keys + ".pos1.x");
            final double y1 = config.getDouble("arenas." + keys + ".pos1.y");
            final double z1 = config.getDouble("arenas." + keys + ".pos1.z");
            final double yaw1 = config.getDouble("arenas." + keys + ".pos1.yaw");

            final double x2 = config.getDouble("arenas." + keys + ".pos2.x");
            final double y2 = config.getDouble("arenas." + keys + ".pos2.y");
            final double z2 = config.getDouble("arenas." + keys + ".pos2.z");
            final double yaw2 = config.getDouble("arenas." + keys + ".pos2.yaw");

            final World world = Bukkit.getWorld(config.getString("arenas." + keys +".world"));

            this.arenas.add(new Arena(new Location(world, x1,y1, z1, (float) yaw1, 0F), new Location(world,x2, y2, z2, (float) yaw2, 0F), keys));
        }


    }


    public void createArena(final Arena arena) {
        arenas.add(arena);
    }
}
