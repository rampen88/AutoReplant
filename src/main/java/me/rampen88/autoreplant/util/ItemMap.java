package me.rampen88.autoreplant.util;

import me.rampen88.autoreplant.AutoReplant;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

public class ItemMap {

	private AutoReplant plugin;
	private HashSet<Material> requiredItems = new HashSet<>();

	public ItemMap(AutoReplant plugin){
		this.plugin = plugin;
		reload();
	}

	public void reload(){
		clear();
		plugin.getConfig().getStringList("RequiredItemMaterials").forEach(s -> {
			try{
				Material requiredItem = Material.valueOf(s);
				requiredItems.add(requiredItem);

			}catch(NullPointerException | IllegalArgumentException e){
				plugin.getLogger().info("Invalid material for required item: " + s);
			}
		});
	}

	public void clear(){
		requiredItems.clear();
	}

	public boolean isPlayerHoldingItem(Player p){
		ItemStack heldItem = p.getInventory().getItemInMainHand();
		return heldItem != null && requiredItems.contains(heldItem.getType());
	}
}
