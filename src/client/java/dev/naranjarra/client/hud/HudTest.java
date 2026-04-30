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
    public static boolean isMenuOpen = false;

    public static void init() {
        HudElementRegistry.attachElementBefore(
                VanillaHudElements.OVERLAY_MESSAGE,
                Identifier.fromNamespaceAndPath(SimsCraft.MOD_ID, "before_chat"),
                HudTest::extract
        );
    }

    public static void updateValue(float nuevoValor) {
        System.out.println("El nuevo valor es " + nuevoValor);
        valorRecibido = nuevoValor;
    }

    private static void extract(GuiGraphicsExtractor graphics, DeltaTracker tickCounter) {
        if(!isMenuOpen) return;

        // 1. Configuramos las dimensiones y posición de tu barra
        int x = 10;
        int y = 20;
        int maxWidth = 100; // Ancho de la barra cuando está al 100%
        int height = 8;     // Grosor de la barra

        // La vejiga va de 0 a 20
        float maxValor = 20.0f;

        // Evitamos que el valor se pase de mambo y rompa el gráfico (Clamp)
        float currentValor = Mth.clamp(valorRecibido, 0.0f, maxValor);

        // Calculamos qué tan ancha debe ser la barra de color
        // Regla de tres: (Valor actual / Valor máximo) * Ancho total
        int fillWidth = (int) ((currentValor / maxValor) * maxWidth);

        // 3. Definimos los colores en ARGB Hexadecimal (Alpha, Red, Green, Blue)
        int colorFondo = 0xFF444444; // Un gris oscuro para el fondo
        int colorTexto = 0xFFFFFFFF; // Blanco

        // Dibujamos el rectángulo de FONDO (La barra vacía)
        // El metodo fill usa coordenadas: (x1, y1, x2, y2, color)
        graphics.fill(x, y, x + maxWidth, y + height, colorFondo);

        // Dibujamos el rectángulo de RELLENO (La barra llena)
        // Solo lo dibujamos si hay algo que rellenar
        // 1. Sacamos el porcentaje actual (de 0.0 a 1.0)
        float porcentaje = currentValor / maxValor;
        int colorBarra;

        // 2. Definimos el color según qué tan baja esté la stat
        if (porcentaje >= 0.5f) {
            colorBarra = 0xFF2ECC71; // Verde (Normal - Más del 50%)
        } else if (porcentaje >= 0.2f) {
            colorBarra = 0xFFF1C40F; // Amarillo (Precaución - Entre 20% y 50%)
        } else {
            colorBarra = 0xFFE74C3C; // Rojo (Crítico - F en el chat, menos del 20%)
        }

        // 3. Dibujamos el relleno con el color dinámico que elegimos
        if (fillWidth > 0) {
            graphics.fill(x, y, x + fillWidth, y + height, colorBarra);
        }

        // 6. (Opcional) Clavamos un texto al lado para que no sea solo una barra flotando
        Font font = Minecraft.getInstance().font;
        graphics.text(font, "Vejiga", x, y - 10, colorTexto, false);
    }
}