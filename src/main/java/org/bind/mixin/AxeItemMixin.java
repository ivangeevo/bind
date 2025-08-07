package org.bind.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.bind.util.PlaceableToolManager;
import org.bind.util.ServerSharedInputState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin {

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void preventStrippingWhenPlacingTool(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (context.getWorld().isClient()) {
            // Skip input state check entirely and always cancel.
            // This prevents flash by blocking the strip early.
            cir.setReturnValue(ActionResult.SUCCESS);
            return;
        }

        // Server-side: check the actual player input state via your synced system
        PlayerEntity player = context.getPlayer();
        if (player instanceof ServerPlayerEntity serverPlayer && shouldPlaceToolServer(context, serverPlayer)) {
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }

    @Unique
    private boolean shouldPlaceToolServer(ItemUsageContext context, ServerPlayerEntity player) {
        if (!ServerSharedInputState.isHeld(player)) return false;

        ItemStack stack = context.getStack();
        if (!(stack.getItem() instanceof ToolItem)) return false;

        BlockPos placePos = context.getBlockPos().offset(context.getSide());
        World world = context.getWorld();

        return PlaceableToolManager.isValidTool(stack)
                && world.getBlockState(placePos).isReplaceable();
    }

}
