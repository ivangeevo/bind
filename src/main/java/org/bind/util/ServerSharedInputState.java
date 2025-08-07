package org.bind.util;

import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ServerSharedInputState {
    private static final Map<UUID, Boolean> toolPlacementInputHeld = new HashMap<>();

    public static void set(PlayerEntity player, boolean held) {
        toolPlacementInputHeld.put(player.getUuid(), held);
    }

    public static boolean isHeld(PlayerEntity player) {
        return toolPlacementInputHeld.getOrDefault(player.getUuid(), false);
    }

    public static void remove(PlayerEntity player) {
        toolPlacementInputHeld.remove(player.getUuid());
    }
}

