package org.bind.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import org.bind.tag.ModTags;

import java.util.concurrent.CompletableFuture;

public class BindBlockTagProvider extends FabricTagProvider.BlockTagProvider {


    public BindBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg)
    {
        getOrCreateTagBuilder(ModTags.Blocks.AGGREGATE_SOUND_BLOCKS)
                .add(Blocks.DIRT)
                .add(Blocks.GRAVEL);

        getOrCreateTagBuilder(ModTags.Blocks.STONE_SOUND_BLOCKS)
                .forceAddTag(BlockTags.STONE_ORE_REPLACEABLES);
    }
}
