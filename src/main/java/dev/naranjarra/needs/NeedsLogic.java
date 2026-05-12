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
import net.minecraft.world.level.storage.ServerLevelData;

public class NeedsLogic {
    static final float MAX_VALUE_NEED = 20;

    public static PlayerNeeds updateNeeds(Player player, PlayerNeeds currentNeeds) {


        Level level = player.level();
        BlockPos pos = player.blockPosition();

        // 1️⃣ CÁLCULOS INDIVIDUALES (Llamamos a los submétodos)
        float newBladder = bladderLogic(currentNeeds.bladder());
        float newEnergy = energyLogic(player, currentNeeds.energy(), level);
        int newHunger = hungerLogic(player);
        float newHygiene = hygieneLogic(player, currentNeeds.hygiene(), level);

        // 2️⃣ CONSECUENCIAS CRUZADAS (Interacciones entre Needs)
        // ⚠️ ACCIDENTE DE VEJIGA: Afecta el mundo y la higiene
        if (newBladder == 0 && currentNeeds.bladder() > 0) {
            level.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
            newBladder = MAX_VALUE_NEED;
            newHygiene = 0f;
        }

        // 3️⃣ ESTADOS Y EFECTOS
        applyEnergyEffects(player, newEnergy, currentNeeds.energy(), level, pos);

        // 4️⃣ EMPAQUETADO FINAL (INMUTABLE)
        return new PlayerNeeds(
                newBladder,
                currentNeeds.fun(),
                currentNeeds.social(),
                newEnergy,
                newHygiene,
                newHunger
        );
    }

    // --- 🧻 LÓGICA DE VEJIGA ---
    private static float bladderLogic(float currentBladder) {
        return Mth.clamp(currentBladder - 1f, 0f, MAX_VALUE_NEED);
    }

    // --- 🛏️ LÓGICA DE SUEÑO ---
    private static float energyLogic(Player player, float currentEnergy, Level level) {
        if (player.isSleeping()) {
            if (level instanceof ServerLevel serverLevel && serverLevel.getServer().isSingleplayer()) {
                // 1️⃣ LEEMOS el tiempo con el método válido
                long currentTime = serverLevel.getOverworldClockTime();

                // 2️⃣ ESCRIBIMOS el tiempo accediendo a la capa de DATOS del servidor
                if (serverLevel.getLevelData() instanceof ServerLevelData serverData) {
                    serverData.setGameTime(currentTime + 8000); // Adelantamos 8 horas (8000 ticks)
                }

                player.stopSleeping(); // Despertamos al Sim
                return 20.0f; // Energía al máximo
            } else {
                // ⚡ MULTIPLAYER: Carga súper rápida en tiempo real
                return Mth.clamp(currentEnergy + 0.5f, 0f, 20f);
            }
        } else {

            // 🚶 DESGASTE NORMAL
            float energyDrop = player.isSprinting() ? 0.3f : 0.1f;
            return Mth.clamp(currentEnergy - energyDrop, 0f, 20f);
        }
    }

    // --- 🍔 LÓGICA DE HAMBRE ---
    private static int hungerLogic(Player player) {
        return player.getFoodData().getFoodLevel();
    }

    // --- 🧼 LÓGICA DE HIGIENE ---
    private static float hygieneLogic(Player player, float currentHygiene, Level level) {
        float newHyg = player.isInWater() ?
                Mth.clamp(currentHygiene + 2.0f, 0f, MAX_VALUE_NEED) :
                Mth.clamp(currentHygiene - 0.5f, 0f, MAX_VALUE_NEED);

        if (newHyg <= 0 && level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SMOKE,
                    player.getX(), player.getY() + 1.0, player.getZ(),
                    2, 0.5, 0.5, 0.5, 0.0);
        }
        return newHyg;
    }

    // --- LOGICA DE DIVERSION
    private static float funLogic(Player player, float currentFun) {
        return Mth.clamp(currentFun - 0.5f, 0f, MAX_VALUE_NEED);
    }

    // --- LOGICA DE SOCIAL
    private static float socialLogic(Player player, float currentSocial) {
        return Mth.clamp(currentSocial - 0.5f, 0f, MAX_VALUE_NEED);
    }


    // --- 💤 EFECTOS DE SUEÑO ---
    private static void applyEnergyEffects(Player player, float newEnergy, float oldEnergy, Level level, BlockPos pos) {
        if (newEnergy <= 5.0f && newEnergy > 0) {
            player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 60, 0, false, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.MINING_FATIGUE, 60, 0, false, false, true));
        } else if (newEnergy == 0) {
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0, false, false, true));
            if (oldEnergy > 0) {
                level.playSound(null, pos, SoundEvents.HORSE_BREATHE_BABY, SoundSource.PLAYERS, 1.0f, 0.5f);
            }
        }
    }
}