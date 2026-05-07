package dev.naranjarra;

import dev.naranjarra.needs.PlayerNeeds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimsCraftServer implements ModInitializer {
    public static final String MOD_ID = "simscraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // 📄 REGISTRA una variable global de tipo PlayerNeeds
    public static final AttachmentType<PlayerNeeds> PLAYER_NEEDS = AttachmentRegistry.<PlayerNeeds>builder()
            .persistent(PlayerNeeds.CODEC) // Es PERMANENTE
            .copyOnDeath() // Es PERSISTENTE
            .syncWith(PlayerNeeds.STREAM_CODEC, AttachmentSyncPredicate.targetOnly()) // SYNC AUTOMATICA solo al JUGADOR OBJETIVO (targetOnly())
            .buildAndRegister(Identifier.fromNamespaceAndPath(MOD_ID, "player_needs")); // CREA y FIRMA el Post It

    @Override
    public void onInitialize() {
        LOGGER.info("Sun sun!");
    }
}