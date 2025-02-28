package org.bind.util;

import com.bwt.items.BwtItems;
import com.bwt.tags.BwtBlockTags;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.bind.block.ModBlocks;
import org.bind.block.entity.PlacedToolBE;
import org.bind.tag.ModTags;
import org.tough_environment.item.ChiselToolMaterials;
import org.tough_environment.item.ModItems;

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
     * Places the tool block with the correct state and plays sound.
     */
    public static boolean placeToolBlock(World world, BlockPos pos, ItemUsageContext context) {
        ItemStack originalTool = context.getStack();
        ItemStack tool = originalTool.copy();
        PlayerEntity playerEntity = context.getPlayer();

        BlockPos attachPos = pos.offset(context.getSide().getOpposite());
        BlockState stateAtPos = world.getBlockState(attachPos);

        // Validate if the tool can be placed on this block
        if (!isValidPlacement(tool, stateAtPos)) {
            return false;
        }

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
        /**
        world.playSound(
                null,
                pos,
                getPlaceSound(toolAttachedToState),
                SoundCategory.BLOCKS,
                (soundGroup.getVolume() + 1.0F) / 2.0F,
                soundGroup.getPitch() * 1.2F
        );
         **/
        ToolPlacementSoundManager.playCraftingSound(stateAtPos, playerEntity);

        world.emitGameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Emitter.of(playerEntity, placedState));
        originalTool.decrementUnlessCreative(1, playerEntity);
        return true;
    }

    private static boolean isValidPlacement(ItemStack toolStack, BlockState stateAtPos) {
        if (toolStack.isIn(ItemTags.PICKAXES)) {
            return stateAtPos.isIn(ModTags.Blocks.STONE_SOUND_BLOCKS);
        }

        if (toolStack.isIn(ItemTags.SHOVELS) || toolStack.isIn(ItemTags.HOES)) {
            return stateAtPos.isIn(ModTags.Blocks.AGGREGATE_SOUND_BLOCKS);
        }

        if (toolStack.isIn(ItemTags.SWORDS)) {
            return stateAtPos.isIn(ModTags.Blocks.AGGREGATE_SOUND_BLOCKS) || stateAtPos.isIn(ModTags.Blocks.WOOD_SOUND_BLOCKS);
        }

        if (toolStack.isIn(ItemTags.AXES)) {
            return stateAtPos.isIn(ModTags.Blocks.WOOD_SOUND_BLOCKS);
        }

        if (toolStack.isOf(ModItems.CHISEL_IRON) || toolStack.isOf(ModItems.CHISEL_DIAMOND)) {
            return stateAtPos.isIn(ModTags.Blocks.STONE_SOUND_BLOCKS);
        }

        if (toolStack.isOf(BwtItems.netheriteMattockItem)) {
            return stateAtPos.isIn(BwtBlockTags.MATTOCK_MINEABLE);
        }

        if (toolStack.isOf(BwtItems.netheriteBattleAxeItem)) {
            return stateAtPos.isIn(BwtBlockTags.BATTLEAXE_MINEABLE);
        }

        return true; // Allow all other tools
    }

}
