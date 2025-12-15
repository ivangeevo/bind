package org.btwr.bind.block.entity.render;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import org.btwr.bind.block.ModBlocks;
import org.btwr.bind.block.entity.ModBlockEntities;
import org.btwr.bind.block.entity.renderer.PlacedToolBERenderer;

public class ModRendering {

    public static void init() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PLACED_TOOL, RenderLayer.getCutout());
        BlockEntityRendererFactories.register(ModBlockEntities.PLACED_TOOL, PlacedToolBERenderer::new);
    }

}