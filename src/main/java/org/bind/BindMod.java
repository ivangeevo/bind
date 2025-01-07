package org.bind;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.bind.block.ModBlocks;
import org.bind.block.entity.ModBlockEntities;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_CONTROL;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

public class BindMod implements ModInitializer
{
    public static final String MOD_ID = "bind";

    public static final Logger LOGGER = LoggerFactory.getLogger("bind");

    @Override
    public void onInitialize()
    {
        //registerModEventHandlers();

        ModBlocks.registerModBlocks();
        ModBlockEntities.registerBlockEntities();


    }

    private void registerModEventHandlers()
    {
        // Register event handler for block usage
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            ItemStack heldItem = player.getStackInHand(hand);

            if (isCtrlPressed() && heldItem.getItem() instanceof ToolItem)
            {

                return heldItem.getItem().useOnBlock(new ItemUsageContext(player, hand, hitResult));
            }
            return ActionResult.PASS;
        });
    }

    private boolean isCtrlPressed() {
        long windowHandle = MinecraftClient.getInstance().getWindow().getHandle();
        return glfwGetKey(windowHandle, GLFW_KEY_LEFT_CONTROL) == 1 ||
                glfwGetKey(windowHandle, GLFW_KEY_RIGHT_CONTROL) == 1;
    }


}
