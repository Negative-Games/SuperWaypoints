package games.negative.waypoint;

import games.negative.framework.BasePlugin;
import games.negative.waypoint.api.WaypointAPI;
import games.negative.waypoint.api.WaypointHandler;
import games.negative.waypoint.api.WaypointManager;
import games.negative.waypoint.command.main.CommandWaypoint;
import games.negative.waypoint.core.Item;
import games.negative.waypoint.core.Locale;
import games.negative.waypoint.core.Log;
import games.negative.waypoint.core.provider.WaypointAPIProvider;
import games.negative.waypoint.listener.PlayerProfileListener;

public class SuperWaypoints extends BasePlugin {

    private static SuperWaypoints instance;
    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;

        Log.init(this);
        Locale.init(this);
        Item.init(this);

        new WaypointAPIProvider(this);

        WaypointAPI api = WaypointAPI.getInstance();
        WaypointManager waypointManager = api.getWaypointManager();
        WaypointHandler waypointHandler = api.getWaypointHandler();

        registerListeners(
                new PlayerProfileListener(waypointManager)
        );

        registerCommands(
                new CommandWaypoint(waypointManager, waypointHandler)
        );
    }

    public static SuperWaypoints getInstance() {
        return instance;
    }
}
