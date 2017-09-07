package mr_krab.customjoinstream.utils;

import mr_krab.customjoinstream.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static String colorize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static void playJoinEffect(Player player) {
        if(!player.hasPermission("customjoinstream.effect")) {
            return;
        }
        String group = Plugin.getInstance().getChat().getPrimaryGroup(player);
        getNearbyPlayers(player).forEach(near -> {
            near.playEffect(player.getLocation(),
                    Effect.valueOf(Plugin.getInstance().getConfig().getString("Groups." + group + ".Effects.Effect1")),
                    null);
            near.playEffect(player.getLocation(),
                    Effect.valueOf(Plugin.getInstance().getConfig().getString("Groups." + group + ".Effects.Effect2")),
                    null);
        });
    }

    public static void playJoinSound(Player player) {
        if(!player.hasPermission("customjoinstream.sound")) {
            return;
        }
        String group = Plugin.getInstance().getChat().getPrimaryGroup(player);
        getNearbyPlayers(player).forEach(near -> {
            near.playSound(player.getLocation(),
                    Sound.valueOf(Plugin.getInstance().getConfig().getString("Groups." + group + ".Sounds.Sound1").toUpperCase()),
                    10, 100);
            near.playSound(player.getLocation(),
                    Sound.valueOf(Plugin.getInstance().getConfig().getString("Groups." + group + ".Sounds.Sound2").toUpperCase()),
                    10, 100);
        });
    }

    private static List<Player> getNearbyPlayers(Player player) {
        return player.getNearbyEntities(Plugin.getInstance().getConfig().getDouble("Radius"),
                Plugin.getInstance().getConfig().getDouble("Radius"),
                Plugin.getInstance().getConfig().getDouble("Radius")).stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player)entity).collect(Collectors.toList());
    }
}

