package org.btwr.bind.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;
import org.btwr.bind.util.ToolPlacementProfileBuilder;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class BindToolPlacementProfiles extends ToolPlacementProfileProvider {

    private static final String[] vanillaTools = new String[]{"pickaxe", "axe", "shovel", "hoe", "sword"};

    public BindToolPlacementProfiles(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup, Consumer<Entry> consumer) {
        for (String tool : vanillaTools) {
            consumer.accept(new Entry(
                    tool,
                    ToolPlacementProfileBuilder.create()
                            .boundingBox(2f, 14f, 0f, 16f)
                            .visualOffsets(-15f, 4.75f, 0f)
                            .outlineOffsets(4f, 2f, 2f)
                            .build()
            ));
        }

        consumer.accept(new Entry(
                "chisel",
                ToolPlacementProfileBuilder.create()
                        .boundingBox(5f, 5f, 4f, 11f)
                        .visualOffsets(0f, 0f, 0f)
                        .outlineOffsets(11f, 0f, 0f)
                        .build()
        ));
    }
}

