package dev.naranjarra.networking.payload;

import dev.naranjarra.SimsCraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SimsStatsPayload(float hunger, float bladder) implements CustomPacketPayload {
    //Le asigno un nombre al Payload para IDENTIFICARLO (En este caso lleva los stats)
    public static final Identifier SIMS_STATS_PAYLOAD_ID =
            Identifier.fromNamespaceAndPath(SimsCraft.MOD_ID, "sims_stats");

    public static final CustomPacketPayload.Type<SimsStatsPayload> ID =
            new CustomPacketPayload.Type<>(SIMS_STATS_PAYLOAD_ID);

    //Codifica el Payload en Bytes para poder pasarlo por la red al cliente
    public static final StreamCodec<RegistryFriendlyByteBuf, SimsStatsPayload> CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.FLOAT, SimsStatsPayload::hunger,    // Primer float
                    ByteBufCodecs.FLOAT, SimsStatsPayload::bladder,    // Segundo float
                    SimsStatsPayload::new                            // Constructor
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}