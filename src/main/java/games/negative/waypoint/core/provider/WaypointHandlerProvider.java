package games.negative.waypoint.core.provider;

import com.google.common.collect.Maps;
import games.negative.waypoint.api.WaypointHandler;
import games.negative.waypoint.api.model.display.WaypointDisplayHandler;
import games.negative.waypoint.api.model.key.SharedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class WaypointHandlerProvider implements WaypointHandler {

    private final Map<SharedKey, WaypointDisplayHandler> handlers;
    private WaypointDisplayHandler handler;

    public WaypointHandlerProvider() {
        this.handlers = Maps.newHashMap();
    }

    @Override
    public void addHandler(@NotNull WaypointDisplayHandler handler) {
        this.handlers.put(handler.getKey(), handler);
    }

    @Override
    public void removeHandler(@NotNull WaypointDisplayHandler type) {
        this.handlers.remove(type.getKey());
    }

    @Override
    public @Nullable WaypointDisplayHandler getHandler(@NotNull SharedKey key) {
        return this.handlers.getOrDefault(key, null);
    }

    @Override
    public @Nullable WaypointDisplayHandler getMainHandler() {
        return handler;
    }

    @Override
    public void setMainHandler(@NotNull WaypointDisplayHandler handler) {
        this.handler = handler;
    }
}
