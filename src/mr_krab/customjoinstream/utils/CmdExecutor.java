package mr_krab.customjoinstream.utils;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import mr_krab.customjoinstream.Plugin;


public class CmdExecutor implements  CommandExecutor {

	Plugin instance;
	public CmdExecutor(Plugin plugin) {
		instance = plugin;
	}


	@SuppressWarnings("static-access")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
				if (cmd.getName().equalsIgnoreCase("customjoinstream") && args.length >=1) {
					
						// Аргумент на перезагрузку плагина.
				if (args[0].equalsIgnoreCase("reload")) {
					if(sender.hasPermission("customjoinstream.reload")){
						instance.reloadConfig();
						instance.checkConfigVersion();
						instance.loc.init();
						sender.sendMessage(instance.loc.getString("PluginPrefix") + instance.loc.getString("Reload"));
						return true;
					} else {
						sender.sendMessage(instance.loc.getString("NoPermissions"));
							}
					// Аргумент на справку
				} else if (args[0].equalsIgnoreCase("help")) {
						if(sender.hasPermission("customjoinstream.admin")) {
							sender.sendMessage(instance.loc.getString("ServerPrefix"));
							sender.sendMessage(instance.loc.getString("HelpLine1"));
							sender.sendMessage(instance.loc.getString("HelpLine2"));
							sender.sendMessage(instance.loc.getString("HelpLine3"));
							sender.sendMessage(instance.loc.getString("HelpLine4"));
							sender.sendMessage(instance.loc.getString("HelpLine5"));
							sender.sendMessage(instance.loc.getString("HelpLine6"));
							sender.sendMessage(instance.loc.getString("HelpLine7"));
							sender.sendMessage(instance.loc.getString("HelpLine8"));
						} else {
							sender.sendMessage(instance.loc.getString("NoPermissions"));
								}
					
						// Аргумент на установку точки телепортации при входе/выходе
					} else if (args[0].equalsIgnoreCase("setjoinloc")) {
							if(sender.hasPermission("customjoinstream.setloc")){
							if (!(sender instanceof Player)) {
								sender.sendMessage(instance.loc.getString("PluginPrefix") + instance.loc.getString("OnlyPlayer"));
								return false;
							}
							Player p = (Player) sender;
										try {
											Plugin.getInstance().locm.SetJoinLoc(p.getLocation());;
										} catch (IOException e) {
											e.printStackTrace();
										}
							sender.sendMessage(instance.loc.getString("PluginPrefix") + instance.loc.getString("Setloc").replace("%loc", "JoinLoc"));
							return true;
							} else {
								sender.sendMessage(instance.loc.getString("NoPermissions"));
							}
							
						// Аргумент на установку точки спавна
					}else if (args[0].equalsIgnoreCase("setspawn")) {
							if(sender.hasPermission("customjoinstream.setloc")){
								if (!(sender instanceof Player)) {
								sender.sendMessage(instance.loc.getString("PluginPrefix") + instance.loc.getString("OnlyPlayer"));
								return false;
								}
							Player p = (Player) sender;
									try {
										Plugin.getInstance().locm.SetSpawnLoc(p.getLocation());
									} catch (IOException e) {
										e.printStackTrace();
									}
							sender.sendMessage(instance.loc.getString("PluginPrefix") + instance.loc.getString("Setloc").replace("%loc", "Spawn"));
							return true;
							} else {
								sender.sendMessage(instance.loc.getString("NoPermissions"));
							}
/*===========================================================================================================================================*/	
														
						// Аргумент на телепортацию на стартовую позицию
					}else if (args[0].equalsIgnoreCase("tpjoin")) {
							if(sender.hasPermission("customjoinstream.tpjoin")){
								if (!(sender instanceof Player)) {
								sender.sendMessage(instance.loc.getString("PluginPrefix") + instance.loc.getString("OnlyPlayer"));
								return false;
								}
							if(Plugin.getInstance().fileConfig == null) {
								sender.sendMessage(instance.loc.getString("ServerPrefix") + instance.loc.getString("PosNotFound"));
								return false;
							}
							if(Plugin.getInstance().fileConfig.getString("JoinLoc") !=null) {
							Player p = (Player) sender;
							Location join = Plugin.getInstance().locm.TpJoinLoc(p.getName());
							p.teleport(join);
							sender.sendMessage(instance.loc.getString("PluginPrefix") + instance.loc.getString("TpJoinLoc"));
							return true;
							}
							sender.sendMessage(instance.loc.getString("ServerPrefix") + instance.loc.getString("PosNotFound"));
							} else {
								sender.sendMessage(instance.loc.getString("NoPermissions"));
							}
							
						// Аргумент на телепортацию на спавн
					}else if (args[0].equalsIgnoreCase("spawn")) {
							if(sender.hasPermission("customjoinstream.spawn")){
								if (!(sender instanceof Player)) {
								sender.sendMessage(instance.loc.getString("PluginPrefix") + instance.loc.getString("OnlyPlayer"));
								return false;
								}
								if(Plugin.getInstance().fileConfig == null) {
									sender.sendMessage(instance.loc.getString("ServerPrefix") + instance.loc.getString("PosNotFound"));
									return false;
								}
							if(Plugin.getInstance().fileConfig.getString("Spawn") != null) {
							Player p = (Player) sender;
							Location join = Plugin.getInstance().locm.TpSpawn(p.getName());
							p.teleport(join);
							sender.sendMessage(instance.loc.getString("ServerPrefix") + instance.loc.getString("TpSpawn"));
							return true;
							}
							sender.sendMessage(instance.loc.getString("ServerPrefix") + instance.loc.getString("PosNotFound"));
							} else {
								sender.sendMessage(instance.loc.getString("NoPermissions"));
							}
							
/*===========================================================================================================================================*/
							
							// Аргумент на скрытие игрока
					}else if (args[0].equalsIgnoreCase("hide")) {
							if(sender.hasPermission("customjoinstream.hide")){
								if (!(sender instanceof Player)) {
								sender.sendMessage(instance.loc.getString("PluginPrefix") + instance.loc.getString("OnlyPlayer"));
								return false;
								}
							Player player = (Player) sender;
					        if(!player.hasMetadata("hidden")) {
					            for(Player ps : Bukkit.getOnlinePlayers()) {
					                ps.hidePlayer(player);
					            	player.setPlayerListName(null);
					            }
					            player.setMetadata("hidden", new FixedMetadataValue(instance.getInstance(), true));
								sender.sendMessage(instance.loc.getString("ServerPrefix") + instance.loc.getString("HideOn"));
					            return true;
					        } else {
					            for(Player ps : Bukkit.getOnlinePlayers()) {
					                ps.showPlayer(player);
					            	player.setPlayerListName(player.getName());
					            }
					            player.removeMetadata("hidden", instance.getInstance());
								sender.sendMessage(instance.loc.getString("ServerPrefix") + instance.loc.getString("HideOff"));
					            return true;
					        }
							} else {
								sender.sendMessage(instance.loc.getString("NoPermissions"));
							}
							
							// Аргумент на полет игрока
					}else if (args[0].equalsIgnoreCase("fly")) {
						if(sender.hasPermission("customjoinstream.fly")){
							if (!(sender instanceof Player)) {
							sender.sendMessage(instance.loc.getString("PluginPrefix") + instance.loc.getString("OnlyPlayer"));
							return false;
							}
						Player player = (Player) sender;
				        if(!player.hasMetadata("flying")) {
						player.setAllowFlight(true);
						player.setFlying(true);
			            player.setMetadata("flying", new FixedMetadataValue(instance.getInstance(), true));
						player.sendMessage(instance.loc.getString("ServerPrefix") + instance.loc.getString("FlyOn"));
					} else {
						player.setAllowFlight(false);
						player.setFlying(false);
			            player.removeMetadata("flying", instance.getInstance());
						player.sendMessage(instance.loc.getString("ServerPrefix") + instance.loc.getString("FlyOff"));
					}
					return true;
					} else {
						sender.sendMessage(instance.loc.getString("NoPermissions"));
					}
				
					/* В ТУДУ лист
					 * Добавить еще аргументов при необходимости
					 */
				}
				}
				if (args.length == 0) {
				if(sender.hasPermission("customjoinstream.admin")) {
					sender.sendMessage(instance.loc.getString("ServerPrefix"));
					sender.sendMessage(instance.loc.getString("HelpLine0"));
				} else {
					sender.sendMessage(instance.loc.getString("NoPermissions"));
					} 
			}
	return false;
	}
}