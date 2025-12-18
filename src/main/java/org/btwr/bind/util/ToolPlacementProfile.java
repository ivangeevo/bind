package org.btwr.bind.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ToolPlacementProfile(BoundingBoxData boundingBox, VisualOffsetData visualOffsets, OutlineOffsets outlineOffsets)
{

    public static final Codec<ToolPlacementProfile> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    BoundingBoxData.CODEC.fieldOf("bounding_box")
                            .forGetter(ToolPlacementProfile::boundingBox),
                    VisualOffsetData.CODEC.fieldOf("visual_offsets")
                            .forGetter(ToolPlacementProfile::visualOffsets),
                    OutlineOffsets.CODEC.fieldOf("outline_offsets")
                            .forGetter(ToolPlacementProfile::outlineOffsets)
                    ).apply(instance, ToolPlacementProfile::new)
            );

}