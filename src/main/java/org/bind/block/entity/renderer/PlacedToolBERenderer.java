package org.bind.block.entity.renderer;

import net.minecraft.block.enums.BlockFace;
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
        BlockFace face = entity.getCachedState().get(PlacedToolBlock.FACE);

        if (!item.isEmpty()) {
            matrices.push();

            // Move to the center of the block
            matrices.translate(0.5f, 0.58f, 0.5f);

            // Apply the visual offset logic
            applyVisualOffset(matrices, facing, face);

            // Rotate based on the facing direction
            applyRotation(matrices, facing, face);

            // Use the itemRenderer to render the item
            this.itemRenderer.renderItem(item, ModelTransformationMode.GUI,
                    LightmapTextureManager.pack(8, 15), OverlayTexture.DEFAULT_UV,
                    matrices, vertexConsumers, entity.getWorld(), 1);

            matrices.pop();
        }
    }

    private void applyVisualOffset(MatrixStack matrices, Direction facing, BlockFace face) {
        float visualOffset = 0.25f;

        switch (face) {
            case WALL -> {
                switch (facing) {
                    case NORTH -> matrices.translate(0.0f, 0.0f, -0.5f + visualOffset);
                    case SOUTH -> matrices.translate(0.0f, 0.0f, 0.5f - visualOffset);
                    case WEST -> matrices.translate(-0.5f + visualOffset, 0.0f, 0.0f);
                    case EAST -> matrices.translate(0.5f - visualOffset, 0.0f, 0.0f);
                    default -> {
                    }
                }
            }
            case FLOOR -> matrices.translate(0.0f, -0.5f + visualOffset, 0.0f);
            case CEILING -> matrices.translate(0.0f, 0.5f - visualOffset, 0.0f);
        }

    }

    private void applyRotation(MatrixStack matrices, Direction facing, BlockFace face) {
        switch (facing) {
            case WEST -> matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0));
            case SOUTH -> matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
            case EAST -> matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
            case NORTH -> matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270));
            default -> {}
        }
        switch (face) {
            case CEILING -> matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(0));
            case WALL -> matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90));
            case FLOOR -> matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180));
        }
    }
}
