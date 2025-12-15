package org.btwr.bind.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import org.btwr.bind.BindMod;

public record PlaceToolKeyHeldC2SPayload(boolean held) implements CustomPayload {

    public static final Identifier ID = Identifier.of(BindMod.MOD_ID, "ctrl_key_held");
    public static final CustomPayload.Id<PlaceToolKeyHeldC2SPayload> PAYLOAD_ID = new CustomPayload.Id<>(ID);

    public static final PacketCodec<RegistryByteBuf, PlaceToolKeyHeldC2SPayload> CODEC =
            PacketCodec.tuple(PacketCodecs.BOOL, PlaceToolKeyHeldC2SPayload::held, PlaceToolKeyHeldC2SPayload::new);

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }

}