package mr_krab.customjoinstream.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import mr_krab.customjoinstream.Plugin;

public class CustomJoinStreamCommand
	implements CommandExecutor {

	/* В ТУДУ лист
     * Добавить команды
     * Улучшить работу пермов, сделать наследование
     */

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length != 1 || !args[0].equalsIgnoreCase("reload")) {
			if(!sender.hasPermission("customjoinstream.admin")) {
				sender.sendMessage(Plugin.getInstance().getLocale().getString("NoPermissions"));
				return false;
			}
			sender.sendMessage(Plugin.getInstance().getLocale().getString("Prefix"));
			sender.sendMessage(Plugin.getInstance().getLocale().getString("HelpLine1"));
			sender.sendMessage(Plugin.getInstance().getLocale().getString("HelpLine2"));
			return false;
		}
		if(!sender.hasPermission("customjoinstream.reload")) {
			sender.sendMessage(Plugin.getInstance().getLocale().getString("NoPermissions"));
			return false;
		}
		Plugin.getInstance().reloadConfig();
		Plugin.getInstance().getLocale().init();
		sender.sendMessage(Plugin.getInstance().getLocale().getString("Prefix") + Plugin.getInstance().getLocale().getString("Reload"));
		return false;
	}
}