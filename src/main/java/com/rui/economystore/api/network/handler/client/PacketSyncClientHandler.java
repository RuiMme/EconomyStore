package com.rui.economystore.api.network.handler.client;

import com.rui.economystore.api.network.messages.client.StatEconomySyncClientMessage;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static com.rui.economystore.api.network.handler.client.PacketSyncInternal.acceptEconomySyncInternal;

public class PacketSyncClientHandler {
    public static void acceptEconomySyncClient(StatEconomySyncClientMessage message, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> acceptEconomySyncInternal(message));
        });
        ctx.setPacketHandled(true);
    }
}
