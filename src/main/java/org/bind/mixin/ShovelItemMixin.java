package org.bind.mixin;

import net.minecraft.item.ShovelItem;
import org.bind.util.PlaceableAsItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShovelItem.class)
public abstract class ShovelItemMixin implements PlaceableAsItem {
    @Override
    public float bind$getVisualPitchDegrees() {
        return 15f;
    }

    @Override
    public float bind$getVisualVerticalOffsetPixels() {
        return 4.5f;
    }

    @Override
    public float bind$getVisualHorizontalOffsetPixels() {
        return -2f;
    }
}
