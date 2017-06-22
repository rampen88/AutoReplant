package me.rampen88.autoreplant.util;

import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.util.BlockUtils;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

public class McmmoHook {

	public void attemptTrackBlock(Block b){
		if(BlockUtils.shouldBeWatched(b.getState())){
			// Set the metadata the same way as mcmmo. https://github.com/mcMMO-Dev/mcMMO/blob/master/src/main/java/com/gmail/nossr50/skills/herbalism/HerbalismManager.java#L325
			b.setMetadata(mcMMO.greenThumbDataKey, new FixedMetadataValue(mcMMO.p, (int) (System.currentTimeMillis() / 1000L)));
		}
	}

}
