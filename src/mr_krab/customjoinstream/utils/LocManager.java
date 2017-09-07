package mr_krab.customjoinstream.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import mr_krab.customjoinstream.Plugin;

public class LocManager {

	/*
	 * Поработать над переносом в отдельный класс.
	 *
	 * Создание локаций
	 * Стартовая. Для телепорта при входе/выходе игрока
	 */
	public void SetJoinLoc (Location loc) throws IOException {
		Plugin.getInstance().fileConfig.set("JoinLoc.World", loc.getWorld().getName());
		Plugin.getInstance().fileConfig.set("JoinLoc.X", loc.getBlockX() + 0.5);
		Plugin.getInstance().fileConfig.set("JoinLoc.Y", loc.getBlockY());
		Plugin.getInstance().fileConfig.set("JoinLoc.Z", loc.getBlockZ() + 0.5);
		Plugin.getInstance().fileConfig.set("JoinLoc.Yaw", loc.getYaw());
		Plugin.getInstance().fileConfig.set("JoinLoc.Pitch", loc.getPitch()); {
		Plugin.getInstance().fileConfig.save(Plugin.getInstance().getDataFolder() + File.separator +"locations.yml");
		}
	}
	// Спавн. Для использования при отсутсвии других плагинов с этой функцией
	public void SetSpawnLoc (Location loc) throws IOException {
		Plugin.getInstance().fileConfig.set("Spawn.World", loc.getWorld().getName());
		Plugin.getInstance().fileConfig.set("Spawn.X", loc.getBlockX() + 0.5);
		Plugin.getInstance().fileConfig.set("Spawn.Y", loc.getBlockY());
		Plugin.getInstance().fileConfig.set("Spawn.Z", loc.getBlockZ() + 0.5);
		Plugin.getInstance().fileConfig.set("Spawn.Yaw", loc.getYaw());
		Plugin.getInstance().fileConfig.set("Spawn.Pitch", loc.getPitch());
		Plugin.getInstance().fileConfig.save(Plugin.getInstance().getDataFolder() + File.separator +"locations.yml");
	}
	// Телепорт на стартовую позицию.
	public Location TpJoinLoc(String string) {
		Location loc = new Location(Bukkit.getServer().getWorld(Plugin.getInstance().fileConfig.getString("JoinLoc.World")),
				Plugin.getInstance().fileConfig.getDouble("JoinLoc.X"),
				Plugin.getInstance().fileConfig.getDouble("JoinLoc.Y"),
				Plugin.getInstance().fileConfig.getDouble("JoinLoc.Z"),
				Plugin.getInstance().fileConfig.getInt("JoinLoc.Yaw"),
				Plugin.getInstance().fileConfig.getInt("JoinLoc.Pitch"));
		return loc;
	}
	// Телепорт на спавн.
	public Location TpSpawn(String string) {
		if (Plugin.getInstance().fileConfig.getConfigurationSection("Spawn") == null) return null;
		Location loc = new Location(Bukkit.getServer().getWorld(Plugin.getInstance().fileConfig.getString("Spawn.World")),
				Plugin.getInstance().fileConfig.getDouble("Spawn.X"),
				Plugin.getInstance().fileConfig.getDouble("Spawn.Y"),
				Plugin.getInstance().fileConfig.getDouble("Spawn.Z"),
				Plugin.getInstance().fileConfig.getInt("Spawn.Yaw"),
				Plugin.getInstance().fileConfig.getInt("Spawn.Pitch"));
		return loc;
	}
}
