package org.bind;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.bind.block.ModBlocks;
import org.bind.block.entity.ModBlockEntities;

public class BindMod implements ModInitializer {

    public static final String MOD_ID = "bind";

    public static final Logger LOGGER = LoggerFactory.getLogger("bind");

    @Override
    public void onInitialize() {
        ModBlocks.registerModBlocks();
        ModBlockEntities.registerBlockEntities();
    }

}
