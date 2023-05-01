package games.negative.waypoint.ui;

import games.negative.framework.gui.GUI;
import games.negative.waypoint.api.WaypointHandler;
import games.negative.waypoint.api.WaypointManager;
import games.negative.waypoint.api.model.Waypoint;
import games.negative.waypoint.api.model.WaypointProfile;

import java.util.List;

public class WaypointMenu extends GUI {
    public WaypointMenu(WaypointProfile profile, WaypointManager manager, WaypointHandler handler) {
        super("My Waypoints", 6);


    }
}
