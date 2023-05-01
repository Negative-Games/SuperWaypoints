package games.negative.waypoint.core.structure;

import com.google.common.collect.Lists;
import games.negative.waypoint.api.model.Waypoint;
import games.negative.waypoint.api.model.WaypointProfile;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

@Data
public class WaypointProfileImpl implements WaypointProfile {

    private final UUID uuid;
    private final List<Waypoint> waypoints;

    public WaypointProfileImpl(UUID uuid) {
        this(uuid, Lists.newArrayList());
    }

    public WaypointProfileImpl(UUID uuid, List<Waypoint> waypoints) {
        this.uuid = uuid;
        this.waypoints = waypoints;
    }

    @Override
    public void addWaypoint(@NotNull Waypoint waypoint) {
        waypoints.add(waypoint);
    }

    @Override
    public void removeWaypoint(@NotNull Waypoint waypoint) {
        waypoints.remove(waypoint);
    }

    @Override
    public @Nullable Waypoint getWaypoint(@NotNull String key) {
        return waypoints.stream().filter(waypoint -> waypoint.getKey().equals(key)).findFirst().orElse(null);
    }

    @Override
    public @NotNull List<Waypoint> getWaypoints() {
        return waypoints;
    }

    @Override
    public @NotNull UUID getKey() {
        return uuid;
    }

    @Override
    public void setKey(@NotNull UUID uuid) {
        throw new UnsupportedOperationException("Cannot change UUID of a profile");
    }
}
