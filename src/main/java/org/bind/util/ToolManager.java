package org.bind.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.bind.block.ModBlocks;
import org.bind.block.blocks.PlacedToolBlock;
import org.bind.tag.ModTags;

import java.util.Objects;

public class ToolManager {

    public enum ToolType {
        PICKAXE, AXE, SHOVEL, HOE, SWORD, UNKNOWN
    }

    /**
     * Gets the tool associated with a tool based on its type.
     */
    public static Block getBlockFromTool(ItemStack tool) {
        ToolType toolType = determineToolType(tool);

        return switch (toolType) {
            case PICKAXE -> ModBlocks.PLACED_PICKAXE;
            case AXE -> ModBlocks.PLACED_AXE;
            case SHOVEL -> ModBlocks.PLACED_SHOVEL;
            case HOE -> ModBlocks.PLACED_HOE;
            case SWORD -> ModBlocks.PLACED_SWORD;
            default -> Blocks.AIR;
        };
    }

    /**
     * Determines the type of tool based on the item stack.
     */
    public static ToolType determineToolType(ItemStack tool) {
        if (tool.getItem() instanceof ToolItem toolItem) {
            if (toolItem.getMaterial() == ToolMaterials.WOOD
                    || toolItem.getMaterial() == ToolMaterials.STONE
                    || toolItem.getMaterial() == ToolMaterials.IRON
                    || toolItem.getMaterial() == ToolMaterials.DIAMOND
                    || toolItem.getMaterial() == ToolMaterials.NETHERITE) {

                if (tool.isIn(ItemTags.PICKAXES)) return ToolType.PICKAXE;
                if (tool.isIn(ItemTags.AXES)) return ToolType.AXE;
                if (tool.isIn(ItemTags.SHOVELS)) return ToolType.SHOVEL;
                if (tool.isIn(ItemTags.HOES)) return ToolType.HOE;
                if (tool.isIn(ItemTags.SWORDS)) return ToolType.SWORD;
            }
        }

        return ToolType.UNKNOWN;
    }

    /**
     * Plays the appropriate place sound based on the block state.
     */
    public static SoundEvent getPlaceSound(BlockState state) {
        if (state.isIn(ModTags.Blocks.AGGREGATE_SOUND_BLOCKS)) {
            return SoundEvents.BLOCK_GRAVEL_PLACE;
        }
        if (state.isIn(ModTags.Blocks.STONE_SOUND_BLOCKS)) {
            return SoundEvents.BLOCK_STONE_PLACE;
        }
        return SoundEvents.BLOCK_WOOD_PLACE; // Default sound
    }

    /**
     * Places the tool block with the correct state and plays sound.
     */
    public static boolean placeToolBlock(World world, BlockPos pos, ItemStack tool, BlockState state, Direction facing) {
        Block toolBlock = getBlockFromTool(tool);
        if (toolBlock != Blocks.AIR) {
            world.setBlockState(pos, toolBlock.getDefaultState().with(PlacedToolBlock.FACING, facing));
            world.playSound(null, pos, Objects.requireNonNullElse(getPlaceSound(state), SoundEvents.BLOCK_WOOD_PLACE), SoundCategory.BLOCKS, 1.0F, 1.0F);
            return true;
        }
        return false;
    }
}
