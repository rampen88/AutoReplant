package me.rampen88.autoreplant;

import me.rampen88.autoreplant.commands.AutoReplantCommand;
import me.rampen88.autoreplant.listener.BlockListener;
import me.rampen88.autoreplant.util.McmmoHook;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class AutoReplant extends JavaPlugin {

	private static McmmoHook mcMMOHook;
	private BlockListener listener;

	public void onEnable(){
		saveDefaultConfig();

		listener = new BlockListener(this);
		getCommand("autoreplant").setExecutor(new AutoReplantCommand(this));

		if(Bukkit.getPluginManager().isPluginEnabled("mcMMO")){
			mcMMOHook = new McmmoHook();
		}
	}

	public void onDisable(){
		HandlerList.unregisterAll(this);
	}

	public static McmmoHook getMcMMOHook(){
		return mcMMOHook;
	}

	public void reload(){
		reloadConfig();
		listener.reload();
	}

}
