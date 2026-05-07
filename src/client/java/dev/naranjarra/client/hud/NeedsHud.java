package dev.naranjarra.client.hud;

import dev.naranjarra.SimsCraftServer;
import dev.naranjarra.needs.PlayerNeeds;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public class NeedsHud {
    public static boolean isMenuOpen = false;

    // Definimos los Identifiers de los íconos y la textura del PANEL UNA sola vez al cargar la clase
    private static final Identifier PANEL_SPRITE = Identifier.fromNamespaceAndPath(SimsCraftServer.MOD_ID, "panel_bg");
    private static final Identifier ICON_ENERGY = Identifier.fromNamespaceAndPath(SimsCraftServer.MOD_ID, "icon_energy");
    private static final Identifier ICON_HUNGER = Identifier.fromNamespaceAndPath(SimsCraftServer.MOD_ID, "icon_hunger");
    private static final Identifier ICON_BLADDER = Identifier.fromNamespaceAndPath(SimsCraftServer.MOD_ID, "icon_bladder");
    private static final Identifier ICON_HYGIENE = Identifier.fromNamespaceAndPath(SimsCraftServer.MOD_ID, "icon_hygiene");
    private static final Identifier ICON_SOCIAL = Identifier.fromNamespaceAndPath(SimsCraftServer.MOD_ID, "icon_social");
    private static final Identifier ICON_FUN = Identifier.fromNamespaceAndPath(SimsCraftServer.MOD_ID, "icon_fun");

    public static void init() {
        HudElementRegistry.attachElementBefore( //HudElementRegistry poneme el HUD justo antes del
                VanillaHudElements.OVERLAY_MESSAGE, //OVERLAY MESSAGE ("Tu cama está obstruída")
                Identifier.fromNamespaceAndPath( //simscraft:before_chat
                        SimsCraftServer.MOD_ID,
                        "before_chat"),
                NeedsHud::extract //QUÉ DIBUJAR (Es un callback que se ejecuta cuando llegue el momento)
        );
    }

    // ✏️🌈 DIBUJADO DEL PANEL
    private static void extract(GuiGraphicsExtractor graphics, DeltaTracker tickCounter) {
        if (!isMenuOpen) return;

        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;

        // LEER los datos que llegaron automáticamente del servidor
        PlayerNeeds currentNeeds = client.player.getAttachedOrCreate(SimsCraftServer.PLAYER_NEEDS, () -> PlayerNeeds.DEFAULT);

        Font font = client.font;

        // Dimensiones base
        int screenWidth = client.getWindow().getGuiScaledWidth();
        int screenHeight = client.getWindow().getGuiScaledHeight();

        // Configuración de Layout
        int barWidth = 150;
        int barHeight = 8;

        int margin = 6;
        int padding = 8;
        int spacing = 28;
        int cantBarras = 3; // Vejiga y Hambre

        // Cálculo del Panel
        int panelWidth = barWidth + (padding * 2);
        int panelHeight = (cantBarras * spacing) + padding;

        int pX = screenWidth - panelWidth - margin;
        int pY = screenHeight - panelHeight - margin;

        // --- DIBUJO DEL PANEL DE FONDO (TU IMAGEN) ---
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, PANEL_SPRITE, pX, pY, panelWidth, panelHeight, -1);

        // --- DIBUJO DE LAS BARRAS ---
        int xBarras = pX + padding;
        int yBase = (pY + panelHeight) - padding - barHeight;

        // --- BARRAS ESTÉTICAS DE FONDO ---
        int colorGuia = 0x15000000; // Negro muy transparente
        int guiaHeight = 18;        // Alto de la franja que envuelve la barra (sin tapar el texto)

        for (int i = 0; i < cantBarras; i++) {
            // Calculamos la posición Y de cada franja basándonos en el spacing
            int yGuia = yBase - (spacing * i) - 5;

            // Dibujamos la franja de lado a lado del panel (respetando un poquito de padding)
            graphics.fill(pX + 2, yGuia, pX + panelWidth - 2, yGuia + guiaHeight, colorGuia);
        }

        NeedBarWidget.draw(graphics, font, "Energy", currentNeeds.energy(), 20, xBarras, yBase, ICON_ENERGY);
        NeedBarWidget.draw(graphics, font, "Hunger", (float)currentNeeds.hunger(), 20, xBarras, yBase - spacing, ICON_HUNGER);
        NeedBarWidget.draw(graphics, font, "Bladder", currentNeeds.bladder(), 20, xBarras, yBase - (spacing * 2), ICON_BLADDER);

        NeedBarWidget.draw(graphics, font, "Hygiene", currentNeeds.hygiene(), 20, xBarras + (panelWidth / 2), yBase - (spacing * 2), ICON_HYGIENE);
        NeedBarWidget.draw(graphics, font, "Social", currentNeeds.social(), 20, xBarras + (panelWidth / 2), yBase - spacing, ICON_SOCIAL);
        NeedBarWidget.draw(graphics, font, "Fun", currentNeeds.fun(), 20, xBarras + (panelWidth / 2), yBase, ICON_FUN);
    }
}