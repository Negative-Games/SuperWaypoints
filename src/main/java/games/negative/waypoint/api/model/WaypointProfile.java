package games.negative.waypoint.api.model;

import com.google.common.collect.Lists;
import games.negative.framework.key.Keyd;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

@Data
public class WaypointProfile implements Keyd<UUID> {

    private final UUID key;
    private final List<Waypoint> waypoints;

    public WaypointProfile(@NotNull UUID key) {
        this(key, Lists.newArrayList());
    }

    public WaypointProfile(@NotNull UUID key, @NotNull List<Waypoint> waypoints) {
        this.key = key;
        this.waypoints = waypoints;
    }

    public void addWaypoint(@NotNull Waypoint waypoint) {
        waypoints.add(waypoint);
    }

    public void removeWaypoint(@NotNull Waypoint waypoint) {
        waypoints.remove(waypoint);
    }

    @Nullable
    public Waypoint getWaypoint(@NotNull String name) {
        return waypoints.stream().filter(waypoint -> waypoint.getKey().equals(name)).findFirst().orElse(null);
    }

    @NotNull
    public List<Waypoint> getWaypoints() {
        return waypoints;
    }

    @Override
    public @NotNull UUID getKey() {
        return key;
    }

    @Override
    public void setKey(@NotNull UUID uuid) {
        throw new UnsupportedOperationException("You cannot set the key of a WaypointProfile!");
    }
}
