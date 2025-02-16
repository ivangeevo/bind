package org.bind.mixin;

import net.minecraft.item.PickaxeItem;
import org.bind.util.PlaceableAsItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PickaxeItem.class)
public abstract class PickaxeItemMixin implements PlaceableAsItem {
    @Override
    public float bind$getVisualPitchDegrees() {
        return -15f;
    }

    @Override
    public float bind$getVisualVerticalOffsetPixels() {
        return 4.75f;
    }

    @Override
    public float bind$getVisualHorizontalOffsetPixels() {
        return 0f;
    }
}
