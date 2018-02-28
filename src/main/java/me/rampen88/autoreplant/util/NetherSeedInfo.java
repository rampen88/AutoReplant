package me.rampen88.autoreplant.util;

import org.bukkit.Material;
import org.bukkit.NetherWartsState;

public class NetherSeedInfo extends SeedInfo{

	private NetherWartsState newState;

	NetherSeedInfo(NetherWartsState newState, Material requiredItem, Material requiredBlock, String permission, String noSeedPermission) {
		super(null, requiredItem, requiredBlock, permission, noSeedPermission);
		this.newState = newState;
	}

	public NetherWartsState getNewStatePls() {
		return newState;
	}
}
