package games.negative.waypoint.api.model;

import games.negative.framework.key.Keyd;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an existing Waypoint.
 */
public interface Waypoint extends Keyd<String> {

    /**
     * Get the creation time of the waypoint.
     * @return The creation time of the waypoint.
     */
    long getCreationTime();

    /**
     * Get the world name of the waypoint.
     * @return The world name of the waypoint.
     */
    @NotNull
    String getWorld();

    /**
     * Set the world name of the waypoint.
     * @param world The world name of the waypoint.
     */
    void setWorld(@NotNull String world);

    /**
     * Get the x coordinate of the waypoint.
     * @return The x coordinate of the waypoint.
     */
    double getX();

    /**
     * Set the x coordinate of the waypoint.
     * @param x The x coordinate of the waypoint.
     */
    void setX(double x);

    /**
     * Get the y coordinate of the waypoint.
     * @return The y coordinate of the waypoint.
     */
    double getY();

    /**
     * Set the y coordinate of the waypoint.
     * @param y The y coordinate of the waypoint.
     */
    void setY(double y);

    /**
     * Get the z coordinate of the waypoint.
     * @return The z coordinate of the waypoint.
     */
    double getZ();

    /**
     * Set the z coordinate of the waypoint.
     * @param z The z coordinate of the waypoint.
     */
    void setZ(double z);

    /**
     * Parse the waypoint into a {@link Location}.
     * @return The parsed location.
     * @throws NullPointerException If the world is not loaded or does not exist.
     */
    default Location getLocation() throws NullPointerException {
        World world = Bukkit.getWorld(getWorld());
        if (world == null)
            throw new NullPointerException("World " + getWorld() + " is not loaded or does not exist.");

        return new Location(world, getX(), getY(), getZ());
    }

    /**
     * Set the location of the waypoint.
     * @param location The location of the waypoint.
     */
    default void setLocation(@NotNull Location location) {
        World world = location.getWorld();
        if (world == null) return;

        setWorld(world.getName());
        setX(location.getX());
        setY(location.getY());
        setZ(location.getZ());
    }

    /**
     * Get the icon of the waypoint.
     * @return The icon of the waypoint.
     * @throws NullPointerException If the icon is null.
     */
    @Nullable
    Material getIcon();

}
