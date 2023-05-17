package games.negative.waypoint.command.main;

import games.negative.framework.commands.Command;
import games.negative.framework.commands.Context;
import games.negative.waypoint.api.WaypointHandler;
import games.negative.waypoint.api.WaypointManager;
import games.negative.waypoint.api.model.WaypointProfile;
import games.negative.waypoint.core.Log;
import games.negative.waypoint.ui.WaypointMenu;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandWaypoint implements Command {

    private final WaypointManager manager;
    private final WaypointHandler handler;

    public CommandWaypoint(WaypointManager manager, WaypointHandler handler) {
        this.manager = manager;
        this.handler = handler;
    }

    @Override
    public void execute(@NotNull Context context) {
        Player player = context.getPlayer();
        assert player != null;

        WaypointProfile profile = manager.getProfile(player.getUniqueId());
        if (profile == null) {
            Log.ERROR.message("Could not retrieve profile for player " + player.getName());
            return;
        }

        new WaypointMenu(profile, manager, handler, 1).open(player);
    }
}
