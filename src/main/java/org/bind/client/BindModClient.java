package org.bind.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import org.bind.block.ModBlocks;
import org.bind.block.entity.ModBlockEntities;
import org.bind.block.entity.PlacedToolBE;
import org.bind.block.entity.renderer.PlacedToolBERenderer;

public class BindModClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient() {
        registerPlaceableToolRender(ModBlocks.PLACED_PICKAXE, ModBlockEntities.PLACED_PICKAXE);
        registerPlaceableToolRender(ModBlocks.PLACED_AXE, ModBlockEntities.PLACED_AXE);
    }

    private void registerPlaceableToolRender(Block block, BlockEntityType<PlacedToolBE> type) {
        BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
        BlockEntityRendererFactories.register(type, PlacedToolBERenderer::new);
    }

}
