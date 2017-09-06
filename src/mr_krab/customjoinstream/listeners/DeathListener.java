package mr_krab.customjoinstream.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener
	implements Listener {

	//Скрытие сообщения о смерти
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDeath(PlayerDeathEvent event) {
		event.setDeathMessage(null);
	}
}