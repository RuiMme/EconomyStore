package com.rui.economystore.api.network.handler.server;

import com.rui.economystore.api.network.MenuProviderData;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenItemStoreContainerHandler {
    public OpenItemStoreContainerHandler() {
    }

    public static void encode(OpenItemStoreContainerHandler msg, PacketBuffer buf) {
    }

    public static OpenItemStoreContainerHandler decode(PacketBuffer buf) {
        return new OpenItemStoreContainerHandler();
    }

    public static void handle(OpenItemStoreContainerHandler msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity player = ctx.get().getSender();
                if(player != null) {
                    player.openMenu(MenuProviderData.getItemStoreMenuProvider(player.level,player.blockPosition()));
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
