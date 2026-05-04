package dev.naranjarra.client;

import dev.naranjarra.client.hud.HudTest;
import dev.naranjarra.client.hud.KeyInputHandler;
import dev.naranjarra.networking.payload.SimsStatsPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class SimsCraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HudTest.init();
		KeyInputHandler.register();

		ClientPlayNetworking.registerGlobalReceiver(SimsStatsPayload.ID, (statsPayload, context) -> {
			float hunger = statsPayload.hunger();
			float bladder = statsPayload.bladder();
			context.client().execute(()-> {
				HudTest.updateValue(bladder);
			});
		});
	}
}