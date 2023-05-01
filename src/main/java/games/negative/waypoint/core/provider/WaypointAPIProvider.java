package games.negative.waypoint.core.provider;

import games.negative.waypoint.SuperWaypoints;
import games.negative.waypoint.api.WaypointAPI;
import games.negative.waypoint.api.WaypointHandler;
import games.negative.waypoint.api.WaypointManager;
import org.jetbrains.annotations.NotNull;

public class WaypointAPIProvider extends WaypointAPI {

    private final WaypointManager manager;
    private final WaypointHandler handler;
    public WaypointAPIProvider(@NotNull SuperWaypoints plugin) {
        setInstance(this);

        this.manager = new WaypointManagerProvider(plugin);
        this.handler = new WaypointHandlerProvider();
    }

    @Override
    public WaypointManager getWaypointManager() {
        return this.manager;
    }

    @Override
    public WaypointHandler getWaypointHandler() {
        return handler;
    }
}
