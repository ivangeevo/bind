package org.bind.mixin;

import net.minecraft.item.AxeItem;
import org.bind.util.PlaceableAsItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin implements PlaceableAsItem {
    @Override
    public float bind$getVisualPitchDegrees() {
        return 0f;
    }

    @Override
    public float bind$getVisualVerticalOffsetPixels() {
        return 5f;
    }

    @Override
    public float bind$getVisualHorizontalOffsetPixels() {
        return 0f;
    }
}
