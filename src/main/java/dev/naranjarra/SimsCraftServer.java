package dev.naranjarra;

import dev.naranjarra.networking.payload.NeedsPayload;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimsCraftServer implements ModInitializer {
    public static final String MOD_ID = "modid";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Sun sun!");

        //Registra el Payload (No lo inicia) y explica cómo decodificarlo del otro lado
        PayloadTypeRegistry.clientboundPlay().register(NeedsPayload.ID, NeedsPayload.CODEC);
    }
}