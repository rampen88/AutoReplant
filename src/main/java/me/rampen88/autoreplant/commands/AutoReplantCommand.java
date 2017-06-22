package me.rampen88.autoreplant.commands;

import me.rampen88.autoreplant.AutoReplant;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AutoReplantCommand implements CommandExecutor {

	private AutoReplant plugin;

	public AutoReplantCommand(AutoReplant plugin){
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){
		if(commandSender.hasPermission("auto.replant.reload")){
			plugin.reload();
			commandSender.sendMessage(getMessage("Reload"));
		}else{
			commandSender.sendMessage(getMessage("NoPermission"));
		}
		return true;
	}

	private String getMessage(String s){
		return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages." + s));
	}
}
