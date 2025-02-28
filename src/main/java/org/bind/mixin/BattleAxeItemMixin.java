package org.bind.mixin;

import com.bwt.items.BattleAxeItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import org.bind.block.blocks.PlacedToolBlock;
import org.bind.util.PlaceableAsItem;
import org.spongepowered.asm.mixin.Mixin;

import java.util.HashMap;

@Mixin(BattleAxeItem.class)
public abstract class BattleAxeItemMixin implements PlaceableAsItem {

    @Override
    public float bind$getVisualPitchDegrees() {
        return 0f;
    }

    @Override
    public float bind$getVisualVerticalOffsetPixels() {
        return 5f;
    }

    @Override
    public float bind$getVisualHorizontalOffsetPixels() {
        return 0f;
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
        float heightOffsetAmount = 2;
        float widthOffsetAmount = 4;

        switch (state.get(PlacedToolBlock.FACE)) {
            case WALL -> {
                switch (state.get(PlacedToolBlock.FACING)) {
                    case NORTH -> shapesMap.put(Direction.NORTH, Block.createCuboidShape(7.5, minHeight, minWidth, 8.5, maxHeight, maxWidth - widthOffsetAmount));
                    case SOUTH -> shapesMap.put(Direction.SOUTH, Block.createCuboidShape(7.5, minHeight, minWidth + widthOffsetAmount, 8.5, maxHeight, maxWidth));
                    case WEST -> shapesMap.put(Direction.WEST, Block.createCuboidShape(minWidth, minHeight, 7.5, maxWidth - widthOffsetAmount, maxHeight, 8.5));
                    case EAST -> shapesMap.put(Direction.EAST, Block.createCuboidShape(minWidth + widthOffsetAmount, minHeight, 7.5, maxWidth, maxHeight, 8.5));
                    default -> {}
                }
            }
            case FLOOR -> {
                switch (state.get(PlacedToolBlock.FACING)) {
                    case NORTH -> shapesMap.put(Direction.NORTH, Block.createCuboidShape(7.5, minHeight - heightOffsetAmount, minWidth + heightOffsetAmount, 8.5, maxHeight - heightOffsetAmount, maxWidth - heightOffsetAmount));
                    case SOUTH -> shapesMap.put(Direction.SOUTH, Block.createCuboidShape(7.5, minHeight - heightOffsetAmount, minWidth + heightOffsetAmount, 8.5, maxHeight - heightOffsetAmount, maxWidth - heightOffsetAmount));
                    case WEST -> shapesMap.put(Direction.WEST, Block.createCuboidShape(minWidth + heightOffsetAmount, minHeight - heightOffsetAmount, 7.5, maxWidth - heightOffsetAmount, maxHeight - heightOffsetAmount, 8.5));
                    case EAST -> shapesMap.put(Direction.EAST, Block.createCuboidShape(minWidth + heightOffsetAmount, minHeight - heightOffsetAmount, 7.5, maxWidth - heightOffsetAmount, maxHeight - heightOffsetAmount, 8.5));
                    default -> {}
                }
            }
            case CEILING -> {
                switch (state.get(PlacedToolBlock.FACING)) {
                    case NORTH -> shapesMap.put(Direction.NORTH, Block.createCuboidShape(7.5, minHeight + heightOffsetAmount, minWidth + heightOffsetAmount, 8.5, maxHeight + heightOffsetAmount, maxWidth - heightOffsetAmount));
                    case SOUTH -> shapesMap.put(Direction.SOUTH, Block.createCuboidShape(7.5, minHeight + heightOffsetAmount, minWidth + heightOffsetAmount, 8.5, maxHeight + heightOffsetAmount, maxWidth - heightOffsetAmount));
                    case WEST -> shapesMap.put(Direction.WEST, Block.createCuboidShape(minWidth + heightOffsetAmount, minHeight + heightOffsetAmount, 7.5, maxWidth - heightOffsetAmount, maxHeight + heightOffsetAmount, 8.5));
                    case EAST -> shapesMap.put(Direction.EAST, Block.createCuboidShape(minWidth + heightOffsetAmount, minHeight + heightOffsetAmount, 7.5, maxWidth - heightOffsetAmount, maxHeight + heightOffsetAmount, 8.5));
                    default -> {}
                }
            }
        }

    }

}
