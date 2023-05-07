package games.negative.waypoint.ui.editor;

import com.google.common.collect.Lists;
import games.negative.framework.gui.AnvilGUI;
import games.negative.framework.gui.GUI;
import games.negative.framework.util.Utils;
import games.negative.waypoint.SuperWaypoints;
import games.negative.waypoint.api.WaypointManager;
import games.negative.waypoint.api.model.Waypoint;
import games.negative.waypoint.api.model.WaypointProfile;
import games.negative.waypoint.core.Item;
import games.negative.waypoint.core.util.UtilLore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WaypointEditorMenu extends GUI {

    public WaypointEditorMenu(@NotNull WaypointManager manager, @NotNull Waypoint waypoint) {
        super("Waypoint Editor", 5);

        List<Integer> fillerSlots = Lists.newArrayList(
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 14, 15, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26
        );
        ItemStack filler = Item.BLACK_FILLER.getItem();

        fillerSlots.forEach(index -> setItem(index, player -> filler));

        ItemStack setName = Item.SET_NAME.getItem().clone();
        UtilLore.replaceLore(setName, "%name%", waypoint.getKey());

        setItemClickEvent(10, player -> setName, (player, event) -> {
            new AnvilGUI.Builder().title("Waypoint Editor - Name")
                    .text("Enter Name")
                    .onClose(user -> new DelayedOpenTask(manager, waypoint, player).runTaskLater(SuperWaypoints.getInstance(), 2))
                    .onComplete((user, text) -> {
                        if (text == null || !text.matches("^[a-zA-Z0-9_]{3,16}$"))
                            return AnvilGUI.Response.text("Invalid name");

                        waypoint.setKey(text);
                        new DelayedOpenTask(manager, waypoint, player).runTaskLater(SuperWaypoints.getInstance(), 2);
                        return AnvilGUI.Response.close();
                    }).open(player);
        });

        ItemStack setLocation = Item.SET_LOCATION.getItem().clone();
        String world = waypoint.getWorld();
        double x = waypoint.getX();
        double y = waypoint.getY();
        double z = waypoint.getZ();

        UtilLore.replaceLore(setLocation, "%world%", world);
        UtilLore.replaceLore(setLocation, "%x%", Utils.decimalFormat(x));
        UtilLore.replaceLore(setLocation, "%y%", Utils.decimalFormat(y));
        UtilLore.replaceLore(setLocation, "%z%", Utils.decimalFormat(z));

        setItemClickEvent(13, player -> setLocation, (player, event) -> new WaypointEditorLocationMenu(manager, waypoint).open(player));

        ItemStack setIcon = Item.SET_ICON.getItem().clone();
        Material icon = waypoint.getIcon();

        UtilLore.replaceLore(setIcon, "%icon%", (icon == null ? "Not Set" : icon.name()));

        setItemClickEvent(16, player -> setIcon, (player, event) -> new WaypointEditorIconMenu(manager, waypoint, 1).open(player));

        ItemStack confirm = Item.GREEN_CONFIRM.getItem();
        List<Integer> confirmSlots = Lists.newArrayList(27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44);
        confirmSlots.forEach(index -> setItemClickEvent(index, player -> confirm, (player, event) -> {
            if (waypoint.getWorld() == null) {
                player.sendMessage("World is null");
                return;
            }

            Location location = waypoint.getLocation();
            if (location == null) {
                player.sendMessage("Location is null");
                return;
            }

            WaypointProfile profile = manager.getProfile(player.getUniqueId());
            if (profile == null) {
                player.sendMessage("Profile is null");
                return;
            }

            player.closeInventory();
        }));
    }

    private static class DelayedOpenTask extends BukkitRunnable {

        private final WaypointManager manager;
        private final Waypoint waypoint;
        private final Player player;

        private DelayedOpenTask(WaypointManager manager, Waypoint waypoint, Player player) {
            this.manager = manager;
            this.waypoint = waypoint;
            this.player = player;
        }

        @Override
        public void run() {
            new WaypointEditorMenu(manager, waypoint).open(player);
        }
    }

}
