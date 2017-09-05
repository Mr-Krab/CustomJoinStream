package mr_krab.customjoinstream;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import mr_krab.customjoinstream.events.Death;
import mr_krab.customjoinstream.events.Join;
import mr_krab.customjoinstream.events.Kick;
import mr_krab.customjoinstream.events.Quit;
import mr_krab.customjoinstream.utils.CmdExecutor;
import mr_krab.customjoinstream.utils.CommandRegister;
import mr_krab.customjoinstream.utils.Locale;
import net.milkbowl.vault.chat.Chat;

public class Plugin extends JavaPlugin implements Listener {
	public Locale loc = new Locale(this); {
	loc.init();
	}
	public static Plugin instance;
	public FileConfiguration config = getConfig();
	PluginManager pm = getServer().getPluginManager();
	public ConsoleCommandSender console = getServer().getConsoleSender();
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
	    console.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "CustomJoinStream" + ChatColor.DARK_GRAY + "] " + ChatColor.GREEN + "Plugin is loading");
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(new Join(this), this);
		getServer().getPluginManager().registerEvents(new Quit(this), this);
		getServer().getPluginManager().registerEvents(new Kick(), this);
		getServer().getPluginManager().registerEvents(new Death(), this);
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
	    console.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "CustomJoinStream" + ChatColor.DARK_GRAY + "] " + ChatColor.GREEN + "Plugin is now enabled");
	    console.sendMessage(ChatColor.GOLD + "Author " + ChatColor.RED + "Mr_Krab");
	    console.sendMessage(ChatColor.YELLOW + "Thank you for using and testing my plugins.");
	}
	// Выключение плагина
	public void onDisable() {
		console.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "CustomJoinStream" + ChatColor.DARK_GRAY + "] " + ChatColor.DARK_RED + "Plugin is now disabled");
	}
}