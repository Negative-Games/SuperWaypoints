package games.negative.waypoint.api.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.gson.annotations.SerializedName;
import games.negative.framework.key.Keyd;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
public class Waypoint implements Keyd<String> {

    @SerializedName("key")
    private String key;

    @SerializedName("creation-time")
    private final long creationTime;

    @SerializedName("world")
    private String world;

    @SerializedName("x")
    private double x;

    @SerializedName("y")
    private double y;

    @SerializedName("z")
    private double z;

    @SerializedName("icon")
    private Material icon;

    public Waypoint(String key, long creationTime, String world, double x, double y, double z, Material icon) {
        this.key = key;
        this.creationTime = creationTime;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.icon = icon;
    }

    public void setLocation(@NotNull Location location) {
        World world = location.getWorld();
        Preconditions.checkNotNull(world, "World cannot be null!");

        this.world = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    @Nullable
    public Location getLocation() {
        World world = Bukkit.getWorld(this.world);
        if (world == null) return null;

        return new Location(world, x, y, z);
    }

    @Override
    public @NotNull String getKey() {
        return key;
    }

    @Override
    public void setKey(@NotNull String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Waypoint waypoint)) return false;
        return creationTime == waypoint.creationTime && Double.compare(waypoint.x, x) == 0 && Double.compare(waypoint.y, y) == 0 && Double.compare(waypoint.z, z) == 0 && Objects.equal(key, waypoint.key) && Objects.equal(world, waypoint.world) && icon == waypoint.icon;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key, creationTime, world, x, y, z, icon);
    }
}
