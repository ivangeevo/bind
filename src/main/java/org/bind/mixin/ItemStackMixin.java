package org.bind.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.bind.util.PlaceableToolManager;
import org.bind.util.SharedInputState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow public abstract Item getItem();

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void cancelOffhandUse(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (context.getHand() == Hand.OFF_HAND && SharedInputState.getToolPlacementInputHeld() && this.isHoldingPlaceableTool(context.getPlayer())) {
            cir.setReturnValue(ActionResult.FAIL);
        }
    }

    @Inject(
            method = "useOnBlock",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;useOnBlock(Lnet/minecraft/item/ItemUsageContext;)Lnet/minecraft/util/ActionResult;"),
            cancellable = true
    )
    private void onUseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        World world = context.getWorld();
        if (this.getItem() instanceof ToolItem && SharedInputState.getToolPlacementInputHeld() && !world.isClient) {

            BlockPos pos = context.getBlockPos();
            BlockPos placePos = pos.offset(context.getSide());
            if (PlaceableToolManager.isValidTool(context.getStack()) && world.getBlockState(placePos).isReplaceable()) {
                if (!world.getBlockState(context.getBlockPos()).isOf(Blocks.SHORT_GRASS)) {
                    boolean success = PlaceableToolManager.placeToolBlock(world, placePos, context);
                    if (success) {
                    cir.setReturnValue(ActionResult.SUCCESS);
                    }
                }
            }
        }
    }


    @Unique
    private boolean isHoldingPlaceableTool(PlayerEntity player) {
        return player != null && player.getMainHandStack().getItem() instanceof ToolItem;
    }


}
