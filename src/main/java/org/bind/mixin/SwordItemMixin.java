package org.bind.mixin;

import net.minecraft.item.SwordItem;
import org.bind.util.PlaceableAsItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SwordItem.class)
public class SwordItemMixin implements PlaceableAsItem {
    @Override
    public float bind$getVisualPitchDegrees() {
        return 45;
    }

    @Override
    public float bind$getVisualVerticalOffsetPixels() {
        return 1.5f;
    }
}
