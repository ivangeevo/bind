package org.btwr.bind.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import org.btwr.bind.block.blocks.PlacedToolBlock;
import org.btwr.bind.tag.ModTags;
import org.btwr.shared_library.tag.BTWRConventionalTags;

import java.util.HashMap;

public class ToolRenderManager {

    public enum BoundingBox {
        PICKAXES(2.0F, 14.0F, 0.0F, 16.0F),
        AXES(2.0F, 14.0F, 0.0F, 16.0F),
        SHOVELS(2.0F, 14.0F, 0.0F, 16.0F),
        HOES(2.0F, 14.0F, 0.0F, 16.0F),
        SWORDS(2.0F, 14.0F, 0.0F, 16.0F),
        TE_CHISEL(5.0F, 5.0F, 4.0F, 11.0F);

        private final float minHeight;
        private final float maxHeight;
        private final float minWidth;
        private final float maxWidth;

        BoundingBox(float minH, float maxH, float minW, float maxW) {
            this.minHeight = minH;
            this.maxHeight = maxH;
            this.minWidth = minW;
            this.maxWidth = maxW;
        }

        public static BoundingBox fromItem(Item item) {
            return getToolCategory(item, PICKAXES, AXES, SHOVELS, HOES, SWORDS, TE_CHISEL);
        }

        public float getMinHeight() {
            return this.minHeight;
        }

        public float getMaxHeight() {
            return this.maxHeight;
        }

        public float getMinWidth() {
            return this.minWidth;
        }

        public float getMaxWidth() {
            return this.maxWidth;
        }

    }

    public static class Outlines {
        public static void forChisels(BlockState state, HashMap<Direction, VoxelShape> shapesMap, float minHeight, float maxHeight, float minWidth, float maxWidth) {
            float wOnWallOffsetAmount = 11;

            float minWallHeight = 5;
            float maxWallHeight = 12f;

            float minWallWidth = 0;
            float maxWallWidth = 16f;

            switch (state.get(PlacedToolBlock.FACE)) {
                case WALL -> {
                    switch (state.get(PlacedToolBlock.FACING)) {
                        case NORTH ->
                                shapesMap.put(Direction.NORTH, Block.createCuboidShape(7.5, minWallHeight, minWallWidth, 8.5, maxWallHeight, maxWallWidth - wOnWallOffsetAmount));
                        case SOUTH ->
                                shapesMap.put(Direction.SOUTH, Block.createCuboidShape(7.5, minWallHeight, minWallWidth + wOnWallOffsetAmount, 8.5, maxWallHeight, maxWallWidth));
                        case WEST ->
                                shapesMap.put(Direction.WEST, Block.createCuboidShape(minWallWidth, minWallHeight, 7.5, maxWallWidth - wOnWallOffsetAmount, maxWallHeight, 8.5));
                        case EAST ->
                                shapesMap.put(Direction.EAST, Block.createCuboidShape(minWallWidth + wOnWallOffsetAmount, minWallHeight, 7.5, maxWallWidth, maxWallHeight, 8.5));
                        default -> {
                        }
                    }
                }
                case FLOOR -> {
                    switch (state.get(PlacedToolBlock.FACING)) {
                        case NORTH ->
                                shapesMap.put(Direction.NORTH, Block.createCuboidShape(7.5, 0, minWidth, 8.5, maxHeight, maxWidth));
                        case SOUTH ->
                                shapesMap.put(Direction.SOUTH, Block.createCuboidShape(7.5, 0, minWidth, 8.5, maxHeight, maxWidth));
                        case WEST ->
                                shapesMap.put(Direction.WEST, Block.createCuboidShape(minWidth, 0, 7.5, maxWidth, maxHeight, 8.5));
                        case EAST ->
                                shapesMap.put(Direction.EAST, Block.createCuboidShape(minWidth, 0, 7.5, maxWidth, maxHeight, 8.5));
                        default -> {
                        }
                    }
                }
                case CEILING -> {
                    switch (state.get(PlacedToolBlock.FACING)) {
                        case NORTH ->
                                shapesMap.put(Direction.NORTH, Block.createCuboidShape(7.5, 11, minWidth, 8.5, 16, maxWidth));
                        case SOUTH ->
                                shapesMap.put(Direction.SOUTH, Block.createCuboidShape(7.5, 11, minWidth, 8.5, 16, maxWidth));
                        case WEST ->
                                shapesMap.put(Direction.WEST, Block.createCuboidShape(minWidth, 11, 7.5, maxWidth, 16, 8.5));
                        case EAST ->
                                shapesMap.put(Direction.EAST, Block.createCuboidShape(minWidth, 11, 7.5, maxWidth, 16, 8.5));
                        default -> {
                        }
                    }
                }
            }
        }

