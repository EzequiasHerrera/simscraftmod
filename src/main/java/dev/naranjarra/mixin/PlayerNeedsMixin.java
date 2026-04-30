package dev.naranjarra.mixin;

import dev.naranjarra.networking.payload.SimsStatsPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerNeedsMixin {
    // ♦️ DEFINO TODAS LAS NECESIDADES DEL SIM ♦️

    //VEJIGA 🚽🪠🚾
    @Unique
    private float bladderLevel = 20.0f;

    //DIVERSIÓN 🎮🕹️
    @Unique
    private float funLevel = 20.0f;

    //SOCIAL 🗣️💬
    @Unique
    private float socialLevel = 20.0f;

    //ENERGÍA 🔋🪫
    @Unique
    private float energyLevel = 20.0f;

    //HIGIENE 🚿🧼🫧
    @Unique
    private float hygieneLevel = 20.0f;

    @Unique
    private int tickCounter = 0;

    // Función inyectada en CADA TICK al FINAL
    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        Player player = (Player) (Object) this;

        // Comprobación si es el lado del cliente o servidor
        if (player.level().isClientSide()) {
            return;
        }

        // SUMO ➕1️⃣ al contador de TICKS ⏱️
        tickCounter++;

        //Si pasaron 40 ticks (20 segundos) envía los datos al client
        if (tickCounter >= 40) {

            int hunger = player.getFoodData().getFoodLevel();

            if (bladderLevel > 0) {
                bladderLevel -= 1.0f;
            }

            if (bladderLevel == 0) {
                System.out.println("Tu Sim se ha orinado.");
                //Si en un futuro necesito mandar a TODOS los jugadores:
                //for (ServerPlayer serverPlayer : PlayerLookup.world((ServerLevel) player.level())) {
                //    ServerPlayNetworking.send(serverPlayer, new SimsStatsPayload(15.5f, 42.0f));
            }

            if (player instanceof ServerPlayer serverPlayer) {
                ServerPlayNetworking.send(serverPlayer, new SimsStatsPayload(hunger, bladderLevel));
            }

            tickCounter = 0;
        }
    }
}
