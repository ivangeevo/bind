package org.btwr.bind.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.btwr.bind.util.ToolPlacementProfile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class ToolPlacementProfileProvider implements DataProvider {

    protected final FabricDataOutput dataOutput;
    protected final CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup;

    protected ToolPlacementProfileProvider(
            FabricDataOutput dataOutput,
            CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup
    ) {
        this.dataOutput = dataOutput;
        this.registryLookup = registryLookup;
    }

    protected record Entry(String profilePath, ToolPlacementProfile profile) {}

    /** Implement to define which profiles are generated */
    protected abstract void configure(RegistryWrapper.WrapperLookup lookup, Consumer<Entry> consumer);

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        List<Entry> entries = new ArrayList<>();

        return registryLookup.thenCompose(lookup -> {
            configure(lookup, entries::add);

            CompletableFuture<?>[] futures = entries.stream()
                    .map(entry -> write(writer, entry))
                    .toArray(CompletableFuture[]::new);

            return CompletableFuture.allOf(futures);
        });
    }

    private CompletableFuture<?> write(DataWriter writer, Entry entry) {
        Path path = dataOutput
                .getResolver(DataOutput.OutputType.DATA_PACK, "tool_placement")
                .resolveJson(Identifier.of(dataOutput.getModId(), entry.profilePath()));

        return DataProvider.writeCodecToPath(
                writer,
                registryLookup.join(),
                ToolPlacementProfile.CODEC,
                entry.profile(),
                path
        );
    }

    @Override
    public String getName() {
        return "Tool Placement Profiles";
    }

}