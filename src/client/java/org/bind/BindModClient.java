package org.bind;

import net.fabricmc.api.ClientModInitializer;
import org.bind.block.entity.render.ModRendering;
import org.bind.networking.ModClientNetworking;

public class BindModClient implements ClientModInitializer
{

    @Override
    public void onInitializeClient() {
        ModRendering.init();
        ModClientNetworking.init();
    }

}
