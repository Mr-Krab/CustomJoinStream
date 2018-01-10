package mr_krab.customjoinstream.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import mr_krab.customjoinstream.Plugin;
import mr_krab.customjoinstream.utils.EventUtils;

public class Join implements Listener {
	

	// Скрытие стандартного сообщения о входе игрока
	@EventHandler(priority = EventPriority.HIGHEST)
		public void onJoin(PlayerJoinEvent event) {
			Player player = event.getPlayer();
			EventUtils.playerHide(player);
			EventUtils.playerHideOther(player);
			EventUtils.hideAllPlayers(player);
			EventUtils.playJoinSound(player);
			EventUtils.playJoinEffect(player);
			EventUtils.playerTpJoinLoc(player);
			EventUtils.flyPlayerJoin(player);
			String prefix = Plugin.getInstance().getChat().getPlayerPrefix(player);
			String suffix = Plugin.getInstance().getChat().getPlayerSuffix(player);
			if(!player.hasPermission("customjoinstream.stream")) {
				event.setJoinMessage(null);
				return;
			}
			event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', Plugin.getInstance().getConfig().getString("General.Messages.JoinMessage").replace("%prefix", prefix).replace("%nickname", event.getPlayer().getName()).replace("%suffix", suffix)));
		}
}