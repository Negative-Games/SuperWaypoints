package games.negative.waypoint.api;

import games.negative.waypoint.api.model.Waypoint;
import games.negative.waypoint.api.model.display.WaypointDisplayHandler;
import games.negative.waypoint.api.model.key.SharedKey;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents the management class for Waypoint Handling
 */
public interface WaypointHandler {

    /**
     * This method will handle the activation logic for waypoints
     * @param player The player who activated the waypoint
     * @param waypoint The waypoint that was activated
     */
    default void handleActivation(@NotNull Player player, @NotNull Waypoint waypoint) {
        WaypointDisplayHandler handler = getMainHandler();
        handler.activate(player, waypoint);
    }

    /**
     * This method will handle the deactivation logic for waypoints
     * @param player The player who deactivated the waypoint
     * @param waypoint The waypoint that was deactivated
     */
    default void handleDeactivation(@NotNull Player player, @NotNull Waypoint waypoint) {
        WaypointDisplayHandler handler = getMainHandler();
        handler.deactivate(player, waypoint);
    }

    /**
     * Add a new display handler
     * @param handler The handler
     */
    void addHandler(@NotNull WaypointDisplayHandler handler);

    /**
     * Remove a display handler
     * @param type The type of handler
     */
    void removeHandler(@NotNull WaypointDisplayHandler type);

    /**
     * Get a handler for a specific type
     * @param key The key of the handler
     * @return The handler
     */
    @Nullable
    WaypointDisplayHandler getHandler(@NotNull SharedKey key);

    /**
     * Get the main handler used on the server currently.
     * @return The main handler
     */
    @NotNull
    WaypointDisplayHandler getMainHandler();

    /**
     * Set the main handler used on the server currently.
     * @param handler The main handler
     */
    void setMainHandler(@NotNull WaypointDisplayHandler handler);
}
