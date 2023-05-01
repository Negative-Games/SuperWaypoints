package games.negative.waypoint.api;

import games.negative.waypoint.api.model.Waypoint;
import games.negative.waypoint.api.model.WaypointProfile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

/**
 * Represents the management class for Waypoints
 */
public interface WaypointManager {

    /**
     * This method will handle the joining logic for players
     * @param uuid The UUID of the player
     */
    void handleJoin(@NotNull UUID uuid);

    /**
     * This method will handle the quitting logic for players
     * @param uuid The UUID of the player
     */
    void handleQuit(@NotNull UUID uuid);

    void addActiveWaypoint(@NotNull UUID uuid, @NotNull Waypoint waypoint);

    void removeActiveWaypoint(@NotNull UUID uuid);

    @Nullable
    Waypoint getActiveWaypoint(@NotNull UUID uuid);

    /**
     * Get all loaded Profiles
     * @return A map of all loaded profiles
     */
    Map<UUID, WaypointProfile> getProfiles();

    /**
     * Get all active waypoints
     * @return A map of all active waypoints
     */
    Map<UUID, Waypoint> getActiveWaypoints();

    /**
     * Get a profile by a player
     * @param uuid The UUID of the player
     * @return The profile of the player
     * @throws NullPointerException If the profile is not loaded or does not exist
     */
    @Nullable
    default WaypointProfile getProfile(@NotNull UUID uuid) {
        return getProfiles().get(uuid);
    }
}
