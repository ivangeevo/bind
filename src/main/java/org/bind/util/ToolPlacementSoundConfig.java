package org.bind.util;

import net.minecraft.block.Block;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import org.bind.tag.ModTags;

public enum ToolPlacementSoundConfig
{
    AGGREGATE_SOUND(
            ModTags.Blocks.AGGREGATE_SOUND_BLOCKS,
            SoundEvents.BLOCK_GRAVEL_PLACE,
            0.5F,
            1.25F,
            0.25F
    ),
    WOODEN_SOUND(
            ModTags.Blocks.WOOD_SOUND_BLOCKS,
            SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR,
            0.5F,
            1.25F,
            0.25F
    ),
    STONE_TOOL_SOUND(
            ModTags.Blocks.STONE_SOUND_BLOCKS,
            SoundEvents.BLOCK_ANVIL_LAND,
            0.5F,
            1.75F,
            0.25F
    );

    private final TagKey<Block> tag;
    private final SoundEvent sound;
    private final float volume;
    private final float basePitch;
    private final float pitchVariance;

    ToolPlacementSoundConfig(TagKey<Block> tag, SoundEvent sound, float volume, float basePitch, float pitchVariance) {
        this.tag = tag;
        this.sound = sound;
        this.volume = volume;
        this.basePitch = basePitch;
        this.pitchVariance = pitchVariance;
    }

    public TagKey<Block> getTag() {
        return tag;
    }

    public SoundEvent getSound() {
        return sound;
    }

    public float getVolume() {
        return volume;
    }

    public float getBasePitch() {
        return basePitch;
    }

    public float getPitchVariance() {
        return pitchVariance;
    }
}
