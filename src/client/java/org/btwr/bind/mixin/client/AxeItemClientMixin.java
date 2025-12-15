package org.btwr.bind.mixin.client;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import org.btwr.bind.util.ClientSharedInputState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public abstract class AxeItemClientMixin {

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void preventStripPrediction(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (context.getWorld().isClient()) {
            if (ClientSharedInputState.toolPlacementInputHeld) {
                BlockPos pos = context.getBlockPos();
                BlockState state = context.getWorld().getBlockState(pos);
                Block block = state.getBlock();

                if (AxeItemAccessor.getStrippedBlocks().containsKey(block)) {
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            }
        }
    }

}