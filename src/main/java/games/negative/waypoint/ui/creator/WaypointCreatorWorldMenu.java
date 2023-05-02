package games.negative.waypoint.ui.creator;

import com.google.common.collect.Lists;
import games.negative.framework.base.itembuilder.ItemBuilder;
import games.negative.framework.gui.GUI;
import games.negative.waypoint.api.WaypointManager;
import games.negative.waypoint.api.model.Waypoint;
import games.negative.waypoint.core.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WaypointCreatorWorldMenu extends GUI {
    public WaypointCreatorWorldMenu(@NotNull WaypointManager manager, @NotNull Waypoint.Builder builder, int page) {
        super("Waypoint Creator - Location", 5);

        List<Integer> fillerSlots = Lists.newArrayList(
                0, 1, 2, 3, 4, 5, 6, 7, 8,
                9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44
        );
        ItemStack blackFillerItem = Item.BLACK_FILLER.getItem();

        fillerSlots.forEach(index -> setItem(index, player -> blackFillerItem));

        List<World> worlds = Bukkit.getWorlds();
        int limit = Math.max((5 * 9) - fillerSlots.size(), 0);
        worlds.stream().skip((long) (page - 1) * limit).limit(limit).forEach(world -> {

            ItemStack item = ItemBuilder.newItemBuilder(Material.GRASS_BLOCK).setName("&e" + world.getName()).build();

            addItemClickEvent(player -> item, (player, event) -> {
                builder.world(world.getName());
                new WaypointCreatorLocationMenu(manager, builder).open(player);
            });

        });

        if (page > 1) {
            ItemStack previousPage = Item.PREVIOUS_PAGE.getItem();
            setItemClickEvent(35, player -> previousPage, (player, event) -> new WaypointCreatorWorldMenu(manager, builder, page - 1).open(player));
        }

        if (worlds.size() > page * limit) {
            ItemStack nextPage = Item.NEXT_PAGE.getItem();
            setItemClickEvent(44, player -> nextPage, (player, event) -> new WaypointCreatorWorldMenu(manager, builder, page + 1).open(player));
        }

        ItemStack returnItem = Item.RETURN.getItem();
        setItemClickEvent(40, player -> returnItem, (player, event) -> new WaypointCreatorMenu(manager, builder).open(player));

    }
}
