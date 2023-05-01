package games.negative.waypoint.api.model.display;

import games.negative.framework.key.Keyd;
import games.negative.waypoint.api.model.Waypoint;
import games.negative.waypoint.api.model.key.SharedKey;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a handler for displaying waypoints to players
 * This is used so the plugin can have multiple ways of displaying
 * waypoints to players.
 */
public abstract class WaypointDisplayHandler implements Keyd<SharedKey> {

    private final SharedKey key;

    public WaypointDisplayHandler(SharedKey key) {
        this.key = key;
    }

    /**
     * Invoked when a player activates a waypoint
     * @param player The player who activated the waypoint
     * @param waypoint The waypoint that was activated
     */
    public abstract void activate(@NotNull Player player, @NotNull Waypoint waypoint);

    /**
     * Invoked when a player deactivates a waypoint
     * @param player The player who deactivated the waypoint
     * @param waypoint The waypoint that was deactivated
     */
    public abstract void deactivate(@NotNull Player player, @NotNull Waypoint waypoint);

    @Override
    public @NotNull SharedKey getKey() {
        return key;
    }
}
