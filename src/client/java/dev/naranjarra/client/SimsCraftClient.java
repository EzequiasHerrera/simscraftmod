package dev.naranjarra.client;

import dev.naranjarra.networking.payload.SimsStatsPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class SimsCraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(SimsStatsPayload.ID, (payload, context) -> {
			float valor1 = payload.valor1();
			float valor2 = payload.valor2();

			System.out.println("Estoy en el cliente y recibí " + valor1 + " y " + valor2);
		});
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
}