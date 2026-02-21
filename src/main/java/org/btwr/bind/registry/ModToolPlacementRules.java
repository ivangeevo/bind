package org.btwr.bind.registry;

import net.minecraft.registry.tag.ItemTags;
import org.btwr.bind.tag.ModTags;
import org.btwr.bind.util.PlaceableToolManager;
import org.btwr.shared_library.api.tag.BTWRConventionalTags;

public class ModToolPlacementRules {

    public static void init() {
        PlaceableToolManager.Rules.register(ModTags.Items.PICKAXES, ModTags.Blocks.STONE_SOUND_BLOCKS);
        PlaceableToolManager.Rules.register(ItemTags.SHOVELS, ModTags.Blocks.AGGREGATE_SOUND_BLOCKS);
        PlaceableToolManager.Rules.register(ItemTags.HOES, ModTags.Blocks.AGGREGATE_SOUND_BLOCKS);
        PlaceableToolManager.Rules.register(ItemTags.SWORDS, ModTags.Blocks.AGGREGATE_SOUND_BLOCKS);
        PlaceableToolManager.Rules.register(ItemTags.AXES, ModTags.Blocks.WOOD_SOUND_BLOCKS);
        PlaceableToolManager.Rules.register(BTWRConventionalTags.Items.ADVANCED_CHISELS, ModTags.Blocks.STONE_SOUND_BLOCKS);
    }
}
