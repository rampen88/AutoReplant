package me.rampen88.autoreplant.util;

import org.bukkit.Material;
import org.bukkit.permissions.Permissible;

public class SeedInfo {

	private int newAge;
	private Material requiredItem;
	private Material requiredBlock;
	private String permission;
	private String noSeedPermission;

	SeedInfo(int newAge, Material requiredItem, Material requiredBlock, String permission, String noSeedPermission){
		this.newAge = newAge;
		this.requiredItem = requiredItem;
		this.requiredBlock = requiredBlock;
		this.permission = permission;
		this.noSeedPermission = noSeedPermission;
	}

	public int getNewAge(){
		return newAge;
	}

	public Material getRequiredItem(){
		return requiredItem;
	}

	public Material getRequiredBlock() {
		return requiredBlock;
	}

	public boolean hasPermission(Permissible permissible){
		// if permission is null, there is no specific perm for this type.
		return permission == null || permissible.hasPermission(permission);
	}

	public boolean hasNoSeedPermission(Permissible permissible){
		// Permission should never be null
		return permissible.hasPermission(noSeedPermission);
	}

}
