package org.btwr.bind.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record VisualOffsetData(float pitchDegrees, float verticalOffsetPixels, float horizontalOffsetPixels) {

    public static final Codec<VisualOffsetData> CODEC =
            RecordCodecBuilder.create(i -> i.group(
                    Codec.FLOAT.fieldOf("pitch").forGetter(VisualOffsetData::pitchDegrees),
                    Codec.FLOAT.fieldOf("v_offset").forGetter(VisualOffsetData::verticalOffsetPixels),
                    Codec.FLOAT.fieldOf("h_offset").forGetter(VisualOffsetData::horizontalOffsetPixels)
            ).apply(i, VisualOffsetData::new));

}