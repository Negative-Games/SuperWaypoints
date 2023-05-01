package games.negative.waypoint.ui.creator;

import com.google.common.collect.Lists;
import games.negative.framework.gui.AnvilGUI;
import games.negative.framework.gui.GUI;
import games.negative.framework.util.Utils;
import games.negative.waypoint.SuperWaypoints;
import games.negative.waypoint.api.WaypointManager;
import games.negative.waypoint.api.model.Waypoint;
import games.negative.waypoint.core.Item;
import games.negative.waypoint.core.util.UtilLore;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WaypointCreatorLocationMenu extends GUI {
    public WaypointCreatorLocationMenu(@NotNull WaypointManager manager, @NotNull Waypoint.Builder builder) {
        super("Waypoint Creator - Location", 5);

        List<Integer> fillerSlots = Lists.newArrayList(
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26
        );
        ItemStack filler = Item.BLACK_FILLER.getItem();

        fillerSlots.forEach(index -> setItem(index, player -> filler));

        ItemStack worlds = Item.WORLDS.getItem();
        setItemClickEvent(10, player -> worlds, (player, event) -> new WaypointCreatorWorldMenu(manager, builder, 1).open(player));

        ItemStack x = Item.X_COORDINATE.getItem().clone();
        double builderX = builder.getX();

        UtilLore.replaceLore(x, "%x%", Utils.decimalFormat(builderX));

        setItemClickEvent(12, player -> x, (player, event) -> {
            new AnvilGUI.Builder()
                    .title("Waypoint Creator - Location")
                    .text("Enter X Coordinate")
                    .onClose(user -> new WaypointCreatorLocationMenu(manager, builder).open(player))
                    .onComplete((user, text) -> {
                        if (text == null || !text.matches("^-?[0-9]{1,6}$"))
                            return AnvilGUI.Response.text("Invalid X Coordinate");

                        builder.x(Integer.parseInt(text));
                        new DelayedOpenTask(builder, manager, player).runTaskLater(SuperWaypoints.getInstance(), 2L);
                        return AnvilGUI.Response.close();
                    }).open(player);
        });

        ItemStack y = Item.Y_COORDINATE.getItem().clone();
        double builderY = builder.getY();

        UtilLore.replaceLore(y, "%y%", Utils.decimalFormat(builderY));

        setItemClickEvent(14, player -> y, (player, event) -> {
            new AnvilGUI.Builder()
                    .title("Waypoint Creator - Location")
                    .text("Enter Y Coordinate")
                    .onClose(user -> new WaypointCreatorLocationMenu(manager, builder).open(player))
                    .onComplete((user, text) -> {
                        if (text == null || !text.matches("^-?[0-9]{1,6}$"))
                            return AnvilGUI.Response.text("Invalid Y Coordinate");

                        builder.y(Integer.parseInt(text));
                        new DelayedOpenTask(builder, manager, player).runTaskLater(SuperWaypoints.getInstance(), 2L);
                        return AnvilGUI.Response.close();
                    }).open(player);
        });

        ItemStack z = Item.Z_COORDINATE.getItem().clone();
        double builderZ = builder.getZ();

        UtilLore.replaceLore(z, "%z%", Utils.decimalFormat(builderZ));

        setItemClickEvent(16, player -> z, (player, event) -> {
            new AnvilGUI.Builder()
                    .title("Waypoint Creator - Location")
                    .text("Enter Z Coordinate")
                    .onClose(user -> new WaypointCreatorLocationMenu(manager, builder).open(player))
                    .onComplete((user, text) -> {
                        if (text == null || !text.matches("^-?[0-9]{1,6}$"))
                            return AnvilGUI.Response.text("Invalid Z Coordinate");

                        builder.y(Integer.parseInt(text));
                        new DelayedOpenTask(builder, manager, player).runTaskLater(SuperWaypoints.getInstance(), 2L);
                        return AnvilGUI.Response.close();
                    }).open(player);
        });

        ItemStack confirm = Item.GREEN_CONFIRM.getItem();
        List<Integer> confirmSlots = Lists.newArrayList(27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44);
        confirmSlots.forEach(index -> setItemClickEvent(index, player -> confirm, (player, event) -> {
            Location location = builder.getLocation();
            if (location == null) {
                player.sendMessage("Invalid Location");
                return;
            }

            new WaypointCreatorMenu(manager, builder).open(player);
        }));
    }

    private static class DelayedOpenTask extends BukkitRunnable {

        private final Waypoint.Builder builder;
        private final WaypointManager manager;
        private final Player player;

        private DelayedOpenTask(Waypoint.Builder builder, WaypointManager manager, Player player) {
            this.builder = builder;
            this.manager = manager;
            this.player = player;
        }

        @Override
        public void run() {
            new WaypointCreatorLocationMenu(manager, builder).open(player);
        }
    }
}
