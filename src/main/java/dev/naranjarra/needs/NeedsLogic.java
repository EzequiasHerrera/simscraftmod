package dev.naranjarra.needs;

import net.minecraft.world.entity.player.Player;

public class NeedsLogic {
    public static PlayerNeeds updateNeeds(Player player, PlayerNeeds currentNeeds) {

        float newBladder = Math.max(0, currentNeeds.bladder() - 0.5f);
        float newEnergy = Math.max(0, currentNeeds.energy() - 0.1f);
        float newHygiene = player.isInWater() ?
                Math.min(20.0f, currentNeeds.hygiene() + 2.0f)
                :
                Math.max(0, currentNeeds.hygiene() - 0.5f);
        int newHunger = player.getFoodData().getFoodLevel();

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
