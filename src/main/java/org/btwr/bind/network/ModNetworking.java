package org.btwr.bind.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import org.btwr.bind.util.ServerSharedInputState;

public class ModNetworking {

    public static void init() {
        PayloadTypeRegistry.playC2S().register(
                PlaceToolKeyHeldC2SPayload.PAYLOAD_ID,
                PlaceToolKeyHeldC2SPayload.CODEC
        );

        ServerPlayNetworking.registerGlobalReceiver(
                PlaceToolKeyHeldC2SPayload.PAYLOAD_ID,
                (payload, context) -> {
                    boolean held = payload.held();
                    ServerPlayerEntity player = context.player();
                    if (player.getServer() == null) return;

                    player.getServer().execute(() -> {
                        ServerSharedInputState.set(player, held);
                    });
                }
        );
    }

}