package org.bind.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.bind.block.ModBlocks;
import org.bind.block.blocks.PlacedToolBlock;
import org.bind.util.ToolManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.bind.tag.ModTags;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static org.lwjgl.glfw.GLFW.*;

@Mixin(Item.class)
public abstract class ItemMixin {

    @Unique
    private final boolean isItemEntity = (Item)(Object)this instanceof ToolItem;

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void onUseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (!isItemEntity) {
            return;
        }

        BlockPos pos = context.getBlockPos();
        BlockState state = context.getWorld().getBlockState(pos);
        World world = context.getWorld();
        BlockPos placePos = context.getBlockPos().offset(context.getSide());

        if (!world.isClient()) {
            if (ToolManager.determineToolType(context.getStack()) != ToolManager.ToolType.UNKNOWN
                    && world.getBlockState(placePos).isReplaceable()
                    && isCtrlPressed()) {

                boolean success = ToolManager.placeToolBlock(world, placePos, context.getStack(), state, context.getHorizontalPlayerFacing());
                if (success) {
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            }
        }
        cir.setReturnValue(ActionResult.PASS);
    }

    private boolean isCtrlPressed() {
        long windowHandle = MinecraftClient.getInstance().getWindow().getHandle();
        return glfwGetKey(windowHandle, GLFW_KEY_LEFT_CONTROL) == 1 || glfwGetKey(windowHandle, GLFW_KEY_RIGHT_CONTROL) == 1;
    }


}
