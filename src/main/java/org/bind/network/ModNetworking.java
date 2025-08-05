package org.bind.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class ModNetworking {

    public static void init() {
        PayloadTypeRegistry.playC2S()
                .register(
                        PlaceToolKeyHeldC2SPayload.PAYLOAD_ID,
                        PlaceToolKeyHeldC2SPayload.CODEC
                );

    }

}