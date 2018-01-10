package mr_krab.customjoinstream.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import mr_krab.customjoinstream.Plugin;
import mr_krab.customjoinstream.utils.EventUtils;

public class Quit implements Listener {
	
	// Скрытие стандартного сообщения о выходе игрока
		@EventHandler(priority = EventPriority.HIGHEST)
	    public boolean QuitUpdate(PlayerQuitEvent event) {
			Player player = event.getPlayer();
			EventUtils.playerTpJoinLoc(player);
			String prefix = Plugin.getInstance().getChat().getPlayerPrefix(player);
			String suffix = Plugin.getInstance().getChat().getPlayerSuffix(player);
			if(!player.hasPermission("customjoinstream.stream")) {
				event.setQuitMessage(null);
				return false;
			}
			event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', Plugin.getInstance().getConfig().getString("General.Messages.QuitMessage").replace("%prefix", prefix).replace("%nickname", event.getPlayer().getName()).replace("%suffix", suffix)));
			return true;
		}
}
