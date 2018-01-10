package mr_krab.customjoinstream;

import java.io.File;
import java.io.IOException;
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
import mr_krab.customjoinstream.events.Respawn;
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
		saveDefaultConfig();
		loc.init();
		console.sendMessage("§7[§3CustomJoinStream§7] " + loc.getString("Loading"));
		// Проверка на наличие Vault
		console.sendMessage("§7[§3CustomJoinStream§7] " + loc.getString("CheckVault"));
		if(getServer().getPluginManager().getPlugin("Vault") == null) { 
			console.sendMessage("§7[§3CustomJoinStream§7] " + loc.getString("VaultNotFound"));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		console.sendMessage("§7[§3CustomJoinStream§7] " + loc.getString("VaultIsFound"));
		console.sendMessage("§7[§3CustomJoinStream§7] " + loc.getString("Loading2"));

		instance = this;

		saveDefaultConfig();
		
		setupChat();

		//Регистрируем слушатели
		getServer().getPluginManager().registerEvents(new Join(), this);
		getServer().getPluginManager().registerEvents(new Quit(), this);
		getServer().getPluginManager().registerEvents(new Death(), this);
		getServer().getPluginManager().registerEvents(new Kick(), this);
		getServer().getPluginManager().registerEvents(new Respawn(), this);

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
		console.sendMessage("§7[§3CustomJoinStream§7] " + loc.getString("EnableSuccess1")  + this.getDescription().getVersion());
		console.sendMessage("§7[§3CustomJoinStream§7] " + loc.getString("Author") + "§cMr_Krab");
		console.sendMessage("§7[§3CustomJoinStream§7] " + loc.getString("EnableSuccess2"));
		// Проверка версии конфига
		checkConfigVersion();
	}

	//Выключение плагина
	@Override
	public void onDisable() {
		instance = null;

		console.sendMessage("§7[§3CustomJoinStream§7] " + loc.getString("Disable"));

		loc = null;
		console = null;
		chat = null;
	}
	
	public void checkConfigVersion(){
		if (!getConfig().isSet("ConfigVersion")) {
			console.sendMessage("§7[§3CustomJoinStream§7] " + loc.getString("ConfigWarn1"));
			console.sendMessage(loc.getString("ConfigWarn2"));
			try {
				getConfig().save(getDataFolder() + File.separator + "OldConfig.yml");
				saveResource("config.yml", true);
				reloadConfig();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if ((getConfig().getInt("ConfigVersion")) != 1) {
			console.sendMessage("§7[§3CustomJoinStream§7] " + loc.getString("ConfigWarn1"));
			console.sendMessage(loc.getString("ConfigWarn2"));
			try {
				getConfig().save(getDataFolder() + File.separator + "OldConfig.yml");
				saveResource("config.yml", true);
				reloadConfig();
			} catch (IOException e) {
				e.printStackTrace();
			}
			saveDefaultConfig();
		}
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