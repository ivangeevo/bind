package org.bind.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import org.bind.block.blocks.PlacedToolBlock;
import org.bind.util.PlaceableAsItem;
import org.spongepowered.asm.mixin.Mixin;

import java.util.HashMap;

@Mixin(ShovelItem.class)
public abstract class ShovelItemMixin implements PlaceableAsItem {
    @Override
    public float bind$getVisualPitchDegrees() {
        return 15f;
    }

    @Override
    public float bind$getVisualVerticalOffsetPixels() {
        return 4.5f;
    }

    @Override
    public float bind$getVisualHorizontalOffsetPixels() {
        return -2f;
    }

    @Override
    public float bind$getBlockBoundingBoxMinHeightPixels() {
        return 2f;
    }

    @Override
    public float bind$getBlockBoundingBoxMaxHeightPixels() {
        return 14f;
    }

    @Override
    public void bind$getOutlineShapesForTool(BlockState state, HashMap<Direction, VoxelShape> shapesMap, float minHeight, float maxHeight, float minWidth, float maxWidth) {
        float hOffsetAmount = 2;
        float wOffsetAmount = 4;

        switch (state.get(PlacedToolBlock.FACE)) {
            case WALL -> {
                switch (state.get(PlacedToolBlock.FACING)) {
                    case NORTH -> shapesMap.put(Direction.NORTH, Block.createCuboidShape(7.5, minHeight, minWidth, 8.5, maxHeight, maxWidth - wOffsetAmount));
                    case SOUTH -> shapesMap.put(Direction.SOUTH, Block.createCuboidShape(7.5, minHeight, minWidth + wOffsetAmount, 8.5, maxHeight, maxWidth));
                    case WEST -> shapesMap.put(Direction.WEST, Block.createCuboidShape(minWidth, minHeight, 7.5, maxWidth - wOffsetAmount, maxHeight, 8.5));
                    case EAST -> shapesMap.put(Direction.EAST, Block.createCuboidShape(minWidth + wOffsetAmount, minHeight, 7.5, maxWidth, maxHeight, 8.5));
                    default -> {}
                }
            }
            case FLOOR -> {
                switch (state.get(PlacedToolBlock.FACING)) {
                    case NORTH -> shapesMap.put(Direction.NORTH, Block.createCuboidShape(7.5, minHeight - hOffsetAmount, minWidth + hOffsetAmount, 8.5, maxHeight - hOffsetAmount, maxWidth - hOffsetAmount));
                    case SOUTH -> shapesMap.put(Direction.SOUTH, Block.createCuboidShape(7.5, minHeight - hOffsetAmount, minWidth + hOffsetAmount, 8.5, maxHeight - hOffsetAmount, maxWidth - hOffsetAmount));
                    case WEST -> shapesMap.put(Direction.WEST, Block.createCuboidShape(minWidth + hOffsetAmount, minHeight - hOffsetAmount, 7.5, maxWidth - hOffsetAmount, maxHeight - hOffsetAmount, 8.5));
                    case EAST -> shapesMap.put(Direction.EAST, Block.createCuboidShape(minWidth + hOffsetAmount, minHeight - hOffsetAmount, 7.5, maxWidth - hOffsetAmount, maxHeight - hOffsetAmount, 8.5));
                    default -> {}
                }
            }
            case CEILING -> {
                switch (state.get(PlacedToolBlock.FACING)) {
                    case NORTH -> shapesMap.put(Direction.NORTH, Block.createCuboidShape(7.5, minHeight + hOffsetAmount, minWidth + hOffsetAmount, 8.5, maxHeight + hOffsetAmount, maxWidth - hOffsetAmount));
                    case SOUTH -> shapesMap.put(Direction.SOUTH, Block.createCuboidShape(7.5, minHeight + hOffsetAmount, minWidth + hOffsetAmount, 8.5, maxHeight + hOffsetAmount, maxWidth - hOffsetAmount));
                    case WEST -> shapesMap.put(Direction.WEST, Block.createCuboidShape(minWidth + hOffsetAmount, minHeight + hOffsetAmount, 7.5, maxWidth - hOffsetAmount, maxHeight + hOffsetAmount, 8.5));
                    case EAST -> shapesMap.put(Direction.EAST, Block.createCuboidShape(minWidth + hOffsetAmount, minHeight + hOffsetAmount, 7.5, maxWidth - hOffsetAmount, maxHeight + hOffsetAmount, 8.5));
                    default -> {}
                }
            }
        }

    }
}
