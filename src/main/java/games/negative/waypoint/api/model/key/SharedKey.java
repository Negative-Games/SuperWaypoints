package games.negative.waypoint.api.model.key;

import org.bukkit.plugin.java.JavaPlugin;

public record SharedKey(JavaPlugin origin, String key) {

}
