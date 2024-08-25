package com.rui.economystore.api.network;

import com.rui.economystore.client.screens.economyscreen.EconomyContainer;
import com.rui.economystore.client.screens.stores.itemstore.ItemStoreContainer;
import com.rui.economystore.client.screens.stores.recyclingstore.RecyclingStoreContainer;
import com.rui.economystore.libs.LibMisc;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class MenuProviderData {
    private static final ITextComponent ECONOMY_CONTAINER_TITLE = new TranslationTextComponent(LibMisc.MOD_ID+".container.economy_container");
    private static final ITextComponent RECYCLING_STORE_CONTAINER_TITLE = new TranslationTextComponent(LibMisc.MOD_ID+".container.recycling_store_container");
    private static final ITextComponent ITEM_STORE_CONTAINER_TITLE = new TranslationTextComponent(LibMisc.MOD_ID+".container.item_store_container");
    public static INamedContainerProvider getEconomyMenuProvider(World p_220052_2_, BlockPos p_220052_3_) {
        return new SimpleNamedContainerProvider((p_220270_2_, p_220270_3_, p_220270_4_) -> new EconomyContainer(p_220270_2_, p_220270_3_, IWorldPosCallable.create(p_220052_2_, p_220052_3_)), ECONOMY_CONTAINER_TITLE);
    }
    public static INamedContainerProvider getRecyclingStoreMenuProvider(World p_220052_2_, BlockPos p_220052_3_) {
        return new SimpleNamedContainerProvider((p_220270_2_, p_220270_3_, p_220270_4_) -> new RecyclingStoreContainer(p_220270_2_, p_220270_3_, IWorldPosCallable.create(p_220052_2_, p_220052_3_)), RECYCLING_STORE_CONTAINER_TITLE);
    }

    public static INamedContainerProvider getItemStoreMenuProvider(World p_220052_2_, BlockPos p_220052_3_) {
        return new SimpleNamedContainerProvider((p_220270_2_, p_220270_3_, p_220270_4_) -> new ItemStoreContainer(p_220270_2_, p_220270_3_, IWorldPosCallable.create(p_220052_2_, p_220052_3_)), ITEM_STORE_CONTAINER_TITLE);
    }
}
