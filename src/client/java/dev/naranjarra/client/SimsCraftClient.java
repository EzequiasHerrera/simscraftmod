package dev.naranjarra.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.naranjarra.SimsCraft;
import dev.naranjarra.client.hud.HudTest;
import dev.naranjarra.networking.payload.SimsStatsPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class SimsCraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HudTest.init();

		KeyMapping.Category CATEGORY = KeyMapping.Category.register(
				Identifier.fromNamespaceAndPath(SimsCraft.MOD_ID, "Sims Craft")
		);

		KeyMapping sendToChatKey = KeyMappingHelper.registerKeyMapping(
				new KeyMapping(
						"key.simscraft.toggle_menu", // The translation key for the key mapping.
						InputConstants.Type.KEYSYM, // // The type of the keybinding; KEYSYM for keyboard, MOUSE for mouse.
						GLFW.GLFW_KEY_J, // The GLFW keycode of the key.
						CATEGORY // The category of the mapping.
				));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (sendToChatKey.consumeClick()) {
				if (client.player != null) {
					HudTest.isMenuOpen = !HudTest.isMenuOpen;
				}
			}
		});

		ClientPlayNetworking.registerGlobalReceiver(SimsStatsPayload.ID, (statsPayload, context) -> {
			float hunger = statsPayload.hunger();
			float bladder = statsPayload.bladder();

			context.client().execute(()-> {
				HudTest.updateValue(bladder);
			});
		});
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

	}
}