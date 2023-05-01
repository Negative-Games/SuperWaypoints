package games.negative.waypoint.core;

import games.negative.framework.base.itembuilder.ItemBuilder;
import games.negative.waypoint.SuperWaypoints;
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

        config.set("display-name", meta.getDisplayName());
        config.set("material", type.name());
        config.set("lore", meta.getLore());

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

        meta.setDisplayName(displayName);
        meta.setLore(lore);

        item.setItemMeta(meta);
    }

    public ItemStack getItem() {
        return item;
    }
}
