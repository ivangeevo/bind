package org.bind.util;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.bind.block.ModBlocks;
import org.bind.block.entity.PlacedToolBE;
import org.bind.tag.ModTags;
import org.tough_environment.item.ChiselToolMaterials;

public class PlaceableToolManager {

    /**
     * Determines the type of tool based on the item stack.
     */
    public static boolean isValidTool(ItemStack tool) {
        if (!(tool.getItem() instanceof ToolItem toolItem)) {
            return false;
        }

        if (toolItem.getMaterial() != ToolMaterials.WOOD
                && toolItem.getMaterial() != ToolMaterials.STONE
                && toolItem.getMaterial() != ToolMaterials.IRON
                && toolItem.getMaterial() != ToolMaterials.DIAMOND
                && toolItem.getMaterial() != ToolMaterials.NETHERITE
                && !isViableChiselToolMaterial(toolItem)) {
            return false;
        }

        return tool.isIn(ItemTags.PICKAXES)
                || tool.isIn(ItemTags.AXES)
                || tool.isIn(ItemTags.SHOVELS)
                || tool.isIn(ItemTags.HOES)
                || tool.isIn(ItemTags.SWORDS)
                || tool.isIn(ModTags.Items.SPECIAL_MODDED_TOOLS)
                ;
    }

    private static boolean isViableChiselToolMaterial(ToolItem toolItem) {
        return toolItem.getMaterial() == ChiselToolMaterials.WOOD
                || toolItem.getMaterial() != ChiselToolMaterials.IRON
                || toolItem.getMaterial() != ChiselToolMaterials.DIAMOND;
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
    public static boolean placeToolBlock(World world, BlockPos pos, ItemUsageContext context) {
        ItemStack originalTool = context.getStack();
        ItemStack tool = originalTool.copy();
        PlayerEntity playerEntity = context.getPlayer();

        BlockState placedState = ModBlocks.PLACED_TOOL.getPlacementState(new ItemPlacementContext(context));
        if (placedState == null) {
            return false;
        }
        world.setBlockState(pos, placedState);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof PlacedToolBE placedToolBlockEntity) {
            placedToolBlockEntity.setToolStack(tool);
        }
        placedState.getBlock().onPlaced(world, pos, placedState, playerEntity, tool);
        if (playerEntity instanceof ServerPlayerEntity) {
            Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)playerEntity, pos, tool);
        }
        BlockState toolAttachedToState = context.getWorld().getBlockState(pos);
        BlockSoundGroup soundGroup = toolAttachedToState.getSoundGroup();
        world.playSound(
                playerEntity,
                pos,
                getPlaceSound(toolAttachedToState),
                SoundCategory.BLOCKS,
                (soundGroup.getVolume() + 1.0F) / 2.0F,
                soundGroup.getPitch() * 0.8F
        );
        world.emitGameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Emitter.of(playerEntity, placedState));
        originalTool.decrementUnlessCreative(1, playerEntity);
        return true;
    }
}
