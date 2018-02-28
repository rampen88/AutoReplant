package me.rampen88.autoreplant.util;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;

public class SeedInfo {

	private CropState newState;
	private Material requiredItem;
	private Material requiredBlock;
	private String permission;
	private String noSeedPermission;

	SeedInfo(CropState newState, Material requiredItem, Material requiredBlock, String permission, String noSeedPermission){
		this.newState = newState;
		this.requiredItem = requiredItem;
		this.requiredBlock = requiredBlock;
		this.permission = permission;
		this.noSeedPermission = noSeedPermission;
	}

	public CropState getNewState(){
		return newState;
	}

	public Material getRequiredItem(){
		return requiredItem;
	}

	public Material getRequiredBlock() {
		return requiredBlock;
	}

	public boolean hasPermission(Player p){
		// if permission is null, there is no specific perm for this type.
		return permission == null || p.hasPermission(permission);
	}

	public boolean hasNoseedPermission(Permissible permissible){
		// Permission should never be null
		return permissible.hasPermission(noSeedPermission);
	}

}
