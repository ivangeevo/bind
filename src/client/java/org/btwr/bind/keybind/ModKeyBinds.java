package org.btwr.bind.keybind;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ModKeyBinds {

    public static KeyBinding TOOL_PLACEMENT_KEY;

    public static void register() {
        TOOL_PLACEMENT_KEY = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "key.bind.tool_placement_key",
                        InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_CONTROL,
                        "key.category.bind.keybinds"
                )
        );
    }

}