        public static void forDefault(BlockState state, HashMap<Direction, VoxelShape> shapesMap, float minHeight, float maxHeight, float minWidth, float maxWidth) {
            float heightOffsetAmount = 2;
            float widthOffsetAmount = 4;

            switch (state.get(PlacedToolBlock.FACE)) {
                case WALL -> {
                    switch (state.get(PlacedToolBlock.FACING)) {
                        case NORTH ->
                                shapesMap.put(Direction.NORTH, Block.createCuboidShape(7.5, minHeight, minWidth, 8.5, maxHeight, maxWidth - widthOffsetAmount));
                        case SOUTH ->
                                shapesMap.put(Direction.SOUTH, Block.createCuboidShape(7.5, minHeight, minWidth + widthOffsetAmount, 8.5, maxHeight, maxWidth));
                        case WEST ->
                                shapesMap.put(Direction.WEST, Block.createCuboidShape(minWidth, minHeight, 7.5, maxWidth - widthOffsetAmount, maxHeight, 8.5));
                        case EAST ->
                                shapesMap.put(Direction.EAST, Block.createCuboidShape(minWidth + widthOffsetAmount, minHeight, 7.5, maxWidth, maxHeight, 8.5));
                        default -> {
                        }
                    }
                }
                case FLOOR -> {
                    switch (state.get(PlacedToolBlock.FACING)) {
                        case NORTH ->
                                shapesMap.put(Direction.NORTH, Block.createCuboidShape(7.5, minHeight - heightOffsetAmount, minWidth + heightOffsetAmount, 8.5, maxHeight - heightOffsetAmount, maxWidth - heightOffsetAmount));
                        case SOUTH ->
                                shapesMap.put(Direction.SOUTH, Block.createCuboidShape(7.5, minHeight - heightOffsetAmount, minWidth + heightOffsetAmount, 8.5, maxHeight - heightOffsetAmount, maxWidth - heightOffsetAmount));
                        case WEST ->
                                shapesMap.put(Direction.WEST, Block.createCuboidShape(minWidth + heightOffsetAmount, minHeight - heightOffsetAmount, 7.5, maxWidth - heightOffsetAmount, maxHeight - heightOffsetAmount, 8.5));
                        case EAST ->
                                shapesMap.put(Direction.EAST, Block.createCuboidShape(minWidth + heightOffsetAmount, minHeight - heightOffsetAmount, 7.5, maxWidth - heightOffsetAmount, maxHeight - heightOffsetAmount, 8.5));
                        default -> {
                        }
                    }
                }
                case CEILING -> {
                    switch (state.get(PlacedToolBlock.FACING)) {
                        case NORTH ->
                                shapesMap.put(Direction.NORTH, Block.createCuboidShape(7.5, minHeight + heightOffsetAmount, minWidth + heightOffsetAmount, 8.5, maxHeight + heightOffsetAmount, maxWidth - heightOffsetAmount));
                        case SOUTH ->
                                shapesMap.put(Direction.SOUTH, Block.createCuboidShape(7.5, minHeight + heightOffsetAmount, minWidth + heightOffsetAmount, 8.5, maxHeight + heightOffsetAmount, maxWidth - heightOffsetAmount));
                        case WEST ->
                                shapesMap.put(Direction.WEST, Block.createCuboidShape(minWidth + heightOffsetAmount, minHeight + heightOffsetAmount, 7.5, maxWidth - heightOffsetAmount, maxHeight + heightOffsetAmount, 8.5));
                        case EAST ->
                                shapesMap.put(Direction.EAST, Block.createCuboidShape(minWidth + heightOffsetAmount, minHeight + heightOffsetAmount, 7.5, maxWidth - heightOffsetAmount, maxHeight + heightOffsetAmount, 8.5));
                        default -> {
                        }
                    }
                }
            }

        }
    }

    public enum VisualOffsets {
        PICKAXES(-15.0F, 4.75F, 0.0F),
        AXES(0.0F, 5.0F, 0.0F),
        SHOVELS(15.0F, 4.5F, -2.0F),
        HOES(0.0F, 5.5F, -1.0F),
        SWORDS(45.0F, 1.5F, 0.0F),
        TE_CHISEL(0.0F, 0.0F, 0.0F);

        private final float pitchDegrees;
        private final float vOffsetPixels;
        private final float hOffsetPixels;

        VisualOffsets(float pitch, float vOffset, float hOffset) {
            this.pitchDegrees = pitch;
            this.vOffsetPixels = vOffset;
            this.hOffsetPixels = hOffset;
        }

        public static VisualOffsets fromItem(Item item) {
            return getToolCategory(item, PICKAXES, AXES, SHOVELS, HOES, SWORDS, TE_CHISEL);
        }

        public float getVisualPitchDegrees() {
            return this.pitchDegrees;
        }

        public float getVisualVerticalOffsetPixels() {
            return this.vOffsetPixels;
        }

        public float getVisualHorizontalOffsetPixels() {
            return this.hOffsetPixels;
        }

    }

    static <T> T getToolCategory(Item item, T pickaxe, T axe, T shovel, T hoe, T sword, T chisel) {
        if (!(item instanceof ToolItem)) {
            return null;
        }
        else {
            ItemStack tool = item.getDefaultStack();

            if (tool.isIn(ModTags.Items.PICKAXES)) {
                return pickaxe;
            }
            else if (tool.isIn(ModTags.Items.AXES)) {
                return axe;
            }
            else if (tool.isIn(ItemTags.SHOVELS)) {
                return shovel;
            }
            else if (tool.isIn(ItemTags.HOES)) {
                return hoe;
            }
            else if (tool.isIn(ItemTags.SWORDS)) {
                return sword;
            }
            else {
                return !tool.isIn(BTWRConventionalTags.Items.ADVANCED_CHISELS)
                        && !tool.isIn(BTWRConventionalTags.Items.MODERN_CHISELS)
                        ? null
                        : chisel;
            }
        }
    }

}