package org.bind.mixin;

import net.minecraft.item.HoeItem;
import org.bind.util.PlaceableAsItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(HoeItem.class)
public class HoeltemMixin implements PlaceableAsItem {
    @Override
    public float bind$getVisualPitchDegrees() {
        return 0f;
    }

    @Override
    public float bind$getVisualVerticalOffsetPixels() {
        return 5.5f;
    }

    @Override
    public float bind$getVisualHorizontalOffsetPixels() {
        return -1f;
    }
}
