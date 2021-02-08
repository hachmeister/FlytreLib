package net.flytre.flytre_lib.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public abstract class Hud {

    private final MinecraftClient client;

    public Hud() {
        client = MinecraftClient.getInstance();
        HudRenderCallback.EVENT.register((__, ___) -> this.render());
    }


    @Environment(EnvType.CLIENT)
    private void render() {
        final PlayerEntity player = client.player;
        final TextRenderer textRenderer = client.textRenderer;
        final MatrixStack matrixStack = new MatrixStack();
        final ItemRenderer itemRenderer = client.getItemRenderer();


        int x = client.getWindow().getScaledWidth() - 100;
        int y = client.getWindow().getScaledHeight() - 70;

        if (player == null || player.inventory == null)
            return;
        draw(matrixStack, player, textRenderer, itemRenderer, x, y);
    }

    public abstract void draw(MatrixStack matrixStack, PlayerEntity player, TextRenderer textRenderer, ItemRenderer itemRenderer, int maxX, int maxY);


    protected void drawBundle(MatrixStack matrixStack, int x, int y, ItemStack stack, Text text, ItemRenderer itemRenderer, TextRenderer textRenderer) {
        drawBundle(matrixStack, x, y, stack, text, itemRenderer, textRenderer, 0x88101747, 11250603);
    }

    protected void drawBundle(MatrixStack matrixStack, int x, int y, ItemStack stack, Text text, ItemRenderer itemRenderer, TextRenderer textRenderer, int backgroundColor, int textColor) {
        DrawableHelper.fill(matrixStack, x - 5, y - 5, x + 90, y + 20, backgroundColor);
        itemRenderer.renderGuiItemIcon(stack, x, y);
        itemRenderer.renderGuiItemOverlay(textRenderer, stack, x, y);
        textRenderer.draw(matrixStack, text, x + 25, y + 3, textColor);
    }

    protected void drawTwoLineBundle(MatrixStack matrixStack, int x, int y, ItemStack stack, Text text, Text text2, ItemRenderer itemRenderer, TextRenderer textRenderer) {
        drawTwoLineBundle(matrixStack, x, y, stack, text, text2, itemRenderer, textRenderer, 0x88101747, 11250603);
    }

    protected void drawTwoLineBundle(MatrixStack matrixStack, int x, int y, ItemStack stack, Text text, Text text2, ItemRenderer itemRenderer, TextRenderer textRenderer, int backgroundColor, int textColor) {
        DrawableHelper.fill(matrixStack, x - 5, y - 5, x + 75, y + 25, backgroundColor);
        itemRenderer.renderGuiItemIcon(stack, x, y);
        itemRenderer.renderGuiItemOverlay(textRenderer, stack, x, y);
        textRenderer.draw(matrixStack, text, x + 25, y + 2, textColor);
        textRenderer.draw(matrixStack, text2, x + 25, y + 14, textColor);

    }

}
