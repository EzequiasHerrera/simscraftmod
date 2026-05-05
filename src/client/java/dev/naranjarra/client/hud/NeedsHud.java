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
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public class NeedsHud {
    private static PlayerNeeds needs = new PlayerNeeds(20,20,20,20,20,20);
    public static boolean isMenuOpen = false;

    // 1. Definimos la textura UNA sola vez al cargar la clase ✅ REALMENTE LEE LA TEXTURA EN EL LUGAR CORRECTO ✅
    private static final Identifier PANEL_SPRITE = Identifier.fromNamespaceAndPath(SimsCraftServer.MOD_ID, "panel_bg");

    // Declaramos los Identifiers de los íconos
    private static final Identifier ICON_ENERGY = Identifier.fromNamespaceAndPath(SimsCraftServer.MOD_ID, "icon_energy");
    private static final Identifier ICON_HUNGER = Identifier.fromNamespaceAndPath(SimsCraftServer.MOD_ID, "icon_hunger");
    private static final Identifier ICON_BLADDER = Identifier.fromNamespaceAndPath(SimsCraftServer.MOD_ID, "icon_bladder");
    private static final Identifier ICON_HYGIENE = Identifier.fromNamespaceAndPath(SimsCraftServer.MOD_ID, "icon_hygiene");
    private static final Identifier ICON_SOCIAL = Identifier.fromNamespaceAndPath(SimsCraftServer.MOD_ID, "icon_social");
    private static final Identifier ICON_FUN = Identifier.fromNamespaceAndPath(SimsCraftServer.MOD_ID, "icon_fun");

    public static void init() {
        HudElementRegistry.attachElementBefore(
                VanillaHudElements.OVERLAY_MESSAGE,
                Identifier.fromNamespaceAndPath(SimsCraftServer.MOD_ID, "before_chat"),
                NeedsHud::extract
        );
    }

    public static void updateNeedValues(NeedsPayload payload) {
        needs.update(payload.bladder(), payload.hunger());
    }

    private static void extract(GuiGraphicsExtractor graphics, DeltaTracker tickCounter) {
        if(!isMenuOpen) return;

        Minecraft client = Minecraft.getInstance();
        Font font = client.font;

        // Dimensiones base
        int screenWidth = client.getWindow().getGuiScaledWidth();
        int screenHeight = client.getWindow().getGuiScaledHeight();

        // Configuración de Layout
        int barWidth = 120;
        int barHeight = 8;

        int margin = 6;
        int padding = 6;
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

        NeedBarWidget.draw(graphics, font, "Energy", needs.hunger(), 20, xBarras, yBase, ICON_ENERGY);
        NeedBarWidget.draw(graphics, font, "Hunger", needs.hunger(), 20, xBarras, yBase - spacing, ICON_HUNGER);
        NeedBarWidget.draw(graphics, font, "Bladder", needs.bladder(), 20, xBarras, yBase- (spacing * 2), ICON_BLADDER);

        NeedBarWidget.draw(graphics, font, "Hygiene", needs.hunger(), 20, xBarras + (panelWidth/2), yBase - (spacing * 2), ICON_HYGIENE);
        NeedBarWidget.draw(graphics, font, "Social", needs.hunger(), 20, xBarras + (panelWidth/2), yBase - spacing, ICON_SOCIAL);
        NeedBarWidget.draw(graphics, font, "Fun", needs.hunger(), 20, xBarras + (panelWidth/2), yBase, ICON_FUN);
    }
}