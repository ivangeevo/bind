package org.bind.datagen;

import com.bwt.items.BwtItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import org.bind.tag.ModTags;
import org.tough_environment.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class BindItemTagProvider extends FabricTagProvider.ItemTagProvider {


    public BindItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {


        getOrCreateTagBuilder(ModTags.Items.SPECIAL_MODDED_TOOLS)
                // Tough Environment chisels
                .add(ModItems.CHISEL_WOOD)
                .add(ModItems.CHISEL_IRON)
                .add(ModItems.CHISEL_DIAMOND)

                // Better With Time tools
                .add(BwtItems.netheriteMattockItem)
                .add(BwtItems.netheriteBattleAxeItem)
        ;



    }
}
