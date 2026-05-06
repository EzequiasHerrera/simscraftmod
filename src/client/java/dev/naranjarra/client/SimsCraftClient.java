package dev.naranjarra.client;

import dev.naranjarra.client.hud.NeedsHud;
import dev.naranjarra.client.hud.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;

public class SimsCraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Iniciamos la interfaz y el teclado
		NeedsHud.init();
		KeyInputHandler.register();
	}
}