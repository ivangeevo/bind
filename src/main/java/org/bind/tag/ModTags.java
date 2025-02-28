package org.bind.tag;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.bind.BindMod;

public class ModTags {

    public static class Blocks {

        public static final TagKey<Block> AGGREGATE_SOUND_BLOCKS = createTag("aggregate_sound_blocks");
        public static final TagKey<Block> STONE_SOUND_BLOCKS = createTag("stone_sound_blocks");
        public static final TagKey<Block> WOOD_SOUND_BLOCKS = createTag("wood_sound_blocks");

        private static TagKey<Block> createTag (String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(BindMod.MOD_ID, name));
        }
    }

    public static class Items {

        /** Tools from other mods need to be put in this tag in order to be made placeable **/
        public static final TagKey<Item> SPECIAL_MODDED_TOOLS = createTag("special_modded_tools");

        private static TagKey<Item> createTag (String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(BindMod.MOD_ID, name));
        }
    }
}
