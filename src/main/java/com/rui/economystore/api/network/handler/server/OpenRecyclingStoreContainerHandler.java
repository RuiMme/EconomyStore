package com.rui.economystore.api.network.handler.server;

import com.rui.economystore.api.network.MenuProviderData;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenRecyclingStoreContainerHandler {
    public OpenRecyclingStoreContainerHandler() {
    }

    public static void encode(OpenRecyclingStoreContainerHandler msg, PacketBuffer buf) {
    }

    public static OpenRecyclingStoreContainerHandler decode(PacketBuffer buf) {
        return new OpenRecyclingStoreContainerHandler();
    }

    public static void handle(OpenRecyclingStoreContainerHandler msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity player = ctx.get().getSender();
                if(player != null) {
                    player.openMenu(MenuProviderData.getRecyclingStoreMenuProvider(player.level,player.blockPosition()));
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
