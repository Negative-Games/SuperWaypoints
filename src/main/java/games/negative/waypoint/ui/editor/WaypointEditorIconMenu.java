package games.negative.waypoint.ui.editor;

import com.google.common.collect.Lists;
import games.negative.framework.gui.GUI;
import games.negative.waypoint.api.WaypointManager;
import games.negative.waypoint.api.model.Waypoint;
import games.negative.waypoint.core.Item;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class WaypointEditorIconMenu extends GUI {

    public WaypointEditorIconMenu(WaypointManager manager, Waypoint waypoint, int page) {
        super("Waypoint Editor - Icon", 6);

        List<Integer> fillerSlots = Lists.newArrayList(0, 1, 2,3, 4, 5, 6, 7, 8, 9,
                17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53);

        ItemStack filler = Item.BLACK_FILLER.getItem();
        fillerSlots.forEach(index -> setItem(index, player -> filler));

        Material[] values = Material.values();
        int limit = Math.max((6 * 9) - fillerSlots.size(), 0);

        Arrays.stream(values).filter(material -> !material.isAir() && (material.isBlock() || material.isItem())).skip((long) (page - 1) * limit).limit(limit).forEach(material -> {
            ItemStack item = new ItemStack(material);

            addItemClickEvent(player -> item, (player, event) -> {
                waypoint.setIcon(material);
                new WaypointEditorMenu(manager, waypoint).open(player);
            });
        });

        if (page > 1) {
            ItemStack previousPage = Item.PREVIOUS_PAGE.getItem();
            setItemClickEvent(45, player -> previousPage, (player, event) -> new WaypointEditorIconMenu(manager, waypoint, page - 1).open(player));
        }

        if (values.length > page * limit) {
            ItemStack nextPage = Item.NEXT_PAGE.getItem();
            setItemClickEvent(53, player -> nextPage, (player, event) -> new WaypointEditorIconMenu(manager, waypoint, page + 1).open(player));
        }

        ItemStack returnItem = Item.RETURN.getItem();
        setItemClickEvent(49, player -> returnItem, (player, event) -> new WaypointEditorMenu(manager, waypoint).open(player));
    }

}
