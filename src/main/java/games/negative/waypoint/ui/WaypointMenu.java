package games.negative.waypoint.ui;

import com.google.common.collect.Lists;
import games.negative.framework.base.itembuilder.ItemBuilder;
import games.negative.framework.gui.GUI;
import games.negative.waypoint.api.WaypointHandler;
import games.negative.waypoint.api.WaypointManager;
import games.negative.waypoint.api.model.Waypoint;
import games.negative.waypoint.api.model.WaypointProfile;
import games.negative.waypoint.api.model.builder.WaypointBuilder;
import games.negative.waypoint.core.Item;
import games.negative.waypoint.ui.creator.WaypointCreatorMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class WaypointMenu extends GUI {
    public WaypointMenu(WaypointProfile profile, WaypointManager manager, WaypointHandler handler, int page) {
        super("My Waypoints", 6);

        List<Integer> fillerSlots = Lists.newArrayList(
                0, 1, 2, 3, 4, 5, 6, 7, 8,
                9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53
        );
        ItemStack blackFillerItem = Item.BLACK_FILLER.getItem();

        fillerSlots.forEach(index -> setItem(index, player -> blackFillerItem));

        ItemStack addWaypoint = Item.ADD_WAYPOINT.getItem();
        setItemClickEvent(49, player -> addWaypoint, (player, event) -> {
            WaypointBuilder builder = new WaypointBuilder();
            Location location = player.getLocation();
            builder.location(location);

            new WaypointCreatorMenu(manager, builder).open(player);
        });

        List<Waypoint> waypoints = profile.getWaypoints();
        int limit = Math.max((6 * 9) - fillerSlots.size(), 0);
        waypoints.stream().skip((long) (page - 1) * limit).limit(limit).forEach(waypoint -> {
            Material waypointIcon = waypoint.getIcon();
            final Material icon = (waypointIcon == null ? Material.GRASS_BLOCK : waypointIcon);

            String key = waypoint.getKey();

            ItemStack logo = ItemBuilder.newItemBuilder(icon).setName("&e" + key).build();
            addItemClickEvent(player -> logo, (player, event) -> {
                player.sendMessage("click waypoint");
            });

        });

        if (waypoints.size() > page * limit) {
            ItemStack next = Item.NEXT_PAGE.getItem();
            setItemClickEvent(53, player -> next, (player, event) -> new WaypointMenu(profile, manager, handler, page + 1).open(player));
        }

        if (page > 1) {
            ItemStack previous = Item.PREVIOUS_PAGE.getItem();
            setItemClickEvent(45, player -> previous, (player, event) -> new WaypointMenu(profile, manager, handler, page - 1).open(player));
        }
    }
}
