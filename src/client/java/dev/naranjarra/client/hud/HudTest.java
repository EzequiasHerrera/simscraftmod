package dev.naranjarra.client.hud;

import dev.naranjarra.SimsCraft;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;

public class HudTest {
    private static float valorRecibido = 0.0f;

    public static void init() {
        HudElementRegistry.attachElementBefore(
                VanillaHudElements.OVERLAY_MESSAGE,
                Identifier.fromNamespaceAndPath(SimsCraft.MOD_ID, "before_chat"),
                HudTest::extract
        );
    }

    public static void updateValue(float nuevoValor) {
        valorRecibido = nuevoValor;
    }

    private static void extract(GuiGraphicsExtractor graphics, DeltaTracker tickCounter) {
        int color = 0xFFFF0000; // Red
        int targetColor = 0xFF00FF00; // Green

        // You can use the Util.getMillis() function to get the current time in milliseconds.
        // Divide by 1000 to get seconds.
        double currentTime = Util.getMillis() / 1000.0;

        // "lerp" simply means "linear interpolation", which is a fancy way of saying "blend".
        float lerpedAmount = Mth.abs(Mth.sin((float) currentTime));
        int lerpedColor = ARGB.linearLerp(lerpedAmount, color, targetColor);
        Font font = Minecraft.getInstance().font;

        String texto = String.valueOf(valorRecibido);

        // Draw a square with the lerped color.
        // x1, x2, y1, y2, color
        graphics.text(font, texto, 0, 0, 0xFFFFFFFF, true);
    }
}