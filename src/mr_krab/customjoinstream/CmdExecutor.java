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
						plugin.loc.init();
						sender.sendMessage(plugin.loc.getString("Prefix") + plugin.loc.getString("Reload"));
						return true;
					}
					/* В ТУДУ лист
					 * Добавить команды
					 * Улучшить работу пермов, сделать наследование
					 */
                  }  
				sender.sendMessage(plugin.loc.getString("NoPermissions"));
		}
				if(sender.hasPermission("customjoinstream.admin")){
					sender.sendMessage(plugin.loc.getString("Prefix"));
					sender.sendMessage(plugin.loc.getString("HelpLine1"));
					sender.sendMessage(plugin.loc.getString("HelpLine2"));
				} else {
					sender.sendMessage(plugin.loc.getString("NoPermissions"));
			}
	return false;
	}
	
}