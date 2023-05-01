package games.negative.waypoint.ui.creator;

import com.google.common.collect.Lists;
import games.negative.framework.gui.GUI;
import games.negative.waypoint.api.WaypointManager;
import games.negative.waypoint.api.model.Waypoint;
import games.negative.waypoint.core.Item;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class WaypointCreatorIconMenu extends GUI {
    public WaypointCreatorIconMenu(WaypointManager manager, Waypoint.Builder builder, int page) {
        super("Waypoint Creator - Icon", 6);

        List<Integer> fillerSlots = Lists.newArrayList(0, 1, 2,3, 4, 5, 6, 7, 8, 9,
        17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53);

        ItemStack filler = Item.BLACK_FILLER.getItem();
        fillerSlots.forEach(index -> setItem(index, player -> filler));

        Material[] values = Material.values();
        int limit = (6 * 9) - values.length;

        /*
        sending stacktrace in console, will fix later
        Arrays.stream(values).filter(material -> !material.name().contains("AIR") && (material.isItem() || material.isBlock())).skip((long) (page - 1) * limit).limit(limit).forEach(material -> {
            ItemStack item = new ItemStack(material);

            addItemClickEvent(player -> item, (player, event) -> {
                builder.icon(material);
                new WaypointCreatorMenu(manager, builder).open(player);
            });
        });

         */

    }
}
