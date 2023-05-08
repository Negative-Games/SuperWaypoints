package games.negative.waypoint.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import games.negative.framework.util.Utils;
import games.negative.waypoint.SuperWaypoints;
import games.negative.waypoint.api.model.Waypoint;
import games.negative.waypoint.api.model.display.WaypointDisplayHandler;
import games.negative.waypoint.api.model.key.SharedKey;
import games.negative.waypoint.core.Log;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.HologramLines;
import me.filoghost.holographicdisplays.api.hologram.VisibilitySettings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class HologramDisplay extends WaypointDisplayHandler {

    private static final int MAX_HOLOGRAM_TELEPORT_DISTANCE = 5;
    private static final long UPDATE_INTERVAL = 5L;

    private final SuperWaypoints plugin;
    private final Map<UUID, Waypoint> tracking;
    private final Map<UUID, Hologram> holograms;
    public HologramDisplay(@NotNull SuperWaypoints plugin) {
        super(new SharedKey(plugin, "hologram"));

        this.plugin = plugin;

        this.tracking = Maps.newHashMap();
        this.holograms = Maps.newHashMap();

        PluginManager manager = Bukkit.getPluginManager();
        Plugin depend = manager.getPlugin("HolographicDisplays");
        if (depend == null || !depend.isEnabled()) {
            Log.ERROR.message("HolographicDisplays is not enabled, disabling hologram display handler.");
            return;
        }

        new HologramTask().runTaskTimer(plugin, 0, UPDATE_INTERVAL);
    }

    @Override
    public void activate(@NotNull Player player, @NotNull Waypoint waypoint) {
        this.tracking.put(player.getUniqueId(), waypoint);

        Hologram hologram = HolographicDisplaysAPI.get(plugin).createHologram(player.getLocation());
        VisibilitySettings visibility = hologram.getVisibilitySettings();
        visibility.setGlobalVisibility(VisibilitySettings.Visibility.HIDDEN);
        visibility.setIndividualVisibility(player, VisibilitySettings.Visibility.VISIBLE);

        HologramLines lines = hologram.getLines();
        lines.appendText("%distance%m");
    }

    @Override
    public void deactivate(@NotNull Player player, @NotNull Waypoint waypoint) {
        UUID uuid = player.getUniqueId();

        this.tracking.remove(uuid);

        Hologram removed = this.holograms.remove(uuid);
        removed.delete();
    }

    @Override
    public void onDisable() {
        Log.INFO.message("Disabling hologram display handler...");
        this.holograms.values().forEach(Hologram::delete);
    }

    private class HologramTask extends BukkitRunnable {

        @Override
        public void run() {
            Set<UUID> uuids = tracking.keySet();

            List<UUID> offline = Lists.newArrayList();
            for (UUID uuid : uuids) {
                Waypoint waypoint = tracking.get(uuid);
                Hologram hologram = holograms.get(uuid);
                Player player = Bukkit.getPlayer(uuid);
                if (waypoint == null || hologram == null)
                    continue;

                if (player == null) {
                    offline.add(uuid);
                    continue;
                }

                Location destination = waypoint.getLocation();
                if (destination == null)
                    continue;

                Location location = player.getLocation();

                double distance = Math.abs(destination.distance(location));

                HologramLines lines = hologram.getLines();
                lines.insertText(0, Utils.decimalFormat(distance) + "m");

                // Calculate the direction vector from the hologram to the destination
                Vector direction = destination.toVector().subtract(location.toVector());

                // Limit the magnitude of the direction vector to the maximum hologram teleport distance
                double maxDistance = MAX_HOLOGRAM_TELEPORT_DISTANCE;
                if (direction.length() > maxDistance) {
                    direction.normalize().multiply(maxDistance);
                }

                // Add the direction vector to the hologram's current location to teleport it closer to the destination
                Location updated = hologram.getPosition().toLocation().add(direction);
                hologram.setPosition(updated);
            }

            offline.forEach(uuid -> {
                tracking.remove(uuid);

                Hologram removed = holograms.remove(uuid);
                removed.delete();
            });
        }
    }
}
