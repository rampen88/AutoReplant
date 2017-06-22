package me.rampen88.autoreplant.util;

import org.bukkit.CropState;
import org.bukkit.Material;

public class SeedInfo
{
	private CropState newState;
	private Material requiredItem;

	SeedInfo(CropState newState, Material requiredItem){
		this.newState = newState;
		this.requiredItem = requiredItem;
	}

	public CropState getNewState(){
		return newState;
	}

	public Material getRequiredItem(){
		return requiredItem;
	}
}
