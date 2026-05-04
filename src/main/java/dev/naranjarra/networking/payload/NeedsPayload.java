package dev.naranjarra.networking.payload;

import dev.naranjarra.SimsCraftServer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record NeedsPayload(float hunger, float bladder) implements CustomPacketPayload {
    //Le asigno un nombre al Payload para IDENTIFICARLO (En este caso lleva los stats)
    public static final Identifier SIMS_STATS_PAYLOAD_ID =
            Identifier.fromNamespaceAndPath(SimsCraftServer.MOD_ID, "sims_stats");

    public static final CustomPacketPayload.Type<NeedsPayload> ID =
            new CustomPacketPayload.Type<>(SIMS_STATS_PAYLOAD_ID);

    //Codifica el Payload en Bytes para poder pasarlo por la red al cliente
    public static final StreamCodec<RegistryFriendlyByteBuf, NeedsPayload> CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.FLOAT, NeedsPayload::hunger,    // Primer float
                    ByteBufCodecs.FLOAT, NeedsPayload::bladder,    // Segundo float
                    NeedsPayload::new                            // Constructor
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}