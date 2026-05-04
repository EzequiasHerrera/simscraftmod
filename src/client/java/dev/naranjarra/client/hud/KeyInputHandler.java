package dev.naranjarra.client.hud;

import com.mojang.blaze3d.platform.InputConstants;
import dev.naranjarra.SimsCraftServer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static KeyMapping toggleMenuKey;

    public static void register() {
        KeyMapping.Category category = KeyMapping.Category.register(
                Identifier.fromNamespaceAndPath(SimsCraftServer.MOD_ID, "sims_craft")
        );

        toggleMenuKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.simscraft.toggle_menu",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                category
        ));

        registerTickEvents();
    }

    private static void registerTickEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleMenuKey.consumeClick()) {
                if (client.player != null) {
                    NeedsHud.isMenuOpen = !NeedsHud.isMenuOpen;
                }
            }
        });
    }
}