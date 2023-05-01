package games.negative.waypoint.api.model;

import games.negative.framework.key.Keyd;
import games.negative.waypoint.core.structure.WaypointImpl;
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


    /**
     * Builder for {@link Waypoint}.
     */
    class Builder {

        private String name;
        private String world;
        private double x;
        private double y;
        private double z;
        private Material icon;

        public Builder() {
            this(null);
        }

        /**
         * Create a new builder.
         * @param name The name of the waypoint.
         */
        public Builder(String name) {
            this.name = name;
        }

        /**
         * Get the name of the waypoint.
         * @return The name of the waypoint.
         */
        public String getName() {
            return name;
        }

        public Builder name(@NotNull String name) {
            this.name = name;
            return this;
        }

        /**
         * Set the world of the waypoint.
         * @param world The world of the waypoint.
         * @return The builder.
         */
        public Builder world(@NotNull String world) {
            this.world = world;
            return this;
        }

        /**
         * Get the world of the waypoint.
         * @return The world of the waypoint.
         */
        public String getWorld() {
            return world;
        }

        /**
         * Set the location of the waypoint.
         * @param x The x coordinate of the waypoint.
         * @return The builder.
         */
        public Builder x(double x) {
            this.x = x;
            return this;
        }

        /**
         * Get the x coordinate of the waypoint.
         * @return The x coordinate of the waypoint.
         */
        public double getX() {
            return x;
        }

        /**
         * Set the location of the waypoint.
         * @param y The y coordinate of the waypoint.
         * @return The builder.
         */
        public Builder y(double y) {
            this.y = y;
            return this;
        }

        /**
         * Get the y coordinate of the waypoint.
         * @return The y coordinate of the waypoint.
         */
        public double getY() {
            return y;
        }

        /**
         * Set the location of the waypoint.
         * @param z The z coordinate of the waypoint.
         * @return The builder.
         */
        public Builder z(double z) {
            this.z = z;
            return this;
        }

        /**
         * Get the z coordinate of the waypoint.
         * @return The z coordinate of the waypoint.
         */
        public double getZ() {
            return z;
        }

        /**
         * Set the icon of the waypoint.
         * @param icon The icon of the waypoint.
         * @return The builder.
         */
        public Builder icon(@Nullable Material icon) {
            this.icon = icon;
            return this;
        }

        /**
         * Get the icon of the waypoint.
         * @return The icon of the waypoint.
         */
        public Material getIcon() {
            return icon;
        }

        public Location getLocation() {
            World world = Bukkit.getWorld(this.world);
            if (world == null) return null;

            return new Location(world, x, y, z);
        }

        /**
         * Build the waypoint.
         * @return The waypoint.
         */
        public Waypoint build() {
            return new WaypointImpl(
                    name,
                    System.currentTimeMillis(),
                    world,
                    x,
                    y,
                    z,
                    icon
            );
        }
    }

}
