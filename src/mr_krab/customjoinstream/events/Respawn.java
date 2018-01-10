package mr_krab.customjoinstream.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import mr_krab.customjoinstream.Plugin;

public class Respawn implements Listener {
	
	Plugin plugin;
	
	// Телепорт на спавн при возрождении
	@SuppressWarnings("static-access")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void re(PlayerRespawnEvent re) {
		Player player = re.getPlayer();
        String group = plugin.getInstance().getChat().getPrimaryGroup(player);
		if (plugin.getInstance().getConfig().getBoolean("Groups." + group + ".RespawnTP") || plugin.getInstance().getConfig().getBoolean("General.RespawnTP")) {
			re.setRespawnLocation(plugin.getInstance().locm.TpSpawn(player.getName()));
		} 
	}
}
