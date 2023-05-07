package games.negative.waypoint.api.model;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.SerializedName;
import games.negative.framework.key.Keyd;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

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

    @NotNull
    public Location getLocation() {
        World world = Bukkit.getWorld(this.world);
        Preconditions.checkNotNull(world, "World cannot be null!");

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
}
