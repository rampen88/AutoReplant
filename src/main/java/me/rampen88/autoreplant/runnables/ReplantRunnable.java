package me.rampen88.autoreplant.runnables;

import me.rampen88.autoreplant.listener.BlockListener;
import me.rampen88.autoreplant.util.SeedInfo;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ReplantRunnable extends BukkitRunnable {

	private BlockState state;
	private SeedInfo info;
	private Block b;
	private Player player;

	private BlockListener blockListener;

	private ReplantRunnable(Block b, SeedInfo info, Player player, BlockListener blockListener){
		this.state = b.getState();
		this.info = info;
		this.player = player;
		this.b = b;
		this.blockListener = blockListener;
	}

	public void run(){
		// Check if block is still broken, and if the plugin should check for farmland, check if the block under is the required block.
		if(b.getType() != Material.AIR || (blockListener.shouldCheckFarmland() && b.getRelative(BlockFace.DOWN).getType() != info.getRequiredBlock()))
			return;

		if(!info.hasNoSeedPermission(player)) {
			if(player.getInventory().contains(info.getRequiredItem(), 1))
				player.getInventory().removeItem(new ItemStack(info.getRequiredItem()));
			else
				return;
		}

		if(blockListener.shouldUpdatePlayerInv())
			player.updateInventory();

		BlockState replacedState = b.getState();
		// Has to be called before the BlockPlaceEvent.
		setAndUpdateState();

		if(blockListener.shouldCallBlockPlaceEvent()) {
			BlockPlaceEvent e = new BlockPlaceEvent(state.getBlock(), replacedState, b.getRelative(BlockFace.DOWN), player.getInventory().getItemInMainHand(), player, true, EquipmentSlot.HAND);
			Bukkit.getServer().getPluginManager().callEvent(e);
		}
	}

	private void setAndUpdateState(){
		Ageable ageable = (Ageable) state.getBlockData();
		ageable.setAge(info.getNewAge() <=  ageable.getMaximumAge() ? info.getNewAge() : ageable.getMaximumAge());
		state.setBlockData(ageable);
		state.update(true);
	}

	public static ReplantRunnable createRunnable(Block b, SeedInfo info, Player player, BlockListener blockListener){
		return new ReplantRunnable(b, info, player, blockListener);
	}

}
