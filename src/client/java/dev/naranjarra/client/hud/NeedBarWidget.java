package dev.naranjarra.client.hud;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class NeedBarWidget {
    public static void draw(GuiGraphicsExtractor graphics, Font font, String label, float current, float max, int x, int y, Identifier icon) {
        int maxWidth = 40;
        int height = 6;
        int iconSize = 16;
        int offset = 15;

        float percentage = Mth.clamp(current / max, 0f, 1f);
        int fillWidth = (int) (percentage * maxWidth);

        // Lógica de colores centralizada
        int colorBarra = 0xFF2ECC71; // Verde
        if (percentage < 0.2f) colorBarra = 0xFFE74C3C; // Rojo
        else if (percentage < 0.5f) colorBarra = 0xFFF1C40F; // Amarillo

        int colorFondoBarra = 0xFF486545; // Verde oscuro
        if (percentage < 0.2f) colorFondoBarra = 0xFF561212; // Rojo
        else if (percentage < 0.5f) colorFondoBarra = 0xFF786b20; // Amarillo

        // Dibujar background (Aplicamos offset TANTO a la izquierda como a la derecha)
        // Se mantiene tu -1 para el borde superior/izquierdo y el +1 para el inferior/derecho
        graphics.fill(x + offset - 1, y - 1, x + offset + maxWidth + 1, y + height + 1, colorFondoBarra);

        // Dibujar barra interna (Aplicamos offset TANTO a la izquierda como a la derecha)
        if (fillWidth > 0) {
            graphics.fill(x + offset, y, x + offset + fillWidth, y + height, colorBarra);
        }

        // Dibujar texto (Queda anclado a la izquierda, arriba del ícono)
        graphics.text(font, label, x, y - 14, 0xFF14205b, false);

        // Dibujar ícono (Queda anclado a la izquierda original 'x')
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, icon, x, y - 4, iconSize, iconSize, -1);
    }
}