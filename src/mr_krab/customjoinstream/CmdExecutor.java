package mr_krab.customjoinstream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class CmdExecutor implements CommandExecutor {

	Plugin plugin;
	public CmdExecutor(Plugin p) {
		plugin = p;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
				if (cmd.getName().equalsIgnoreCase("customjoinstream") && args.length >=1) {
				if (args[0].equalsIgnoreCase("reload")) {
					if(sender.hasPermission("customjoinstream.reload")){
						plugin.reloadConfig();
						sender.sendMessage("Конфиг перезагружен");
						return true;
					}
					/* В ТУДУ лист
					 * Добавить команды
					 * Изменить сообщения на выводимые из конфига
					 */
                  }  
			sender.sendMessage("Недостаточно прав customjoinstream.reload");
		}
				if(sender.hasPermission("customjoinstream.admin")){
				sender.sendMessage("-----=====[Справка По плагину CustomJoinStream]=====-----");
				sender.sendMessage("customjoinstream - Основная команда. Далее cjs");
				sender.sendMessage("cjs reload - Перезагрузка плагина");
				sender.sendMessage("-----=====+++++++++***+++***+++***+++++++++++=====-----");
				} else {
					sender.sendMessage("Недостаточно прав customjoinstream.admin");
			}
	return false;
	}
	
}