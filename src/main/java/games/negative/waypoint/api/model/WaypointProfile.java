package games.negative.waypoint.api.model;

import games.negative.framework.key.Keyd;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * Represents a player's Waypoint Profile.
 * This class will contain all Waypoint appropriate data
 * for an individual player.
 */
public interface WaypointProfile extends Keyd<UUID> {

    /**
     * Add a new Waypoint to the profile.
     * @param waypoint The waypoint to add.
     */
    void addWaypoint(@NotNull Waypoint waypoint);

    /**
     * Remove a Waypoint from the profile.
     * @param waypoint The waypoint to remove.
     */
    void removeWaypoint(@NotNull Waypoint waypoint);

    /**
     * Get a Waypoint from the profile.
     * @param key The key of the waypoint.
     * @return The waypoint, or null if it doesn't exist.
     */
    @Nullable
    Waypoint getWaypoint(@NotNull String key);

    /**
     * Get all Waypoints from the profile.
     * @return The waypoints.
     */
    @NotNull
    List<Waypoint> getWaypoints();

}
