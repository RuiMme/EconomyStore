package com.rui.economystore.api.network.handler.server;

import com.rui.economystore.client.screens.stores.recyclingstore.RecyclingStoreContainer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SelectStoreItemHandler {
    private int item;

    public SelectStoreItemHandler() {
    }

    public SelectStoreItemHandler(int p_i49545_1_) {
        this.item = p_i49545_1_;
    }

    public static void encode(SelectStoreItemHandler msg, PacketBuffer buf) {
        buf.writeVarInt(msg.item);
    }

    public static SelectStoreItemHandler decode(PacketBuffer buf) {
        return new SelectStoreItemHandler(buf.readVarInt());
    }

    public static void handle(SelectStoreItemHandler msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity player = ctx.get().getSender();
                if (player != null) {
                    Container container = player.containerMenu;
                    if (container instanceof RecyclingStoreContainer) {
                        RecyclingStoreContainer recyclingStoreContainer = (RecyclingStoreContainer) container;
                        recyclingStoreContainer.tryMoveItems(msg.getItem());
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
