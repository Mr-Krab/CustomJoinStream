package mr_krab.customjoinstream.utils;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;

public class CommandRegister extends Command implements PluginIdentifiableCommand {
    protected Plugin plugin;
    protected final CommandExecutor owner;
    protected final Object registeredWith;
  
    public CommandRegister(String[] aliases, String desc, String usage, CommandExecutor owner, Object registeredWith, Plugin instance) {
        super(aliases[0], desc, usage, Arrays.asList(aliases));
        this.owner = owner;
        this.plugin =  instance;
        this.registeredWith = registeredWith;
    }
  
    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        return this.owner.onCommand(sender, this, label, args);
    }
  
  
    public Object getRegisteredWith() {
        return this.registeredWith;
    }
}