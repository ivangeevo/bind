package org.bind.util;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ToolPlacementSoundManager
{

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
