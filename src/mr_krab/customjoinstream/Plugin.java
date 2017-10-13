package mr_krab.customjoinstream;

import java.io.File;
import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


import mr_krab.customjoinstream.events.Death;
import mr_krab.customjoinstream.events.Join;
import mr_krab.customjoinstream.events.Kick;
import mr_krab.customjoinstream.events.Quit;
import mr_krab.customjoinstream.utils.CmdExecutor;
import mr_krab.customjoinstream.utils.CommandRegister;
import mr_krab.customjoinstream.utils.LocManager;
import mr_krab.customjoinstream.utils.Locale;
import net.milkbowl.vault.chat.Chat;

public class Plugin extends JavaPlugin {

	private static Plugin instance;
	
	// Инициализация локализации
	public Locale loc = new Locale(this);
	

	public LocManager locm = new LocManager();

	public CommandSender console = getServer().getConsoleSender();
	
	private Chat chat = null;

	public File file = new File(getDataFolder() + File.separator + "locations.yml");
	public FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);

	//Включение плагина
	@Override
	public void onEnable() {
		console.sendMessage("§8[§3CustomJoinStream§8] §eLoading plugin...");
		console.sendMessage("§8[§3CustomJoinStream§8] §eChecking for the presence of a plugin Vault...");
		// Проверка на наличие Vault
		if(getServer().getPluginManager().getPlugin("Vault") == null) { 
			console.sendMessage("§8[§3CustomJoinStream§8] §cVault not found, disabling...");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		console.sendMessage("§8[§3CustomJoinStream§8] §aVault is found, continue the load plugin");
		console.sendMessage("§8[§3CustomJoinStream§8] §eLoading and enabling components...");

		instance = this;

		saveDefaultConfig();
		loc.init();
		
		setupChat();

		//Регистрируем слушатели
		getServer().getPluginManager().registerEvents(new Join(), this);
		getServer().getPluginManager().registerEvents(new Quit(), this);
		getServer().getPluginManager().registerEvents(new Death(), this);
		getServer().getPluginManager().registerEvents(new Kick(), this);

		//Регистрация команды

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
		console.sendMessage("§8[§3CustomJoinStream§8] §aPlugin is now enabled");
		console.sendMessage("§6Author: §cMr_Krab");
		console.sendMessage("§9Thank you for using and testing my plugins.");
	}

	//Выключение плагина
	@Override
	public void onDisable() {
		instance = null;

		console.sendMessage("§8[§3CustomJoinStream§8] §cPlugin is now disabled");

		loc = null;
		console = null;
		chat = null;
	}

	public Locale getLocale() {
		return loc;
	}
	
	public LocManager getLocManager() {
		return locm;
	}

	public Chat getChat() {
		return chat;
	}

	private void setupChat() {
		RegisteredServiceProvider<Chat> cp = getServer().getServicesManager().getRegistration(Chat.class);
		if(cp == null) return;
		chat = cp.getProvider();
	}

	public static Plugin getInstance() {
		return instance;
	}
} 