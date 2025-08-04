package org.bind;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import org.bind.block.ModBlocks;
import org.bind.block.entity.ModBlockEntities;
import org.bind.block.entity.renderer.PlacedToolBERenderer;
import org.bind.keybind.ModKeyBinds;
import org.bind.network.CtrlKeyHeldC2SPayload;
import org.bind.util.SharedInputState;

public class BindModClient implements ClientModInitializer
{

    // Used to detect state changes
    private static boolean lastHeldState = false;

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PLACED_TOOL, RenderLayer.getCutout());
        BlockEntityRendererFactories.register(ModBlockEntities.PLACED_TOOL, PlacedToolBERenderer::new);

        ModKeyBinds.register();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            boolean isHeld = ModKeyBinds.TOOL_PLACEMENT_KEY.isPressed();

            if (isHeld != lastHeldState) {
                ClientPlayNetworking.send(new CtrlKeyHeldC2SPayload(isHeld));
                lastHeldState = isHeld;
                SharedInputState.toolPlacementInputHeld = isHeld;
            }
        });

    }


}
