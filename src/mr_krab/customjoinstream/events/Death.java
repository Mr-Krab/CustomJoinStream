package mr_krab.customjoinstream.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Death implements Listener  {
	
	
	// Скрытие стандартного сообщения о смерти игрока
		@EventHandler(priority = EventPriority.HIGHEST)
		public boolean DeathNull(PlayerDeathEvent de) {
			de.setDeathMessage(null);
			return false;
			}
}
