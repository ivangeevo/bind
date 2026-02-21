package org.btwr.bind.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import org.btwr.bind.tag.ModTags;
import org.btwr.shared_library.api.tag.BTWRConventionalTags;

import java.util.concurrent.CompletableFuture;

public class BindBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public BindBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        this.getOrCreateTagBuilder(ModTags.Blocks.AGGREGATE_SOUND_BLOCKS)
                .forceAddTag(BlockTags.SHOVEL_MINEABLE)
                .forceAddTag(BlockTags.HOE_MINEABLE);
        
        this.getOrCreateTagBuilder(ModTags.Blocks.STONE_SOUND_BLOCKS)
                .forceAddTag(BlockTags.PICKAXE_MINEABLE)
                .forceAddTag(BlockTags.BASE_STONE_OVERWORLD)
                .forceAddTag(BlockTags.BASE_STONE_NETHER);
        
        this.getOrCreateTagBuilder(ModTags.Blocks.WOOD_SOUND_BLOCKS)
                .forceAddTag(BlockTags.AXE_MINEABLE)
                .forceAddTag(BlockTags.LOGS)
                .forceAddTag(BlockTags.PLANKS)
                .forceAddTag(BlockTags.SLABS)
                .forceAddTag(BlockTags.STAIRS)
                .addOptionalTag(BTWRConventionalTags.Blocks.STUMP_BLOCKS);
    }

}