package me.rampen88.autoreplant.runnables;

import me.rampen88.autoreplant.listener.BlockListener;
import me.rampen88.autoreplant.util.SeedInfo;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.Crops;

public class CropsReplantRunnable extends ReplantRunnable{

	private Crops crops;

	CropsReplantRunnable(Block b, SeedInfo info, Player player, BlockListener blockListener) {
		super(b, info, player, blockListener);
		crops = (Crops) state.getData();
	}

	@Override
	protected void setAndUpdateState() {
		crops.setState(info.getNewState());
		state.update(true);
	}
}
