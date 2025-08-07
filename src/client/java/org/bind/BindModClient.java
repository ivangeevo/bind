package org.bind;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.bind.block.entity.render.ModRendering;
import org.bind.mixin.client.AxeItemAccessor;
import org.bind.networking.ModClientNetworking;
import org.bind.util.ClientSharedInputState;

public class BindModClient implements ClientModInitializer
{

    @Override
    public void onInitializeClient() {
        ModRendering.init();
        ModClientNetworking.init();

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world.isClient && hand == Hand.MAIN_HAND) {
                if (player.getMainHandStack().getItem() instanceof AxeItem) {
                    if (ClientSharedInputState.toolPlacementInputHeld) {
                        BlockState state = world.getBlockState(hitResult.getBlockPos());
                        Block block = state.getBlock();
                        boolean isStrippable = AxeItemAccessor.getStrippedBlocks().containsKey(block);

                        if (isStrippable) {
                            return ActionResult.SUCCESS; // Cancels clientside prediction
                        }
                    }
                }
            }

            return ActionResult.PASS;
        });


    }

}
