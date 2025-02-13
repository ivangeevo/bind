package org.bind.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.bind.BindMod;
import org.bind.block.blocks.*;

public class ModBlocks {
    public static final Block PLACED_TOOL = Registry.register(
            Registries.BLOCK,
            Identifier.of(BindMod.MOD_ID, "placed_tool"),
            new PlacedToolBlock(
                    AbstractBlock.Settings.create().strength(0.1F)
            )
    );

    public static void registerModBlocks() {
        BindMod.LOGGER.debug("Registering ModBlocks for " + BindMod.MOD_ID);
    }

}
