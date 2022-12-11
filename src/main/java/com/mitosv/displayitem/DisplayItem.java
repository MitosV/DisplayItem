package com.mitosv.displayitem;

import com.mitosv.displayitem.block.RegisterBlocks;
import com.mitosv.displayitem.block.displayblock.item.RegisterItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DisplayItem implements ModInitializer {


    public static final String MOD_ID = "displayitem";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    @Override
    public void onInitialize() {
        RegisterBlocks.registerModBlocks();
        RegisterItems.registerModItems();
    }
}
