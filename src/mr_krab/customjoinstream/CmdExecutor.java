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
		if(!sender.hasPermission("customjoinstream.reload")) {
			sender.sendMessage("Недостаточно прав");
			return true;
			}else{
				if (cmd.getName().equals("customjoinstream") && args.length >=1) {
				if (args[0].equalsIgnoreCase("reload")) {
					plugin.reloadConfig();
				}
				sender.sendMessage("Конфиг перезагружен");
                        }
						return true;                       
    }
	}
}
