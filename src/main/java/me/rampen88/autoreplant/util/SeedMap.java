package me.rampen88.autoreplant.util;

import me.rampen88.autoreplant.AutoReplant;
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
		section.getKeys(false).forEach(s -> {
			try{
				Material material = Material.valueOf(s);
				Material requiredItem = Material.valueOf(section.getString(s + ".RequiredItem"));

				// Get the material for the required block, .getString returns null if it does not exist, in that cause, default to SOIL.
				String block = section.getString(s + ".RequiredBlock");
				Material requiredBlock = block != null ? Material.valueOf(block) : Material.FARMLAND;

				String permission = section.getString(s + ".Permission");
				String noSeedPermission = section.getString(s + ".NoSeedPermission", "auto.replant.noseed");
				int age = section.getInt(s + ".NewState", 0);

				SeedInfo info = new SeedInfo(age, requiredItem, requiredBlock, permission, noSeedPermission);
				seeds.put(material, info);

			}catch (NullPointerException | IllegalArgumentException e){
				plugin.getLogger().info("Invalid configuration for seed type: " + s);
			}
		});
	}

	public SeedInfo getSeedInfo(Material type){
		return seeds.get(type);
	}
}
