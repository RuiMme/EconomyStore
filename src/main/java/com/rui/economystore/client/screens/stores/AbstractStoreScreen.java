package com.rui.economystore.client.screens.stores;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rui.economystore.libs.LibMisc;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;

public abstract class AbstractStoreScreen<T extends AbstractStoreContainer> extends ContainerScreen<T> {
    protected static final ResourceLocation STORE_SCREEN = new ResourceLocation(LibMisc.MOD_ID, "textures/gui/store_screen.png");
    protected final PlayerEntity player;
    protected int scrollOff;
    protected final AbstractStoreScreen.ItemButton[] itemButtons = new AbstractStoreScreen.ItemButton[7];
    protected int shopItem;
    protected boolean isDragging;
    protected final HashMap<Item, Float> itemMap;
    public AbstractStoreScreen(T p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_, HashMap<Item, Float> itemMap) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
        this.player = p_i51105_2_.player;
        this.itemMap = itemMap;
        this.imageWidth = 276;
        this.inventoryLabelX = 107;
    }

    @Override
    protected void renderBg(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(STORE_SCREEN);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        blit(p_230450_1_, i, j, this.getBlitOffset(), 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 512);
    }

    protected void renderLabels(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
        this.font.draw(p_230451_1_, this.inventory.getDisplayName(), (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);
    }

    protected void renderScroller(MatrixStack p_238840_1_, int p_238840_2_, int p_238840_3_) {
        int i = itemMap.size() + 1 - 7;
        if (i > 1) {
            int j = 139 - (27 + (i - 1) * 139 / i);
            int k = 1 + j / i + 139 / i;
            int l = 113;
            int i1 = Math.min(113, this.scrollOff * k);
            if (this.scrollOff == i - 1) {
                i1 = 113;
            }

            blit(p_238840_1_, p_238840_2_ + 94, p_238840_3_ + 18 + i1, this.getBlitOffset(), 0.0F, 199.0F, 6, 27, 256, 512);
        } else {
            blit(p_238840_1_, p_238840_2_ + 94, p_238840_3_ + 18, this.getBlitOffset(), 6.0F, 199.0F, 6, 27, 256, 512);
        }
    }

    protected boolean canScroll(int p_214135_1_) {
        return p_214135_1_ > 7;
    }

    public boolean mouseScrolled(double p_231043_1_, double p_231043_3_, double p_231043_5_) {
        int i = itemMap.size();
        if (this.canScroll(i)) {
            int j = i - 7;
            this.scrollOff = (int) ((double) this.scrollOff - p_231043_5_);
            this.scrollOff = MathHelper.clamp(this.scrollOff, 0, j);
        }

        return true;
    }

    public boolean mouseDragged(double p_231045_1_, double p_231045_3_, int p_231045_5_, double p_231045_6_, double p_231045_8_) {
        int i = itemMap.size();
        if (this.isDragging) {
            int j = this.topPos + 18;
            int k = j + 139;
            int l = i - 7;
            float f = ((float)p_231045_3_ - (float)j - 13.5F) / ((float)(k - j) - 27.0F);
            f = f * (float)l + 0.5F;
            this.scrollOff = MathHelper.clamp((int)f, 0, l);
            return true;
        } else {
            return super.mouseDragged(p_231045_1_, p_231045_3_, p_231045_5_, p_231045_6_, p_231045_8_);
        }
    }

    public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
        this.isDragging = false;
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        if (this.canScroll(itemMap.size()) && p_231044_1_ > (double)(i + 94) && p_231044_1_ < (double)(i + 94 + 6) && p_231044_3_ > (double)(j + 18) && p_231044_3_ <= (double)(j + 18 + 139 + 1)) {
            this.isDragging = true;
        }

        return super.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_);
    }

    @OnlyIn(Dist.CLIENT)
    protected class ItemButton extends Button {
        final int index;

        public ItemButton(int p_i50601_2_, int p_i50601_3_, int p_i50601_4_, Button.IPressable p_i50601_5_) {
            super(p_i50601_2_, p_i50601_3_, 89, 20, StringTextComponent.EMPTY, p_i50601_5_);
            this.index = p_i50601_4_;
            this.visible = false;
        }

        public int getIndex() {
            return this.index;
        }

        public void renderToolTip(MatrixStack p_230443_1_, int p_230443_2_, int p_230443_3_) {
            if (this.isHovered && itemMap.size() > this.index + AbstractStoreScreen.this.scrollOff) {
                Item item = (Item) itemMap.keySet().toArray()[this.index + AbstractStoreScreen.this.scrollOff];
                if (p_230443_2_ < this.x + 20) {
                    ItemStack itemstack = item.getDefaultInstance();
                    AbstractStoreScreen.this.renderTooltip(p_230443_1_, itemstack, p_230443_2_, p_230443_3_);
                }
            }
        }
    }
}
