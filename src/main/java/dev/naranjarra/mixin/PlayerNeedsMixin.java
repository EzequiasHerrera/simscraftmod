package dev.naranjarra.mixin;

import dev.naranjarra.SimsCraftServer;
import dev.naranjarra.needs.NeedsLogic;
import dev.naranjarra.needs.PlayerNeeds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerNeedsMixin {
    //@Unique coloca el MOD_ID antes a la variable para evitar problemas con otros mods
    @Unique private int tickCounter = 0;

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        Player player = (Player) (Object) this;

        if (player.level().isClientSide()) return;
        if (player.gameMode().isCreative()) return;

        tickCounter++;

        if (tickCounter >= 40) {
            // Cada 2 segundos
            // 1️⃣📦 OBTENER datos actuales (o los de por defecto si es nuevo)
            PlayerNeeds currentNeeds = player.getAttachedOrCreate(
                    SimsCraftServer.PLAYER_NEEDS, // TIPO
                    () -> PlayerNeeds.DEFAULT // VALOR DEFAULT DE EMERGENCIA
            );

            // 2️⃣🧮 CALCULAR nuevos valores (Inmutables: creamos uno nuevo)
            PlayerNeeds updatedNeeds = NeedsLogic.updateNeeds(player, currentNeeds);

            // 3️⃣💾📦 GUARDAR en el jugador (esto dispara la sincronización al cliente)
            player.setAttached(SimsCraftServer.PLAYER_NEEDS, updatedNeeds);

            tickCounter = 0;
        }
    }

    @Inject(method = "dropEquipment", at = @At("TAIL"))
    private void onPlayerDeath(ServerLevel world, CallbackInfo ci) {
        Player player = (Player) (Object) this;

        if (player.getHealth() <= 0){
            player.setAttached(SimsCraftServer.PLAYER_NEEDS, PlayerNeeds.DEFAULT);
        }
    }
}