package dev.naranjarra.mixin;

import dev.naranjarra.SimsCraftServer;
import dev.naranjarra.needs.PlayerNeeds;
import dev.naranjarra.networking.payload.NeedsPayload;
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
    PlayerNeeds needs = new PlayerNeeds(20, 20, 20,20,20,20);

    @Unique
    private int tickCounter = 0;

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        Player player = (Player) (Object) this;

        if (player.level().isClientSide()) return;

        tickCounter++;

        if (tickCounter >= 40) { // Cada 2 segundos
            // 1. OBTENER datos actuales (o los de por defecto si es nuevo)
            PlayerNeeds current = player.getAttachedOrCreate(SimsCraftServer.PLAYER_NEEDS, () -> PlayerNeeds.DEFAULT);

            // 2. CALCULAR nuevos valores (Inmutables: creamos uno nuevo) [cite: 143, 157, 331]
            int actualHunger = player.getFoodData().getFoodLevel();
            float newBladder = Math.max(0, current.bladder() - 1.0f);

            PlayerNeeds updated = new PlayerNeeds(
                    newBladder,
                    current.fun(),
                    current.social(),
                    current.energy(),
                    current.hygiene(),
                    actualHunger
            );

            // 3. GUARDAR en el jugador (esto dispara la sincronización al cliente) [cite: 139, 153, 327]
            player.setAttached(SimsCraftServer.PLAYER_NEEDS, updated);

            if (newBladder <= 0) {
                System.out.println("¡Tu Sim se ha orinado!");
            }

            tickCounter = 0;
        }
    }
}
