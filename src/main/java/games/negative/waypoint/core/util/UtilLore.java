package games.negative.waypoint.core.util;

import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@UtilityClass
public class UtilLore {

    public void replaceLore(@NotNull ItemStack item, @NotNull String placeholder, @NotNull String replacement) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        List<String> lore = meta.getLore();
        if (lore == null) return;

        lore.replaceAll(s -> s.replaceAll(placeholder, replacement));

        meta.setLore(lore);
        item.setItemMeta(meta);
    }
}
