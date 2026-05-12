package dev.naranjarra.needs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class NeedsLogic {
    public static PlayerNeeds updateNeeds(Player player, PlayerNeeds currentNeeds) {
        float newBladder = Math.max(0, currentNeeds.bladder() - 1f);
        float newEnergy = Math.max(0, currentNeeds.energy() - 0.1f);
        int newHunger = player.getFoodData().getFoodLevel();
        float newHygiene = player.isInWater() ?
                Math.min(20.0f, currentNeeds.hygiene() + 2.0f)
                :
                Math.max(0, currentNeeds.hygiene() - 0.5f);

        Level level = player.level();
        BlockPos pos = player.blockPosition();

        // 💤 SUEÑO: Recuperación y Esfuerzo Físico
        if (player.isSleeping()) {
            // Se recupera mientras duerme en una cama
            newEnergy = Mth.clamp(currentNeeds.energy() + 1.0f, 0f, 20f);
        } else {
            // Se cansa el TRIPLE de rápido si va corriendo (sprint)
            float energyDrop = player.isSprinting() ? 0.3f : 0.1f;
            newEnergy = Mth.clamp(currentNeeds.energy() - energyDrop, 0f, 20f);
        }

        //CONSECUENCIAS
        if (newBladder == 0 && currentNeeds.bladder() > 0) {
            // Coloca un bloque de AGUA en los pies del Sim
            level.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());

            // Castigo secundario: La higiene baja a 0 instantáneamente
            newBladder = 20.0f;
            newHygiene = 0f;
        }

        // 🤢 HIGIENE: Mal olor
        if (newHygiene <= 0 && level instanceof ServerLevel serverLevel) {
            // Spawnea partículas de HUMO para simular la suciedad
            serverLevel.sendParticles(ParticleTypes.SMOKE,
                    player.getX(), player.getY() + 1.0, player.getZ(),
                    2, 0.5, 0.5, 0.5, 0.0);
        }

        // 💤 SUEÑO: Consecuencias progresivas
        if (newEnergy <= 5.0f && newEnergy > 0) {
            // ESTADO: Cansado (Lentitud al caminar y picar bloques)
            player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 60, 0, false, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.MINING_FATIGUE, 60, 0, false, false, true));
        } else if (newEnergy == 0) {
            // ESTADO: Desmayado (Pantalla negra)
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0, false, false, true));

            // GATILLO ÚNICO: Reproduce el ronquido SOLO en el instante en que llega a 0
            if (currentNeeds.energy() > 0) {
                level.playSound(null, pos, SoundEvents.HORSE_BREATHE_BABY, SoundSource.PLAYERS, 1.0f, 0.5f);
            }
        }

        return new PlayerNeeds(
                newBladder,
                currentNeeds.fun(),
                currentNeeds.social(),
                newEnergy,
                newHygiene,
                newHunger
        );
    }
}
