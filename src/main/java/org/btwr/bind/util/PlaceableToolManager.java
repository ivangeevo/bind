package org.btwr.bind.util;

import com.bwt.items.BwtItems;
import com.bwt.tags.BwtBlockTags;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.btwr.bind.block.ModBlocks;
import org.btwr.bind.block.entity.PlacedToolBE;
import org.btwr.bind.tag.ModTags;
import org.btwr.shared_library.api.tag.BTWRConventionalTags;

import java.util.ArrayList;
import java.util.List;

public class PlaceableToolManager {

    public record ToolPlacementRule(TagKey<Item> toolTag, TagKey<Block> validSurfaceTag) {}

    /** Define rules for which surface a specific tool can be placed on **/
    public static final class Rules {

        private static final List<ToolPlacementRule> RULES = new ArrayList<>();

        public static void register(TagKey<Item> toolTag, TagKey<Block> surfaceTag) {
            RULES.add(new ToolPlacementRule(toolTag, surfaceTag));
        }

        public static boolean canPlace(ItemStack tool, BlockState surface) {
            for (ToolPlacementRule rule : RULES) {
                if (tool.isIn(rule.toolTag()) && surface.isIn(rule.validSurfaceTag())) {
                    return true;
                }
            }
            return false;
        }
    }



    /**
     * Determines the type of tool based on the item stack.
     */
    public static boolean isValidTool(ItemStack tool) {
        if (!(tool.getItem() instanceof ToolItem)) {
            return false;
        }
        else {
            return tool.isIn(ModTags.Items.PICKAXES)
                    || tool.isIn(ModTags.Items.AXES)
                    || tool.isIn(ItemTags.SHOVELS)
                    || tool.isIn(ItemTags.HOES)
                    || tool.isIn(ItemTags.SWORDS)
                    || tool.isIn(BTWRConventionalTags.Items.ADVANCED_CHISELS)
                    || tool.isIn(BTWRConventionalTags.Items.MODERN_CHISELS);
        }
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

        assert playerEntity != null;
        playPlacementSound(stateAtPos, playerEntity);

        world.emitGameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Emitter.of(playerEntity, placedState));
        originalTool.decrementUnlessCreative(1, playerEntity);
        return true;
    }

    /**
    private static boolean isValidPlacement(ItemStack toolStack, BlockState stateAtPos) {
        return Rules.canPlace(toolStack, stateAtPos);
    }
     **/

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

        if (toolStack.isIn(BTWRConventionalTags.Items.ADVANCED_CHISELS)) {
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

    public static void playPlacementSound(BlockState state, PlayerEntity player) {
        World world = player.getWorld();
        BlockPos thisPos = player.getBlockPos();
        SoundEvent sound = null;
        float volume = 0.1F;
        float pitch = 1.0F;

        // Loop through the enum to find the matching tag
        for (ToolPlacementSoundConfig config : ToolPlacementSoundConfig.values()) {
            if (state.isIn(config.getTag())) {
                sound = config.getSound();
                volume = config.getVolume();
                pitch = config.getBasePitch() + (world.random.nextFloat() * config.getPitchVariance());
                break;
            }
        }

        // Play the sound if one was found
        if (sound != null) {
            world.playSound(null, thisPos, sound, SoundCategory.BLOCKS, volume, pitch);
        }
    }

}