package com.rui.economystore.client.screens.stores.recyclingstore;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rui.economystore.api.network.DataHelper;
import com.rui.economystore.api.network.PacketHandler;
import com.rui.economystore.api.network.handler.server.SelectStoreItemHandler;
import com.rui.economystore.api.network.handler.server.SellItemInStoreHandler;
import com.rui.economystore.client.screens.economyscreen.EconomyScreen;
import com.rui.economystore.client.screens.stores.AbstractStoreScreen;
import com.rui.economystore.common.data.RecyclingStoreItem;
import com.rui.economystore.libs.LibDataToTextComponent;
import com.rui.economystore.libs.LibMisc;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class RecyclingStoreScreen extends AbstractStoreScreen<RecyclingStoreContainer> {
    public RecyclingStoreScreen(RecyclingStoreContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_, RecyclingStoreItem.itemMap);
    }

    private void postButtonClick() {
//        this.menu.tryMoveItems(this.shopItem);
        SelectStoreItemHandler handler = new SelectStoreItemHandler(this.shopItem);
        PacketHandler.sendToServer(handler);
    }

    protected void init() {
        super.init();
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        int k = j + 16 + 2;

        for (int l = 0; l < 7; ++l) {
            this.itemButtons[l] = this.addButton(new RecyclingStoreScreen.ItemButton(i + 5, k, l, (p_214132_1_) -> {
                if (p_214132_1_ instanceof AbstractStoreScreen.ItemButton) {
                    this.shopItem = ((AbstractStoreScreen.ItemButton) p_214132_1_).getIndex() + this.scrollOff;
                    this.postButtonClick();
                }
            }));
            k += 20;
        }
        this.addButton(new Button(i + 200, j + 35, 28, 20, new TranslationTextComponent(LibMisc.MOD_ID + ".container.sale"), (press) -> {
            this.menu.sellItemAndGetMoney(player);
            PacketHandler.sendToServer(new SellItemInStoreHandler());
        }));
        this.addButton(new Button(i + 258, j + 10, 10, 10, new TranslationTextComponent(LibMisc.MOD_ID + ".container.close"), (press) -> {
            if (this.minecraft != null) {
                this.minecraft.setScreen(new EconomyScreen(player));
            }
        }));
    }

    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(p_230430_1_);
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        if (!itemMap.isEmpty()) {
            int i = (this.width - this.imageWidth) / 2;
            int j = (this.height - this.imageHeight) / 2;
            int k = j + 16 + 1;
            int l = i + 5 + 5;
            RenderSystem.pushMatrix();
            RenderSystem.enableRescaleNormal();
            this.minecraft.getTextureManager().bind(STORE_SCREEN);
            this.renderScroller(p_230430_1_, i, j);
            int i1 = 0;
            int color = 16777215;

            this.font.draw(p_230430_1_, new TranslationTextComponent(LibMisc.MOD_ID+".container.player_money",DataHelper.getPlayerEconomy(player)),i+144,j+13, 4210752);

            for (Item item : itemMap.keySet()) {
                String itemString = itemMap.get(item).toString();
                int marginX = 0;
                TranslationTextComponent component = new TranslationTextComponent(itemString);
                if (!this.canScroll(itemMap.size()) || (i1 >= this.scrollOff && i1 < 7 + this.scrollOff)) {
                    ItemStack itemStack = item.getDefaultInstance();
                    this.itemRenderer.blitOffset = 100.0F;
                    int j1 = k + 2;
                    this.itemRenderer.renderAndDecorateFakeItem(itemStack, l, j1);
                    this.itemRenderer.renderGuiItemDecorations(this.font, itemStack, l, j1);

                    this.renderButtonArrows(p_230430_1_, i, j1);
                    if(itemString.length() > 3) {
                        marginX = itemString.length()-3;
                    }
                    this.font.draw(p_230430_1_, component, i + 77 - 6 * marginX, j1 + 5, color);
                    this.itemRenderer.blitOffset = 0.0F;
                    k += 20;
                }
                ++i1;
            }

            int k1 = this.shopItem;
            Item item = (Item) itemMap.keySet().toArray()[k1];

            for (AbstractStoreScreen.ItemButton storescreen$itembutton : this.itemButtons) {
                if (storescreen$itembutton.isHovered()) {
                    storescreen$itembutton.renderToolTip(p_230430_1_, p_230430_2_, p_230430_3_);
                }

                storescreen$itembutton.visible = storescreen$itembutton.getIndex() < itemMap.size();
            }

            RenderSystem.popMatrix();
            RenderSystem.enableDepthTest();
        }

        this.renderTooltip(p_230430_1_, p_230430_2_, p_230430_3_);
    }

    private void renderButtonArrows(MatrixStack p_238842_1_, int p_238842_3_, int p_238842_4_) {
        RenderSystem.enableBlend();
        this.minecraft.getTextureManager().bind(STORE_SCREEN);
        blit(p_238842_1_, p_238842_3_ + 5 + 35 + 20, p_238842_4_ + 3, this.getBlitOffset(), 15.0F, 171.0F, 10, 9, 256, 512);
    }
}
