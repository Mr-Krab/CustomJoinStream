package mr_krab.customjoinstream.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import mr_krab.customjoinstream.Plugin;

public class Quit implements Listener {
	private Plugin instance;
	public Quit(Plugin plugin){
	      instance = plugin;
	}
	// Скрытие стандартного сообщения о выходе игрока
		@EventHandler(priority = EventPriority.HIGHEST)
	    public boolean QuitUpdate(PlayerQuitEvent qe) {
	        qe.setQuitMessage(null); {
	        // Проверка на наличие пермишена и вывод своего сообщения
	        Player p = qe.getPlayer();
	        String group = Plugin.c.getPrimaryGroup(p);
	        String prefix = Plugin.c.getGroupPrefix(p.getWorld(), group); 
	        String suffix = Plugin.c.getGroupSuffix(p.getWorld(), group); 
			if(!p.hasPermission("customjoinstream.stream")) {
				} else {
						qe.setQuitMessage(instance.getConfig().getString("Messages.QuitMessage").replace("%prefix", prefix).replace("%nickname", qe.getPlayer().getName()).replace("%suffix", suffix).replace("&", "§"));
					return true;
				}
			return false;
	        }
		}
}
