package org.bind;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.bind.datagen.BindBlockTagProvider;
import org.bind.datagen.BindItemTagProvider;


public class BindModDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(BindBlockTagProvider::new);
        pack.addProvider(BindItemTagProvider::new);

    }
}
