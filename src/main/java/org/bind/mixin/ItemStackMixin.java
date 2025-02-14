package org.bind.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.bind.util.ToolManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static org.lwjgl.glfw.GLFW.*;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin
{
    @Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;useOnBlock(Lnet/minecraft/item/ItemUsageContext;)Lnet/minecraft/util/ActionResult;"), cancellable = true)
    private void onUseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir, @Local Item item) {
        World world = context.getWorld();

        if (!(item instanceof ToolItem) || !isCtrlPressed() || world.isClient()) {
            return;
        }

        BlockPos pos = context.getBlockPos();
        BlockPos placePos = pos.offset(context.getSide());

        if (!ToolManager.isValidTool(context.getStack()) || !world.getBlockState(placePos).isReplaceable()) {
            return;
        }

        boolean success = ToolManager.placeToolBlock(world, placePos, context);
        if (success) {
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }

    @Unique
    private boolean isCtrlPressed() {
        long windowHandle = MinecraftClient.getInstance().getWindow().getHandle();
        return glfwGetKey(windowHandle, GLFW_KEY_LEFT_CONTROL) == 1 || glfwGetKey(windowHandle, GLFW_KEY_RIGHT_CONTROL) == 1;
    }


}
