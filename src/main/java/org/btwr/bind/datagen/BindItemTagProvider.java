package org.btwr.bind.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import org.btwr.bind.tag.ModTags;

import java.util.concurrent.CompletableFuture;

public class BindItemTagProvider extends FabricTagProvider.ItemTagProvider {

    private final Identifier BWT_MATTOCK = Identifier.of("bwt", "netherite_mattock");
    private final Identifier BWT_BATTLE_AXE = Identifier.of("bwt", "netherite_battle_axe");

    private final Identifier TE_CHISEL_WOOD = Identifier.of("tough_environment", "chisel_wood");
    private final Identifier TE_CHISEL_IRON = Identifier.of("tough_environment", "chisel_iron");
    private final Identifier TE_CHISEL_DIAMOND = Identifier.of("tough_environment", "chisel_diamond");

    public BindItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        this.getOrCreateTagBuilder(ModTags.Items.SPECIAL_MODDED_TOOLS)
                // Tough Environment
                .addOptional(TE_CHISEL_WOOD)
                .addOptional(TE_CHISEL_IRON)
                .addOptional(TE_CHISEL_DIAMOND)

                // Better With Time
                .addOptional(BWT_MATTOCK)
                .addOptional(BWT_BATTLE_AXE);

        this.getOrCreateTagBuilder(ModTags.Items.AXES)
                .forceAddTag(ItemTags.AXES)
                .addOptional(BWT_BATTLE_AXE);

        this.getOrCreateTagBuilder(ModTags.Items.PICKAXES)
                .forceAddTag(ItemTags.PICKAXES)
                .addOptional(BWT_MATTOCK);

        this.getOrCreateTagBuilder(ModTags.Items.VANILLA_PLACEABLE_TOOLS)
                .addTag(ModTags.Items.PICKAXES)
                .addTag(ModTags.Items.AXES)
                .forceAddTag(ItemTags.SHOVELS)
                .forceAddTag(ItemTags.HOES)
                .forceAddTag(ItemTags.SWORDS);
    }

}