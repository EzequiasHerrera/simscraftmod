package dev.naranjarra.client.hud;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.Mth;

public class NeedBarWidget {
    public static void draw(GuiGraphicsExtractor graphics, Font font, String label, float current, float max, int x, int y) {
        int maxWidth = 100;
        int height = 8;

        float percentage = Mth.clamp(current / max, 0f, 1f);
        int fillWidth = (int) (percentage * maxWidth);

        // Lógica de colores centralizada
        int colorBarra = 0xFF2ECC71; // Verde
        if (percentage < 0.2f) colorBarra = 0xFFE74C3C; // Rojo
        else if (percentage < 0.5f) colorBarra = 0xFFF1C40F; // Amarillo

        // Dibujar fondo
        graphics.fill(x, y, x + maxWidth, y + height, 0xFF444444);
        // Dibujar relleno
        if (fillWidth > 0) {
            graphics.fill(x, y, x + fillWidth, y + height, colorBarra);
        }
        // Dibujar texto
        graphics.text(font, label, x, y - 10, 0xFFFFFFFF, false);
    }
}