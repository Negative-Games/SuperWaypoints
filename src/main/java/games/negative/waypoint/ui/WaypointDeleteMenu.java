package games.negative.waypoint.ui;

import games.negative.framework.gui.GUI;
import games.negative.waypoint.api.model.Waypoint;
import games.negative.waypoint.api.model.WaypointProfile;
import games.negative.waypoint.core.Item;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class WaypointDeleteMenu extends GUI {

    public WaypointDeleteMenu(@NotNull WaypointProfile profile, @NotNull Waypoint waypoint) {
        super("Delete " + waypoint.getKey() + "?", 3);

        ItemStack filler = Item.BLACK_FILLER.getItem();
        for (int i = 0; i < (9 * 3); i++) {
            setItem(i, player -> filler);
        }

        ItemStack confirm = Item.DELETE_CONFIRM.getItem();
        setItemClickEvent(11, player -> confirm, (player, event) -> {
            profile.removeWaypoint(waypoint);
            player.closeInventory();
            player.sendMessage("Deleted waypoint");
        });

        ItemStack cancel = Item.DELETE_CANCEL.getItem();
        setItemClickEvent(15, player -> cancel, (player, event) -> {
            player.closeInventory();
            player.sendMessage("Cancelled deletion");
        });
    }


}
