package mr_krab.customjoinstream.utils;

import mr_krab.customjoinstream.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Utils {

    public static String colorize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
    
    public static void playJoinEffect(Player player) {
        String group = Plugin.getInstance().getChat().getPrimaryGroup(player);
        if(!player.hasPermission("customjoinstream.effect")) {
            return;
        }
        for(Entity near : player.getNearbyEntities(Plugin.getInstance().getConfig().getDouble("Radius"),
                Plugin.getInstance().getConfig().getDouble("Radius"),
                Plugin.getInstance().getConfig().getDouble("Radius"))) {
            if(!(near instanceof Player)) continue;
            ((Player)near).playEffect(player.getLocation(),
                    Effect.valueOf(Plugin.getInstance().getConfig().getString("Groups." + group + ".Effects.Effect1")),
                    null);
            ((Player)near).playEffect(player.getLocation(),
                    Effect.valueOf(Plugin.getInstance().getConfig().getString("Groups." + group + ".Effects.Effect2")),
                    null);
        }
    }
    
    public static void playJoinSound(Player player) {
        String group = Plugin.getInstance().getChat().getPrimaryGroup(player);
        if(!player.hasPermission("customjoinstream.sound")) {
            return;
        }
        for(Entity near : player.getNearbyEntities(Plugin.getInstance().getConfig().getDouble("Radius"),
                Plugin.getInstance().getConfig().getDouble("Radius"),
                Plugin.getInstance().getConfig().getDouble("Radius"))) {
            if(!(near instanceof Player)) continue;
            ((Player)near).playSound(player.getLocation(),
                    Sound.valueOf(Plugin.getInstance().getConfig().getString("Groups." + group + ".Sounds.Sound1").toUpperCase()),
                    10, 100);
            ((Player)near).playSound(player.getLocation(),
                    Sound.valueOf(Plugin.getInstance().getConfig().getString("Groups." + group + ".Sounds.Sound2").toUpperCase()),
                    10, 100);
        }
    }
}
