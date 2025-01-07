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

    public static final Block PLACED_PICKAXE = registerBlock("placed_pickaxe", new PlacedToolBlock(placedBlockSettings()));
    public static final Block PLACED_AXE = registerBlock("placed_axe", new PlacedToolBlock(placedBlockSettings()));
    public static final Block PLACED_SHOVEL = registerBlock("placed_shovel", new PlacedToolBlock(placedBlockSettings()));
    public static final Block PLACED_HOE = registerBlock("placed_hoe", new PlacedToolBlock(placedBlockSettings()));
    public static final Block PLACED_SWORD = registerBlock("placed_sword", new PlacedToolBlock(placedBlockSettings()));


    private static AbstractBlock.Settings placedBlockSettings() {
       return AbstractBlock.Settings.create().strength(0.1F);
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(BindMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, Identifier.of(BindMod.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        BindMod.LOGGER.debug("Registering ModBlocks for " + BindMod.MOD_ID);
    }

}
