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
    @Unique
    private float bladderLevel = 20.0f;

    @Unique
    private int tickCounter = 0;

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        Player player = (Player) (Object) this;

        if (player.level().isClientSide()) {
            return;
        }

        tickCounter++;

        if (tickCounter >= 20) {
            tickCounter = 0;
            int hunger = player.getFoodData().getFoodLevel();

            if (bladderLevel > 0) {
                bladderLevel -= 1.0f;
            }

            if (bladderLevel == 0) {
                System.out.println("Meada historica chavalote.");
                if (player instanceof ServerPlayer serverPlayer) {
                    ServerPlayNetworking.send(serverPlayer, new SimsStatsPayload(hunger, bladderLevel));
                }

                //Si en un futuro necesito mandar a TODOS los jugadores:
//                for (ServerPlayer serverPlayer : PlayerLookup.world((ServerLevel) player.level())) {
//                    ServerPlayNetworking.send(serverPlayer, new SimsStatsPayload(15.5f, 42.0f));
//                }
            }
            System.out.println("Hambre: " + hunger + " | Vejiga: " + bladderLevel);
        }
    }
}
