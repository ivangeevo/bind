package org.btwr.bind.block.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.btwr.bind.BindMod;
import org.btwr.bind.block.ModBlocks;

public class ModBlockEntities {

    public static BlockEntityType<PlacedToolBE> PLACED_TOOL;

    public static void registerBlockEntities() {
        PLACED_TOOL = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(BindMod.MOD_ID, "placed_tool_block_entity"),
                BlockEntityType.Builder.create(PlacedToolBE::new, ModBlocks.PLACED_TOOL).build());

        BindMod.LOGGER.info("Registering Block Entities for " + BindMod.MOD_ID);
    }

}