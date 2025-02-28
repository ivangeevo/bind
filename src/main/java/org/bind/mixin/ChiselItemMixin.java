package org.bind.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import org.bind.block.blocks.PlacedToolBlock;
import org.bind.util.PlaceableAsItem;
import org.spongepowered.asm.mixin.Mixin;
import org.tough_environment.item.items.ChiselItem;

import java.util.HashMap;

@Mixin(ChiselItem.class)
public abstract class ChiselItemMixin implements PlaceableAsItem {

    @Override
    public float bind$getBlockBoundingBoxMinHeightPixels() {
        return 5f;
    }

    @Override
    public float bind$getBlockBoundingBoxMaxHeightPixels() {
        return 5f;
    }

    @Override
    public float bind$getBlockBoundingBoxMinWidthPixels() {
        return 4f;
    }

    @Override
    public float bind$getBlockBoundingBoxMaxWidthPixels() {
        return 11f;
    }

    @Override
    public void bind$getOutlineShapesForTool(BlockState state, HashMap<Direction, VoxelShape> shapesMap, float minHeight, float maxHeight, float minWidth, float maxWidth) {
        float hOffsetAmount = 2;
        float wOffsetAmount = 11;


        float hOnWallOffsetAmount = 2;
        float wOnWallOffsetAmount = 11;

        float minWallHeight = 5;
        float maxWallHeight = 12f;

        float minWallWidth = 0;
        float maxWallWidth = 16f;

        switch (state.get(PlacedToolBlock.FACE)) {
            case WALL -> {
                switch (state.get(PlacedToolBlock.FACING)) {
                    case NORTH -> shapesMap.put(Direction.NORTH, Block.createCuboidShape(7.5, minWallHeight, minWallWidth, 8.5, maxWallHeight, maxWallWidth - wOnWallOffsetAmount));
                    case SOUTH -> shapesMap.put(Direction.SOUTH, Block.createCuboidShape(7.5, minWallHeight, minWallWidth + wOnWallOffsetAmount, 8.5, maxWallHeight, maxWallWidth));
                    case WEST -> shapesMap.put(Direction.WEST, Block.createCuboidShape(minWallWidth, minWallHeight, 7.5, maxWallWidth - wOnWallOffsetAmount, maxWallHeight, 8.5));
                    case EAST -> shapesMap.put(Direction.EAST, Block.createCuboidShape(minWallWidth + wOnWallOffsetAmount, minWallHeight, 7.5, maxWallWidth, maxWallHeight, 8.5));
                    default -> {}
                }
            }
            case FLOOR -> {
                switch (state.get(PlacedToolBlock.FACING)) {
                    case NORTH -> shapesMap.put(Direction.NORTH, Block.createCuboidShape(7.5, 0, minWidth, 8.5, maxHeight, maxWidth));
                    case SOUTH -> shapesMap.put(Direction.SOUTH, Block.createCuboidShape(7.5, 0, minWidth, 8.5, maxHeight, maxWidth));
                    case WEST -> shapesMap.put(Direction.WEST, Block.createCuboidShape(minWidth, 0, 7.5, maxWidth, maxHeight, 8.5));
                    case EAST -> shapesMap.put(Direction.EAST, Block.createCuboidShape(minWidth, 0, 7.5, maxWidth, maxHeight, 8.5));
                    default -> {}
                }
            }
            case CEILING -> {
                switch (state.get(PlacedToolBlock.FACING)) {
                    case NORTH -> shapesMap.put(Direction.NORTH, Block.createCuboidShape(7.5, 11, minWidth, 8.5, 16, maxWidth));
                    case SOUTH -> shapesMap.put(Direction.SOUTH, Block.createCuboidShape(7.5, 11, minWidth, 8.5, 16, maxWidth));
                    case WEST -> shapesMap.put(Direction.WEST, Block.createCuboidShape(minWidth, 11, 7.5, maxWidth, 16, 8.5));
                    case EAST -> shapesMap.put(Direction.EAST, Block.createCuboidShape(minWidth, 11, 7.5, maxWidth, 16, 8.5));
                    default -> {}
                }
            }
        }

    }


}
