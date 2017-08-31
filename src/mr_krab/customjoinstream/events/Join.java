package mr_krab.customjoinstream.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import mr_krab.customjoinstream.Plugin;

public class Join implements Listener {
	private Plugin instance;
	public Join(Plugin plugin){
	      instance = plugin;
	}
	
	// Скрытие стандартного сообщения о входе игрока
	@EventHandler(priority = EventPriority.HIGHEST)
    public boolean JoinUpdate(PlayerJoinEvent je) {
        je.setJoinMessage(null); {
        // Проверка на наличие пермишена и вывод своего сообщения
        Player p = je.getPlayer();
        String group = Plugin.c.getPrimaryGroup(p);
        String prefix = Plugin.c.getGroupPrefix(p.getWorld(), group); 
        String suffix = Plugin.c.getGroupSuffix(p.getWorld(), group); 
		if(!p.hasPermission("customjoinstream.stream")) {
			} else {
					je.setJoinMessage(instance.getConfig().getString("Messages.JoinMessage").replace("%prefix", prefix).replace("%nickname", je.getPlayer().getName()).replace("%suffix", suffix).replace("&", "§"));
				return true;
			}
		return false;
        }
	}	
}
