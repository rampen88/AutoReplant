package me.rampen88.autoreplant;

import me.rampen88.autoreplant.commands.AutoReplantCommand;
import me.rampen88.autoreplant.listener.BlockListener;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class AutoReplant extends JavaPlugin {

	private BlockListener listener;

	public void onEnable(){
		saveDefaultConfig();

		listener = new BlockListener(this);
		getCommand("autoreplant").setExecutor(new AutoReplantCommand(this));

	}

	public void onDisable(){
		HandlerList.unregisterAll(this);
	}

	public void reload(){
		reloadConfig();
		listener.reload();
	}

}
