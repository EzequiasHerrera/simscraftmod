package dev.naranjarra.client;

import dev.naranjarra.client.hud.NeedsHud;
import dev.naranjarra.client.hud.KeyInputHandler;
import dev.naranjarra.client.render.CustomRenderPipeline;
import net.fabricmc.api.ClientModInitializer;

public class SimsCraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Iniciamos la interfaz y el teclado
		NeedsHud.init();
		KeyInputHandler.register();
		// 🟢 INICIAMOS LA RENDER PIPELINE DEL CUBO
		new CustomRenderPipeline().initialize();
	}
}