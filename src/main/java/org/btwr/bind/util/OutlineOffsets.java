package org.btwr.bind.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record OutlineOffsets(float wallWidthOffset, float floorHeightOffset, float ceilingHeightOffset) {

    public static final Codec<OutlineOffsets> CODEC =
            RecordCodecBuilder.create(i -> i.group(
                    Codec.FLOAT
                            .optionalFieldOf("wall_width_offset", 4f)
                            .forGetter(OutlineOffsets::wallWidthOffset),
                    Codec.FLOAT
                            .optionalFieldOf("floor_height_offset", 2f)
                            .forGetter(OutlineOffsets::floorHeightOffset),
                    Codec.FLOAT
                            .optionalFieldOf("ceiling_height_offset", 2f)
                            .forGetter(OutlineOffsets::ceilingHeightOffset)
            ).apply(i, OutlineOffsets::new));

}
