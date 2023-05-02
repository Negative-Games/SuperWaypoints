package games.negative.waypoint.core.provider;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import games.negative.waypoint.SuperWaypoints;
import games.negative.waypoint.api.WaypointManager;
import games.negative.waypoint.api.model.Waypoint;
import games.negative.waypoint.api.model.WaypointProfile;
import games.negative.waypoint.core.Log;
import games.negative.waypoint.core.structure.WaypointProfileImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Map;
import java.util.UUID;

public class WaypointManagerProvider implements WaypointManager {

    private final SuperWaypoints plugin;
    private final Map<UUID, WaypointProfile> profiles;
    private final Map<UUID, Waypoint> active;

    public WaypointManagerProvider(@NotNull SuperWaypoints plugin) {
        this.plugin = plugin;
        this.profiles = Maps.newHashMap();
        this.active = Maps.newHashMap();

        // First time data loading logic
        File main = plugin.getDataFolder();
        if (!main.exists()) {
            Log.INFO.message("Creating plugin folder");
            main.mkdir();
        }

        File profiles = new File(main, "data");
        if (!profiles.exists()) {
            Log.INFO.message("Creating waypoint data folder");
            profiles.mkdir();
        }

    }

    @Override
    public void handleJoin(@NotNull UUID uuid) {
        File main = plugin.getDataFolder();
        if (!main.exists()) {
            Log.ERROR.message("Plugin folder does not exist");
            return;
        }

        File profiles = new File(main, "data");
        if (!profiles.exists()) {
            Log.ERROR.message("Waypoint data folder does not exist");
            return;
        }

        File profile = new File(profiles, uuid + ".data");
        if (!profile.exists()) {
            Log.INFO.message("Creating new waypoint profile for " + uuid);
            try {
                profile.createNewFile();

                WaypointProfile waypointProfile = new WaypointProfileImpl(uuid);
                this.profiles.remove(uuid);
                this.profiles.put(uuid, waypointProfile);

                Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
                try (Writer writer = new FileWriter(profile)) {
                    gson.toJson(waypointProfile, writer);
                } catch (Exception e) {
                    Log.ERROR.message("Failed to save new waypoint profile for " + uuid.toString());
                    e.printStackTrace();
                    return;
                }

                Log.INFO.message("Successfully created new waypoint profile for " + uuid);
            } catch (Exception e) {
                Log.ERROR.message("Failed to create new waypoint profile for " + uuid.toString());
                e.printStackTrace();
                return;
            }
            return;
        }

        Log.INFO.message("Loading waypoint profile for " + uuid);

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        try(Reader reader = new FileReader(profile)) {
            WaypointProfile waypointProfile = gson.fromJson(reader, WaypointProfileImpl.class);
            this.profiles.remove(uuid);
            this.profiles.put(uuid, waypointProfile);

            Log.INFO.message("Successfully loaded waypoint profile for " + uuid.toString());
        } catch (Exception e) {
            Log.ERROR.message("Failed to load waypoint profile for " + uuid.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void handleQuit(@NotNull UUID uuid) {
        File main = plugin.getDataFolder();
        if (!main.exists()) {
            Log.ERROR.message("Plugin folder does not exist");
            return;
        }

        File profiles = new File(main, "data");
        if (!profiles.exists()) {
            Log.ERROR.message("Waypoint data folder does not exist");
            return;
        }

        File profile = new File(profiles, uuid + ".data");
        WaypointProfile user = this.profiles.getOrDefault(uuid, null);
        if (!profile.exists()) {
            try {
                profile.createNewFile();

                if (user == null) {
                    Log.ERROR.message("Failed to save waypoint profile for " + uuid);
                    return;
                }

                Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
                try (Writer writer = new FileWriter(profile)) {
                    gson.toJson(user, writer);
                } catch (Exception e) {
                    Log.ERROR.message("Failed to save new waypoint profile for " + uuid);
                    e.printStackTrace();
                    return;
                }

                Log.INFO.message("Successfully saved waypoint profile for " + uuid);

                this.profiles.remove(uuid);
            } catch (Exception e) {
                Log.ERROR.message("Failed to create new waypoint profile for " + uuid);
                e.printStackTrace();
                return;
            }
            return;
        }

        Log.INFO.message("Saving waypoint profile for " + uuid);

        if (user == null) {
            Log.ERROR.message("Failed to save waypoint profile for " + uuid);
            return;
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        try (Writer writer = new FileWriter(profile)) {
            gson.toJson(user, writer);
        } catch (Exception e) {
            Log.ERROR.message("Failed to save new waypoint profile for " + uuid);
            e.printStackTrace();
        }
    }

    @Override
    public void addActiveWaypoint(@NotNull UUID uuid, @NotNull Waypoint waypoint) {
        this.active.remove(uuid);
        this.active.put(uuid, waypoint);
    }

    @Override
    public void removeActiveWaypoint(@NotNull UUID uuid) {
        this.active.remove(uuid);
    }

    @Override
    public @Nullable Waypoint getActiveWaypoint(@NotNull UUID uuid) {
        return active.getOrDefault(uuid, null);
    }

    @Override
    public Map<UUID, WaypointProfile> getProfiles() {
        return profiles;
    }

    @Override
    public Map<UUID, Waypoint> getActiveWaypoints() {
        return active;
    }
}
