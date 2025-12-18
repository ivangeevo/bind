package org.btwr.bind.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public final class ToolPlacementRegistry {

    public static final ToolPlacementProfile DEFAULT_PROFILE =
            new ToolPlacementProfile(
                    new BoundingBoxData(2f, 14f, 0f, 16f),
                    new VisualOffsetData(0f, 4.5f, 0f),
                    new OutlineOffsets(4f, 2f, 2f)
            );

    private static final Map<Identifier, ToolPlacementProfile> PROFILES = new HashMap<>();

    public static ToolPlacementProfile get(ItemStack stack) {
        for (var entry : PROFILES.entrySet()) {
            Identifier id = entry.getKey();

            TagKey<Item> tag = TagKey.of(
                    RegistryKeys.ITEM,
                    Identifier.of(id.getNamespace(), "tool_placement/" + id.getPath())
            );

            if (stack.isIn(tag)) {
                return entry.getValue();
            }
        }
        return DEFAULT_PROFILE;
    }

}