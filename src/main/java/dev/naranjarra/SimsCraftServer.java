package dev.naranjarra;

import dev.naranjarra.networking.payload.SimsStatsPayload;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimsCraftServer implements ModInitializer {
    public static final String MOD_ID = "modid";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        PayloadTypeRegistry.clientboundPlay().register(SimsStatsPayload.ID, SimsStatsPayload.CODEC);
        LOGGER.info("Sun sun!");
    }
}