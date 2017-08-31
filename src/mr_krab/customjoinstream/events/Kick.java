package mr_krab.customjoinstream.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class Kick implements Listener {

	// Скрытие стандартного сообщения о кике игрока
	@EventHandler(priority = EventPriority.HIGHEST)
	public void KickNull(PlayerKickEvent ke) {
		ke.setLeaveMessage(null);
		}

}
