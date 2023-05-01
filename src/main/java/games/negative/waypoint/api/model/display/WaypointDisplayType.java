package games.negative.waypoint.api.model.display;

import org.jetbrains.annotations.NotNull;

public enum WaypointDisplayType {

    HOLOGRAM,
    BOSSBAR,
    ACTIONBAR,
    ;

    public static WaypointDisplayType fromString(@NotNull String string) {
        return WaypointDisplayType.valueOf(string.toUpperCase());
    }
}
