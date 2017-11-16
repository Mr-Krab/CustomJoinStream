package mr_krab.customjoinstream.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import mr_krab.customjoinstream.Plugin;

public class EventUtils {
	
	static Plugin instance;
	public EventUtils(Plugin plugin) {
		instance = plugin;
	}
		// Воспроизведение звука из конфига
	public static void playJoinSound(Player player) {
		 if(!player.hasPermission("customjoinstream.sound")) {
	            return;
	        }
	        String group = Plugin.getInstance().getChat().getPrimaryGroup(player);
	        if(Plugin.getInstance().getConfig().get("Groups." + group) !=null) {
            player.playSound(player.getLocation(), Sound.valueOf(Plugin.getInstance().getConfig().getString("Groups." + group + ".Sounds.Sound1").toUpperCase()), 10, 100);
            player.playSound(player.getLocation(), Sound.valueOf(Plugin.getInstance().getConfig().getString("Groups." + group + ".Sounds.Sound2").toUpperCase()), 10, 100);
            getSoundNearbyPlayers(player).forEach(near -> {
	            near.playSound(player.getLocation(),
	                    Sound.valueOf(Plugin.getInstance().getConfig().getString("Groups." + group + ".Sounds.Sound1").toUpperCase()), 10, 100);
	            near.playSound(player.getLocation(),
	                    Sound.valueOf(Plugin.getInstance().getConfig().getString("Groups." + group + ".Sounds.Sound2").toUpperCase()), 10, 100);
	        });
	        }
	    }


		// Воспроизведение эффекта из конфига
	public static void playJoinEffect(Player player) {
		if(!player.hasPermission("customjoinstream.effect")) {
            return;
        }
        String group = Plugin.getInstance().getChat().getPrimaryGroup(player);
        if(Plugin.getInstance().getConfig().get("Groups." + group) !=null) {
        player.playEffect(player.getLocation(), Effect.valueOf(Plugin.getInstance().getConfig().getString("Groups." + group + ".Effects.Effect1")), null);
        player.playEffect(player.getLocation(), Effect.valueOf(Plugin.getInstance().getConfig().getString("Groups." + group + ".Effects.Effect2")), null);
        getEffectNearbyPlayers(player).forEach(near -> {
            near.playEffect(player.getLocation(),
                    Effect.valueOf(Plugin.getInstance().getConfig().getString("Groups." + group + ".Effects.Effect1")), null);
            near.playEffect(player.getLocation(),
                    Effect.valueOf(Plugin.getInstance().getConfig().getString("Groups." + group + ".Effects.Effect2")), null);
        	});
        }
	}
		// Радиус действия звука из конфига
    private static List<Player> getSoundNearbyPlayers(Player player) {
        String group = Plugin.getInstance().getChat().getPrimaryGroup(player);
        return player.getNearbyEntities(Plugin.getInstance().getConfig().getDouble("Groups." + group + ".SuoundRadius"),
                Plugin.getInstance().getConfig().getDouble("Groups." + group + ".SuoundRadius"),
                Plugin.getInstance().getConfig().getDouble("Groups." + group + ".SuoundRadius")).stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player)entity).collect(Collectors.toList());
    }
		// Радиус действия эффекта из конфига
    private static List<Player> getEffectNearbyPlayers(Player player) {
        String group = Plugin.getInstance().getChat().getPrimaryGroup(player);
        return player.getNearbyEntities(Plugin.getInstance().getConfig().getDouble("Groups." + group + ".EffectRadius"),
                Plugin.getInstance().getConfig().getDouble("Groups." + group + ".EffectRadius"),
                Plugin.getInstance().getConfig().getDouble("Groups." + group + ".EffectRadius")).stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player)entity).collect(Collectors.toList());
    }
		// Телепортация игрока на стартовую позицию
	public static void playerTpJoinLoc (Player player) {
        String group = Plugin.getInstance().getChat().getPrimaryGroup(player);
		if(Plugin.getInstance().getConfig().getBoolean("Groups." + group + ".TpToStartLoc.Join")) {
				Location join = Plugin.getInstance().locm.TpJoinLoc(player.getName());
				player.teleport(join);
		} 
		if(Plugin.getInstance().getConfig().getBoolean("Groups." + group + ".TpToStartLoc.Quit")) {
			Location join = Plugin.getInstance().locm.TpJoinLoc(player.getName());
			player.teleport(join);
		}
	}
	
	// Скрытие игрока
	@SuppressWarnings("static-access")
	public static void playerHide (Player player) {
		if(player.hasPermission("customjoinstream.hide")) {
		Bukkit.getOnlinePlayers().forEach(p -> {
            if(!(p.equals(player))){
            	p.hidePlayer(player);
            	player.setPlayerListName(null);
	            player.setMetadata("hidden", new FixedMetadataValue(instance.getInstance(), true));
            	}
        });
		}
	}
	
	
	// Скрытие всех игроков
	public static void hideAllPlayers (Player player) {
		if(Plugin.getInstance().getConfig().getBoolean("HideAllPlayers")) {
		Bukkit.getOnlinePlayers().forEach(p -> {
            if(!(p.equals(player))){
            	p.hidePlayer(player);
            	player.hidePlayer(p);
            }
        });
		}
	}
	
	//Полет при входе
	@SuppressWarnings("static-access")
	public static void flyPlayerJoin (Player player) {
        String group = Plugin.getInstance().getChat().getPrimaryGroup(player);
		if(Plugin.getInstance().getConfig().getBoolean("Groups." + group + ".FlyJoin")) {
	        if(!player.hasMetadata("flying")) {
			player.setAllowFlight(true);
			player.setFlying(true);
            player.setMetadata("flying", new FixedMetadataValue(instance.getInstance(), true));
			} else {
				player.setAllowFlight(false);
				player.setFlying(false);
	            player.removeMetadata("flying", instance.getInstance());
			}
		} else {
			player.setAllowFlight(false);
			player.setFlying(false);
            player.removeMetadata("flying", instance.getInstance());
		}
	}
}