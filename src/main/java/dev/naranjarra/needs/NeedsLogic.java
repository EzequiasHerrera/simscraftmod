package dev.naranjarra.needs;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class NeedsLogic {
    public static PlayerNeeds updateNeeds(Player player, PlayerNeeds currentNeeds) {

        float newBladder = Math.max(0, currentNeeds.bladder() - 1f);
        float newEnergy = Math.max(0, currentNeeds.energy() - 0.1f);
        float newHygiene = player.isInWater() ?
                Math.min(20.0f, currentNeeds.hygiene() + 2.0f)
                :
                Math.max(0, currentNeeds.hygiene() - 0.5f);
        int newHunger = player.getFoodData().getFoodLevel();

        //CONSECUENCIAS
        if (newBladder == 0 && currentNeeds.bladder() > 0) {
            Level level = player.level();
            BlockPos pos = player.blockPosition();

            // Coloca un bloque de AGUA en los pies del Sim
            level.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());

            // Castigo secundario: La higiene baja a 0 instantáneamente
            newBladder = 20.0f;
            newHygiene = 0f;
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
