package mr_krab.customjoinstream;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
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
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.chat.Chat;

public class Plugin extends JavaPlugin implements Listener {
	public static Plugin instance;
	FileConfiguration config = getConfig();
	PluginManager pm = getServer().getPluginManager();
	private static final Logger mclog = Logger.getLogger("minecraft");
	public static Chat c = null;
	private boolean setupChat() {
	    RegisteredServiceProvider<Chat> cp = getServer().getServicesManager().getRegistration(Chat.class);
	    if (cp != null) {
	      c = (Chat)cp.getProvider();
	    }
	    return c != null;
	}
	// Включение плагина
	public void onEnable() {
		saveDefaultConfig();
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		/* Активируем рефлексию Java для регистрации команд
		 * Регистрация производится через класс CommandRegister(не изменять его)
		 * Сами команды прописывать как обычно в CmdExecutor
		 */
		try {
            CmdExecutor cmd = new CmdExecutor(this);
            CommandRegister reg = new CommandRegister(new String[]{"customjoinstream", "cjs"}, "Используйте", "customjoinstream или cjs", cmd, new Object(), this);
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            CommandMap map = (CommandMap)field.get(Bukkit.getServer());
            map.register(this.getDescription().getName(), reg);
        } catch (Exception e) {
            e.printStackTrace();
        }
	    setupChat();
		mclog.info("Плагин [CustomJoinStream] активирован");
		mclog.info("Автор Mr_Krab");
		mclog.info("Спасибо за использование и тестирование моих плагинов");
	}
	// Выключение плагина
	public void onDisable() {
		mclog.info("Плагин [CustomJoinStream] отключается");
	}
	// Скрытие стандартного сообщения о входе игрока
	@EventHandler(priority = EventPriority.HIGHEST)
    public boolean JoinUpdate(PlayerJoinEvent je) {
        je.setJoinMessage(null); {
        // Проверка на наличие пермишена и вывод своего сообщения
        Player p = je.getPlayer();
        String group = Plugin.c.getPrimaryGroup(p);
        String prefix = Plugin.c.getGroupPrefix(p.getWorld(), group); 
		if(!p.hasPermission("customjoinstream.stream")) {
			} else {
					je.setJoinMessage(getConfig().getString("Messages.JoinMessage").replace("%prefix", prefix).replace("%nickname", je.getPlayer().getName()).replace("&", "§"));
				return true;
			}
		return false;
        }
	}
	// Скрытие стандартного сообщения о выходе игрока
	@EventHandler(priority = EventPriority.HIGHEST)
    public boolean QuitUpdate(PlayerQuitEvent qe) {
        qe.setQuitMessage(null); {
        // Проверка на наличие пермишена и вывод своего сообщения
        Player p = qe.getPlayer();
        String group = Plugin.c.getPrimaryGroup(p);
        String prefix = Plugin.c.getGroupPrefix(p.getWorld(), group); 
		if(!p.hasPermission("customjoinstream.stream")) {
			} else {
					qe.setQuitMessage(getConfig().getString("Messages.QuitMessage").replace("%prefix", prefix).replace("%nickname", qe.getPlayer().getName()).replace("&", "§"));
				return true;
			}
		return false;
        }
	}
	// Скрытие стандартного сообщения о смерти игрока
		@EventHandler(priority = EventPriority.HIGHEST)
	    public void DeathNull(PlayerDeathEvent de) {
	        de.setDeathMessage(null);
		}
	// Скрытие стандартного сообщения о кике игрока
		@EventHandler(priority = EventPriority.HIGHEST)
		public void KickNull(PlayerKickEvent ke) {
		    ke.setLeaveMessage(null);
			}
}