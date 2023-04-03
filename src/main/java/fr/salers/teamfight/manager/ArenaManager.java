package fr.salers.teamfight.manager;

import fr.salers.teamfight.TFight;
import fr.salers.teamfight.arena.Arena;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Salers
 * made on fr.salers.teamfight.party
 */
public enum ArenaManager {

    INSTANCE;

    private final List<Arena> arenas = new ArrayList<>();

    public Arena peekNextArena() {
        Arena arena = null;

        for(Arena ars : arenas) {
            if(ars.isOccupied()) continue;

            arena = ars;
        }

        return arena;
    }

    public void loadFromConfig() {
        if(!this.arenas.isEmpty()) arenas.clear();

        final FileConfiguration config = TFight.INSTANCE.getPlugin().getConfig();


    }
}
