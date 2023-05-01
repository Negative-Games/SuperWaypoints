package games.negative.waypoint.core;

import games.negative.waypoint.SuperWaypoints;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public enum Log {

    INFO,
    WARNING,
    ERROR
    ;

    private static boolean muteInfo = true;
    public static void init(JavaPlugin plugin) {
        muteInfo = plugin.getConfig().getBoolean("mute-info-logs", true);
    }

    public void message(@NotNull String message) {
        Logger logger = SuperWaypoints.getInstance().getLogger();
        switch (this) {
            case INFO -> {
                if (muteInfo) return;

                logger.info(message);
            }

            case ERROR -> logger.severe(message);
            case WARNING -> logger.warning(message);
            default -> throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
