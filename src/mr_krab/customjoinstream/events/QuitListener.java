package mr_krab.customjoinstream.events;

import mr_krab.customjoinstream.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import mr_krab.customjoinstream.Plugin;

public class QuitListener
	implements Listener {

	//Скрытие сообщения о выходе игрока
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		String prefix = Plugin.getInstance().getChat().getPlayerPrefix(player);
		String suffix = Plugin.getInstance().getChat().getPlayerSuffix(player);
		if(!player.hasPermission("customjoinstream.stream")) {
			event.setQuitMessage(null);
			return;
		}
		event.setQuitMessage(Plugin.getInstance().getConfig().getString("Messages.QuitMessage")
				.replace("%nickname", player.getName())
				.replace("%prefix", Utils.colorize(prefix))
				.replace("%suffix", Utils.colorize(suffix)));
	}
}
