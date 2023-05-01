package games.negative.waypoint.command.main;

import games.negative.framework.command.Command;
import games.negative.framework.command.annotation.CommandInfo;
import games.negative.waypoint.api.WaypointHandler;
import games.negative.waypoint.api.WaypointManager;
import games.negative.waypoint.api.model.WaypointProfile;
import games.negative.waypoint.core.Log;
import games.negative.waypoint.ui.WaypointMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(
        name = "waypoint",
        playerOnly = true
)
public class CommandWaypoint extends Command {

    private final WaypointManager manager;
    private final WaypointHandler handler;

    public CommandWaypoint(WaypointManager manager, WaypointHandler handler) {
        this.manager = manager;
        this.handler = handler;
    }


    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        WaypointProfile profile = manager.getProfile(player.getUniqueId());
        if (profile == null) {
            Log.ERROR.message("Could not retrieve profile for player " + player.getName());
            return;
        }

        new WaypointMenu(profile, manager, handler).open(player);
    }
}
