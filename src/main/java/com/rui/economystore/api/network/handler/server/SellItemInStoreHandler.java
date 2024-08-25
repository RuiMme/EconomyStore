package com.rui.economystore.api.network.handler.server;

import com.rui.economystore.client.screens.stores.recyclingstore.RecyclingStoreContainer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SellItemInStoreHandler {

    public static void encode(SellItemInStoreHandler msg, PacketBuffer buf) {
    }

    public static SellItemInStoreHandler decode(PacketBuffer buf) {
        return new SellItemInStoreHandler();
    }

    public static void handle(SellItemInStoreHandler msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity player = ctx.get().getSender();
                if (player != null) {
                    Container container = player.containerMenu;
                    if (container instanceof RecyclingStoreContainer) {
                        RecyclingStoreContainer recyclingStoreContainer = (RecyclingStoreContainer) container;
                        recyclingStoreContainer.sellItemAndGetMoney(player);
                    }
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
