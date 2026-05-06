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

    // Reemplaza el bloque del builder por este:
    public static final AttachmentType<PlayerNeeds> PLAYER_NEEDS = AttachmentRegistry.<PlayerNeeds>builder()
            .persistent(PlayerNeeds.CODEC)
            .copyOnDeath()
            // NOTA: Ahora incluimos el STREAM_CODEC para que Fabric sepa cómo enviarlo por red
            .syncWith(PlayerNeeds.STREAM_CODEC, AttachmentSyncPredicate.targetOnly())
            .buildAndRegister(Identifier.fromNamespaceAndPath(MOD_ID, "player_needs"));

    @Override
    public void onInitialize() {
        LOGGER.info("Sun sun!");
    }
}