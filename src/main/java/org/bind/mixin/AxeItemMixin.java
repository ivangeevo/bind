package org.bind.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.bind.util.PlaceableAsItem;
import org.bind.util.PlaceableToolManager;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin implements PlaceableAsItem {

    @Inject(
            method = {"useOnBlock"},
            at = {@At("HEAD")},
            cancellable = true
    )
    private void preventStrippingWhenPlacingTool(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (this.shouldPlaceTool(context)) {
            cir.setReturnValue(ActionResult.FAIL);
        }

    }

    @Unique
    private boolean shouldPlaceTool(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        if (player != null /**&& this.isCtrlPressed()**/) {
            ItemStack stack = context.getStack();
            if (!(stack.getItem() instanceof ToolItem)) {
                return false;
            } else {
                BlockPos placePos = context.getBlockPos().offset(context.getSide());
                World world = context.getWorld();
                return PlaceableToolManager.isValidTool(stack) && world.getBlockState(placePos).isReplaceable();
            }
        } else {
            return false;
        }
    }

    /**
    @Unique
    private boolean isCtrlPressed() {
        long windowHandle = class_310.method_1551().method_22683().method_4490();
        return GLFW.glfwGetKey(windowHandle, 341) == 1 || GLFW.glfwGetKey(windowHandle, 345) == 1;
    }
    **/
}
