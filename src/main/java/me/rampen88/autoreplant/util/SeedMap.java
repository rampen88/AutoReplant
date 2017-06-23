package me.rampen88.autoreplant.util;

import me.rampen88.autoreplant.AutoReplant;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.NetherWartsState;
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

				// Get the material for the required block, .getString returns null if it does not exist, in that cause, default to SOIL.
				String block = section.getString(s + ".RequiredBlock");
				Material requiredBlock = block != null ? Material.valueOf(block) : Material.SOIL;

				// Get the permission for the item. if there's no specific permission for it, the string will be null.
				String permission = section.getString(s + ".Permission");

				SeedInfo info;
				if(material == Material.NETHER_WARTS){
					NetherWartsState newState = NetherWartsState.valueOf(section.getString(s + ".NewState"));
					info = new NetherSeedInfo(newState, requiredItem, requiredBlock, permission);
				}else{
					CropState newState = CropState.valueOf(section.getString(s + ".NewState"));
					info = new SeedInfo(newState, requiredItem, requiredBlock, permission);
				}
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
