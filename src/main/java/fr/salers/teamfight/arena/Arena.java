package fr.salers.teamfight.arena;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

/**
 * @author Salers
 * made on fr.salers.teamfight.arena
 */
@Getter
@Setter
public class Arena {

    private final Location firstLocation, secondLocation;
    private boolean occupied;

    public Arena(Location firstLocation, Location secondLocation) {
        this.firstLocation = firstLocation;
        this.secondLocation = secondLocation;
        this.occupied = false;
    }
}
