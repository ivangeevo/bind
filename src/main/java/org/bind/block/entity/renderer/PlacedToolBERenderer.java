package org.bind.block.entity.renderer;

import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import org.bind.block.blocks.PlacedToolBlock;
import org.bind.block.entity.PlacedToolBE;

public class PlacedToolBERenderer implements BlockEntityRenderer<PlacedToolBE> {

    private final ItemRenderer itemRenderer;

    public PlacedToolBERenderer(BlockEntityRendererFactory.Context ctx) {
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(PlacedToolBE entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemStack item = entity.getToolStack();
        Direction facing = entity.getCachedState().get(PlacedToolBlock.FACING);

        if (!item.isEmpty()) {
            matrices.push();

            // Move to the center of the block
            matrices.translate(0.5f, 0.58f, 0.5f);

            // Apply the visual offset logic
            applyVisualOffset(matrices, facing);

            // Rotate based on the facing direction
            applyRotation(matrices, facing);

            // Scale the item to an appropriate size
            matrices.scale(0.5f, 0.5f, 0.5f);

            // Use the itemRenderer to render the item
            this.itemRenderer.renderItem(item, ModelTransformationMode.GUI,
                    LightmapTextureManager.pack(8, 15), OverlayTexture.DEFAULT_UV,
                    matrices, vertexConsumers, entity.getWorld(), 1);

            matrices.pop();
        }
    }

    private void applyVisualOffset(MatrixStack matrices, Direction facing) {
        float visualOffset = 0.25f;

        switch (facing) {
            case NORTH:
                matrices.translate(0.0f, 0.0f, -0.5f + visualOffset);
                break;
            case SOUTH:
                matrices.translate(0.0f, 0.0f, 0.5f - visualOffset);
                break;
            case WEST:
                matrices.translate(-0.5f + visualOffset, 0.0f, 0.0f);
                break;
            case EAST:
                matrices.translate(0.5f - visualOffset, 0.0f, 0.0f);
                break;
            default:
                break;
        }
    }

    private void applyRotation(MatrixStack matrices, Direction facing) {
        switch (facing) {
            case NORTH:
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                break;
            case SOUTH:
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0));
                break;
            case WEST:
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
                break;
            case EAST:
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270));
                break;
            default:
                break;
        }
    }
}
