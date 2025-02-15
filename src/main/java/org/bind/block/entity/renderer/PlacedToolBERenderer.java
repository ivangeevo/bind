package org.bind.block.entity.renderer;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import org.bind.block.blocks.PlacedToolBlock;
import org.bind.block.entity.PlacedToolBE;
import org.bind.util.PlaceableAsItem;
import org.jetbrains.annotations.Nullable;

public class PlacedToolBERenderer implements BlockEntityRenderer<PlacedToolBE> {

    private final ItemRenderer itemRenderer;

    public PlacedToolBERenderer(BlockEntityRendererFactory.Context ctx) {
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(PlacedToolBE entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemStack itemStack = entity.getToolStack();
        BlockState state = entity.getCachedState();
        PlaceableAsItem placeableAsItem = null;
        if (itemStack.getItem() instanceof PlaceableAsItem placeableAsItem1) {
            placeableAsItem = placeableAsItem1;
        }

        if (!itemStack.isEmpty()) {
            matrices.push();

            // Move to the center of the block
            matrices.translate(0.5f, 0.5f, 0.5f);

            // Apply the visual offset logic
            applyVisualOffset(matrices, state, placeableAsItem);

            // Rotate based on the facing direction
            applyRotation(matrices, state, placeableAsItem);

            // Use the itemRenderer to render the item
            this.itemRenderer.renderItem(itemStack, ModelTransformationMode.GUI,
                    LightmapTextureManager.pack(8, 15), OverlayTexture.DEFAULT_UV,
                    matrices, vertexConsumers, entity.getWorld(), 1);

            matrices.pop();
        }
    }

    private void applyVisualOffset(MatrixStack matrices, BlockState state, @Nullable PlaceableAsItem placeableAsItem) {
        float verticalFloorOffset = 0.25f;
        float horizontalFloorOffset = 0f;
        if (placeableAsItem != null) {
            verticalFloorOffset = placeableAsItem.bind$getVisualVerticalOffsetPixels() / 16f;
            horizontalFloorOffset = placeableAsItem.bind$getVisualHorizontalOffsetPixels() / 16f;
        }

        switch (state.get(PlacedToolBlock.FACE)) {
            case WALL -> {
                switch (state.get(PlacedToolBlock.FACING)) {
                    case NORTH -> matrices.translate(0.0f, horizontalFloorOffset, -0.5f + verticalFloorOffset);
                    case SOUTH -> matrices.translate(0.0f, horizontalFloorOffset, 0.5f - verticalFloorOffset);
                    case WEST -> matrices.translate(-0.5f + verticalFloorOffset, horizontalFloorOffset, 0.0f);
                    case EAST -> matrices.translate(0.5f - verticalFloorOffset, horizontalFloorOffset, 0.0f);
                    default -> {}
                }
            }
            case FLOOR -> {
                switch (state.get(PlacedToolBlock.FACING)) {
                    case NORTH -> matrices.translate(0.0f, -0.5f + verticalFloorOffset, -horizontalFloorOffset);
                    case SOUTH -> matrices.translate(0.0f, -0.5f + verticalFloorOffset, horizontalFloorOffset);
                    case WEST -> matrices.translate(-horizontalFloorOffset, -0.5f + verticalFloorOffset, 0.0f);
                    case EAST -> matrices.translate(horizontalFloorOffset, -0.5f + verticalFloorOffset, 0.0f);
                    default -> {}
                }
            }
            case CEILING -> {
                switch (state.get(PlacedToolBlock.FACING)) {
                    case NORTH -> matrices.translate(0.0f, 0.5f - verticalFloorOffset, horizontalFloorOffset);
                    case SOUTH -> matrices.translate(0.0f, 0.5f - verticalFloorOffset, -horizontalFloorOffset);
                    case WEST -> matrices.translate(horizontalFloorOffset, 0.5f - verticalFloorOffset, 0.0f);
                    case EAST -> matrices.translate(-horizontalFloorOffset, 0.5f - verticalFloorOffset, 0.0f);
                    default -> {}
                }
            }
        }
    }

    private void applyRotation(MatrixStack matrices, BlockState state, @Nullable PlaceableAsItem placeableAsItem) {
        float pitch = 0f;
        if (placeableAsItem != null) {
            pitch = placeableAsItem.bind$getVisualPitchDegrees();
        }

        switch (state.get(PlacedToolBlock.FACING)) {
            case WEST -> matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0));
            case SOUTH -> matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
            case EAST -> matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
            case NORTH -> matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270));
            default -> {}
        }
        switch (state.get(PlacedToolBlock.FACE)) {
            case CEILING -> matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(0 + pitch));
            case WALL -> matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90 + pitch));
            case FLOOR -> matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180 + pitch));
        }
    }
}
