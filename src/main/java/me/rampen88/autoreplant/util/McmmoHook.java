package me.rampen88.autoreplant.util;

import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.util.BlockUtils;

import org.bukkit.block.BlockState;

public class McmmoHook {

	public void attemptTrackBlock(BlockState state){
		if(BlockUtils.shouldBeWatched(state)){
			// Not entirely sure how this works, but it seems to work at least >.> https://github.com/mcMMO-Dev/mcMMO/blob/master/src/main/java/com/gmail/nossr50/listeners/BlockListener.java#L142
			// TODO: Should probably remove this as an option.
			mcMMO.getPlaceStore().setTrue(state);
		}
	}

}
