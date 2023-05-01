package games.negative.waypoint.api;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public abstract class WaypointAPI {

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private static WaypointAPI instance;

    public abstract WaypointManager getWaypointManager();
}
