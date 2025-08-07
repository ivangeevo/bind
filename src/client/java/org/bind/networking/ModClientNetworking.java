package org.bind.networking;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import org.bind.network.PlaceToolKeyHeldC2SPayload;
import org.bind.util.ClientSharedInputState;

import static org.lwjgl.glfw.GLFW.*;


public class ModClientNetworking {

    // Used to detect state changes
    private static boolean lastHeldState = false;

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            long windowHandle = MinecraftClient.getInstance().getWindow().getHandle();

            boolean isCtrlPressed = glfwGetKey(windowHandle, GLFW_KEY_LEFT_CONTROL) == 1
                    || glfwGetKey(windowHandle, GLFW_KEY_RIGHT_CONTROL) == 1;

            if (isCtrlPressed != lastHeldState) {
                ClientPlayNetworking.send(new PlaceToolKeyHeldC2SPayload(isCtrlPressed));
                lastHeldState = isCtrlPressed;
                ClientSharedInputState.toolPlacementInputHeld = isCtrlPressed;
            }
        });
    }


}
