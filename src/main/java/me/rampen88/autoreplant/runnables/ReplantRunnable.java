package me.rampen88.autoreplant.runnables;

import me.rampen88.autoreplant.AutoReplant;
import me.rampen88.autoreplant.listener.BlockListener;
import me.rampen88.autoreplant.util.McmmoHook;
import me.rampen88.autoreplant.util.SeedInfo;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class ReplantRunnable extends BukkitRunnable {

	BlockState state;
	SeedInfo info;
	private Player player;
	private Block b;

	private BlockListener blockListener;

	ReplantRunnable(Block b, SeedInfo info, Player player, BlockListener blockListener){
		this.state = b.getState();
		this.info = info;
		this.player = player;
		this.b = b;
		this.blockListener = blockListener;
	}

	public void run(){
		// check if block is still broken and that player have 1 of the required item in their inventory.
		if(b.getType() != Material.AIR || !player.getInventory().contains(info.getRequiredItem(), 1)) return;

		// If the plugin should check for farmland, check if the block under is farmland.
		if(blockListener.shouldCheckFarmland() && b.getRelative(BlockFace.DOWN).getType() != info.getRequiredBlock()) return;

		// Remove the required item from players inventory
		player.getInventory().removeItem(new ItemStack(info.getRequiredItem()));
		// Check if plugin should update players inventory, does not seem to be needed, but keeping it as an option.
		if(blockListener.shouldUpdatePlayerInv()) player.updateInventory();

		BlockState replacedState = b.getState();

		// set and update the block state. Has to be called before the BlockPlaceEvent.
		setAndUpdateState();

		// If plugin should call block place event, call a BlockPlaceEvent.
		if(blockListener.shouldCallBlockPlaceEvent()) {

			BlockPlaceEvent e = new BlockPlaceEvent(state.getBlock(), replacedState, b.getRelative(BlockFace.DOWN), player.getInventory().getItemInMainHand(), player, true, EquipmentSlot.HAND);
			Bukkit.getServer().getPluginManager().callEvent(e);

		}else if(blockListener.shouldAttemptMcmmoData()){ // if plugin should not call block place event, see if it should attempt to let McMMO track the block.

			McmmoHook hook = AutoReplant.getMcMMOHook();
			if (hook != null) hook.attemptTrackBlock(state);

		}
	}

	protected abstract void setAndUpdateState();

	public static ReplantRunnable createRunnable(Block b, SeedInfo info, Player player, BlockListener blockListener){
		return b.getType() == Material.NETHER_WARTS ? new NetherReplantRunnable(b, info, player, blockListener) : new CropsReplantRunnable(b, info, player, blockListener);
	}

}
