package games.negative.waypoint;

import games.negative.framework.BasePlugin;
import games.negative.waypoint.core.Locale;
import games.negative.waypoint.core.Log;

public class SuperWaypoints extends BasePlugin {

    private static SuperWaypoints instance;
    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;

        Log.init(this);
        Locale.init(this);
    }

    public static SuperWaypoints getInstance() {
        return instance;
    }
}
