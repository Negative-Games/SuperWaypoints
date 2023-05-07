package games.negative.waypoint.ui.creator;

import com.google.common.collect.Lists;
import games.negative.framework.gui.AnvilGUI;
import games.negative.framework.gui.GUI;
import games.negative.framework.util.Utils;
import games.negative.waypoint.SuperWaypoints;
import games.negative.waypoint.api.WaypointManager;
import games.negative.waypoint.api.model.Waypoint;
import games.negative.waypoint.api.model.WaypointProfile;
import games.negative.waypoint.api.model.builder.WaypointBuilder;
import games.negative.waypoint.core.Item;
import games.negative.waypoint.core.util.UtilLore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WaypointCreatorMenu extends GUI {
    public WaypointCreatorMenu(@NotNull WaypointManager manager, @NotNull WaypointBuilder builder) {
        super("Waypoint Creator", 5);

        List<Integer> fillerSlots = Lists.newArrayList(
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 14, 15, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26
        );
        ItemStack filler = Item.BLACK_FILLER.getItem();

        fillerSlots.forEach(index -> setItem(index, player -> filler));

        ItemStack setName = Item.SET_NAME.getItem().clone();
        UtilLore.replaceLore(setName, "%name%", (builder.getKey() == null ? "Not Set" : builder.getKey()));

        setItemClickEvent(10, player -> setName, (player, event) -> {
            new AnvilGUI.Builder().title("Waypoint Creator - Name")
                    .text("Enter Name")
                    .onClose(user -> new DelayedOpenTask(manager, builder, player).runTaskLater(SuperWaypoints.getInstance(), 2))
                    .onComplete((user, text) -> {
                        if (text == null || !text.matches("^[a-zA-Z0-9_]{3,16}$"))
                            return AnvilGUI.Response.text("Invalid name");

                        builder.key(text);
                        new DelayedOpenTask(manager, builder, player).runTaskLater(SuperWaypoints.getInstance(), 2);
                        return AnvilGUI.Response.close();
                    }).open(player);
        });

        ItemStack setLocation = Item.SET_LOCATION.getItem().clone();
        String builderWorld = builder.getWorld();
        double builderX = builder.getX();
        double builderY = builder.getY();
        double builderZ = builder.getZ();

        UtilLore.replaceLore(setLocation, "%world%", (builderWorld == null ? "Not Set" : builderWorld));
        UtilLore.replaceLore(setLocation, "%x%", Utils.decimalFormat(builderX));
        UtilLore.replaceLore(setLocation, "%y%", Utils.decimalFormat(builderY));
        UtilLore.replaceLore(setLocation, "%z%", Utils.decimalFormat(builderZ));

        setItemClickEvent(13, player -> setLocation, (player, event) -> new WaypointCreatorLocationMenu(manager, builder).open(player));

        ItemStack setIcon = Item.SET_ICON.getItem().clone();
        Material icon = builder.getIcon();

        UtilLore.replaceLore(setIcon, "%icon%", (icon == null ? "Not Set" : icon.name()));

        setItemClickEvent(16, player -> setIcon, (player, event) -> new WaypointCreatorIconMenu(manager, builder, 1).open(player));

        ItemStack confirm = Item.GREEN_CONFIRM.getItem();
        List<Integer> confirmSlots = Lists.newArrayList(27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44);
        confirmSlots.forEach(index -> setItemClickEvent(index, player -> confirm, (player, event) -> {
            String name = builder.getKey();
            if (name == null) {
                player.sendMessage("Name is null");
                return;
            }

            if (builder.getWorld() == null) {
                player.sendMessage("World is null");
                return;
            }

            Location location = builder.getLocation();
            if (location == null) {
                player.sendMessage("Location is null");
                return;
            }

            WaypointProfile profile = manager.getProfile(player.getUniqueId());
            if (profile == null) {
                player.sendMessage("Profile is null");
                return;
            }

            Waypoint finalization = builder.build();
            profile.addWaypoint(finalization);

            player.sendMessage("Confirm");
            player.closeInventory();
        }));
    }

    private static class DelayedOpenTask extends BukkitRunnable {

        private final WaypointManager manager;
        private final WaypointBuilder builder;
        private final Player player;

        private DelayedOpenTask(WaypointManager manager, WaypointBuilder builder, Player player) {
            this.manager = manager;
            this.builder = builder;
            this.player = player;
        }

        @Override
        public void run() {
            new WaypointCreatorMenu(manager, builder).open(player);
        }
    }
}
