package dev.naranjarra.client;

import dev.naranjarra.client.hud.NeedsHud;
import dev.naranjarra.client.hud.KeyInputHandler;
import dev.naranjarra.networking.payload.NeedsPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class SimsCraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		NeedsHud.init();
		KeyInputHandler.register();

		// Listener del Payload de Stats del Server
		ClientPlayNetworking.registerGlobalReceiver(NeedsPayload.ID, (statsPayload, context) -> {
			//Realizo las actualizaciones en el thread principal para evitar problemas
			context.client().execute(()-> {
				NeedsHud.updateNeedValues(statsPayload);
			});
		});
	}
}