package dev.naranjarra.network;


import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record SimsStatsPayload(float hunger, float bladder) implements CustomPacketPayload {
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return null;
    }
}