package com.rui.economystore.api.network.handler.server;

import com.rui.economystore.api.network.DataHelper;
import com.rui.economystore.api.network.PacketHandler;
import com.rui.economystore.api.network.messages.client.StatEconomySyncClientMessage;
import com.rui.economystore.api.network.messages.server.StatEconomySyncServerMessage;
import com.rui.economystore.api.stat.StatBase;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncServerHandler {
    public static void acceptEconomySyncServer(StatEconomySyncServerMessage message, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            ServerPlayerEntity player = ctx.getSender();
            if (player != null) {
                StatBase<?> stat = StatBase.stats.get(message.stat);
                DataHelper.setPlayerEconomy(player, stat, message.money);
                PacketHandler.sendToClient(player,new StatEconomySyncClientMessage(StatBase.stats.indexOf(stat), message.money));
            }
        });
        ctx.setPacketHandled(true);
    }
}
