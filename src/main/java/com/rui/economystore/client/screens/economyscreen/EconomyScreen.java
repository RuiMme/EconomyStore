package com.rui.economystore.client.screens.economyscreen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rui.economystore.api.network.DataHelper;
import com.rui.economystore.api.network.PacketHandler;
import com.rui.economystore.api.network.handler.server.OpenItemStoreContainerHandler;
import com.rui.economystore.api.network.handler.server.OpenRecyclingStoreContainerHandler;
import com.rui.economystore.libs.LibDataToTextComponent;
import com.rui.economystore.libs.LibMisc;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import static com.rui.economystore.api.network.MenuProviderData.getItemStoreMenuProvider;
import static com.rui.economystore.api.network.MenuProviderData.getRecyclingStoreMenuProvider;

public class EconomyScreen extends ContainerScreen<EconomyContainer> {
    private static final ResourceLocation ECONOMY_SCREEN = new ResourceLocation(LibMisc.MOD_ID, "textures/gui/economy_screen.png");
    private final PlayerEntity player;

    public EconomyScreen(PlayerEntity player) {
        this(new EconomyContainer(0, player.inventory), player.inventory, new TranslationTextComponent(""));
    }

    public EconomyScreen(EconomyContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
        this.player = p_i51105_2_.player;
    }

    @Override
    protected void renderBg(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(ECONOMY_SCREEN);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        this.blit(p_230450_1_, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(p_230430_1_);
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        this.renderTooltip(p_230430_1_, p_230430_2_, p_230430_3_);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        int color = 4210752;
        int initX = 20;
        int initY = 20;
        int centerX = 68;
        int marginY = 15;
        // 170 146

        this.font.draw(p_230430_1_, new TranslationTextComponent(LibMisc.MOD_ID + ".container.player"), x + initX, y + initY, color);
        this.font.draw(p_230430_1_, player.getDisplayName(), x + initX + centerX, y + initY, color);

        this.font.draw(p_230430_1_, new TranslationTextComponent(LibMisc.MOD_ID + ".container.money"), x + initX, y + initY + marginY, color);
        this.font.draw(p_230430_1_, LibDataToTextComponent.toComponent(DataHelper.getPlayerEconomy(player)), x + initX + centerX, y + initY + marginY, color);


        this.addButton(new Button(x + 20, y + 100, 136, 20, new TranslationTextComponent(LibMisc.MOD_ID + ".container.to_item_store"), (press) -> {
            PacketHandler.sendToServer(new OpenItemStoreContainerHandler());
        }));
        this.addButton(new Button(x + 20, y + 125, 136, 20, new TranslationTextComponent(LibMisc.MOD_ID + ".container.to_recycling_store"), (press) -> {
            PacketHandler.sendToServer(new OpenRecyclingStoreContainerHandler());
        }));
    }

    @Override
    protected void renderLabels(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
    }


}
