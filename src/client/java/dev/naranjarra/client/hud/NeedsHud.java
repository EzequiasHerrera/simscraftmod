package dev.naranjarra.client.hud;

import dev.naranjarra.SimsCraftServer;
import dev.naranjarra.needs.PlayerNeeds;
import dev.naranjarra.networking.payload.NeedsPayload;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;

public class NeedsHud {
    private static PlayerNeeds needs = new PlayerNeeds(20,20,20,20,20,20);
    public static boolean isMenuOpen = false;

    public static void init() {
        HudElementRegistry.attachElementBefore(
                VanillaHudElements.OVERLAY_MESSAGE,
                Identifier.fromNamespaceAndPath(SimsCraftServer.MOD_ID, "before_chat"),
                NeedsHud::extract
        );
    }

    // TODO: Aquí debo colocar todos los needs faltantes y actualizarlos porque es la función que corre desde el server y se guardan en NeedsHud.bladder por ejemplo
    public static void updateNeedValues(NeedsPayload payload) {
        needs.update(payload.bladder(), payload.hunger());
    }

    private static void extract(GuiGraphicsExtractor graphics, DeltaTracker tickCounter) {
        if(!isMenuOpen) return;

        // 1. Configuramos las dimensiones y posición de tu barra
        int x = 10;
        int y = 20;
        int spacing = 25;

        // Clavamos un texto al lado para que no sea solo una barra flotando
        Font font = Minecraft.getInstance().font;

        NeedBarWidget.draw(graphics, font, "Vejiga", needs.bladder(), 20, x, y);
        NeedBarWidget.draw(graphics, font, "Hambre", needs.hunger(), 20, x, y);
    }
}