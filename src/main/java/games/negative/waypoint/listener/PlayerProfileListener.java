package games.negative.waypoint.listener;

import games.negative.waypoint.api.WaypointManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerProfileListener implements Listener {

    private final WaypointManager manager;

    public PlayerProfileListener(WaypointManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        manager.handleJoin(player.getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        manager.handleQuit(player.getUniqueId());
    }
}
