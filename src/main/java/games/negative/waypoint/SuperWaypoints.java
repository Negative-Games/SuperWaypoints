package games.negative.waypoint;

import games.negative.framework.BasePlugin;
import games.negative.waypoint.api.WaypointAPI;
import games.negative.waypoint.api.WaypointHandler;
import games.negative.waypoint.api.WaypointManager;
import games.negative.waypoint.api.model.display.WaypointDisplayHandler;
import games.negative.waypoint.command.main.CommandWaypoint;
import games.negative.waypoint.core.Item;
import games.negative.waypoint.core.Locale;
import games.negative.waypoint.core.Log;
import games.negative.waypoint.core.provider.WaypointAPIProvider;
import games.negative.waypoint.handler.HologramDisplay;
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

        HologramDisplay hologramDisplay = new HologramDisplay(this);
        waypointHandler.setMainHandler(hologramDisplay);
        waypointHandler.addHandler(hologramDisplay);

        registerListeners(
                new PlayerProfileListener(waypointManager)
        );

        registerCommands(
                new CommandWaypoint(waypointManager, waypointHandler)
        );
    }

    @Override
    public void onDisable() {
        WaypointAPI api = WaypointAPI.getInstance();
        api.getWaypointManager().onDisable();

        WaypointDisplayHandler main = api.getWaypointHandler().getMainHandler();
        if (main != null) main.onDisable();

    }

    public static SuperWaypoints getInstance() {
        return instance;
    }
}
