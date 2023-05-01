package games.negative.waypoint.core;

import com.google.common.collect.Lists;
import games.negative.framework.message.Message;
import games.negative.waypoint.SuperWaypoints;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Getter
public enum Locale {



    ;
    private final String id;
    private final List<String> defaultMessage;
    private Message message;

    Locale(String id, String... defaultMessage) {
        this(id, Lists.newArrayList(defaultMessage));
    }

    Locale(String id, List<String> defaultMessage) {
        this.id = id;
        this.defaultMessage = defaultMessage;
    }

    @SneakyThrows
    public static void init(SuperWaypoints plugin) {
        File configFile = new File(plugin.getDataFolder(), "messages.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        if (!configFile.exists()) {
            Arrays.stream(values()).forEach(locale -> {
                String id = locale.getId();
                List<String> defaultMessage = locale.getDefaultMessage();

                config.set(id, defaultMessage);
            });

        } else {
            Arrays.stream(values()).filter(locale -> {
                String id = locale.getId();
                return (config.get(id, null) == null);
            }).forEach(locale -> config.set(locale.getId(), locale.getDefaultMessage()));

        }
        config.save(configFile);

        // Creates the message objects
        Arrays.stream(values()).forEach(locale ->
                locale.message = new Message(config.getStringList(locale.getId())
                        .toArray(new String[0])));
    }

    public void send(CommandSender sender) {
        message.send(sender);
    }

    public void send(Iterable<CommandSender> players) {
        message.send(players);
    }

    public void broadcast() {
        message.broadcast();
    }

    public Message replace(String placeholder, String replacement) {
        return message.replace(placeholder, replacement);
    }

}
