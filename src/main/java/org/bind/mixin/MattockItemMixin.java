package org.bind.mixin;

import com.bwt.items.MattockItem;
import org.bind.util.PlaceableAsItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MattockItem.class)
public abstract class MattockItemMixin implements PlaceableAsItem {

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
