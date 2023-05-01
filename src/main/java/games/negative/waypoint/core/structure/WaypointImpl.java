package games.negative.waypoint.core.structure;

import games.negative.waypoint.api.model.Waypoint;
import lombok.Data;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
public class WaypointImpl implements Waypoint {

    private final String key;
    private final long creation;
    private String world;
    private double x;
    private double y;
    private double z;
    private Material icon;

    public WaypointImpl(String key, long creation, String world, double x, double y, double z) {
        this(key, creation, world, x, y, z, null);
    }

    public WaypointImpl(String key, long creation, String world, double x, double y, double z, Material icon) {
        this.key = key;
        this.creation = creation;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.icon = icon;
    }

    @Override
    public long getCreationTime() {
        return creation;
    }

    @Override
    public @NotNull String getWorld() {
        return world;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getZ() {
        return z;
    }

    @Override
    public @Nullable Material getIcon() {
        return icon;
    }

    @Override
    public @NotNull String getKey() {
        return key;
    }

    @Override
    public void setKey(@NotNull String s) {
        throw new UnsupportedOperationException("Cannot change key of a waypoint");
    }
}
