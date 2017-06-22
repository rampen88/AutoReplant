package me.rampen88.autoreplant.listener;

import me.rampen88.autoreplant.AutoReplant;

import me.rampen88.autoreplant.runnables.ReplantRunnable;
import me.rampen88.autoreplant.util.ItemMap;
import me.rampen88.autoreplant.util.SeedInfo;
import me.rampen88.autoreplant.util.SeedMap;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockListener implements Listener {

	private AutoReplant plugin;
	private SeedMap seedMap;
	private ItemMap itemMap;

	private boolean extraInvCheck;
	// TODO: Move to another class
	private boolean checkFarmland;
	private boolean callBlockPlaceEvent;
	private boolean attemptMcmmoData;
	private boolean updatePlayerInv;

	private int delay;

	public BlockListener(AutoReplant plugin){
		this.plugin = plugin;
		seedMap = new SeedMap(plugin);

		reload();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
	public void onBlockBreak(BlockBreakEvent e){
		if(!e.getPlayer().hasPermission("auto.replant")) return;

		Block b = e.getBlock();

		SeedInfo info = seedMap.getSeedInfo(b.getType());
		// if info is null, its not a block that should be replanted.
		if(info == null) return;

		Player p = e.getPlayer();
		// if the plugin should check that the player has a seed in their inventory before creating a runnable, and the player does not have it, return.
		if(extraInvCheck && !p.getInventory().contains(info.getRequiredItem(), 1)) return;

		// Check if player needs to be holding a certain item, and if the player is holding the needed item(s)
		if(itemMap != null && !itemMap.isPlayerHoldingItem(p)) return;

		// Create a runnable to run the task later, after the block has been broken.
		new ReplantRunnable(b, info, p, this).runTaskLater(plugin, delay);
	}

	public void reload(){
		seedMap.reload();

		checkFarmland = plugin.getConfig().getBoolean("CheckForFarmland");
		extraInvCheck = plugin.getConfig().getBoolean("ExtraInventoryCheckForItem");
		callBlockPlaceEvent = plugin.getConfig().getBoolean("ShouldCallBlockPlaceEvent");
		attemptMcmmoData = plugin.getConfig().getBoolean("AttemptSetMcmmoMetadata");
		updatePlayerInv = plugin.getConfig().getBoolean("UpdatePlayerInventory");

		delay = plugin.getConfig().getInt("ReplantDelay");

		if(plugin.getConfig().getBoolean("RequireItem")) {

			// if itemMap is null, create one. otherwise, reload the values in the current one.
			if(itemMap == null) itemMap = new ItemMap(plugin);
			else itemMap.reload();

		}else if(itemMap != null){ // if RequireItem was true, then changed to false, itemMap will not the null. if so, clear the values and set the variable to null.
			itemMap.clear();
			itemMap = null;
		}
	}

	public boolean shouldCheckFarmland(){
		return checkFarmland;
	}

	public boolean shouldCallBlockPlaceEvent(){
		return callBlockPlaceEvent;
	}

	public boolean shouldAttemptMcmmoData(){
		return attemptMcmmoData;
	}

	public boolean shouldUpdatePlayerInv(){
		return updatePlayerInv;
	}

}