package me.rampen88.autoreplant.util;

import me.rampen88.autoreplant.AutoReplant;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;

public class SeedMap {

	private HashMap<Material, SeedInfo> seeds = new HashMap<>();
	private AutoReplant plugin;

	public SeedMap(AutoReplant plugin){
		this.plugin = plugin;
	}

	public void reload(){
		ConfigurationSection section = plugin.getConfig().getConfigurationSection("Seeds");
		// Get the keys from Seeds section in the configuration, and loop through each of them.
		section.getKeys(false).forEach(s -> {
			try{
				Material material = Material.valueOf(s);
				Material requiredItem = Material.valueOf(section.getString(s + ".RequiredItem"));
				CropState newState = CropState.valueOf(section.getString(s + ".NewState"));

				seeds.put(material, new SeedInfo(newState, requiredItem));
			}catch (NullPointerException | IllegalArgumentException e){
				plugin.getLogger().info("Invalid configuration for seed type: " + s);
			}
		});
	}

	public SeedInfo getSeedInfo(Material type){
		return seeds.get(type);
	}
}
