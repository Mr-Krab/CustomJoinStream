package mr_krab.customjoinstream;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin implements Listener {
	public static Plugin instance;
	FileConfiguration config = getConfig();
    public void register() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }
	PluginManager pm = getServer().getPluginManager();
	private static final Logger mclog = Logger.getLogger("minecraft");
	// Включение плагина
	public void onEnable() {
		saveDefaultConfig();
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		mclog.info("Плагин [CustomJoinStream] активирован");
		mclog.info("Автор Mr_Krab");
		mclog.info("Спасибо за использование и тестирование моих плагинов");
	}
	// Выключение плагина
	public void onDisable() {
		mclog.info("Плагин [CustomJoinStream] отключается");
	}
	// Скрытие стандартного сообщения о входе игрока
	@EventHandler(priority = EventPriority.MONITOR)
    public boolean JoinUpdate(PlayerJoinEvent je) {
        je.setJoinMessage(null); {
        // Проверка на наличие пермишена и вывод своего сообщения
        Player p = je.getPlayer();
		if(!p.hasPermission("customjoinstream.stream")) {
			} else {
					je.setJoinMessage(getConfig().getString("JoinMessage").replace("%p", je.getPlayer().getName()).replace("&", "§"));
				return true;
			}
		return false;
        }
	}
	// Скрытие стандартного сообщения о выходе игрока
	@EventHandler(priority = EventPriority.MONITOR)
    public boolean QuitUpdate(PlayerQuitEvent je) {
        je.setQuitMessage(null); {
        // Проверка на наличие пермишена и вывод своего сообщения
        Player p = je.getPlayer();
		if(!p.hasPermission("customjoinstream.stream")) {
			} else {
					je.setQuitMessage(getConfig().getString("QuitMessage").replace("%p", je.getPlayer().getName()).replace("&", "§"));
				return true;
			}
		return false;
        }
	}
	// Скрытие стандартного сообщения о смерти игрока
		@EventHandler(priority = EventPriority.MONITOR)
	    public void DeathNull(PlayerDeathEvent de) {
	        de.setDeathMessage(null);
		}
	// Скрытие стандартного сообщения о кике игрока
		@EventHandler(priority = EventPriority.MONITOR)
		public void KickNull(PlayerKickEvent ke) {
		    ke.setLeaveMessage(null);
			}
}