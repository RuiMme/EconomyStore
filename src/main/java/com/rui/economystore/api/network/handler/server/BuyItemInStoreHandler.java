package com.rui.economystore.api.network.handler.server;

import com.rui.economystore.api.network.DataHelper;
import com.rui.economystore.api.wrolddata.ResetItemStoreMultipleSavedData;
import com.rui.economystore.client.screens.stores.itemstore.ItemStoreContainer;
import com.rui.economystore.client.screens.stores.recyclingstore.RecyclingStoreContainer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class BuyItemInStoreHandler {
    private int item;
    public BuyItemInStoreHandler() {

    }
    public BuyItemInStoreHandler(int item) {
        this.item = item;
    }
    public static void encode(BuyItemInStoreHandler msg, PacketBuffer buf) {
        buf.writeVarInt(msg.item);
    }

    public static BuyItemInStoreHandler decode(PacketBuffer buf) {
        return new BuyItemInStoreHandler(buf.readVarInt());
    }

    public static void handle(BuyItemInStoreHandler msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity player = ctx.get().getSender();
                if (player != null) {
                    Container container = player.containerMenu;
                    if (container instanceof ItemStoreContainer) {
                        ItemStoreContainer itemStoreContainer = (ItemStoreContainer) container;
                        itemStoreContainer.buyItem(player, msg.getItem());
//                        System.out.println(ResetItemStoreMultipleSavedData.get(player.server).getMultiple());
//                        ItemStoreContainer.sellItemAndGetMoney(player);
                    }
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }

    public int getItem() {
        return item;
    }
}
