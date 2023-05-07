package games.negative.waypoint.handler;

import games.negative.waypoint.SuperWaypoints;
import games.negative.waypoint.api.model.Waypoint;
import games.negative.waypoint.api.model.display.WaypointDisplayHandler;
import games.negative.waypoint.api.model.key.SharedKey;
import games.negative.waypoint.core.Log;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public class HologramDisplay extends WaypointDisplayHandler {
    public HologramDisplay(@NotNull SuperWaypoints plugin) {
        super(new SharedKey(plugin, "hologram"));

        PluginManager manager = Bukkit.getPluginManager();
        Plugin depend = manager.getPlugin("HolographicDisplays");
        if (depend == null || !depend.isEnabled()) {
            Log.ERROR.message("HolographicDisplays is not enabled, disabling hologram display handler.");
            return;
        }


    }

    @Override
    public void activate(@NotNull Player player, @NotNull Waypoint waypoint) {

    }

    @Override
    public void deactivate(@NotNull Player player, @NotNull Waypoint waypoint) {

    }
}
