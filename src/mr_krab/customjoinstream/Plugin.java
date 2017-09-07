package mr_krab.customjoinstream;

import mr_krab.customjoinstream.commands.CustomJoinStreamCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import mr_krab.customjoinstream.listeners.DeathListener;
import mr_krab.customjoinstream.listeners.JoinListener;
import mr_krab.customjoinstream.listeners.QuitListener;
import mr_krab.customjoinstream.utils.Locale;
import net.milkbowl.vault.chat.Chat;

public class Plugin
	extends JavaPlugin {

	private static Plugin instance;

	private Locale loc = new Locale(this);
	private CommandSender console = getServer().getConsoleSender();
	private Chat chat = null;

	//Включение плагина
	@Override
	public void onEnable() {
		if(getServer().getPluginManager().getPlugin("Vault") == null) { //Чат не будет без него работать
			console.sendMessage("§8[§3CustomJoinStream§8] §cVault not found, disabling...");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		instance = this;

		console.sendMessage("§8[§3CustomJoinStream§8] §aPlugin is now loading");
		saveDefaultConfig();
		loc.init();

		setupChat();

		//Регистрируем слушатели
		getServer().getPluginManager().registerEvents(new JoinListener(), this);
		getServer().getPluginManager().registerEvents(new QuitListener(), this);
		getServer().getPluginManager().registerEvents(new DeathListener(), this);

		//Регистрация команды
		getCommand("customjoinstream").setExecutor(new CustomJoinStreamCommand());

		console.sendMessage("§8[§3CustomJoinStream§8] §aPlugin is now enabled");
		console.sendMessage("§6Author §cMr_Krab");
		console.sendMessage("§eThank you for using and testing my plugins.");
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