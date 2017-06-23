package me.rampen88.autoreplant.runnables;

import me.rampen88.autoreplant.listener.BlockListener;
import me.rampen88.autoreplant.util.NetherSeedInfo;
import me.rampen88.autoreplant.util.SeedInfo;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.NetherWarts;

public class NetherReplantRunnable extends ReplantRunnable{

	private NetherWarts netherWarts;
	private NetherSeedInfo nInfo;

	NetherReplantRunnable(Block b, SeedInfo info, Player player, BlockListener blockListener) {
		super(b, info, player, blockListener);

		this.netherWarts = (NetherWarts) state.getData();
		this.nInfo = (NetherSeedInfo) info; // SeedInfo should always be instance of NetherSeedInfo if material is NETHER_WARTS.
	}

	@Override
	protected void setAndUpdateState() {
		netherWarts.setState(nInfo.getNewStatePls());
		state.update(true);
	}
}
