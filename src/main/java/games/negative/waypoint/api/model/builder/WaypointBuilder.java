package games.negative.waypoint.api.model.builder;

import com.google.common.base.Preconditions;
import games.negative.waypoint.api.model.Waypoint;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WaypointBuilder {

    private String key;
    private String world;
    private double x;
    private double y;
    private double z;
    private Material icon;

    public WaypointBuilder() {
    }

    public WaypointBuilder key(String key) {
        this.key = key;
        return this;
    }

    public WaypointBuilder world(String world) {
        this.world = world;
        return this;
    }

    public WaypointBuilder x(double x) {
        this.x = x;
        return this;
    }

    public WaypointBuilder y(double y) {
        this.y = y;
        return this;
    }

    public WaypointBuilder z(double z) {
        this.z = z;
        return this;
    }

    public void location(@NotNull Location location) {
        World world = location.getWorld();
        Preconditions.checkNotNull(world, "Location World is null");

        this.world = world.getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public WaypointBuilder icon(Material icon) {
        this.icon = icon;
        return this;
    }

    public String getKey() {
        return key;
    }

    public String getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Material getIcon() {
        return icon;
    }

    @Nullable
    public Location getLocation() {
        World world = Bukkit.getWorld(this.world);
        if (world == null) return null;

        return new Location(world, x, y, z);
    }

    @NotNull
    public Waypoint build() {
        Location location = getLocation();
        Preconditions.checkNotNull(location, "Location is null");

        World world = location.getWorld();
        Preconditions.checkNotNull(world, "Location World is null");

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        return new Waypoint(key, System.currentTimeMillis(), world.getName(), x, y, z, icon);
    }
}
