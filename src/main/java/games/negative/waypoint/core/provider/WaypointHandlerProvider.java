package games.negative.waypoint.core.provider;

import games.negative.waypoint.api.WaypointHandler;
import games.negative.waypoint.api.model.display.WaypointDisplayHandler;
import games.negative.waypoint.api.model.key.SharedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WaypointHandlerProvider implements WaypointHandler {
    @Override
    public void addHandler(@NotNull WaypointDisplayHandler handler) {

    }

    @Override
    public void removeHandler(@NotNull WaypointDisplayHandler type) {

    }

    @Override
    public @Nullable WaypointDisplayHandler getHandler(@NotNull SharedKey key) {
        return null;
    }

    @Override
    public @NotNull WaypointDisplayHandler getMainHandler() {
        return null;
    }

    @Override
    public void setMainHandler(@NotNull WaypointDisplayHandler handler) {

    }
}
