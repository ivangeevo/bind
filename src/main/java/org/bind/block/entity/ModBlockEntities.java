package org.bind.block.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.bind.BindMod;
import org.bind.block.ModBlocks;

public class ModBlockEntities {

    public static BlockEntityType<PlacedToolBE> PLACED_PICKAXE;
    public static BlockEntityType<PlacedToolBE> PLACED_AXE;

    public static void registerBlockEntities() {

        PLACED_PICKAXE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(BindMod.MOD_ID, "placed_pickaxe_be"),
                BlockEntityType.Builder.create(PlacedToolBE::new, ModBlocks.PLACED_PICKAXE).build());

        PLACED_AXE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(BindMod.MOD_ID, "placed_axe_be"),
                BlockEntityType.Builder.create(PlacedToolBE::new, ModBlocks.PLACED_AXE).build());

        BindMod.LOGGER.info("Registering Block Entities for " + BindMod.MOD_ID);
    }

}
