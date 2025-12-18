package org.btwr.bind.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record BoundingBoxData(float minHeight, float maxHeight, float minWidth, float maxWidth) {

    public static final Codec<BoundingBoxData> CODEC =
            RecordCodecBuilder.create(i -> i.group(
                    Codec.FLOAT.fieldOf("min_height").forGetter(BoundingBoxData::minHeight),
                    Codec.FLOAT.fieldOf("max_height").forGetter(BoundingBoxData::maxHeight),
                    Codec.FLOAT.fieldOf("min_width").forGetter(BoundingBoxData::minWidth),
                    Codec.FLOAT.fieldOf("max_width").forGetter(BoundingBoxData::maxWidth)
            ).apply(i, BoundingBoxData::new));

}
