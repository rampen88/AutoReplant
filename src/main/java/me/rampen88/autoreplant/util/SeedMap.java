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
		section.getKeys(false).forEach(s -> {
			try{
				Material material = Material.valueOf(s);
				Material requiredItem = Material.valueOf(section.getString(s + ".RequiredItem"));

				// Get the material for the required block, .getString returns null if it does not exist, in that cause, default to SOIL.
				String block = section.getString(s + ".RequiredBlock");
				Material requiredBlock = block != null ? Material.valueOf(block) : Material.SOIL;

				String permission = section.getString(s + ".Permission");
				String noSeedPermission = section.getString(s + ".NoSeedPermission", "auto.replant.noseed");

				SeedInfo info;
				if(material == Material.NETHER_WARTS){
					NetherWartsState newState = NetherWartsState.valueOf(section.getString(s + ".NewState"));
					info = new NetherSeedInfo(newState, requiredItem, requiredBlock, permission, noSeedPermission);
				}else{
					CropState newState = CropState.valueOf(section.getString(s + ".NewState"));
					info = new SeedInfo(newState, requiredItem, requiredBlock, permission, noSeedPermission);
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
