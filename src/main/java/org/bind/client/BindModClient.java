package org.bind.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
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
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PLACED_TOOL, RenderLayer.getCutout());
        BlockEntityRendererFactories.register(ModBlockEntities.PLACED_TOOL, PlacedToolBERenderer::new);
    }

}
