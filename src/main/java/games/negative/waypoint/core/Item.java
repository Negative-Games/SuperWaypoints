package games.negative.waypoint.core;

import games.negative.framework.base.itembuilder.ItemBuilder;
import games.negative.framework.util.Utils;
import games.negative.waypoint.SuperWaypoints;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

public enum Item {

    BLACK_FILLER(ItemBuilder.newItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build()),

    NEXT_PAGE(ItemBuilder.newItemBuilder(Material.ARROW).setName("&aNext Page").build()),
    PREVIOUS_PAGE(ItemBuilder.newItemBuilder(Material.ARROW).setName("&cPrevious Page").build()),

    ADD_WAYPOINT(ItemBuilder.newItemBuilder(Material.ANVIL).setName("&aAdd Waypoint").build()),

    SET_NAME(ItemBuilder.newItemBuilder(Material.NAME_TAG).setName("&aSet Name").setLore(
            "&7Current Name: &f%name%"
    ).build()),

    SET_LOCATION(ItemBuilder.newItemBuilder(Material.IRON_BARS).setName("&aSet Location").setLore(
            "&7Current World: &f%world%",
            "&7Current X: &f%x%",
            "&7Current Y: &f%y%",
            "&7Current Z: &f%z%"
    ).build()),

    SET_ICON(ItemBuilder.newItemBuilder(Material.OAK_SIGN).setName("&aSet Icon").setLore(
            "&7Current Icon: &f%icon%"
    ).build()),

    GREEN_CONFIRM(ItemBuilder.newItemBuilder(Material.LIME_STAINED_GLASS_PANE).setName("&a&lCONFIRM").build()),

    WORLDS(ItemBuilder.newItemBuilder(Material.GRASS_BLOCK).setName("&aWorlds").build()),

    X_COORDINATE(ItemBuilder.newItemBuilder(Material.COMPASS).setName("&aX Coordinate").setLore(
            "&7Current X Coordinate: &f%x%"
    ).build()),

    Y_COORDINATE(ItemBuilder.newItemBuilder(Material.COMPASS).setName("&aY Coordinate").setLore(
            "&7Current Y Coordinate: &f%y%"
    ).build()),

    Z_COORDINATE(ItemBuilder.newItemBuilder(Material.COMPASS).setName("&aZ Coordinate").setLore(
            "&7Current Z Coordinate: &f%z%"
    ).build()),

    RETURN(ItemBuilder.newItemBuilder(Material.BOOK).setName("&c&lReturn").build()),
    ;

    private final ItemStack item;

    Item(@NotNull ItemStack item) {
        this.item = item;
    }

    public static void init(@NotNull SuperWaypoints plugin) {
        File main = plugin.getDataFolder();
        if (!main.exists()) {
            Log.INFO.message("Creating plugin folder");
            main.mkdir();
        }

        File items = new File(main, "menu_items");
        if (!items.exists()) {
            Log.INFO.message("Creating menu items folder");
            items.mkdir();
        }

        for (Item entry : values()) {
            File file = new File(items, entry.name().toLowerCase() + ".yml");
            if (!file.exists()) {
                Log.INFO.message("Creating menu item file for " + entry.name());
                entry.save(plugin);
            } else {
                Log.INFO.message("Loading menu item file for " + entry.name());
                entry.load(plugin);
            }
        }
    }

    private void save(SuperWaypoints plugin) {
        File main = plugin.getDataFolder();
        if (!main.exists()) {
            Log.ERROR.message("Plugin folder does not exist");
            return;
        }

        File items = new File(main, "menu_items");
        if (!items.exists()) {
            Log.ERROR.message("Menu items folder does not exist");
            return;
        }

        File file = new File(items, name().toLowerCase() + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.ERROR.message("Could not create menu item file for " + name());
                e.printStackTrace();
                return;
            }
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        Material type = item.getType();
        ItemMeta meta = item.getItemMeta();
        assert meta != null;

        String displayName = meta.getDisplayName().replace("§", "&");

        config.set("display-name", displayName);
        config.set("material", type.name());

        List<String> lore = meta.getLore();
        if (lore != null) lore.replaceAll(s -> s.replace("§", "&"));

        config.set("lore", lore);

        try {
            config.save(file);
            Log.INFO.message("Saved menu item file for " + name());
        } catch (IOException e) {
            Log.ERROR.message("Could not save menu item file for " + name());
            e.printStackTrace();
        }
    }

    private void load(SuperWaypoints plugin) {
        File main = plugin.getDataFolder();
        if (!main.exists()) {
            Log.ERROR.message("Plugin folder does not exist");
            return;
        }

        File items = new File(main, "menu_items");
        if (!items.exists()) {
            Log.ERROR.message("Menu items folder does not exist");
            return;
        }

        File file = new File(items, name().toLowerCase() + ".yml");
        if (!file.exists()) {
            Log.ERROR.message("Menu item file for " + name() + " does not exist");
            return;
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        String displayName = config.getString("display-name");
        String material = config.getString("material");
        List<String> lore = config.getStringList("lore");

        item.setType(Material.valueOf(material));
        ItemMeta meta = item.getItemMeta();
        assert meta != null;

        meta.setDisplayName(displayName == null ? null : Utils.color(displayName));
        meta.setLore(Utils.color(lore));

        item.setItemMeta(meta);
    }

    public ItemStack getItem() {
        return item;
    }
}
