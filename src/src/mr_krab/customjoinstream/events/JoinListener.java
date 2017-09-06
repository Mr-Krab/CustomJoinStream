package mr_krab.customjoinstream.events;

import mr_krab.customjoinstream.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import mr_krab.customjoinstream.Plugin;

public class JoinListener
	implements Listener {
	
	//Скрытие сообщения о входе игрока
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Utils.playJoinSound(player);
		Utils.playJoinEffect(player);
		String prefix = Plugin.getInstance().getChat().getPlayerPrefix(player);
		String suffix = Plugin.getInstance().getChat().getPlayerSuffix(player);
		if(!player.hasPermission("customjoinstream.stream")) {
			event.setJoinMessage(null);
			return;
		}
		event.setJoinMessage(Plugin.getInstance().getConfig().getString("Messages.QuitMessage")
				.replace("%nickname", player.getName())
				.replace("%prefix", Utils.colorize(prefix))
				.replace("%suffix", Utils.colorize(suffix)));
	}
}
